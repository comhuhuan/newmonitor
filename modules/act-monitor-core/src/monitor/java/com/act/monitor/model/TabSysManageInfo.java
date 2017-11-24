package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabSysManageInfoDao;
import com.act.monitor.entity.TabSysManageInfoEntity;

public class TabSysManageInfo extends TabSysManageInfoEntity {

    public static TabSysManageInfo getByKey(Byte syamanageId) {
        return (TabSysManageInfo)BaseEntity.getByKey(TabSysManageInfo.class, syamanageId);
    }

    public static TabSysManageInfoDao getDao() {
        return (TabSysManageInfoDao)BaseEntity.getDao(TabSysManageInfo.class);
    }
}