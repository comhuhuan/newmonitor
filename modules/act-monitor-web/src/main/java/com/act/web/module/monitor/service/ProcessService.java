/**
 * @Title: ProcessService.java 
 * @Package com.act.web.module.monitor.service 
 * @Description: 进程状态页面service
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月21日17:00:05
 * @version V1.0   
*/
package com.act.web.module.monitor.service;

public interface ProcessService {
	
	/** 
	 * @Title: initialize
	 * @param uuId 唯一用户ID
	 * @param deviceId 设备ID(euId/cuIp)
	 * @param houseId 机房ID
	 * @param elementType 状态类型
	 * @param queryType elementType为euDevice或cuDevice时才有此参数
	 * @Description: 初始化详情区域 单位性质
	 * @create 2017年6月21日17:02:25
	 * @update 2017年6月21日17:02:25
	*/
	Object initializeTitle(String uuId, String deviceId, String houseId, String elementType, String queryType);
	
//	/** 
//	 * @Title: initializeTrend
//	 * @param uuId 唯一用户ID
//	 * @param deviceId 设备ID(euId/cuIp)
//	 * @param houseId 机房ID
//	 * @param processType 进程类型
//	 * @Description: 初始化趋势图 单位性质(/)
//	 * @create 2017年6月27日16:43:18
//	 * @update 2017年6月27日16:43:18
//	*/
//	Object initializeTrend(String uuId, String deviceId, String houseId, String processType,String startTime, String endTime);
//	
//	/** 
//	 * @Title: initialize
//	 * @param uuId 唯一用户ID
//	 * @param deviceId 设备ID(euId/cuIp)
//	 * @param houseId 机房ID
//	 * @param processType 进程类型
//	 * @Description: 初始化列表区域 单位性质(/)  //TODO
//	 * @create 2017年6月21日17:02:25
//	 * @update 2017年6月21日17:02:25
//	*/
//	Object initialize_list(String uuId, String deviceId, String houseId, String processType);
}
