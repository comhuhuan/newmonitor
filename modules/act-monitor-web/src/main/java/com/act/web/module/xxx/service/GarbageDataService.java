/**
 * @Title: GarbageDataService.java 
 * @Package com.act.web.module.bdad.service 
 * @Description: (垃圾数据管理service) 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-5 下午7:42:13
 * @version V1.0   
 */
package com.act.web.module.xxx.service;

import java.util.Map;

import com.act.framework.util.PageResult;
import com.act.web.module.xxx.vo.GarbageDataVo;
import com.act.web.module.xxx.vo.ReportViewVo;

public interface GarbageDataService {

	/**
	 * @Title: pagingList
	 * @Description: 分页查询t_bdad_garbagedata 并返回select sql字段总数
	 * @create 2017-5-12 上午10:45:08
	 * @update 2017-5-12 上午10:45:08
	 */
	PageResult<GarbageDataVo> pagingList(PageResult<GarbageDataVo> page,
			GarbageDataVo garbageDataVo);

	/**
	 * @Title: pagingGarbage
	 * @Description: 根据t_bdad_garbagedata 表select_sql字段 返回分页报表 以及表头
	 * @create 2017-5-10 下午4:59:39
	 * @update 2017-5-10 下午4:59:39
	 */
	ReportViewVo pagingGarbage(PageResult<Map<String, Object>> page,
			ReportViewVo reportViewVo);

	/**
	 * @Title: clearData
	 * @Description: 根据t_bdad_garbagedata 表delete_sql字段 删除数据库中内容
	 * @return Long 删除条数 返回值为0时.出现异常
	 * @throws
	 * @create 2017-5-12 下午2:07:09
	 * @update 2017-5-12 下午2:07:09
	 */
	Integer clearData(String garbageDelete);

	/** 
	 * @Title: initialize 
	 * @Description: select garbage_id as value,remark as label from t_bdad_garbagedata 
	 * @return List<SelectUtil> 垃圾类别select键值对
	 * @create 2017-5-15 下午3:50:19
	 * @update 2017-5-15 下午3:50:19
	*/
	Object initialize();
}
