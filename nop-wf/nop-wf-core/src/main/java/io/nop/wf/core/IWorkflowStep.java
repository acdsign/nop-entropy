/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.wf.core;

import io.nop.core.context.IServiceContext;
import io.nop.wf.api.WfStepReference;
import io.nop.wf.api.actor.IWfActor;
import io.nop.wf.core.model.IWorkflowActionModel;
import io.nop.wf.core.model.IWorkflowStepModel;
import io.nop.wf.core.model.WfStepType;
import io.nop.wf.core.store.IWorkflowStepRecord;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public interface IWorkflowStep extends Comparable<IWorkflowStep> {
    IWorkflowStepModel getModel();

    IWorkflowStepRecord getRecord();

    IWorkflow getWorkflow();

    default WfStepReference getStepReference() {
        return new WfStepReference(getWfName(), getWfVersion(), getWfId(), getStepId());
    }

    default String getWfName() {
        return getWorkflow().getWfName();
    }

    default String getWfVersion() {
        return getWorkflow().getWfVersion();
    }

    default String getWfId() {
        return getWorkflow().getWfId();
    }

    /**
     * 工作流步骤实例id, 等价于getRecord().getStepId()。
     *
     * @return
     */
    default String getStepId() {
        return getRecord().getStepId();
    }

    String getStepName();

    default boolean isInternal() {
        return getModel().isInternal();
    }

    default boolean isIndependent() {
        return getModel().isIndependent();
    }

    default int getStepStatus() {
        return getRecord().getStatus();
    }

    default boolean isHistory() {
        return getStepStatus() >= WfConstants.ACTIVITY_STATUS_HISTORY_BOUND;
    }

    default boolean isWaiting() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_WAITING;
    }

    default boolean isActivated() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_ACTIVATED;
    }

    default boolean isSuspended() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_SUSPENDED;
    }

    default boolean isKilled() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_KILLED;
    }

    default boolean isWithdrawn() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_WITHDRAWN;
    }

    default boolean isReject() {
        return getStepStatus() == WfConstants.ACTIVITY_STATUS_REJECT;
    }

    default boolean isJoinType() {
        return getModel().getType() == WfStepType.join;
    }

    default boolean isStepType() {
        return getModel().getType() == WfStepType.step;
    }

    default boolean isFlowType() {
        return getModel().getType() == WfStepType.flow;
    }

    IWfActor getActor();

    IWfActor getOwner();

    IWfActor getCaller();

    IWfActor getAssigner();

    void changeActor(IWfActor actor, IServiceContext ctx);

    void changeOwnerId(String ownerId, IServiceContext ctx);

    @Nonnull
    List<? extends IWorkflowStep> getPrevSteps();

    @Nonnull
    List<? extends IWorkflowStep> getNextSteps();

    void markRead(IServiceContext ctx);

    void kill(Map<String, Object> args, IServiceContext ctx);

    void triggerChange(IServiceContext ctx);

    /**
     * 目前在本步骤允许执行的action
     *
     * @param ctx
     * @return
     */
    @Nonnull
    List<? extends IWorkflowActionModel> getAllowedActions(IServiceContext ctx);

    /**
     * 如果不允许执行此action, 则抛出异常
     *
     * @param ctx
     */
    Object invokeAction(String actionName, Map<String, Object> args, IServiceContext ctx);

    @Nonnull
    List<WorkflowTransitionTarget> getTransitionTargetsForAction(String actionName, IServiceContext ctx);


    /**
     * 转移到指定步骤。转移到指定步骤。如果本步骤尚未结束，则先结束本步骤。如果本步骤已结束，则直接增加目标步骤实例
     */
    void transitTo(String stepName, Map<String, Object> args, IServiceContext ctx);


    /**
     * 对于join步骤，这里返回join正在等待的处于运行状态的步骤集合
     *
     * @return
     */
    @Nonnull
    List<? extends IWorkflowStep> getJoinWaitSteps();

    /**
     * 找到具有指定类型的，最近时刻的前导步骤实例
     *
     * @param stepName
     * @return
     */
    IWorkflowStep getPrevStepByName(String stepName);

    /**
     * 找到具有指定类型的，最近时刻的后续步骤实例
     *
     * @param stepName
     * @return
     */
    IWorkflowStep getNextStepByName(String stepName);

    /**
     * 发送给指定actor的，并且具有指定类型的步骤实例
     *
     * @param stepName
     * @param actor
     * @return
     */
    IWorkflowStep getNextStepByName(String stepName, IWfActor actor);

    @Nonnull
    List<? extends IWorkflowStep> getPrevNormalStepsInTree();

    @Nonnull
    List<? extends IWorkflowStep> getNextNormalStepsInTree();

    @Nonnull
    List<? extends IWorkflowStep> getPrevStepsInTree();

    @Nonnull
    List<? extends IWorkflowStep> getNextStepsInTree();


    /**
     * 具有同样步骤name的其他步骤实例
     *
     * @return
     */
    @Nonnull
    List<? extends IWorkflowStep> getOtherStepsWithSameName();
}
