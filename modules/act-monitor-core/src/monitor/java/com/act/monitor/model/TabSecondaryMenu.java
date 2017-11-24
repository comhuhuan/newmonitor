package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabSecondaryMenuDao;
import com.act.monitor.entity.TabSecondaryMenuEntity;

public class TabSecondaryMenu extends TabSecondaryMenuEntity {

    public static TabSecondaryMenu getByKey(int secMenuId) {
        return (TabSecondaryMenu)BaseEntity.getByKey(TabSecondaryMenu.class, secMenuId);
    }

    public static TabSecondaryMenuDao getDao() {
        return (TabSecondaryMenuDao)BaseEntity.getDao(TabSecondaryMenu.class);
    }
}