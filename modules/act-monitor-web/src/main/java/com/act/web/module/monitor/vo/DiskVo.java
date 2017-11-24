package com.act.web.module.monitor.vo;

import java.util.List;
import java.util.Map;

public class DiskVo extends BasicVo{
	private List<Map<String,Object>> diskInfo;
	private String diskTotalSize;
	
	private String useRate;
	
	private Integer diskNum;
	
	private String diskName;

	public String getDiskTotalSize() {
		return diskTotalSize;
	}
	public void setDiskTotalSize(String diskTotalSize) {
		this.diskTotalSize = diskTotalSize;
	}

	public String getUseRate() {
		return useRate;
	}
	public void setUseRate(String useRate) {
		this.useRate = useRate;
	}
	public String getDiskName() {
		return diskName;
	}
	public void setDiskName(String diskName) {
		this.diskName = diskName;
	}
	public Integer getDiskNum() {
		return diskNum;
	}
	public void setDiskNum(Integer diskNum) {
		this.diskNum = diskNum;
	}
	public List<Map<String, Object>> getDiskInfo() {
		return diskInfo;
	}
	public void setDiskInfo(List<Map<String, Object>> diskInfo) {
		this.diskInfo = diskInfo;
	}

}