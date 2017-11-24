package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.MonsysAllTabnameInfoDao;
import com.act.monitor.entity.MonsysAllTabnameInfoEntity;

import java.util.HashMap;

public class MonsysAllTabnameInfo extends MonsysAllTabnameInfoEntity {

    public static MonsysAllTabnameInfo getByKey(String uuid, String tablename) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("uuid",uuid);
        params.put("tablename",tablename);
        return (MonsysAllTabnameInfo)BaseEntity.getByKey(MonsysAllTabnameInfo.class, params);
    }

    public static MonsysAllTabnameInfoDao getDao() {
        return (MonsysAllTabnameInfoDao)BaseEntity.getDao(MonsysAllTabnameInfo.class);
    }
}