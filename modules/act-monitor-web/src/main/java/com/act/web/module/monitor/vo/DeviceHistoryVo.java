/**
 * @Title: DeviceVo.java 
 * @Package com.act.web.module.monitor.vo 
 * @Description: 设备状态vo 
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月20日17:26:16
 * @version V1.0   
*/
package com.act.web.module.monitor.vo;

import com.act.web.module.common.vo.BaseVo;

public class DeviceHistoryVo extends BaseVo{ //TODO
	private Integer badDviceNum;	//故障服务器数量
	private Integer dviceNum;	//服务器总的数量
	private Integer pageIndex;
	private Integer pageSize;
	private String idcID;	//IDCID
	private String idcName;	//IDC名称
	private Long houseID;//机房ID
	private Integer euId;
	private Integer cuID;
	private String euName;
	private String cuName;
	private String recordTime;	//更新时间
	private Integer status;//设备状态
	private String uuid;//运营商唯一标识
	private String tableName;//表名
	private String elementType;//查询类型
	private Integer dateNum = 7;//查询的天数
	public Integer getBadDviceNum() {
		return badDviceNum;
	}
	public void setBadDviceNum(Integer badDviceNum) {
		this.badDviceNum = badDviceNum;
	}
	public Integer getDviceNum() {
		return dviceNum;
	}
	public void setDviceNum(Integer dviceNum) {
		this.dviceNum = dviceNum;
	}
	public String getIdcID() {
		return idcID;
	}
	public void setIdcID(String idcID) {
		this.idcID = idcID;
	}
	public String getIdcName() {
		return idcName;
	}
	public void setIdcName(String idcName) {
		this.idcName = idcName;
	}

	public Long getHouseID() {
		return houseID;
	}
	public void setHouseID(Long houseID) {
		this.houseID = houseID;
	}
	public Integer getEuId() {
		return euId;
	}
	public void setEuId(Integer euId) {
		this.euId = euId;
	}
	public Integer getCuID() {
		return cuID;
	}
	public void setCuID(Integer cuID) {
		this.cuID = cuID;
	}
	public String getEuName() {
		return euName;
	}
	public void setEuName(String euName) {
		this.euName = euName;
	}
	public String getCuName() {
		return cuName;
	}
	public void setCuName(String cuName) {
		this.cuName = cuName;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
	
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public Integer getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getDateNum() {
		return dateNum;
	}
	public void setDateNum(Integer dateNum) {
		this.dateNum = dateNum;
	}
   
}
