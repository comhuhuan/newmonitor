/**
 * @Title: DeviceController.java 
 * @Package com.act.web.module.monitor.controller 
 * @Description: 设备搜索
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月20日16:46:32
 * @version V1.0   
 */
package com.act.web.module.monitor.controller;

import com.act.framework.util.PageResult;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.DeviceStatusService;
import com.act.web.module.monitor.vo.DeviceStatusVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/monitor/deviceStatus")
public class DeviceStatusController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(DeviceStatusController.class);

	@Resource
	private DeviceStatusService deviceService;


	/**
	 * @Title: tuopu
	 * @Description:dic下的机房拓扑图 //TODO
	 * @create 2017年6月24日
	 * @update 2017年6月24日
	 */
	@ResponseBody
	@RequestMapping(value = "/tuopu.do")
	public Object tuopu(PageResult<DeviceStatusVo> page, DeviceStatusVo deviceVo) {
		List<DeviceStatusVo> resultList = null;
		try {
			 String uuid = deviceVo.getUuid();
			 Integer devceType =  deviceVo.getDevceType();
			 if(null == uuid || "".equals(uuid) || null == devceType || "".equals(devceType)){
				 log.error("查询失败!", "参数错误");
				 return ajax(Status.error, "参数错误");
			 }
			 resultList = deviceService.tuopu(deviceVo);
		} catch (Exception e) {
			log.error("查询失败!", e);
			e.printStackTrace();
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, resultList);
	}
	
	/**
	 * @Title: 
	 * @Description:dic下的机房拓扑图 //TODO
	 * @create 2017年6月24日
	 * @update 2017年6月24日
	 */
	@ResponseBody
	@RequestMapping(value = "/queryDeviceByHouse.do")
	public Object queryDeviceByHouse(DeviceStatusVo deviceVo) {
		List<DeviceStatusVo> resultList = null;
		try {
			 String uuid = deviceVo.getUuid();
			 
			 if(null == uuid || "".equals(uuid)){
				 log.error("查询失败!", "参数错误");
				 return ajax(Status.error, "参数错误");
			 }
		
			 resultList = deviceService.queryDeviceByHouse(deviceVo);
		} catch (Exception e) {
			log.error("查询失败!", e);
			e.printStackTrace();
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, resultList);
	}

}
