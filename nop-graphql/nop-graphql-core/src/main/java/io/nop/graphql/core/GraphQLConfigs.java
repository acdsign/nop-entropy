/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.graphql.core;

import io.nop.api.core.annotations.core.Description;
import io.nop.api.core.annotations.core.Locale;
import io.nop.api.core.config.IConfigReference;

import java.time.Duration;

import static io.nop.api.core.config.AppConfig.varRef;

@Locale("zh-CN")
public interface GraphQLConfigs {
    @Description("GraphQL查询语句的解析缓存的超时时间")
    IConfigReference<Duration> CFG_GRAPHQL_QUERY_PARSE_CACHE_TIMEOUT = varRef("nop.graphql.query.parse-cache-timeout",
            Duration.class, null);

    @Description("GraphQL查询语句的解析缓存的大小")
    IConfigReference<Integer> CFG_GRAPHQL_QUERY_PARSE_CACHE_SIZE = varRef("nop.graphql.query.parse-cache-size",
            Integer.class, 2000);

    @Description("GraphQL查询语句的最大文本大小")
    IConfigReference<Integer> CFG_GRAPHQL_QUERY_PARSE_MAX_LENGTH = varRef("nop.graphql.query.parse-max-length",
            Integer.class, 4096);

    @Description("GraphQL查询允许的最大嵌套深度")
    IConfigReference<Integer> CFG_GRAPHQL_QUERY_MAX_DEPTH = varRef("nop.graphql.query.max-depth", Integer.class, 7);

    @Description("GraphQL引擎启用Maker Checker机制，在执行变更操作之前会先尝试执行对应的tryAction")
    IConfigReference<Boolean> CFG_GRAPHQL_MAKER_CHECKER_ENABLED = varRef("nop.graphql.maker-checker.enabled",
            Boolean.class, false);

    @Description("GraphQL允许返回的最多的数据行数")
    IConfigReference<Integer> CFG_GRAPHQL_MAX_PAGE_SIZE = varRef("nop.graphql.max-page-size", Integer.class, 1000);

    @Description("GraphQL缺省的分页行数")
    IConfigReference<Integer> CFG_GRAPHQL_DEFAULT_PAGE_SIZE = varRef("nop.graphql.default-page-size", Integer.class,
            10);

    @Description("是否启用GraphQL内置的内省功能来获取对象定义")
    IConfigReference<Boolean> CFG_GRAPHQL_SCHEMA_INTROSPECTION_ENABLED = varRef(
            "nop.graphql.schema-introspection.enabled", Boolean.class, false);

    @Description("GraphQL引擎内置加载的定义文件")
    IConfigReference<String> CFG_GRAPHQL_BUILTIN_SCHEMA_PATHS = varRef("nop.graphql.builtin-schema-path", String.class,
            null);

    @Description("系统启动时主动初始化所有BizObject对象")
    IConfigReference<Boolean> CFG_GRAPHQL_EAGER_INIT_BIZ_OBJECT = varRef("nop.graphql.eager-init-biz-object",
            Boolean.class, true);
}