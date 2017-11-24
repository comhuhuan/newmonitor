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

import com.act.framework.util.PageResult;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.monitor.vo.DeviceVo;
import com.act.web.util.ExcelJsonUtil;

public interface DeviceService {
	
	/**
	 * @param page
	 *            分页属性
	 * @param deviceVo
	 *            查询条件
	 * @Title: pagingList
	 * @Description: 得到分页list 数据来源
	 * @create 2017年6月20日16:55:20
	 * @update 2017年6月20日16:55:20
	 */
	PageResult<DeviceVo> pagingList(PageResult<DeviceVo> page, DeviceVo deviceVo);
	
	/**
	 * @Title: deviceVo
	 * @Description: 设备搜索页面加载时初始化省份下拉框列表  
	 * @author liuyang
	 * @create 2017年6月23日17:51:24
	 * @update 2017年6月23日17:51:24
	 */
	Object provList(DeviceVo deviceVo);
	
	/**
	 * @param provId
	 *            省份ID
	 * @Title: getIdcByProv
	 * @Description: 根据省份ID查询运营商信息 数据来源  //数据来源 monsys_all_idc_info
	 * @create 2017年6月27日13:52:40
	 * @update 2017年6月27日13:52:40
	 */
	Object getIdcByProv(DeviceVo deviceVo);
	
	/**
	 * @param deviceVo
	 *            检查省份ID是否存在
	 * @Title: getIdcByProv
	 * @Description: 根据省份ID查询运营商信息 数据来源  //数据来源 monsys_all_idc_info
	 * @create 2017年6月27日13:52:40
	 * @update 2017年6月27日13:52:40
	 */
	public boolean provIdCheck(DeviceVo deviceVo);
	
	/**
	 * @param provId
	 * 				省份ID
	 * @param idcId
	 *            运营商ID
	 * @Title: getHouseByIdc
	 * @Description: 查询机房信息 数据来源  //TODO
	 * @create 2017年6月27日13:52:53
	 * @update 2017年6月27日13:52:53
	 */
	Object getHouseByIdc(String provId,String idcId);


	ExcelJsonUtil<DeviceVo> exportByJson(PageResult<DeviceVo> page,
										 DeviceVo userConfigVo);
}
