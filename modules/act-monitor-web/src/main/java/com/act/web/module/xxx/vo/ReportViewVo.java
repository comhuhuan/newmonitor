/**
 * @Title: ReportViewVo.java 
 * @Package com.act.web.module.bdad.vo 
 * @Description: 报表查看VO
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-10 下午4:57:38
 * @version V1.0   
 */
package com.act.web.module.xxx.vo;

import java.util.Map;

import com.act.framework.util.PageResult;
import com.act.web.module.common.vo.BaseVo;

public class ReportViewVo extends BaseVo{

	private String reportSql;

	private PageResult<Map<String, Object>> page;

	private String[] title;

	public PageResult<Map<String, Object>> getPage() {
		return page;
	}

	public void setPage(PageResult<Map<String, Object>> page) {
		this.page = page;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public String getReportSql() {
		return reportSql;
	}

	public void setReportSql(String reportSql) {
		this.reportSql = reportSql;
	}

}
