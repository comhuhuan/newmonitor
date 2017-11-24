/**   
 * @Title: VueVo.java 
 * @Package com.act.web.module.common.vo 
 * @Description:  vue Framework vue vo test
 * @author   fmj
 * @date 2017-3-20 下午4:05:52 
 * @version V1.0   
 */
package com.act.web.module.common.vo;

import java.util.Date;

public class VueVo extends BaseVo {
	// 查询列表数据
	private String idcid;
	private String idcname;
	private String corp;
	private String idczip;
	private String idcadd;
	private String idcofficer;
	private String emergencycontact;
	private Date timestamp;
	private boolean ftpstatus;
	private Integer idcispnum;
	private Integer usernum;

	private String idc_type;// 证件类型代码
	private String idc_cardname;// 证件类型名称
	private String idc_ID;// 证件号码
	private String idc_tel;// 固定电话
	private String idc_mobile;// 移动电话
	private String idc_Email;// Email 地址
	private String idc_name;// 网络安全责任人

	private String em_IDtype;// 证件类型代码
	private String em_cardname;// 证件类型名称
	private String em_ID;// 证件号码
	private String em_tel;// 固定电话
	private String em_mobile;// 移动电话
	private String em_Email;// Email 地址
	private String em_name;// 网络安全责任人

	// 查询条件
	private String idcIdQuery;
	private String idcNameQuery;
	private String corpQuery;
	private String idcZipQuery;
	private String idcAddQuery;
	private String idcOfficerQuery;
	private String emergencyContactQuery;
	private String[] timeStampQuery;
	private String ftpStatusQuery;

	private String deleteIds;

	public String getIdcid() {
		return idcid;
	}

	public void setIdcid(String idcid) {
		this.idcid = idcid;
	}

	public String getIdcname() {
		return idcname;
	}

	public void setIdcname(String idcname) {
		this.idcname = idcname;
	}

	public String[] getTimeStampQuery() {
		return timeStampQuery;
	}

	public void setTimeStampQuery(String[] timeStampQuery) {
		this.timeStampQuery = timeStampQuery;
	}

	public String getCorp() {
		return corp;
	}

	public void setCorp(String corp) {
		this.corp = corp;
	}

	public String getIdczip() {
		return idczip;
	}

	public void setIdczip(String idczip) {
		this.idczip = idczip;
	}

	public String getIdcadd() {
		return idcadd;
	}

	public void setIdcadd(String idcadd) {
		this.idcadd = idcadd;
	}

	public String getIdcofficer() {
		return idcofficer;
	}

	public void setIdcofficer(String idcofficer) {
		this.idcofficer = idcofficer;
	}

	public String getEmergencycontact() {
		return emergencycontact;
	}

	public void setEmergencycontact(String emergencycontact) {
		this.emergencycontact = emergencycontact;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public boolean isFtpstatus() {
		return ftpstatus;
	}

	public void setFtpstatus(boolean ftpstatus) {
		this.ftpstatus = ftpstatus;
	}

	public Integer getIdcispnum() {
		return idcispnum;
	}

	public void setIdcispnum(Integer idcispnum) {
		this.idcispnum = idcispnum;
	}

	public Integer getUsernum() {
		return usernum;
	}

	public void setUsernum(Integer usernum) {
		this.usernum = usernum;
	}

	public String getIdc_type() {
		return idc_type;
	}

	public void setIdc_type(String idc_type) {
		this.idc_type = idc_type;
	}

	public String getIdc_cardname() {
		return idc_cardname;
	}

	public void setIdc_cardname(String idc_cardname) {
		this.idc_cardname = idc_cardname;
	}

	public String getIdc_ID() {
		return idc_ID;
	}

	public void setIdc_ID(String idc_ID) {
		this.idc_ID = idc_ID;
	}

	public String getIdc_tel() {
		return idc_tel;
	}

	public void setIdc_tel(String idc_tel) {
		this.idc_tel = idc_tel;
	}

	public String getIdc_mobile() {
		return idc_mobile;
	}

	public void setIdc_mobile(String idc_mobile) {
		this.idc_mobile = idc_mobile;
	}

	public String getIdc_Email() {
		return idc_Email;
	}

	public void setIdc_Email(String idc_Email) {
		this.idc_Email = idc_Email;
	}

	public String getIdc_name() {
		return idc_name;
	}

	public void setIdc_name(String idc_name) {
		this.idc_name = idc_name;
	}

	public String getEm_IDtype() {
		return em_IDtype;
	}

	public void setEm_IDtype(String em_IDtype) {
		this.em_IDtype = em_IDtype;
	}

	public String getEm_cardname() {
		return em_cardname;
	}

	public void setEm_cardname(String em_cardname) {
		this.em_cardname = em_cardname;
	}

	public String getEm_ID() {
		return em_ID;
	}

	public void setEm_ID(String em_ID) {
		this.em_ID = em_ID;
	}

	public String getEm_tel() {
		return em_tel;
	}

	public void setEm_tel(String em_tel) {
		this.em_tel = em_tel;
	}

	public String getEm_mobile() {
		return em_mobile;
	}

	public void setEm_mobile(String em_mobile) {
		this.em_mobile = em_mobile;
	}

	public String getEm_Email() {
		return em_Email;
	}

	public void setEm_Email(String em_Email) {
		this.em_Email = em_Email;
	}

	public String getEm_name() {
		return em_name;
	}

	public void setEm_name(String em_name) {
		this.em_name = em_name;
	}

	public String getIdcIdQuery() {
		return idcIdQuery;
	}

	public void setIdcIdQuery(String idcIdQuery) {
		this.idcIdQuery = idcIdQuery;
	}

	public String getIdcNameQuery() {
		return idcNameQuery;
	}

	public void setIdcNameQuery(String idcNameQuery) {
		this.idcNameQuery = idcNameQuery;
	}

	public String getCorpQuery() {
		return corpQuery;
	}

	public void setCorpQuery(String corpQuery) {
		this.corpQuery = corpQuery;
	}

	public String getIdcZipQuery() {
		return idcZipQuery;
	}

	public void setIdcZipQuery(String idcZipQuery) {
		this.idcZipQuery = idcZipQuery;
	}

	public String getIdcAddQuery() {
		return idcAddQuery;
	}

	public void setIdcAddQuery(String idcAddQuery) {
		this.idcAddQuery = idcAddQuery;
	}

	public String getIdcOfficerQuery() {
		return idcOfficerQuery;
	}

	public void setIdcOfficerQuery(String idcOfficerQuery) {
		this.idcOfficerQuery = idcOfficerQuery;
	}

	public String getEmergencyContactQuery() {
		return emergencyContactQuery;
	}

	public void setEmergencyContactQuery(String emergencyContactQuery) {
		this.emergencyContactQuery = emergencyContactQuery;
	}

	public String getFtpStatusQuery() {
		return ftpStatusQuery;
	}

	public void setFtpStatusQuery(String ftpStatusQuery) {
		this.ftpStatusQuery = ftpStatusQuery;
	}

	public String getDeleteIds() {
		return deleteIds;
	}

	public void setDeleteIds(String deleteIds) {
		this.deleteIds = deleteIds;
	}

}
