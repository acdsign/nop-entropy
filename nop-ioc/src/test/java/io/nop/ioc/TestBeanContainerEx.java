/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.ioc;

import io.nop.api.core.ioc.IBeanContainer;
import io.nop.core.initialize.CoreInitialization;
import io.nop.core.unittest.BaseTestCase;
import io.nop.ioc.loader.AppBeanContainerLoader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import test.io.entropy.beans.MyBeanA;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestBeanContainerEx extends BaseTestCase {
    @BeforeAll
    public static void init() {
        CoreInitialization.initialize();
    }

    @AfterAll
    public static void destroy() {
        CoreInitialization.destroy();
    }

    @Test
    public void testEmbedded() {
        IBeanContainer container = new AppBeanContainerLoader().loadFromResource("test",
                attachmentResource("test_embedded.beans.xml"));
        container.start();
        MyBeanA a = container.getBeanByType(MyBeanA.class);
        assertNotNull(a.getB().getC());
        container.stop();
    }

    @Test
    public void autowireBeanInParentContainer() {
        setTestConfig("test.path", "aa");
        IBeanContainer parent = new AppBeanContainerLoader().loadFromResource("test",
                attachmentResource("test_spring.beans.xml"));
        parent.start();

        IBeanContainer container = new AppBeanContainerLoader().loadFromResource("test",
                attachmentResource("test_parent_autowire.beans.xml"), parent);

        container.start();

        MyBeanA a = container.getBeanByType(MyBeanA.class);
        assertNotNull(a.getB());

    }
}
