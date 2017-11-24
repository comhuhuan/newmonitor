package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.WebSysConfigDao;
import com.act.monitor.entity.WebSysConfigEntity;

public class WebSysConfig extends WebSysConfigEntity {

    public static WebSysConfig getByKey(String configid) {
        return (WebSysConfig)BaseEntity.getByKey(WebSysConfig.class, configid);
    }

    public static WebSysConfigDao getDao() {
        return (WebSysConfigDao)BaseEntity.getDao(WebSysConfig.class);
    }
}