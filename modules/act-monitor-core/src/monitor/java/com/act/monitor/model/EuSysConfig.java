package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.EuSysConfigDao;
import com.act.monitor.entity.EuSysConfigEntity;

public class EuSysConfig extends EuSysConfigEntity {

    public static EuSysConfig getByKey(String configid) {
        return (EuSysConfig)BaseEntity.getByKey(EuSysConfig.class, configid);
    }

    public static EuSysConfigDao getDao() {
        return (EuSysConfigDao)BaseEntity.getDao(EuSysConfig.class);
    }
}