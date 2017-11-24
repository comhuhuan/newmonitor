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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DevicePagesVo extends BasicVo{ //TODO
	private String houseName;
	private String idcName;
	private String idcID;
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

	public String getIdcName() {
		return idcName;
	}
	public void setIdcName(String idcName) {
		this.idcName = idcName;
	}
	public String getIdcID() {
		return idcID;
	}
	public void setIdcID(String idcID) {
		this.idcID = idcID;
	}
	public String getHouseName() {
		return houseName;
	}
	public void setHouseName(String houseName) {
		this.houseName = houseName;
	}

}
