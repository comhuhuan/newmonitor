package com.act.web.module.monitor.vo;

public class MemoryVo extends BasicVo{
	
	private String memoryBuffer;	//缓冲区内存
	private String memoryFree;		//空闲内存
	private String memoryUsed;		//使用内存
	private String memoryRate;		//内存使用率
	
	public String getMemoryBuffer() {
		return memoryBuffer;
	}
	public void setMemoryBuffer(String memoryBuffer) {
		this.memoryBuffer = memoryBuffer;
	}
	public String getMemoryFree() {
		return memoryFree;
	}
	public void setMemoryFree(String memoryFree) {
		this.memoryFree = memoryFree;
	}
	public String getMemoryRate() {
		return memoryRate;
	}
	public void setMemoryRate(String memoryRate) {
		this.memoryRate = memoryRate;
	}
	public String getMemoryUsed() {
		return memoryUsed;
	}
	public void setMemoryUsed(String memoryUsed) {
		this.memoryUsed = memoryUsed;
	}
}