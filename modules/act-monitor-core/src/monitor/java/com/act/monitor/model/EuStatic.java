package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.EuStaticDao;
import com.act.monitor.entity.EuStaticEntity;

import java.util.HashMap;

public class EuStatic extends EuStaticEntity {

    public static EuStatic getByKey(String uuid, long houseid, String euid) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("uuid",uuid);
        params.put("houseid",houseid);
        params.put("euid",euid);
        return (EuStatic)BaseEntity.getByKey(EuStatic.class, params);
    }

    public static EuStaticDao getDao() {
        return (EuStaticDao)BaseEntity.getDao(EuStatic.class);
    }
}