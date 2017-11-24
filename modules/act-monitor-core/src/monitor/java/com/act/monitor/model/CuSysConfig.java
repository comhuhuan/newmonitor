package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.CuSysConfigDao;
import com.act.monitor.entity.CuSysConfigEntity;

public class CuSysConfig extends CuSysConfigEntity {

    public static CuSysConfig getByKey(String configid) {
        return (CuSysConfig)BaseEntity.getByKey(CuSysConfig.class, configid);
    }

    public static CuSysConfigDao getDao() {
        return (CuSysConfigDao)BaseEntity.getDao(CuSysConfig.class);
    }
}