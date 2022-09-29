package io.nop.autotest.junit;

import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.commons.util.StringHelper;
import io.nop.core.resource.ResourceHelper;
import io.nop.core.resource.impl.ClassPathResource;
import io.nop.dao.DaoConfigs;

import java.util.Properties;

import static io.nop.core.unittest.BaseTestCase.setTestConfig;
import static io.nop.ioc.IocConfigs.CFG_IOC_APP_BEANS_CONTAINER_START_MODE;
import static io.nop.ioc.IocConfigs.CFG_IOC_APP_BEANS_FILES;
import static io.nop.ioc.IocConfigs.CFG_IOC_APP_BEANS_FILE_ENABLED;
import static io.nop.ioc.IocConfigs.CFG_IOC_APP_BEANS_FILE_PATTERN;
import static io.nop.ioc.IocConfigs.CFG_IOC_APP_BEANS_FILE_SKIP_PATTERN;
import static io.nop.ioc.IocConfigs.CFG_IOC_AUTO_CONFIG_ENABLED;
import static io.nop.ioc.IocConfigs.CFG_IOC_AUTO_CONFIG_PATTERN;
import static io.nop.ioc.IocConfigs.CFG_IOC_AUTO_CONFIG_SKIP_PATTERN;
import static io.nop.ioc.IocConfigs.CFG_IOC_MERGED_BEANS_FILE_ENABLED;

public class NopTestConfigProcessor {

    public void process(NopTestConfig config) {
        if (config.localDb()) {
            setTestConfig(DaoConfigs.CFG_DATASOURCE_DRIVER_CLASS_NAME, "org.h2.Driver");
            setTestConfig(DaoConfigs.CFG_DATASOURCE_USERNAME, "sa");
            setTestConfig(DaoConfigs.CFG_DATASOURCE_PASSWORD, "");
            setTestConfig(DaoConfigs.CFG_DATASOURCE_JDBC_URL, "jdbc:h2:mem:" + StringHelper.generateUUID());
        }

        setTestConfig(CFG_IOC_APP_BEANS_CONTAINER_START_MODE, config.beanContainerStartMode().name());

        if (!config.testConfigFile().isEmpty()) {
            ClassPathResource resource = new ClassPathResource(config.testConfigFile());
            Properties props = ResourceHelper.readProperties(resource);
            for (Object name : props.keySet()) {
                setTestConfig((String) name, props.get(name));
            }
        }

        setTestConfig(CFG_IOC_AUTO_CONFIG_ENABLED, config.enableAutoConfig());
        if (!config.autoConfigPattern().isEmpty()) {
            setTestConfig(CFG_IOC_AUTO_CONFIG_PATTERN, config.autoConfigPattern());
        }
        if (!config.autoConfigSkipPattern().isEmpty()) {
            setTestConfig(CFG_IOC_AUTO_CONFIG_SKIP_PATTERN, config.autoConfigSkipPattern());
        }

        setTestConfig(CFG_IOC_MERGED_BEANS_FILE_ENABLED,config.enableMergedBeansFile());

        setTestConfig(CFG_IOC_APP_BEANS_FILE_ENABLED, config.enableAppBeansFile());
        if (!config.appBeansFilePattern().isEmpty()) {
            setTestConfig(CFG_IOC_APP_BEANS_FILE_PATTERN, config.appBeansFilePattern());
        }
        if (!config.appBeansFileSkipPattern().isEmpty()) {
            setTestConfig(CFG_IOC_APP_BEANS_FILE_SKIP_PATTERN, config.appBeansFileSkipPattern());
        }

        if (!config.testBeansFile().isEmpty()) {
            setTestConfig(CFG_IOC_APP_BEANS_FILES, config.testBeansFile());
        }
    }
}
