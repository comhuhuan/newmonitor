/**
 * @Title: UserManageService.java 
 * @Package com.act.web.module.monitor.service 
 * @Description: 用户管理页面service
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月30日16:17:58
 * @version V1.0   
*/
package com.act.web.module.monitor.service;

import com.act.framework.util.PageResult;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.monitor.vo.UserVo;

public interface UserManageService {
	
	/**
	 * @param page
	 *            分页属性
	 * @param deviceVo
	 *            查询条件
	 * @Title: pagingList
	 * @Description: 得到分页list 数据来源  //TODO
	 * @create 2017年6月30日16:41:22
	 * @update 2017年6月30日16:41:22
	 */
	PageResult<UserVo> pagingList(PageResult<UserVo> page, UserVo userVo);
	
	/**
	 * @Title: getIdcList
	 * @Description: 获取全国运营商 数据来源  //monsys_all_idc_info
	 * @create 2017年7月4日11:13:34
	 * @update 2017年7月4日11:13:34
	 */
	Object getIdcList();
	
	/**
	 * @throws Exception 
	 * @Title: save
	 * @Description: 新增和修改 tab_sys_user
	 * @create 2017年7月4日11:55:41
	 * @update 2017年7月4日11:55:41
	 */
	void save(TabSysUser user, String commonFlag) throws Exception;
	
	/**
	 * @Title: remove
	 * @Description: 根据主键 user_id 删除 tab_sys_user
	 * @create 2017年7月4日16:07:18
	 * @update 2017年7月4日16:07:18
	 */
	void remove(String userId);
}
