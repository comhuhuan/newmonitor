package com.act.web.module.monitor.vo;

public class NetWorkInfoVo extends BasicVo{
	private String mac;
	private String connStatus;
	private String cardName;		//状态类型
	private float bps;
	private Integer cardFunction;			//总状态
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}
	public float getBps() {
		return bps;
	}
	public void setBps(float bps) {
		this.bps = bps;
	}
	public Integer getCardFunction() {
		return cardFunction;
	}
	public void setCardFunction(Integer cardFunction) {
		this.cardFunction = cardFunction;
	}
	public String getConnStatus() {
		return connStatus;
	}
	public void setConnStatus(String connStatus) {
		this.connStatus = connStatus;
	}
	
}