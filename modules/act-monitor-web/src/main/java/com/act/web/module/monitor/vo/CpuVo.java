package com.act.web.module.monitor.vo;

import java.util.Map;

public class CpuVo extends BasicVo{
	private Map<String,Object> coreRate;//单个cpu使用率
	private String coreNum;	//cpu总数
	private String cpuTotalRate; //cpu总使用率
	private String cpu;
	private float cpuFrequcy;
	private long memoryTotal;
	private String cpuModel;
	public String getCpu() {
		return cpu;
	}
	public void setCpu(String cpu) {
		this.cpu = cpu;
	}
	public Map<String,Object> getCoreRate() {
		return coreRate;
	}
	public void setCoreRate(Map<String,Object> coreRate) {
		this.coreRate = coreRate;
	}
	public String getCoreNum() {
		return coreNum;
	}
	public void setCoreNum(String coreNum) {
		this.coreNum = coreNum;
	}
	public String getCpuTotalRate() {
		return cpuTotalRate;
	}
	public void setCpuTotalRate(String cpuTotalRate) {
		this.cpuTotalRate = cpuTotalRate;
	}
	public float getCpuFrequcy() {
		return cpuFrequcy;
	}
	public void setCpuFrequcy(float cpuFrequcy) {
		this.cpuFrequcy = cpuFrequcy;
	}
	public long getMemoryTotal() {
		return memoryTotal;
	}
	public void setMemoryTotal(long memoryTotal) {
		this.memoryTotal = memoryTotal;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	
}