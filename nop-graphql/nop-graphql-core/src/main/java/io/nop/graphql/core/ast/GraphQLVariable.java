/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.graphql.core.ast;

import io.nop.api.core.util.Guard;
import io.nop.api.core.util.SourceLocation;
import io.nop.graphql.core.ast._gen._GraphQLVariable;

import java.util.Map;

public class GraphQLVariable extends _GraphQLVariable {
    public static GraphQLVariable valueOf(SourceLocation loc, String name) {
        Guard.notEmpty(name, "name is empty");
        GraphQLVariable node = new GraphQLVariable();
        node.setLocation(loc);
        node.setName(name);
        return node;
    }

    @Override
    public Object buildValue(Map<String, Object> vars) {
        return vars.get(getName());
    }

    @Override
    public boolean containsVariable() {
        return true;
    }
}