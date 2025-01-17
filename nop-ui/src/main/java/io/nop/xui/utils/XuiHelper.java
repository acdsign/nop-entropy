/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.xui.utils;

import io.nop.api.core.beans.FieldSelectionBean;
import io.nop.api.core.exceptions.NopException;
import io.nop.api.core.util.ProcessResult;
import io.nop.commons.util.StringHelper;
import io.nop.core.resource.ResourceHelper;
import io.nop.core.type.IGenericType;
import io.nop.xlang.xmeta.IObjMeta;
import io.nop.xlang.xmeta.IObjPropMeta;
import io.nop.xlang.xmeta.ISchema;
import io.nop.xlang.xpl.IXplTag;
import io.nop.xlang.xpl.IXplTagLib;
import io.nop.xui.XuiConstants;
import io.nop.xui.model.IUiDisplayMeta;
import io.nop.xui.model.UiFormCellModel;
import io.nop.xui.model.UiFormModel;
import io.nop.xui.model.UiGridModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashSet;
import java.util.Set;

import static io.nop.xui.XuiConstants.DATA_TYPE_ANY;
import static io.nop.xui.XuiConstants.EXT_KIND;
import static io.nop.xui.XuiConstants.KIND_TO_MANY;
import static io.nop.xui.XuiConstants.KIND_TO_ONE;
import static io.nop.xui.XuiConstants.MODE_EDIT;
import static io.nop.xui.XuiConstants.MODE_LIST_EDIT;
import static io.nop.xui.XuiConstants.MODE_LIST_VIEW;
import static io.nop.xui.XuiConstants.MODE_VIEW;
import static io.nop.xui.XuiErrors.ARG_PROP_NAME;
import static io.nop.xui.XuiErrors.ARG_RELATION_NAME;
import static io.nop.xui.XuiErrors.ERR_XUI_INVALID_EXT_RELATION;

public class XuiHelper {
    static final Logger LOG = LoggerFactory.getLogger(XuiHelper.class);

    /**
     * 根据数据类型和当前模式，从控件库中选择合适的控件。例如 double-edit对应于数据类型double，模式edit。
     */
    public static IXplTag getControlTag(IXplTagLib lib, IUiDisplayMeta dispMeta, IObjPropMeta propMeta,
                                        IObjMeta objMeta, String editMode) {
        String control = null;
        String domain = null;
        String stdDomain = null;
        String stdDataType = null;

        if (dispMeta != null) {
            control = dispMeta.getControl();
            domain = dispMeta.getDomain();
            stdDomain = dispMeta.getStdDomain();

            // 单个字段显示上指定的mode将取代表单上设置的mode
            if (dispMeta.getEditMode() != null) {
                editMode = dispMeta.getEditMode();
            }
        }

        if (control == null && propMeta != null)
            control = (String) propMeta.prop_get(XuiConstants.EXT_CONTROL);

        ISchema schema = propMeta == null ? null : propMeta.getSchema();
        if (schema != null) {
            if (domain == null)
                domain = schema.getDomain();

            // 如果设置了字典表，则忽略stdDomain和stdDataType的设置
            if (schema.getDict() != null) {
                stdDomain = XuiConstants.DATA_TYPE_ENUM;
            } else {
                if (stdDomain == null)
                    stdDomain = schema.getStdDomain();
                if (schema.getStdDataType() != null)
                    stdDataType = schema.getStdDataType().getName();
            }
        }

        IObjPropMeta relProp = getRelationProp(propMeta, objMeta);
        String relKind = relProp != null ? (String) relProp.prop_get(EXT_KIND) : null;
        IXplTag tag = getControlTag(lib, control, domain, stdDomain, relKind, stdDataType, editMode);
        if (tag == null) {
            tag = tryGetControl(lib, DATA_TYPE_ANY, MODE_VIEW);
        }

        String prop = propMeta != null ? propMeta.getName() : (dispMeta == null ? null : dispMeta.getId());
        if(editMode != null && !editMode.endsWith(MODE_VIEW) && tag != null && tag.getTagName().equals("view-any")){
            LOG.warn("nop.xui.no-control-defined-for-prop:controlTag={},prop={},domain={},stdDomain={},stdDataType={},mode={}",
                    tag == null ? null : tag.getTagName(), prop, domain, stdDomain, stdDataType, editMode);
        }else {
            LOG.debug("nop.xui.resolve-control-tag:controlTag={},prop={},domain={},stdDomain={},stdDataType={},mode={}",
                    tag == null ? null : tag.getTagName(), prop, domain, stdDomain, stdDataType, editMode);
        }

        return tag;
    }

    /**
     * 根据字段的类型以及编辑模式在标签库中查找合适的控件
     */
    public static IXplTag getControlTag(IXplTagLib lib, String component, String domain, String stdDomain,
                                        String relKind, String stdDataType, String editMode) {
        if (StringHelper.isEmpty(editMode))
            editMode = MODE_VIEW;

        IXplTag tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, editMode);
        if (tag != null)
            return tag;

        // 如果不是view模式，则尝试统一使用edit模式。例如，如果没有query模式下的控件，则我们仍然可以使用一般的edit模式下的控件来输入查询条件
        if (editMode.startsWith(XuiConstants.MODE_LIST_PREFIX)) {
            if (MODE_LIST_VIEW.startsWith(editMode)) {
                tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, MODE_VIEW);
            } else {
                tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, MODE_LIST_EDIT);
                if (tag == null)
                    tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, MODE_EDIT);
            }
        } else {
            if (MODE_VIEW.startsWith(editMode)) {
                if (!MODE_VIEW.equals(editMode)) {
                    tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, MODE_VIEW);
                }
            } else if (!MODE_EDIT.equals(editMode)) {
                tag = _getControlTag(lib, component, domain, stdDomain, relKind, stdDataType, MODE_EDIT);
            }
        }
        return tag;
    }

    private static IXplTag _getControlTag(IXplTagLib lib, String component, String domain, String stdDomain,
                                          String relKind, String stdDataType, String mode) {
        IXplTag tag = tryGetControl(lib, component, mode);
        if (tag != null)
            return tag;

        tag = tryGetControl(lib, domain, mode);
        if (tag != null)
            return tag;

        if (domain != null) {
            int pos = domain.indexOf('-');
            if (pos > 0) {
                String baseDomain = domain.substring(0, pos);
                tag = tryGetControl(lib, baseDomain, mode);
                if (tag != null)
                    return tag;
            }
        }

        tag = tryGetControl(lib, stdDomain, mode);
        if (tag != null)
            return tag;

        tag = tryGetControl(lib, relKind, mode);
        if (tag != null)
            return tag;

        tag = tryGetControl(lib, stdDataType, mode);
        if (tag != null)
            return tag;
        return null;
    }

    private static IXplTag tryGetControl(IXplTagLib lib, String type, String mode) {
        if (StringHelper.isEmpty(type))
            return null;
        IXplTag tag = lib.getTag(mode + '-' + type);
        return tag;
    }

    public static String getListSelection(UiGridModel gridModel, IObjMeta objMeta) {
        if (gridModel == null)
            return null;
        FieldSelectionBean selection = new XuiViewAnalyzer().getListSelection(gridModel, objMeta);
        return selection == null ? null : selection.toString(false);
    }

    public static String getFormSelection(UiFormModel formModel, IObjMeta objMeta) {
        if (formModel == null)
            return null;
        FieldSelectionBean selection = new XuiViewAnalyzer().getFormSelection(formModel, objMeta);
        return selection == null ? null : selection.toString(false);
    }

    public static Set<String> getFormProps(UiFormModel formModel, IObjMeta objMeta) {
        Set<String> propNames = new LinkedHashSet<>();
        if (formModel == null)
            return propNames;
        formModel.getTables().forEach(table -> {
            table.forEachRealCell((cell, ri, ci) -> {
                UiFormCellModel cellModel = formModel.getCell(cell.getId());
                String prop = cell.getId();
                if (cellModel != null) {
                    if (cellModel.getProp() != null) {
                        prop = cellModel.getProp();
                    }
                }
                if (objMeta.hasProp(prop)) {
                    propNames.add(prop);
                }
                return ProcessResult.CONTINUE;
            });
        });
        return propNames;
    }

    /**
     * 得到原子字段column所对应的关联属性。关联属性上标注了ext:kind="to-one"或者ext:kind="to-many"
     *
     * @param propMeta 数据库列所对应的属性或者关联属性
     * @param objMeta  对象meta
     * @return 关联属性。
     */
    public static IObjPropMeta getRelationProp(IObjPropMeta propMeta, IObjMeta objMeta) {
        if (propMeta == null)
            return null;
        String relation = (String) propMeta.prop_get(XuiConstants.EXT_RELATION);
        if (!StringHelper.isEmpty(relation)) {
            IObjPropMeta relProp = objMeta.getProp(relation);
            if (relProp == null)
                throw new NopException(ERR_XUI_INVALID_EXT_RELATION).source(propMeta)
                        .param(ARG_PROP_NAME, propMeta.getName()).param(ARG_RELATION_NAME, relation);

            return relProp;
        } else {
            if (isRelationProp(propMeta))
                return propMeta;
        }
        return null;
    }

    /**
     * 得到关联实体所对应的业务对象名
     */
    public static String getRefBizObjName(IObjPropMeta propMeta) {
        if (!isRelationProp(propMeta))
            return null;

        ISchema schema = propMeta.getSchema();
        if (schema != null) {
            String bizObjName = schema.getBizObjName();
            if (bizObjName != null)
                return bizObjName;

            IGenericType type = schema.getType();
            if (type != null) {
                if (type.isCollectionLike())
                    type = type.getComponentType();
                return StringHelper.lastPart(type.getClassName(), '.');
            }
        }
        return null;
    }

    static boolean isRelationProp(IObjPropMeta propMeta) {
        String kind = (String) propMeta.prop_get(EXT_KIND);
        if (StringHelper.isEmpty(kind))
            return false;
        return KIND_TO_ONE.equals(kind) || KIND_TO_MANY.equals(kind);
    }

    public static String getRelationPickerUrl(IObjPropMeta propMeta, IObjMeta objMeta) {
        IObjPropMeta relProp = getRelationProp(propMeta, objMeta);
        if (relProp == null)
            return null;
        String bizObjName = getRefBizObjName(relProp);
        String moduleId = ResourceHelper.getModuleId(objMeta.resourcePath());
        return '/' + moduleId + "/pages/" + bizObjName + "/picker.page.yaml";
    }
}