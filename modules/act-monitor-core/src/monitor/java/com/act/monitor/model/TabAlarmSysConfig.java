package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabAlarmSysConfigDao;
import com.act.monitor.entity.TabAlarmSysConfigEntity;

public class TabAlarmSysConfig extends TabAlarmSysConfigEntity {

    public static TabAlarmSysConfig getByKey(String configid) {
        return (TabAlarmSysConfig)BaseEntity.getByKey(TabAlarmSysConfig.class, configid);
    }

    public static TabAlarmSysConfigDao getDao() {
        return (TabAlarmSysConfigDao)BaseEntity.getDao(TabAlarmSysConfig.class);
    }
}