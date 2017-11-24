package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.ExceptionTableDao;
import com.act.monitor.entity.ExceptionTableEntity;

import java.util.HashMap;

public class ExceptionTable extends ExceptionTableEntity {

    public static ExceptionTable getByKey(String type, String host, int childclass) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("type",type);
        params.put("host",host);
        params.put("childclass",childclass);
        return (ExceptionTable)BaseEntity.getByKey(ExceptionTable.class, params);
    }

    public static ExceptionTableDao getDao() {
        return (ExceptionTableDao)BaseEntity.getDao(ExceptionTable.class);
    }
}