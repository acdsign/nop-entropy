/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.wf.core.engine;

import io.nop.commons.util.CollectionHelper;
import io.nop.commons.util.StringHelper;
import io.nop.wf.core.WfConstants;
import io.nop.wf.api.actor.IWfActor;
import io.nop.wf.api.actor.IWfActorResolver;
import io.nop.wf.api.actor.WfActorCandidatesBean;
import io.nop.wf.api.actor.WfAssignmentSelection;
import io.nop.wf.core.model.WfAssignmentActorModel;
import io.nop.wf.core.model.WfAssignmentModel;
import io.nop.xlang.api.XLang;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static io.nop.wf.core.WfErrors.ARG_ACTOR_CANDIDATES;
import static io.nop.wf.core.WfErrors.ARG_VALUE;
import static io.nop.wf.core.WfErrors.ARG_WF_ACTOR_ID;
import static io.nop.wf.core.WfErrors.ARG_WF_ACTOR_TYPE;
import static io.nop.wf.core.WfErrors.ERR_WF_ASSIGNMENT_DYNAMIC_RETURN_NOT_WF_ACTOR;
import static io.nop.wf.core.WfErrors.ERR_WF_ASSIGNMENT_OWNER_EXPR_RESULT_NOT_WF_ACTOR;
import static io.nop.wf.core.WfErrors.ERR_WF_SELECTED_ACTOR_COUNT_NOT_ONE;
import static io.nop.wf.core.WfErrors.ERR_WF_SELECTED_ACTOR_NOT_IN_ASSIGNMENT;

public class WfActorAssignSupport {
    private IWfActorResolver wfActorResolver;

    @Inject
    public void setWfActorResolver(IWfActorResolver wfActorResolver) {
        this.wfActorResolver = wfActorResolver;
    }

    public IWfActor resolveActor(String actorType, String actorId, String deptId) {
        return wfActorResolver.resolveActor(actorType, actorId, deptId);
    }

    public IWfActor resolveUser(String userId) {
        return wfActorResolver.resolveUser(userId);
    }

    protected List<IWfActor> getAssignmentActors(WfAssignmentModel assignment, WfRuntime wfRt) {
        if (assignment == null || assignment.getActors() == null || assignment.getActors().isEmpty())
            return Collections.emptyList();

        List<IWfActor> ret = new ArrayList<>(assignment.getActors().size());
        Set<String> actorKeys = new HashSet<>();
        for (WfAssignmentActorModel item : assignment.getActors()) {
            if (item.isDynamic()) {
                // 动态计算的结果可能是actor或者actor的列表
                List<IWfActor> dynamicActors = getDynamicActors(item, wfRt);
                for (IWfActor actor : dynamicActors) {
                    addActor(ret, actorKeys, actor, item.isAssignForUser());
                }
            } else {
                IWfActor actor = resolveActor(item.getType(), item.getActorId(), item.getDeptId());
                addActor(ret, actorKeys, actor, item.isAssignForUser());
            }
        }

        wfRt.setCurrentActors(ret);

        if (!ret.isEmpty()) {
            if (assignment.getSelectExpr() != null) {
                assignment.getSelectExpr().invoke(wfRt);
            }
        }
        return wfRt.getCurrentActors();
    }

    private List<IWfActor> getDynamicActors(WfAssignmentActorModel actorModel, WfRuntime wfRt) {
        String tagName = StringHelper.removeHead(actorModel.getType(), WfConstants.WF_ACTOR_NS_PREFIX);
        wfRt.setValue(WfConstants.VAR_WF_ACTOR_MODEL, actorModel);
        Object value = XLang.getTagAction(WfConstants.WF_ACTOR_LIB_PATH, tagName).invoke(wfRt);
        if (value == null)
            return Collections.emptyList();

        if (value instanceof IWfActor) {
            return Collections.singletonList((IWfActor) value);
        } else if (value instanceof Collection) {
            for (Object o : (Collection<?>) value) {
                if (!(o instanceof IWfActor)) {
                    throw wfRt.newError(ERR_WF_ASSIGNMENT_DYNAMIC_RETURN_NOT_WF_ACTOR)
                            .source(actorModel).param(ARG_WF_ACTOR_TYPE, actorModel.getType());
                }
            }
            return CollectionHelper.toList(value);
        } else {
            throw wfRt.newError(ERR_WF_ASSIGNMENT_DYNAMIC_RETURN_NOT_WF_ACTOR)
                    .source(actorModel).param(ARG_WF_ACTOR_TYPE, actorModel.getType());
        }
    }

    private void addActor(Collection<IWfActor> actors, Set<String> actorKeys, IWfActor actor, boolean forUser) {
        if (forUser) {
            List<? extends IWfActor> users = actor.getUsers();
            if (users != null) {
                for (IWfActor user : users) {
                    if (actorKeys.add(user.getActorKey())) {
                        actors.add(actor);
                    }
                }
            }
        } else {
            if (actorKeys.add(actor.getActorKey()))
                actors.add(actor);
        }
    }

    protected List<IWfActor> getActors(WfAssignmentModel assignment, String targetStep, WfRuntime wfRt) {
        if (assignment == null) {
            return wfRt.getSelectedActors(targetStep);
        }

        // 如果不需要前台选择，则直接返回后台配置的actor
        WfAssignmentSelection selection = assignment.getSelection();
        if (selection == null || selection == WfAssignmentSelection.auto) {
            return getAssignmentActors(assignment, wfRt);
        }

        // 下面的情况都要求使用前台传入的selectedActors,
        // 根据selectInAssignment参数设置决定是否要检查前台传入actor的合法性
        List<IWfActor> selectedActors = wfRt.getSelectedActors(targetStep);

        // 如果没有要求必须在assignment范围内选择，则直接使用selectedActors
        if (!assignment.isSelectInAssignment()) {
            return selectedActors;
        }

        if (CollectionHelper.isEmpty(selectedActors))
            return Collections.emptyList();


        WfActorCandidatesBean candidates = this.getActorCandidates(assignment, wfRt);

        switch (assignment.getSelection()) {
            case multiple: {
                for (IWfActor actor : selectedActors) {
                    if (!candidates.containsSelectedActor(actor))
                        throw wfRt.newError(ERR_WF_SELECTED_ACTOR_NOT_IN_ASSIGNMENT)
                                .source(assignment)
                                .param(ARG_WF_ACTOR_TYPE, actor.getType())
                                .param(ARG_WF_ACTOR_ID, actor.getActorId())
                                .param(ARG_ACTOR_CANDIDATES, candidates);
                }
                return selectedActors;
            }
            case single: {
                if (selectedActors.size() > 1)
                    throw wfRt.newError(ERR_WF_SELECTED_ACTOR_COUNT_NOT_ONE).source(assignment);

                IWfActor actor = selectedActors.get(0);
                if (!candidates.containsSelectedActor(actor))
                    throw wfRt.newError(ERR_WF_SELECTED_ACTOR_NOT_IN_ASSIGNMENT)
                            .source(assignment)
                            .param(ARG_WF_ACTOR_TYPE, actor.getType())
                            .param(ARG_WF_ACTOR_ID, actor.getActorId())
                            .param(ARG_ACTOR_CANDIDATES, candidates);

                return selectedActors;
            }
            default:
                throw new UnsupportedOperationException("nop.err.wf.unsupported-assignment-selection");
        }
    }

    protected WfActorCandidatesBean getActorCandidates(WfAssignmentModel assignment, WfRuntime wfRt) {
        if (assignment == null || assignment.getActors() == null || assignment.getActors().isEmpty())
            return new WfActorCandidatesBean();

        WfActorCandidatesBean ret = new WfActorCandidatesBean();
        ret.setSelection(assignment.getSelection());

        for (WfAssignmentActorModel item : assignment.getActors()) {
            if (item.isDynamic()) {
                // 动态计算的结果可能是actor或者actor的列表
                List<IWfActor> dynamicActors = getDynamicActors(item, wfRt);
                for (IWfActor actor : dynamicActors) {
                    ret.addActorCandidate(actor, item.isSelectUser(), item.isAssignForUser());
                }
            } else {
                IWfActor actor = resolveActor(item.getType(), item.getActorId(), item.getDeptId());
                ret.addActorCandidate(actor, item.isSelectUser(), item.isAssignForUser());
            }
        }
        return ret;
    }

    protected IWfActor getOwner(WfAssignmentModel assignment, IWfActor actor, WfRuntime wfRt) {
        if (assignment == null || assignment.getDefaultOwnerExpr() == null)
            return null;

        if (actor.getType().equals(IWfActor.ACTOR_TYPE_USER))
            return actor;

        Object value = assignment.getDefaultOwnerExpr().invoke(wfRt);
        if (value == null)
            return null;

        if (!(value instanceof IWfActor))
            throw wfRt.newError(ERR_WF_ASSIGNMENT_OWNER_EXPR_RESULT_NOT_WF_ACTOR).param(ARG_VALUE, value);

        return (IWfActor) value;
    }

}