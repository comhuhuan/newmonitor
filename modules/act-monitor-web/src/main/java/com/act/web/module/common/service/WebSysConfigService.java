/**
 * @Title: SystemConfigService.java 
 * @Package com.act.web.module.dnsm.service 
 * @Description: 参数配置
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-24 下午5:11:50
 * @version V1.0   
 */
package com.act.web.module.common.service;



import com.act.web.module.common.vo.ConfigsVo;
import com.act.web.module.common.vo.SystemConfigVo;

import java.util.List;
import java.util.Map;

public interface WebSysConfigService {

	/**
	 * @Title: initializeSmms
	 * @Description: 初始化配置参数
	 * @create 2017-6-9 上午8:54:46
	 * @update 2017-6-9 上午8:54:46
	 */
	List<SystemConfigVo> initialize();

	/**
	 * @Title: resetConfig
	 * @Description: 还原配置
	 * @create 2017-6-9 上午9:55:19
	 * @update 2017-6-9 上午9:55:19
	 */
	void resetConfig();

	/**
	 * @Title: updateConfig
	 * @Description: 配置修改
	 * @param ConfigListVo
	 *            sysList
	 * @create 2017-6-9 上午10:03:19
	 * @update 2017-6-9 上午10:03:19
	 */
	void updateConfig(ConfigsVo sysList) throws Exception;

	/**
	 * @Title: evictConfig
	 * @Description: 清空缓存
	 * @create 2017-6-9 上午11:36:34
	 * @update 2017-6-9 上午11:36:34
	 */
	void evictConfig(String evict);

	/**
	 * @Title: loadConfig
	 * @Description: 清空缓存
	 * @create 2017-6-9 上午11:36:55
	 * @update 2017-6-9 上午11:36:55
	 */
	Map<String, String> loadWebConfig();
}
