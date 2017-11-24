package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.MonsysAllHousenameInfoDao;
import com.act.monitor.entity.MonsysAllHousenameInfoEntity;

import java.util.HashMap;

public class MonsysAllHousenameInfo extends MonsysAllHousenameInfoEntity {

    public static MonsysAllHousenameInfo getByKey(String uuid, long houseid) {
        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("uuid",uuid);
        params.put("houseid",houseid);
        return (MonsysAllHousenameInfo)BaseEntity.getByKey(MonsysAllHousenameInfo.class, params);
    }

    public static MonsysAllHousenameInfoDao getDao() {
        return (MonsysAllHousenameInfoDao)BaseEntity.getDao(MonsysAllHousenameInfo.class);
    }
}