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

public class DeviceVo extends BaseVo{ //TODO
	
	private String provId;	//省份ID
	private String provName;	//省份名
	private String idcId;	//运营商ID
	private String idcName;	//运营商名称
	private String houseId;	//机房ID
	private String houseName;	//机房名称
	private Integer deviceType;	//设备类型	1-CU 2-EU
	private String deviceId;	//设备ID
	private Integer isWrong;	//是否异常 0-正常 1-异常
	private String uuid; 	//唯一用户ID
	private String timeStamp;	//状态更新时间
	private String stateType;	//状态类型
	private Integer state;
	private String dateQuery;	//查询日期

	private Integer processStat;
	private Integer cpuStat;
	private Integer memStat;
	private Integer diskStat;

	public Integer getProcessStat() {
		return processStat;
	}

	public void setProcessStat(Integer processStat) {
		this.processStat = processStat;
	}

	public Integer getCpuStat() {
		return cpuStat;
	}

	public void setCpuStat(Integer cpuStat) {
		this.cpuStat = cpuStat;
	}

	public Integer getMemStat() {
		return memStat;
	}

	public void setMemStat(Integer memStat) {
		this.memStat = memStat;
	}

	public Integer getDiskStat() {
		return diskStat;
	}

	public void setDiskStat(Integer diskStat) {
		this.diskStat = diskStat;
	}

	public String getIdcId() {
		return idcId;
	}
	public void setIdcId(String idcId) {
		this.idcId = idcId;
	}
	public String getIdcName() {
		return idcName;
	}
	public void setIdcName(String idcName) {
		this.idcName = idcName;
	}
	public String getHouseId() {
		return houseId;
	}
	public void setHouseId(String houseId) {
		this.houseId = houseId;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public Integer getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(Integer deviceType) {
		this.deviceType = deviceType;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProvId() {
		return provId;
	}
	public void setProvId(String provId) {
		this.provId = provId;
	}
	public String getProvName() {
		return provName;
	}
	public void setProvName(String provName) {
		this.provName = provName;
	}
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public Integer getIsWrong() {
		return isWrong;
	}
	public void setIsWrong(Integer isWrong) {
		this.isWrong = isWrong;
	}
	public String getStateType() {
		return stateType;
	}
	public void setStateType(String stateType) {
		this.stateType = stateType;
	}
	public String getDateQuery() {
		return dateQuery;
	}
	public void setDateQuery(String dateQuery) {
		this.dateQuery = dateQuery;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}

	
}
