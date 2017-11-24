/**
 * @Title: SqlEngineVo.java 
 * @Package com.act.web.module.bdad.vo 
 * @Description: (sql引擎Vo) 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-7 上午9:53:32
 * @version V1.0   
 */
package com.act.web.module.xxx.vo;

import java.util.Date;

import com.act.web.module.common.vo.BaseVo;

public class SqlEngineVo extends BaseVo {

	// t_bdad_sqlengine
	private Integer engineId;// engine_id(主键)
	private String sqlSentence;// 查询sql sql_sentence
	private String remark;// 备注 remark
	private Date createDate;// 创建时间 createDate
	private Date updateDate;// 修改时间 updateDate

	// left join tab_sys_user user_id
	private String creator;// 创建者 creator
	private String modifier;// 修改者 modifier

	// 查询条件 t_bdad_sqlengine
	private String sqlSentenceQuery;// 查询sql sql_sentence
	private String remarkQuery;// 备注 remark
	private String[] createDateQuery;// 创建时间 createDate
	private String[] updateDateQuery;// 修改时间 updateDate

	// left join tab_sys_user user_id
	private String creatorQuery;// 创建者 creator
	private String modifierQuery;// 修改者 modifier

	public Integer getEngineId() {
		return engineId;
	}

	public void setEngineId(Integer engineId) {
		this.engineId = engineId;
	}

	public String getSqlSentence() {
		return sqlSentence;
	}

	public void setSqlSentence(String sqlSentence) {
		this.sqlSentence = sqlSentence;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public String getModifier() {
		return modifier;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getSqlSentenceQuery() {
		return sqlSentenceQuery;
	}

	public void setSqlSentenceQuery(String sqlSentenceQuery) {
		this.sqlSentenceQuery = sqlSentenceQuery;
	}

	public String getRemarkQuery() {
		return remarkQuery;
	}

	public void setRemarkQuery(String remarkQuery) {
		this.remarkQuery = remarkQuery;
	}

	public String[] getCreateDateQuery() {
		return createDateQuery;
	}

	public void setCreateDateQuery(String[] createDateQuery) {
		this.createDateQuery = createDateQuery;
	}

	public String[] getUpdateDateQuery() {
		return updateDateQuery;
	}

	public void setUpdateDateQuery(String[] updateDateQuery) {
		this.updateDateQuery = updateDateQuery;
	}

	public String getCreatorQuery() {
		return creatorQuery;
	}

	public void setCreatorQuery(String creatorQuery) {
		this.creatorQuery = creatorQuery;
	}

	public String getModifierQuery() {
		return modifierQuery;
	}

	public void setModifierQuery(String modifierQuery) {
		this.modifierQuery = modifierQuery;
	}


}
