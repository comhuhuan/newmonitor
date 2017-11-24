/**
 * @Title: SystemConfigController.java
 * @Package com.act.web.module.dnsm.controller
 * @Description: XXX(用一句话描述该文件做什么)
 * @author fmj
 * @modifier fmj
 * @date 2017-5-24 下午4:50:45
 * @version V1.0
 */
package com.act.web.module.common.controller;


import com.act.web.module.common.service.CuSysConfigService;
import com.act.web.module.common.vo.ConfigsVo;
import com.act.web.module.common.vo.SystemConfigVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/common/cuSysConfig")
public class CuSysConfigController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(CuSysConfigController.class);

    /**
     * 对应SystemConfigService中 evictConfig方法 loadConfig缓存的key
     */
    private String LOAD_CONFIG = "loadCuConfig";

    @Resource
    private CuSysConfigService cuSysConfigService;

    /**
     * @Title: initialize 初始化页面 tab_sys_config 研究院
     * @create 2017-5-8 下午4:21:33
     * @update 2017-5-8 下午4:21:33
     */
    @ResponseBody
    @RequestMapping(value = "/initialize.do")
    public Object initialize() {
        try {
            List<SystemConfigVo> result = cuSysConfigService.initialize();
            return ajax(Status.success, result);
        } catch (Exception e) {
            return ajax(Status.error, "初始化配置失败!");
        }
    }

    /**
     * @Title: updateConfig 更新配置 tab_sys_config
     * @create 2017-5-8 下午4:21:33
     * @update 2017年6月9日16:02:00
     */
    @ResponseBody
    @RequestMapping(value = "/updateConfig.do")
    public Object updateConfig(ConfigsVo sysList) {
        String result = "";
        try {
            cuSysConfigService.updateConfig(sysList);
            cuSysConfigService.evictConfig(LOAD_CONFIG);
            return ajax(Status.success, result);
        } catch (Exception e) {
            logger.error("更新配置失败", e);
            return ajax(Status.error, "更新配置失败!");
        }
    }

    /**
     * @Title: resetConfig 还原配置 tab_sys_config
     * @create 2017-5-8 下午4:21:33
     * @update 2017年6月9日16:02:04
     */
    @ResponseBody
    @RequestMapping(value = "/resetConfig.do")
    public Object resetConfig() {
        String result = "";
        try {
            cuSysConfigService.resetConfig();
            cuSysConfigService.evictConfig("loadCuConfig");
            return ajax(Status.success, result);
        } catch (Exception e) {
            return ajax(Status.error, "还原配置失败!");
        }
    }


}
