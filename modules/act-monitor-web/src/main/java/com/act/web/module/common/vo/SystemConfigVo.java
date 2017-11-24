/**
 * @Title: SystemConfig.java 
 * @Package com.act.web.module.dnsm.vo 
 * @Description: 系统配置类 
 * @author   fmj
 * @modifier fmj
 * @date 2017-6-9 上午8:53:03
 * @version V1.0   
 */
package com.act.web.module.common.vo;

public class SystemConfigVo {
	private String configid;
	private String defaultval;
	private String configval;
	private String title;
	private String showType;

	public String getConfigid() {
		return configid;
	}

	public void setConfigid(String configid) {
		this.configid = configid;
	}

	public String getDefaultval() {
		return defaultval;
	}

	public void setDefaultval(String defaultval) {
		this.defaultval = defaultval;
	}

	public String getConfigval() {
		return configval;
	}

	public void setConfigval(String configval) {
		this.configval = configval;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getShowType() {
		return showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

}
