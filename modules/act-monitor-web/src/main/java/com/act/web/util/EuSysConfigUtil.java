/**
 * @Title: SysConfigUtil.java 
 * @Package com.act.web.util 
 * @Description: 获取配置
 * @date 2017-6-9 上午11:13:19
 * @version V1.0   
 */
package com.act.web.util;

import com.act.web.module.common.service.EuSysConfigService;

import java.util.Map;

public class EuSysConfigUtil {

	/**
	 * Eu接口后缀
	 */
	public static final String EU_WS_INTERFACE = "eu_ws_interface";




	public static final String ZJLX_CODE_TYPE = "zjlx";
	

	private static EuSysConfigService euSysConfigService= SpringContextUtil
			.getBean("euSysConfigServiceImpl");

	/**
	 * @Title: loadConfig
	 * @Description:根据tab_sys_config 的configId 字段 返回configval值
	 * @param configId
	 * @throws
	 * @create 2017-6-9 下午12:36:20
	 * @update 2017-6-9 下午12:36:20
	 */
	public static String loadConfig(String configId) {
		Map<String, String> resultMap = euSysConfigService.loadEuConfig();
		String result = resultMap.get(configId.trim());
		return result;
	}
}
