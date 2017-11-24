/**
 * @Title: GarbageDataVo.java 
 * @Package com.act.web.module.bdad.vo 
 * @Description: (垃圾数据管理VO) 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-7 上午9:45:24
 * @version V1.0   
 */
package com.act.web.module.xxx.vo;

public class GarbageDataVo {

	// t_bdad_garbagedata a
	private Integer garbageId;// garbage_id(主键)
	private String seleteSql;// 查询sql selete_sql
	private String deleteSql;// 删除sql delete_sql
	private String remark;// 备注 remark
	private Long sqlCount;// count selete_sql
	private Boolean invalid;// sql是否失效
	
	private Integer garbageDataType;//查询garbageId 垃圾类别

	public Integer getGarbageId() {
		return garbageId;
	}

	public void setGarbageId(Integer garbageId) {
		this.garbageId = garbageId;
	}

	public String getSeleteSql() {
		return seleteSql;
	}

	public void setSeleteSql(String seleteSql) {
		this.seleteSql = seleteSql;
	}

	public String getDeleteSql() {
		return deleteSql;
	}

	public void setDeleteSql(String deleteSql) {
		this.deleteSql = deleteSql;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getSqlCount() {
		return sqlCount;
	}

	public void setSqlCount(Long sqlCount) {
		this.sqlCount = sqlCount;
	}

	public Boolean getInvalid() {
		return invalid;
	}

	public void setInvalid(Boolean invalid) {
		this.invalid = invalid;
	}

	public Integer getGarbageDataType() {
		return garbageDataType;
	}

	public void setGarbageDataType(Integer garbageDataType) {
		this.garbageDataType = garbageDataType;
	}
	
	

}
