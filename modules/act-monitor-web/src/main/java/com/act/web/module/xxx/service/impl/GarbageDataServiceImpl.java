/**
 * @Title: GarbageDataServiceImpl.java 
 * @Package com.act.web.module.bdad.service.impl 
 * @Description: (垃圾数据管理service实现) 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-5 下午7:42:13
 * @version V1.0   
 */
package com.act.web.module.xxx.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.web.module.xxx.service.GarbageDataService;
import com.act.web.module.xxx.vo.GarbageDataVo;
import com.act.web.module.xxx.vo.ReportViewVo;
import com.act.web.util.SelectUtil;
import com.act.web.util.SqlUtil;

@Service
public class GarbageDataServiceImpl implements GarbageDataService {

	/*
	 * (非 Javadoc) <p>Title: initialize</p> <p>Description: </p>
	 * 
	 * @return
	 * 
	 * @see com.act.web.module.bdad.service.GarbageDataService#initialize()
	 */
	@Override
	public Object initialize() {
		StringBuffer sql = new StringBuffer(
				"select garbage_id as value,remark as label from t_bdad_garbagedata");
		List<SelectUtil> selectUtils = DbUtil.queryForObjectList(
				sql.toString(), SelectUtil.class);
		return selectUtils;
	}

	/*
	 * (非 Javadoc) <p>Title: pagingList</p> <p>Description:
	 * 分页查询t_bdad_garbagedata</p>
	 * 
	 * @return
	 * 
	 * @see com.act.web.module.bdad.service.GarbageDataService#pagingList()
	 */
	@Override
	public PageResult<GarbageDataVo> pagingList(PageResult<GarbageDataVo> page,
			GarbageDataVo garbageDataVo) {
		StringBuffer sql = new StringBuffer(
				"select a.garbage_id as garbageId,a.selete_sql as seleteSql ,a.delete_sql as deleteSql,a.remark as remark ");
		sql.append("from t_bdad_garbagedata a where 1=1");
		List<Object> params = new ArrayList<Object>();
		if (garbageDataVo != null) {
			Integer garbageDataType = garbageDataVo.getGarbageDataType();
			if (null != garbageDataType) {
				sql.append(" and a.garbage_id = ?");
				params.add(garbageDataType);
			}
		}
		Object[] param = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			param[i] = params.get(i);
		}
		page = DbUtil.queryPageForObjectPageResult(sql.toString(),
				GarbageDataVo.class, page.getPageIndex(), page.getPageSize(),param);
		addAttributeCount(page);
		return page;
	}

	/**
	 * @Title: addAttributeCount
	 * @Description: 遍历分页结果集 根据 select count (seleteSql字段) 赋值 sqlCount
	 * @param @param page 设定文件
	 * @return void 返回类型
	 * @throws
	 * @create 2017-5-10 下午3:47:18
	 * @update 2017-5-10 下午3:47:18
	 */
	private void addAttributeCount(PageResult<GarbageDataVo> page) {
		List<GarbageDataVo> rowList = page.getRows();
		for (GarbageDataVo gbVo : rowList) {
			String seleteSql = gbVo.getSeleteSql();
			if (SqlUtil.verifySQL(seleteSql)) {
				String countSql = "select count(*) from (" + seleteSql + ") t";
				Long sqlCount = DbUtil.queryForLong(countSql);
				gbVo.setSqlCount(sqlCount);
			} else {
				gbVo.setSqlCount(0L);
				gbVo.setInvalid(true);
			}
		}

	}

	/*
	 * (非 Javadoc) <p>Title: pagingGarbage</p> <p>Description:
	 * 根据t_bdad_garbagedata 表select_sql字段 返回分页报表 以及表头</p>
	 * 
	 * @param page
	 * 
	 * @param reportViewVo
	 * 
	 * @return
	 * 
	 * @see
	 * com.act.web.module.bdad.service.GarbageDataService#pagingGarbage(com.
	 * act.framework.util.PageResult, com.act.web.module.bdad.vo.ReportViewVo)
	 */
	@Override
	public ReportViewVo pagingGarbage(PageResult<Map<String, Object>> page,
			ReportViewVo reportViewVo) {
		ReportViewVo result = new ReportViewVo();
		String sql = reportViewVo.getReportSql();
		PageResult<Map<String, Object>> pageResult = DbUtil.pageMapListBySql(
				sql, page.getPageIndex(), page.getPageSize());
		String[] title = DbUtil.getHeaderBySql(sql);
		result.setPage(pageResult);
		result.setTitle(title);
		return result;
	}

	/*
	 * (非 Javadoc) <p>Title: clearData</p> <p>Description: 根据t_bdad_garbagedata
	 * 表delete_sql字段 删除数据库中内容</p>
	 * 
	 * @param garbageDelete
	 * 
	 * @return 返回值为0时.出现异常
	 * 
	 * @see
	 * com.act.web.module.bdad.service.GarbageDataService#clearData(java.lang
	 * .String)
	 */
	@Override
	public Integer clearData(String garbageDelete) {
		Integer deletes = DbUtil.update(garbageDelete);
		return deletes;
	}

}
