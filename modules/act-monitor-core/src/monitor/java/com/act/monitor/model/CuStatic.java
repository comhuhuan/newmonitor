package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.CuStaticDao;
import com.act.monitor.entity.CuStaticEntity;

import java.util.HashMap;

public class CuStatic extends CuStaticEntity {

    public static CuStatic getByKey(String uuid, String cuip) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("uuid",uuid);
        params.put("cuip",cuip);
        return (CuStatic)BaseEntity.getByKey(CuStatic.class, params);
    }

    public static CuStaticDao getDao() {
        return (CuStaticDao)BaseEntity.getDao(CuStatic.class);
    }
}