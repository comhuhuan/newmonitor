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

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.framework.util.PageResult;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.DeviceService;
import com.act.web.module.monitor.vo.DeviceVo;
import com.act.web.util.ExcelJsonUtil;

@Controller
@RequestMapping("/monitor/device")
public class DeviceController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(DeviceController.class);

	@Resource
	private DeviceService deviceService;


	/**
	 * @Title: pagingList
	 * @Description:设备搜索 分页查询 
	 * @create 2017年6月20日16:50:52
	 * @update 2017年6月20日16:50:52
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingList.do")
	public Object pagingList(PageResult<DeviceVo> page, DeviceVo deviceVo) {
		try {
			page = deviceService.pagingList(page, deviceVo);
		} catch (Exception e) {
			log.error("分页查询失败!", e);
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, page);
	}
	
	/**
	 * 初始化省份列表信息
	 */
	@ResponseBody
	@RequestMapping(value = "/provList.do")
	public Object provList(DeviceVo deviceVo) {
		try {
			if(null == deviceVo || "".equals(deviceVo)){
				return ajax(Status.error, "参数错误");
			}
			boolean flage = deviceService.provIdCheck(deviceVo);
			if(!flage){
				return ajax(Status.error, "该用户没有查询权限");
			}
			Object result = deviceService.provList(deviceVo);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("省份信息初始化失败!", e);
			return ajax(Status.error, "查询失败!");
		}
	}

	/**
	 * @Title: getIdcByProv
	 * @Description:根据省份查询对应IDC信息 //数据来源 monsys_all_idc_info
	 * @create 2017年6月27日13:49:46
	 * @update 2017年6月27日13:49:46
	 */
	@ResponseBody
	@RequestMapping(value = "/getIdcByProv.do")
	public Object getIdcByProv(DeviceVo deviceVo) {
		try {
			if(null == deviceVo || "".equals(deviceVo)){
				return ajax(Status.error, "参数错误");
			}
			boolean flage = deviceService.provIdCheck(deviceVo);
			if(!flage){
				return ajax(Status.error, "该用户没有查询权限");
			}
			Object result = deviceService.getIdcByProv(deviceVo);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("查询对应运营商信息失败!", e);
			return ajax(Status.error, "获取选项失败!");
		}
	}
	
	/**
	 * @Title: getHouseByIdc
	 * @Description:根据IDC查询对应机房信息 //TODO
	 * @create 2017年6月27日13:50:09
	 * @update 2017年6月27日13:50:09
	 */
	@ResponseBody
	@RequestMapping(value = "/getHouseByIdc.do")
	public Object getHouseByIdc(String provId, String uuid) {
		try {
			Object result = deviceService.getHouseByIdc(provId,uuid);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("查询对应机房信息失败!", e);
			return ajax(Status.error, "获取选项失败!");
		}
	}


	@ResponseBody
	@RequestMapping(value = "/exportByJson.do")
	public Object exportByJson(PageResult<DeviceVo> page,
							   DeviceVo DeviceVo) {
		try {
			ExcelJsonUtil<DeviceVo> result = deviceService
					.exportByJson(page, DeviceVo);
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("导出tab_sys_user失败", e);
			return ajax(Status.error, "导出失败!");
		}
	}
}
