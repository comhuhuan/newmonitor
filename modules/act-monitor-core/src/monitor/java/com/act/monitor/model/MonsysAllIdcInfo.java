package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.MonsysAllIdcInfoDao;
import com.act.monitor.entity.MonsysAllIdcInfoEntity;

public class MonsysAllIdcInfo extends MonsysAllIdcInfoEntity {

    public static MonsysAllIdcInfo getByKey(String uuid) {
        return (MonsysAllIdcInfo)BaseEntity.getByKey(MonsysAllIdcInfo.class, uuid);
    }

    public static MonsysAllIdcInfoDao getDao() {
        return (MonsysAllIdcInfoDao)BaseEntity.getDao(MonsysAllIdcInfo.class);
    }
}