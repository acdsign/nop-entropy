/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.sys.service.entity;

import io.nop.api.core.annotations.biz.BizModel;
import io.nop.biz.crud.CrudBizModel;

import io.nop.sys.dao.entity.NopSysCodeRule;

@BizModel("NopSysCodeRule")
public class NopSysCodeRuleBizModel extends CrudBizModel<NopSysCodeRule>{
    public NopSysCodeRuleBizModel(){
        setEntityName(NopSysCodeRule.class.getName());
    }
}