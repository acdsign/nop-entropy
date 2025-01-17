/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.dataset.record;

import java.util.Collection;
import java.util.function.BiConsumer;

/**
 * 按照规则将一个记录拆分成多个记录
 *
 * @param <T>
 */
public interface IRecordSplitter<T, R> {

    default void splitMulti(Collection<? extends T> data, BiConsumer<String, R> collector) {
        for (T record : data) {
            split(record, collector);
        }
    }

    void split(T record, BiConsumer<String, R> collector);
}