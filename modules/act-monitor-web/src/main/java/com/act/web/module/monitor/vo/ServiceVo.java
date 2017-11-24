package com.act.web.module.monitor.vo;


public class ServiceVo extends BasicVo{
	
	private Integer actLog_uploadNum;//行为日志
	
	private Integer monitorLog_uploadNum;//检测日志
	
	private Integer blockLog_uploadNum;//封堵日志
	
	private Integer statLog_uploadNum;//活跃状态
	
	private Integer resLog_uploadNum;//活跃资源
	
	private Integer occupation;
	
	private Integer actlog_num;
	
	public Integer getActLog_uploadNum() {
		return actLog_uploadNum;
	}

	public void setActLog_uploadNum(Integer actLog_uploadNum) {
		this.actLog_uploadNum = actLog_uploadNum;
	}

	public Integer getOccupation() {
		return occupation;
	}

	public void setOccupation(Integer occupation) {
		this.occupation = occupation;
	}

	public Integer getMonitorLog_uploadNum() {
		return monitorLog_uploadNum;
	}

	public void setMonitorLog_uploadNum(Integer monitorLog_uploadNum) {
		this.monitorLog_uploadNum = monitorLog_uploadNum;
	}

	public Integer getBlockLog_uploadNum() {
		return blockLog_uploadNum;
	}

	public void setBlockLog_uploadNum(Integer blockLog_uploadNum) {
		this.blockLog_uploadNum = blockLog_uploadNum;
	}

	public Integer getStatLog_uploadNum() {
		return statLog_uploadNum;
	}

	public void setStatLog_uploadNum(Integer statLog_uploadNum) {
		this.statLog_uploadNum = statLog_uploadNum;
	}

	public Integer getResLog_uploadNum() {
		return resLog_uploadNum;
	}

	public void setResLog_uploadNum(Integer resLog_uploadNum) {
		this.resLog_uploadNum = resLog_uploadNum;
	}

	public Integer getActlog_num() {
		return actlog_num;
	}

	public void setActlog_num(Integer actlog_num) {
		this.actlog_num = actlog_num;
	}

}