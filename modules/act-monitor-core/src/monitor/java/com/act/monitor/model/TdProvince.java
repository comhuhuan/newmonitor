package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TdProvinceDao;
import com.act.monitor.entity.TdProvinceEntity;

public class TdProvince extends TdProvinceEntity {

    public static TdProvince getByKey(long provId) {
        return (TdProvince)BaseEntity.getByKey(TdProvince.class, provId);
    }

    public static TdProvinceDao getDao() {
        return (TdProvinceDao)BaseEntity.getDao(TdProvince.class);
    }
}