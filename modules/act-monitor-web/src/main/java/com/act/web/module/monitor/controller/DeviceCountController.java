/**
 * @Title: DeviceCountController.java 
 * @Package com.act.web.module.monitor.controller 
 * @Description: 设备搜索
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月20日16:46:32
 * @version V1.0   
 */
package com.act.web.module.monitor.controller;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.framework.util.PageResult;
import com.act.web.constant.CommonContant;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.DeviceCountService;
import com.act.web.module.monitor.vo.DeviceCountVo;
import com.act.web.module.monitor.vo.DeviceHistoryVo;
import com.act.web.module.monitor.vo.DeviceInfoVo;
import com.act.web.util.SqlUtil;

@Controller
@RequestMapping("/monitor/deviceCount")
public class DeviceCountController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(DeviceCountController.class);

	@Resource
	private DeviceCountService deviceService;


	/**
	 * @Title: deviceCount
	 * @Description:查询全国IDC的故障设备分布情况 //TODO
	 * @create 2017年6月20日16:50:52
	 * @update 2017年6月20日16:50:52
	 */
	@ResponseBody
	@RequestMapping(value = "/deviceCount.do")
	public Object deviceCount(PageResult<DeviceCountVo> page, DeviceCountVo deviceVo) {
		List<DeviceCountVo> resultList = null;
		try {
			 resultList = deviceService.deviceCount(deviceVo);
		} catch (Exception e) {
			log.error("查询失败!", e);
			e.printStackTrace();
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, resultList);
	}


	/**
	 * @Title: deviceCount
	 * @Description:查询全国IDC的故障设备分布情况 //TODO
	 * @create 2017年6月20日16:50:52
	 * @update 2017年6月20日16:50:52
	 */
	@ResponseBody
	@RequestMapping(value = "/cuInter.do")
	public Object deviceCount(DeviceCountVo deviceVo) {
		DeviceCountVo	resultList;
		try {
			resultList = deviceService.cuInter(deviceVo);
		} catch (Exception e) {
			log.error("查询失败!", e);
			e.printStackTrace();
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, resultList);
	}

	 /**
		 * @Title: pagingList
		 * @Description:查询设备故障信息列表//TODO
		 * @create 2017年6月20日16:50:52
		 * @update 2017年6月20日16:50:52
		 */
		@ResponseBody
		@RequestMapping(value = "/pageList.do")
		public Object pageList(PageResult<DeviceCountVo> page, DeviceCountVo deviceVo) {
			try {
				page = deviceService.pageList(page, deviceVo);
			} catch (Exception e) {
				log.error("分页查询失败!", e);
				e.printStackTrace();
				return ajax(Status.error, "查询失败!");
			}
			return ajax(Status.success, page);
		}
		
		/**
		 * @Title: deviceInfo
		 * @Description:CU、EU设备状态统计（饼状态图）//TODO
		 * @create 2017年6月20日16:50:52
		 * @update 2017年6月20日16:50:52
		 */
		@ResponseBody
		@RequestMapping(value = "/deviceInfo.do")
		public Object deviceInfo(PageResult<DeviceInfoVo> page, DeviceInfoVo deviceVo) {
			List<DeviceInfoVo> resultList = null;
			try {
				 String uuid = deviceVo.getUuid();
				 Integer devceType = deviceVo.getDevceType();
				 if(null == devceType || "".equals(devceType) || null == uuid || "".equals(uuid)){
					 log.error("查询失败!", "参数错误");
					 return ajax(Status.error, "参数错误");
				 }
				
				 resultList = deviceService.deviceInfo(deviceVo);
			} catch (Exception e) {
				log.error("查询失败!", e);
				return ajax(Status.error, "查询失败!");
			}
			return ajax(Status.success, resultList);
		}
		
		/**
		 * @Title: cuDeviceInfoHistory
		 * @Description:CU设备历史故障查询（矩形图）//TODO
		 * @create 2017年6月20日16:50:52
		 * @update 2017年6月20日16:50:52
		 */
		@ResponseBody
		@RequestMapping(value = "/cuDeviceInfoHistory.do")
		public Object cuDeviceInfoHistory(PageResult<DeviceHistoryVo> page, DeviceHistoryVo deviceVo) {
			List<DeviceHistoryVo> resultList = null;
			try {
				 String uuid = deviceVo.getUuid();
				 String devceType = deviceVo.getElementType();
				 if(null == devceType || "".equals(devceType) || null == uuid || "".equals(uuid)){
					 log.error("查询失败!", "参数错误");
					 return ajax(Status.error, "参数错误");
				 }
				 
				 resultList = deviceService.cuDeviceInfoHistory(deviceVo);
			} catch (Exception e) {
				log.error("查询失败!", e);
				return ajax(Status.error, "查询失败!");
			}
			return ajax(Status.success, resultList);
		}
		
			/**
			 * @Title: euDeviceInfoHistory
			 * @Description:EU设备历史故障查询（矩形图）//TODO
			 * @create 2017年6月20日16:50:52
			 * @update 2017年6月20日16:50:52
			 */
			@ResponseBody
			@RequestMapping(value = "/euDeviceInfoHistory.do")
			public Object euDeviceInfoHistory(PageResult<DeviceHistoryVo> page, DeviceHistoryVo deviceVo) {
				List<DeviceHistoryVo> resultList = null;
				try {
					 String uuid = deviceVo.getUuid();
					 String devceType = deviceVo.getElementType();
					 if(null == devceType || "".equals(devceType) || null == uuid || "".equals(uuid)){
						 log.error("查询失败!", "参数错误");
						 return ajax(Status.error, "参数错误");
					 }
					 resultList = deviceService.euDeviceInfoHistory(deviceVo);
				} catch (Exception e) {
					log.error("查询失败!", e);
					return ajax(Status.error, "查询失败!");
				}
				return ajax(Status.success, resultList);
			}
			
			/**
			 * @Title: badDevicePageList
			 * @Description:CU和EU最近故障设备列表）//TODO
			 * @create 2017年6月20日16:50:52
			 * @update 2017年6月20日16:50:52
			 */
			@ResponseBody
			@RequestMapping(value = "/badDevicePageList.do")
			public Object badDevicePageList(PageResult<DeviceInfoVo> page, DeviceInfoVo deviceVo) {
				try {
					 String uuid = deviceVo.getUuid();
					 Integer devceType = deviceVo.getDevceType();
					 if(null == devceType || "".equals(devceType) || null == uuid || "".equals(uuid)){
						 log.error("查询失败!", "参数错误");
						 return ajax(Status.error, "参数错误");
					 }
					 page = deviceService.badDevicePageList(page,deviceVo);
				} catch (Exception e) {
					log.error("查询失败!", e);
					return ajax(Status.error, "查询失败!");
				}
				return ajax(Status.success, page);
			}
}
