package com.act.web.module.monitor.vo;

import java.util.List;
import java.util.Map;

public class ProcessVo extends BasicVo{
	
	private Integer badtotal;	//故障进程总数
	private List<Map<String,Object>> process;	//进程详情
	private Integer total;	//进程总数
	
	
	public List<Map<String, Object>> getProcess() {
		return process;
	}
	public void setProcess(List<Map<String, Object>> process) {
		this.process = process;
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
	
	
}