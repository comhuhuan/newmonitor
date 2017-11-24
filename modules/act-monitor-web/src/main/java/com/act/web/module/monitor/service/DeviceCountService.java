/**
 * @Title: DeviceCountService.java 
 * @Package com.act.web.module.monitor.service 
 * @Description: 设备搜索页面service
 * @author   liuyang
 * @modifier liuyang
 * @date 2017-5-12 上午11:55:23
 * @version V1.0   
*/
package com.act.web.module.monitor.service;

import java.util.List;

import com.act.framework.util.PageResult;
import com.act.web.module.monitor.vo.DeviceCountVo;
import com.act.web.module.monitor.vo.DeviceHistoryVo;
import com.act.web.module.monitor.vo.DeviceInfoVo;

public interface DeviceCountService {
	
	/**
	 * @param page
	 *            分页属性
	 * @param deviceVo
	 *            查询条件
	 * @Title: pagingList
	 * @Description: 得到分页list 数据来源  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	PageResult<DeviceCountVo> pageList(PageResult<DeviceCountVo> page, DeviceCountVo deviceVo);
	
	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: deviceCount
	 * @Description: 查询全国各省份IDC分布情况   数据来源  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceCountVo> deviceCount(DeviceCountVo deviceVo);
	
	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: deviceInfo
	 * @Description: CU、EU设备状态统计（饼状态图）  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceInfoVo> deviceInfo(DeviceInfoVo deviceVo);
	
	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: deviceInfo
	 * @Description: CU、EU设备状态统计（饼状态图）  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceHistoryVo> cuDeviceInfoHistory(DeviceHistoryVo deviceVo)throws Exception ;
	
	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: deviceInfo
	 * @Description: CU、EU设备状态统计（饼状态图）  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceHistoryVo> euDeviceInfoHistory(DeviceHistoryVo deviceVo)throws Exception;
	
	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: deviceInfo
	 * @Description: CU、EU设备状态统计（饼状态图）  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public PageResult<DeviceInfoVo> badDevicePageList(PageResult<DeviceInfoVo> page,DeviceInfoVo deviceVo)throws Exception;


	DeviceCountVo cuInter(DeviceCountVo deviceVo);
}
