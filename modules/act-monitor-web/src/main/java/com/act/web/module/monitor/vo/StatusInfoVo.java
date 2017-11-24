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

public class StatusInfoVo extends BasicVo{ //TODO
	private List<ProcessVo> historyProcess;//进程详情
	private List<DiskVo> historyDisk;//磁盘
	private List<CpuVo> historyCpu;//CPU
	private List<MemoryVo> historyMemory;//磁盘
	private List<ServiceVo> historyAccessLog;//DU日志上报
	private List<HistoryStatus> historyStatus = new ArrayList<HistoryStatus>();//状态
	private Integer total;	//进程总数
	private Integer badtotal;	//故障进程总数
	private String maxUsrRate;
	private String selectOption;//被选中的下拉选项
	private List<String> option; //页面的下拉选项

	public List<ProcessVo> getHistoryProcess() {
		return historyProcess;
	}
	public void setHistoryProcess(List<ProcessVo> historyProcess) {
		this.historyProcess = historyProcess;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getBadtotal() {
		return badtotal;
	}
	public void setBadtotal(Integer badtotal) {
		this.badtotal = badtotal;
	}


	public String getMaxUsrRate() {
		return maxUsrRate;
	}
	public void setMaxUsrRate(String maxUsrRate) {
		this.maxUsrRate = maxUsrRate;
	}
	public String getSelectOption() {
		return selectOption;
	}
	public void setSelectOption(String selectOption) {
		this.selectOption = selectOption;
	}
	public List<String> getOption() {
		return option;
	}
	public void setOption(List<String> option) {
		this.option = option;
	}

	
	public List<DiskVo> getHistoryDisk() {
		return historyDisk;
	}
	public void setHistoryDisk(List<DiskVo> historyDisk) {
		this.historyDisk = historyDisk;
	}
	public List<CpuVo> getHistoryCpu() {
		return historyCpu;
	}
	public void setHistoryCpu(List<CpuVo> historyCpu) {
		this.historyCpu = historyCpu;
	}

	public List<MemoryVo> getHistoryMemory() {
		return historyMemory;
	}
	public void setHistoryMemory(List<MemoryVo> historyMemory) {
		this.historyMemory = historyMemory;
	}
	public List<HistoryStatus> getHistoryStatus() {
		return historyStatus;
	}
	public void setHistoryStatus(List<HistoryStatus> historyStatus) {
		this.historyStatus = historyStatus;
	}
	public List<ServiceVo> getHistoryAccessLog() {
		return historyAccessLog;
	}
	public void setHistoryAccessLog(List<ServiceVo> historyAccessLog) {
		this.historyAccessLog = historyAccessLog;
	}
	
}
