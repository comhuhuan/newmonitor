/**
 * @Title: SysConfigUtil.java 
 * @Package com.act.web.util 
 * @Description: 获取配置
 * @date 2017-6-9 上午11:13:19
 * @version V1.0   
 */
package com.act.web.util;

import com.act.web.module.common.service.CuSysConfigService;

import java.util.Map;

public class CuSysConfigUtil {

	/**
	 * 信安数据库IP
	 */
	public static final String UCENTER_DB_IP = "ucenter_db_ip";

	

	private static CuSysConfigService cuSysConfigService= SpringContextUtil
			.getBean("cuSysConfigServiceImpl");

	/**
	 * @Title: loadConfig
	 * @Description:根据tab_sys_config 的configId 字段 返回configval值
	 * @param configId
	 * @throws
	 * @create 2017-6-9 下午12:36:20
	 * @update 2017-6-9 下午12:36:20
	 */
	public static String loadConfig(String configId) {
		Map<String, String> resultMap = cuSysConfigService.loadCuConfig();
		String result = resultMap.get(configId);
		return result;
	}
}
