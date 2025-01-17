/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.orm.sql_lib;

import io.nop.commons.text.marker.IMarkedString;
import io.nop.core.context.IEvalContext;
import io.nop.orm.sql_lib._gen._EqlSqlItemModel;

public class EqlSqlItemModel extends _EqlSqlItemModel {
    public EqlSqlItemModel() {

    }

    @Override
    protected IMarkedString generateSql(IEvalContext context) {
        return getSource().generateSql(context);
    }
}
