package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabSysUserDao;
import com.act.monitor.entity.TabSysUserEntity;

public class TabSysUser extends TabSysUserEntity {

    public static TabSysUser getByKey(String userId) {
        return (TabSysUser)BaseEntity.getByKey(TabSysUser.class, userId);
    }

    public static TabSysUserDao getDao() {
        return (TabSysUserDao)BaseEntity.getDao(TabSysUser.class);
    }
}