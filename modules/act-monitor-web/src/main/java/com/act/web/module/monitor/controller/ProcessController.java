/**
 * @Title: ProcessController.java 
 * @Package com.act.web.module.monitor.controller 
 * @Description: 进程搜索
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月21日15:29:54
 * @version V1.0   
 */
package com.act.web.module.monitor.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.ProcessService;

@Controller
@RequestMapping("/monitor/process")
public class ProcessController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(ProcessController.class);
	
	@Resource
	private ProcessService processService;

	/**
	 * 
	 * @Title: initializeTitle 
	 * @Description: 详情区域初始化
	 * @param uuId 唯一用户id
	 * @param deviceId 设备Id
	 * @param houseId 机房ID
	 * @param elementType 状态类型
	 * @param queryType 
	 * 				elementType为设备状态时具体子类型(cpu,process,disk,memory) 
	 * @author liuyang
	 * @create 2017年6月21日15:32:39
	 * @update 2017年6月21日15:32:39
	 */
	@ResponseBody
	@RequestMapping(value = "/initializeTitle.do")
	public Object initializeTitle(String uuid, String deviceId, String houseID, String elementType, String queryType) {
		try{
			Object result = processService.initializeTitle(uuid, deviceId,houseID,elementType,queryType);
			return ajax(Status.success, result);
		}catch (Exception e) {
			log.error("初始化进程状态页面详情区域失败!", e);
			return ajax(Status.error, "初始化详情区域失败!");
		}
	}
	
//	/**
//	 * 
//	 * @Title: initializeTrend 
//	 * @Description: 趋势图初始化
//	 * @param uuId 唯一用户id
//	 * @param deviceId 设备Id
//	 * @param houseId 机房ID
//	 * @param processType 进程类型
//	 * @author liuyang
//	 * @create 2017年6月27日16:41:26
//	 * @update 2017年6月27日16:41:26
//	 */
//	@ResponseBody
//	@RequestMapping(value = "/initializeTrend.do")
//	public Object initializeTrend(String uuId, String deviceId, String houseId, String processType, String startTime, String endTime) {
//		try{
//			Object result = processService.initializeTrend(uuId, deviceId,houseId,processType,startTime,endTime);
////			Object result2 = processService.initialize_trend(uuId, deviceId,houseId,processType);
////			Object result3 = processService.initialize_list(uuId, deviceId,houseId,processType);
//			return ajax(Status.success, result);
//		}catch (Exception e) {
//			log.error("初始化进程状态页面状态区域失败!", e);
//			return ajax(Status.error, "初始化状态区域失败!");
//		}
//	}
}
