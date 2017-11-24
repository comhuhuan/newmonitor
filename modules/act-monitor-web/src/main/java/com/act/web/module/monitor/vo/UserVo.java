/**
 * @Title: UserVo.java 
 * @Package com.act.web.module.monitor.vo 
 * @Description: 用户vo 
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月30日17:14:56
 * @version V1.0   
*/
package com.act.web.module.monitor.vo;

import com.act.web.module.common.vo.BaseVo;

public class UserVo extends BaseVo{ //TODO
	
	private String userId;	//账号
	private String username;	//用户名
	private String password;	//密码
	private String tel;	//手机号码
	private String email;	//邮箱
	private String uuid;	//账户归属
	private String limit;	//权限范围
	private String createTime;	//修改时间
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public String getLimit() {
		return limit;
	}
	public void setLimit(String limit) {
		this.limit = limit;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
}
