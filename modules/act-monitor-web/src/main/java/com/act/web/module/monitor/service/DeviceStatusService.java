/**
 * @Title: DeviceService.java 
 * @Package com.act.web.module.monitor.service 
 * @Description: 设备搜索页面service
 * @author   liuyang
 * @modifier liuyang
 * @date 2017-5-12 上午11:55:23
 * @version V1.0   
*/
package com.act.web.module.monitor.service;

import java.util.List;
import java.util.Map;

import com.act.framework.util.PageResult;
import com.act.web.module.monitor.vo.DeviceCountVo;
import com.act.web.module.monitor.vo.DeviceHistoryVo;
import com.act.web.module.monitor.vo.DeviceInfoVo;
import com.act.web.module.monitor.vo.DeviceStatusVo;

public interface DeviceStatusService {
	

	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: tuopu
	 * @Description: 查询全国各省份IDC分布情况   数据来源  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceStatusVo> tuopu(DeviceStatusVo deviceVo);
	

	/**
	 * @param deviceVo
	 *            查询条件
	 * @Title: queryDeviceByHouse
	 * @Description: 查询全国各省份IDC分布情况   数据来源  //TODO
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	public List<DeviceStatusVo> queryDeviceByHouse(DeviceStatusVo deviceVo);
}
