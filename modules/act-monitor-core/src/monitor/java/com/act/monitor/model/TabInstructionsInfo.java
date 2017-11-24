package com.act.monitor.model;

import com.act.framework.entity.BaseEntity;
import com.act.monitor.dao.TabInstructionsInfoDao;
import com.act.monitor.entity.TabInstructionsInfoEntity;

public class TabInstructionsInfo extends TabInstructionsInfoEntity {

    public static TabInstructionsInfo getByKey(int instructionid) {
        return (TabInstructionsInfo)BaseEntity.getByKey(TabInstructionsInfo.class, instructionid);
    }

    public static TabInstructionsInfoDao getDao() {
        return (TabInstructionsInfoDao)BaseEntity.getDao(TabInstructionsInfo.class);
    }
}