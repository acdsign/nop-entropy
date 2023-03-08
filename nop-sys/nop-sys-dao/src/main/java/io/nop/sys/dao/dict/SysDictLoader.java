/**
 * Copyright (c) 2017-2023 Nop Platform. All rights reserved.
 * Author: canonical_entropy@163.com
 * Blog:   https://www.zhihu.com/people/canonical-entropy
 * Gitee:  https://gitee.com/canonical-entropy/nop-chaos
 * Github: https://github.com/entropy-cloud/nop-chaos
 */
package io.nop.sys.dao.dict;

import io.nop.api.core.beans.DictBean;
import io.nop.api.core.beans.DictOptionBean;
import io.nop.api.core.beans.FilterBeans;
import io.nop.api.core.beans.query.QueryBean;
import io.nop.commons.util.StringHelper;
import io.nop.core.dict.DictProvider;
import io.nop.core.dict.IDictLoader;
import io.nop.core.dict.IDictProvider;
import io.nop.core.i18n.I18nMessageManager;
import io.nop.dao.api.IDaoProvider;
import io.nop.dao.api.IEntityDao;
import io.nop.sys.dao.NopSysConstants;
import io.nop.sys.dao.entity.NopSysDict;
import io.nop.sys.dao.entity.NopSysDictOption;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class SysDictLoader implements IDictLoader {
    @Inject
    IDaoProvider daoProvider;

    @PostConstruct
    public void init() {
        IDictProvider dictProvider = DictProvider.instance();
        dictProvider.addDictLoader(NopSysConstants.SYS_DICT_PREFIX, this);
    }

    @Override
    public boolean supportDict(String dictName) {
        return dictName.startsWith(NopSysConstants.SYS_DICT_PREFIX);
    }

    @PreDestroy
    public void destroy() {
        IDictProvider dictProvider = DictProvider.instance();
        dictProvider.removeDictLoader(NopSysConstants.SYS_DICT_PREFIX, this);
    }

    @Override
    public DictBean loadDict(String locale, String dictName) {
        IEntityDao<NopSysDictOption> dao = daoProvider.daoFor(NopSysDictOption.class);

        DictBean bean = new DictBean();
        bean.setLocale(I18nMessageManager.instance().getDefaultLocale());
        bean.setName(dictName);

        QueryBean query = new QueryBean();
        query.setFilter(FilterBeans.eq(NopSysDictOption.PROP_NAME_dict + '.' + NopSysDict.PROP_NAME_dictName, dictName));
        query.addOrderField(NopSysDictOption.PROP_NAME_value, false);
        List<DictOptionBean> options = dao.findAllByQuery(query).stream().map(option -> {
            DictOptionBean opt = new DictOptionBean();
            opt.setLabel(option.getLabel());
            opt.setValue(option.getValue());
            opt.setDeprecated(StringHelper.isYes(option.getIsDeprecated()));
            opt.setInternal(StringHelper.isYes(option.getIsInternal()));
            return opt;
        }).collect(Collectors.toList());

        bean.setOptions(options);
        return bean;
    }

    @Override
    public boolean existsDict(String dictName) {
        QueryBean query = new QueryBean();
        query.setFilter(FilterBeans.eq(NopSysDict.PROP_NAME_dictName, dictName));
        return daoProvider.daoFor(NopSysDict.class).existsByQuery(query);
    }
}