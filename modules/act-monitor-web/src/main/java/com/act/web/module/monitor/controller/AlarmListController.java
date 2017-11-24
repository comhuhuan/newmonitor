package com.act.web.module.monitor.controller;

import com.act.framework.util.PageResult;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.AlarmListservice;
import com.act.web.module.monitor.vo.AlarmVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;


@Controller
@RequestMapping(" /monitor/alarm")
public class AlarmListController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(AlarmListController.class);
    @Resource
    private AlarmListservice  alarmListservice;

    /**
     *
     * @param page
     * @param alarmVo
     * @return
     */

    @ResponseBody
    @RequestMapping(value = "/alarmList.do")
    public Object pagingList(PageResult<AlarmVo> page, AlarmVo alarmVo) {
        try {
            page = alarmListservice.pageList(page, alarmVo);
        } catch (Exception e) {
            log.error("分页查询失败!", e);
            return ajax(Status.error, "查询失败!");
        }
        return ajax(Status.success, page);
    }




}
