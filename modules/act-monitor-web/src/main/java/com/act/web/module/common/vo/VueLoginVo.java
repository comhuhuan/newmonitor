/**   
 * @Title: VueLoginVo.java 
 * @Package com.act.web.module.common.vo
 * @Description: (vue登入) 
 * @author   fmj
 * @date 2017-3-30 下午8:02:23 
 * @version V1.0   
 */
package com.act.web.module.common.vo;

import java.util.List;
import java.util.Map;

import com.act.monitor.model.TabSysManageInfo;
import com.act.monitor.model.TabSysUser;

public class VueLoginVo {
	private Integer code;
	private String msg;
	private TabSysUser user;
	private Object[] authorization;
	private UnitSystemVersionVo unitSystemVersion;
	private TabSysManageInfo tabSysManageInfo;
	private Map<String, List<String>> secMenuPurview;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public TabSysUser getUser() {
		return user;
	}

	public void setUser(TabSysUser user) {
		this.user = user;
	}

	public Object[] getAuthorization() {
		return authorization;
	}

	public void setAuthorization(Object[] authorization) {
		this.authorization = authorization;
	}

	public UnitSystemVersionVo getUnitSystemVersion() {
		return unitSystemVersion;
	}

	public void setUnitSystemVersion(UnitSystemVersionVo unitSystemVersion) {
		this.unitSystemVersion = unitSystemVersion;
	}

	public TabSysManageInfo getTabSysManageInfo() {
		return tabSysManageInfo;
	}

	public void setTabSysManageInfo(TabSysManageInfo tabSysManageInfo) {
		this.tabSysManageInfo = tabSysManageInfo;
	}

	public Map<String, List<String>> getSecMenuPurview() {
		return secMenuPurview;
	}

	public void setSecMenuPurview(Map<String, List<String>> secMenuPurview) {
		this.secMenuPurview = secMenuPurview;
	}

}
