package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.RolePurviewDao;
import com.act.monitor.entity.RolePurviewEntity;

public class RolePurview extends RolePurviewEntity {

    public static RolePurview getByKey(int id) {
        return (RolePurview)BaseEntity.getByKey(RolePurview.class, id);
    }

    public static RolePurviewDao getDao() {
        return (RolePurviewDao)BaseEntity.getDao(RolePurview.class);
    }
}