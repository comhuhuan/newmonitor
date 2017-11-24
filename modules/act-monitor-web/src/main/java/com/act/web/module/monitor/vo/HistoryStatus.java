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


public class HistoryStatus extends BasicVo{ //TODO
	private String euIP;
	private int allRecoder;
	public int getAllRecoder() {
		return allRecoder;
	}

	public void setAllRecoder(int allRecoder) {
		this.allRecoder = allRecoder;
	}

	private List<RangeInfoVo> rangeInfo;
	
	public String getEuIP() {
		return euIP;
	}

	public void setEuIP(String euIP) {
		this.euIP = euIP;
	}

	public List<RangeInfoVo> getRangeInfo() {
		return rangeInfo;
	}

	public void setRangeInfo(List<RangeInfoVo> rangeInfo) {
		this.rangeInfo = rangeInfo;
	}

	
	
}
