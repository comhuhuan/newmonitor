/**
 * @Title: SysConfigUtil.java 
 * @Package com.act.web.util 
 * @Description: 获取配置
 * @date 2017-6-9 上午11:13:19
 * @version V1.0   
 */
package com.act.web.util;

import com.act.web.module.common.service.WebSysConfigService;

import java.util.Map;

public class WebSysConfigUtil {

	/**
	 * 信安数据库IP
	 */
	public static final String UCENTER_DB_IP = "ucenter_db_ip";

	/**
	 * 信安数据库名字
	 */
	public static final String UCENTER_DB_NAME = "ucenter_db_name";

	/**
	 * 信安数据库密码
	 */
	public static final String UCENTER_DB_PASSWD = "ucenter_db_passwd";
	
	/**
	 * 信安数据库端口
	 */
	public static final String UCENTER_DB_PORT = "ucenter_db_port";
	
	/**
	 * 信安数据库用户名
	 */
	public static final String UCENTER_DB_USER = "ucenter_db_user";




	public static final String ZJLX_CODE_TYPE = "zjlx";
	

	private static WebSysConfigService webSysConfigService= SpringContextUtil
			.getBean("webSysConfigServiceImpl");

	/**
	 * @Title: loadConfig
	 * @Description:根据tab_sys_config 的configId 字段 返回configval值
	 * @param configId
	 * @throws
	 * @create 2017-6-9 下午12:36:20
	 * @update 2017-6-9 下午12:36:20
	 */
	public static String loadConfig(String configId) {
		Map<String, String> resultMap = webSysConfigService.loadWebConfig();
		String result = resultMap.get(configId);
		return result;
	}
}
