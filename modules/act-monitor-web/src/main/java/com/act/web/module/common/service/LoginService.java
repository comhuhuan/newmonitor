/**   
 * @Title: LoginService.java 
 * @Package  com.act.web.module.common.service 
 * @Description: (登入模块service) 
 * @author   fmj
 * @date 2017-2-6 下午4:51:52 
 * @version V1.0   
 */
package com.act.web.module.common.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import com.act.monitor.model.TabMenu;
import com.act.monitor.model.TabSecondaryMenu;
import com.act.monitor.model.TabSysManageInfo;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.common.vo.UnitSystemVersionVo;


public interface LoginService {

	// 得到 TabSysUser(tab_sys_user) 根据UserId
	TabSysUser findByUserId(String uid);

	// 更新 TabSysUser(tab_sys_user) 根据UserId 和 密码
	void updateUserPwd(String encryptPwd, String uid);

	// 更新 TabSysUser(tab_sys_user) 根据UserId 和 密码
	void updateLonginUser(Integer loginAmount, Timestamp lastTime, String uid);

	// 检查 登入IP(tab_ip_limit) 是否受限 不受限为true，受限为false
//	boolean checkIpLimit(String ip);

	// 根据userId判断当前用户是不管理员权限
	boolean getRoleByUserId(String userId);

	// 根据userId查找用户本身所具有的区域信息
//	String findServicePurview(String userId);

	// 根据权限得到所属机房
//	Map<String, String> findAllRoomName(String purService);

	// 查找配置值，如果有配置值则返回配置值，否则返回默认值
	String getConfigValue(String config);

	// 读取配置值并修改常量值
	//void updateConstant();

	List<TabSysManageInfo> findAllSubSystem();

	// 根据用户名Id 系统Id 是否管理员 来返回一级菜单list(加入ehcache缓存)
	List<TabMenu> getFirstMenu(String userId, String sv, boolean isNoAdmin);

	// 根据用户名ID 得到按钮权限 返回一级菜单权限list (加入ehcache缓存)
	List<TabSecondaryMenu> findAllPurview(String userId, boolean isNotAdmin);

	// 根据登录名和一级菜单的id 返回二级菜单列表 (加入ehcache缓存)
	List<Map<String, Object>> getSecMenuList(List<TabMenu> list, String userId,
			String sv, boolean isNoAdmin);

	// 返回按钮权限列表 (加入ehcache缓存)
	Map<String, List<String>> getPurviewMapList(
			List<TabSecondaryMenu> purviewList);
	
	//得到系统版本
	UnitSystemVersionVo findVersion();
	
	//得到系统信息
	TabSysManageInfo findSysInfo(String syamanageId);
}
