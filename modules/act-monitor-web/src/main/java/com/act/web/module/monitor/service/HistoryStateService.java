/**
 * @Title: HistoryStateService.java 
 * @Package com.act.web.module.monitor.service 
 * @Description: 设备历史状态页面service
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月27日21:01:00
 * @version V1.0   
*/
package com.act.web.module.monitor.service;

import com.act.framework.util.PageResult;
import com.act.web.module.monitor.vo.DeviceVo;
import com.act.web.util.ExcelJsonUtil;

public interface HistoryStateService {
	
	/**
	 * @param page
	 *            分页属性
	 * @param deviceVo
	 *            查询条件
	 * @throws Exception 
	 * @Title: pagingList
	 * @Description: 得到分页list 数据来源  
	 * @create 2017年6月27日21:01:39
	 * @update 2017年6月27日21:01:39
	 */
	PageResult<DeviceVo> pagingList(PageResult<DeviceVo> page, DeviceVo deviceVo) throws Exception;


	ExcelJsonUtil<DeviceVo> exportByJson(PageResult<DeviceVo> page,
										 DeviceVo userConfigVo) throws Exception;
	
}
