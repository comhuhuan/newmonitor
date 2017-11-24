/**
 * @Title: UserManageController.java 
 * @Package com.act.web.module.monitor.controller 
 * @Description: 用户管理
 * @author   liuyang
 * @modifier liuyang
 * @date 2017年6月30日16:14:50
 * @version V1.0   
 */
package com.act.web.module.monitor.controller;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.act.framework.util.PageResult;
import com.act.monitor.model.TabSysUser;
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.UserManageService;
import com.act.web.module.monitor.vo.UserVo;

@Controller
@RequestMapping("/monitor/user")
public class UserManageController extends BaseController {

	private final Logger log = LoggerFactory.getLogger(UserManageController.class);
	
	@Resource
	private UserManageService userManageService;
	
	/**
	 * @Title: pagingList
	 * @Description:用户管理 分页查询 //TODO
	 * @create 2017年6月30日16:31:26
	 * @update 2017年6月30日16:31:26
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingList.do")
	public Object pagingList(PageResult<UserVo> page, UserVo userVo) {
		try {
			page = userManageService.pagingList(page, userVo);
		} catch (Exception e) {
			log.error("分页查询失败!", e);
			return ajax(Status.error, "查询失败!");
		}
		return ajax(Status.success, page);
	}
	
	/**
	 * @Title: getIdcList
	 * @Description:获取全国运营商 //数据来源 monsys_all_idc_info
	 * @create 2017年7月4日11:08:59
	 * @update 2017年7月4日11:08:59
	 */
	@ResponseBody
	@RequestMapping(value = "/getIdcList.do")
	public Object getIdcList() {
		try {
			Object result = userManageService.getIdcList();
			return ajax(Status.success, result);
		} catch (Exception e) {
			log.error("分页查询失败!", e);
			return ajax(Status.error, "查询失败!");
		}
	}
	
	/**
	 * @Title: save
	 * @Description:新增/编辑用户
	 * @create 2017年7月4日11:24:30
	 * @update 2017年7月4日11:24:30
	 */
	@ResponseBody
	@RequestMapping(value = "/addUser.do")
	public Object save(TabSysUser user,String commonFlag) {
		try {
			userManageService.save(user,commonFlag);
		} catch (Exception e) {
			log.error("新增用户失败", e);
			return ajax(Status.error, "编辑失败!");
		}
		return ajax(Status.success, "编辑成功");
	}
	
	/**
	 * @Title: remove
	 * @Description: (删除 tab_sys_user)
	 * @param userId
	 * @throws
	 * @create 2017年7月4日16:05:31
	 * @update 2017年7月4日16:05:31
	 */
	@ResponseBody
	@RequestMapping(value = "/remove.do")
	public Object remove(String userId) {
		try {
			userManageService.remove(userId);
		} catch (Exception e) {
			logger.error("删除tab_sys_user失败", e);
			return ajax(Status.error, "删除失败!");
		}
		return ajax(Status.success, "删除成功");
	}
}
