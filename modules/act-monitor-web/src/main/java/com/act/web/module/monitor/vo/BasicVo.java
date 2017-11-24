package com.act.web.module.monitor.vo;

import com.act.web.module.common.vo.BaseVo;

public class BasicVo extends BaseVo{
	private String soft_version;
	private Integer recordNum = 7;			//返回记录的数量
	private String queryDate;
	private String elementType;		//状态类型
	private String euID;
	private String cuIP;
	private String euIP;
	private String queryType;		//查询类型,非必填项 当elementType为euDevice或cuDevice时存在
	private String recordTime;		//记录时间
	private Integer status;			//总状态
	private String deviceId;		//设备ID
	private Long houseID;			//机房ID
	private String houseName;
	private String uuid;			//uuid
	private Integer cuType; //CU设备类型 1-web 2-inter 3-db
	private Integer inflow;
			//查询时间
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	public String getEuID() {
		return euID;
	}
	public void setEuID(String euID) {
		this.euID = euID;
	}
	public String getCuIP() {
		return cuIP;
	}
	public void setCuIP(String cuIP) {
		this.cuIP = cuIP;
	}
	public String getQueryType() {
		return queryType;
	}
	public void setQueryType(String queryType) {
		this.queryType = queryType;
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
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public Long getHouseID() {
		return houseID;
	}
	public void setHouseID(Long houseID) {
		this.houseID = houseID;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Integer getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(Integer recordNum) {
		this.recordNum = recordNum;
	}
	public String getQueryDate() {
		return queryDate;
	}
	public void setQueryDate(String queryDate) {
		this.queryDate = queryDate;
	}
	public String getEuIP() {
		return euIP;
	}
	public void setEuIP(String euIP) {
		this.euIP = euIP;
	}
	public String getSoft_version() {
		return soft_version;
	}
	public void setSoft_version(String soft_version) {
		this.soft_version = soft_version;
	}
	public Integer getCuType() {
		return cuType;
	}
	public void setCuType(Integer cuType) {
		this.cuType = cuType;
	}
	public Integer getInflow() {
		return inflow;
	}
	public void setInflow(Integer inflow) {
		this.inflow = inflow;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	
}