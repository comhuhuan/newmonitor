/**
 * @Title: HistoryStateController.java 
 * @Package com.act.web.module.monitor.controller 
 * @Description: 设备历史状态
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月27日20:49:54
 * @version V1.0   
 */
package com.act.web.module.monitor.controller;

import javax.annotation.Resource;

import com.act.web.util.ExcelJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.framework.util.PageResult;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.HistoryStateService;
import com.act.web.module.monitor.vo.DeviceVo;

@Controller
@RequestMapping("/monitor/historyState")
public class HistoryStateController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(HistoryStateController.class);

	@Resource
	private HistoryStateService historyStateService;


	/**
	 * @Title: pagingList
	 * @Description:初始化查询 分页查询 
	 * @create 2017年6月27日20:55:18
	 * @update 2017年6月27日20:55:18
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingList.do")
	public Object pagingList(PageResult<DeviceVo> page, DeviceVo deviceVo) {
		try {
			page = historyStateService.pagingList(page, deviceVo);
		} catch (Exception e) {
			log.error("分页查询失败!", e);
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, page);
	}

	@ResponseBody
	@RequestMapping(value = "/exportByJson.do")
	public Object exportByJson(PageResult<DeviceVo> page,
							   DeviceVo DeviceVo) {
		try {
			ExcelJsonUtil<DeviceVo> result = historyStateService
					.exportByJson(page, DeviceVo);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("导出tab_sys_user失败", e);
			return ajax(Status.error, "导出失败!");
		}
	}
}

	
