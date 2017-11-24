package com.act.web.module.monitor.service;

import com.act.framework.util.PageResult;
import com.act.web.module.monitor.vo.AlarmVo;
import com.act.web.module.monitor.vo.DeviceCountVo;

public interface AlarmListservice {

    /**
     *
     *
     */


    PageResult<AlarmVo> pageList(PageResult<AlarmVo> page, AlarmVo alarmVo)throws Exception;
}
