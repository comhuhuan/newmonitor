package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabMenuRoleDao;
import com.act.monitor.entity.TabMenuRoleEntity;

public class TabMenuRole extends TabMenuRoleEntity {

    public static TabMenuRole getByKey(int menuId) {
        return (TabMenuRole)BaseEntity.getByKey(TabMenuRole.class, menuId);
    }

    public static TabMenuRoleDao getDao() {
        return (TabMenuRoleDao)BaseEntity.getDao(TabMenuRole.class);
    }
}