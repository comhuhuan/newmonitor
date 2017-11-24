package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabPurviewDao;
import com.act.monitor.entity.TabPurviewEntity;

public class TabPurview extends TabPurviewEntity {

    public static TabPurview getByKey(int id) {
        return (TabPurview)BaseEntity.getByKey(TabPurview.class, id);
    }

    public static TabPurviewDao getDao() {
        return (TabPurviewDao)BaseEntity.getDao(TabPurview.class);
    }
}