package com.act.web.module.monitor.vo;

import java.util.List;

public class NetWorkVo{
	private String soft_version;
	private String elementType;		//状态类型
	private String euID;
	private String euIP;
	private Long houseID;
	private String houseName;
	private String recordTime;
	private Integer status;

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	private List<NetWorkInfoVo> networkInfo;
	public String getSoft_version() {
		return soft_version;
	}
	public void setSoft_version(String soft_version) {
		this.soft_version = soft_version;
	}
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
	public String getEuIP() {
		return euIP;
	}
	public void setEuIP(String euIP) {
		this.euIP = euIP;
	}

	public Long getHouseID() {
		return houseID;
	}
	public void setHouseID(Long houseID) {
		this.houseID = houseID;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public List<NetWorkInfoVo> getNetworkInfo() {
		return networkInfo;
	}
	public void setNetworkInfo(List<NetWorkInfoVo> networkInfo) {
		this.networkInfo = networkInfo;
	}
	public String getRecordTime() {
		return recordTime;
	}
	public void setRecordTime(String recordTime) {
		this.recordTime = recordTime;
	}

	
}