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

public class DeviceStatusVo extends BaseVo{ //TODO
	private Integer badDviceNum;	//故障服务器数量
	private Integer dviceNum;	//服务器总的数量
	private float  percent;//百分比
	private String idcID;	//IDCID
	private String idcName;	//IDC名称
	private Long houseID;//机房ID
	private String houseName;
	private String euID;
	private String cuID;
	private String euName;
	private String cuName;
	private String recordTime;	//更新时间
	private Integer status;//设备状态
	private String uuid;//运营商唯一标识
	private String tableName;//表名
	private Integer devceType;//1-CU 2-EU
	private Integer cuToSmms;//CU到管局通信状态
	private Integer cuToDu;//CU到DU通信状态
	private Integer mechineStatus;//设备状态
	private Integer euToCu;//eu到cu通信状态
	private Integer euToDu;//eu到Du通信状态
	private Integer euServiceStat;//eu服务状态
	private Integer linkStatus;//链路状态
	private Integer networkStatus;//网卡状态
	private String ip;
	private Integer cuType;//CU设备类型 1-WEB 2-INTER 4-DB
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
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
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

	public String getEuID() {
		return euID;
	}
	public void setEuID(String euID) {
		this.euID = euID;
	}
	public String getCuID() {
		return cuID;
	}
	public void setCuID(String cuID) {
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
	public Integer getDevceType() {
		return devceType;
	}
	public void setDevceType(Integer devceType) {
		this.devceType = devceType;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}
	public Integer getCuToSmms() {
		return cuToSmms;
	}
	public void setCuToSmms(Integer cuToSmms) {
		this.cuToSmms = cuToSmms;
	}
	public Integer getCuToDu() {
		return cuToDu;
	}
	public void setCuToDu(Integer cuToDu) {
		this.cuToDu = cuToDu;
	}
	public Integer getMechineStatus() {
		return mechineStatus;
	}
	public void setMechineStatus(Integer mechineStatus) {
		this.mechineStatus = mechineStatus;
	}
	public Integer getEuToCu() {
		return euToCu;
	}
	public void setEuToCu(Integer euToCu) {
		this.euToCu = euToCu;
	}
	public Integer getEuToDu() {
		return euToDu;
	}
	public void setEuToDu(Integer euToDu) {
		this.euToDu = euToDu;
	}

	public Integer getEuServiceStat() {
		return euServiceStat;
	}
	public void setEuServiceStat(Integer euServiceStat) {
		this.euServiceStat = euServiceStat;
	}
	public Integer getLinkStatus() {
		return linkStatus;
	}
	public void setLinkStatus(Integer linkStatus) {
		this.linkStatus = linkStatus;
	}
	public Integer getNetworkStatus() {
		return networkStatus;
	}
	public void setNetworkStatus(Integer networkStatus) {
		this.networkStatus = networkStatus;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Integer getCuType() {
		return cuType;
	}
	public void setCuType(Integer cuType) {
		this.cuType = cuType;
	}
}
