package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabMenuDao;
import com.act.monitor.entity.TabMenuEntity;

public class TabMenu extends TabMenuEntity {

    public static TabMenu getByKey(int menuId) {
        return (TabMenu)BaseEntity.getByKey(TabMenu.class, menuId);
    }

    public static TabMenuDao getDao() {
        return (TabMenuDao)BaseEntity.getDao(TabMenu.class);
    }
}