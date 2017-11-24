package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabSysConfigDao;
import com.act.monitor.entity.TabSysConfigEntity;

public class TabSysConfig extends TabSysConfigEntity {

    public static TabSysConfig getByKey(String configid) {
        return (TabSysConfig)BaseEntity.getByKey(TabSysConfig.class, configid);
    }

    public static TabSysConfigDao getDao() {
        return (TabSysConfigDao)BaseEntity.getDao(TabSysConfig.class);
    }
}