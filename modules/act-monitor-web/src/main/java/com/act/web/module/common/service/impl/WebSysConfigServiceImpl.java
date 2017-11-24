/**
 * @Title: SystemConfigServiceImpl.java
 * @Package com.act.web.module.dnsm.service.impl
 * @Description: 参数配置
 * @author fmj
 * @modifier fmj
 * @date 2017-5-24 下午5:17:30
 * @version V1.0
 */
package com.act.web.module.common.service.impl;


import com.act.framework.util.DbUtil;
import com.act.monitor.model.WebSysConfig;
import com.act.web.module.common.service.WebSysConfigService;
import com.act.web.module.common.vo.ConfigsVo;
import com.act.web.module.common.vo.SystemConfigVo;
import com.act.web.util.JsonPluginsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WebSysConfigServiceImpl implements WebSysConfigService {

    private final static Logger log = LoggerFactory
            .getLogger(WebSysConfigServiceImpl.class);

    /*
     * (非 Javadoc) <p>Title: initialize</p> <p>Description: 初始化配置参数 </p>
     *
     * @return
     *
     * @see com.act.web.module.dnsm.service.SystemConfigService#initialize()
     */
    @Override
    public List<SystemConfigVo> initialize() {
        List<SystemConfigVo> result = DbUtil
                .queryForObjectList(
                        " select a.configid,a.defaultval,a.configval,a.title,a.showtype from web_sys_config a where a.hidden = 1 order by a.showseq asc ",
                        SystemConfigVo.class);
        return result;
    }

    /*
     * (非 Javadoc) <p>Title: 还原配置 resetConfig</p> <p>Description: </p>
     *
     * @see com.act.web.module.dnsm.service.SystemConfigService#resetConfig()
     */
    @Override
    public void resetConfig() {
        DbUtil.update(" update web_sys_config  set configval = defaultval where hidden = 1 ");
    }

    /*
     * (非 Javadoc) <p>Title: updateConfig</p> <p>Description: 更新配置</p>
     *
     * @param ConfigListVo sysList
     *
     * @return
     *
     * @see
     * com.act.web.module.dnsm.service.SystemConfigService#updateConfig(com.
     * act.web.module.dnsm.vo.SystemConfigVo)
     */
    @Override
    public void updateConfig(ConfigsVo sysList) throws Exception {
        List<String> sysConfigs = sysList.getSysList();
        for (String sysConfig : sysConfigs) {
            if (sysConfig != null && sysConfig.trim().length() > 0) {
                WebSysConfig sys = JsonPluginsUtil.jsonToBean(sysConfig,
                        WebSysConfig.class);
                DbUtil.update(
                        " update web_sys_config  set configval = ? where  configid = ? ",
                        sys.getConfigval(), sys.getConfigid());
            }
        }
    }

    @Override
    @Cacheable(value = "myCache", key = "#root.methodName")
    public Map<String, String> loadWebConfig() {

        Map<String, String> result = new HashMap<String, String>();
        List<SystemConfigVo> sysList = DbUtil
                .queryForObjectList(
                        " select a.configid,a.defaultval,a.configval,a.remark,a.showtype from web_sys_config a ",
                        SystemConfigVo.class);
        for (SystemConfigVo sys : sysList) {
            result.put(sys.getConfigid(), sys.getConfigval());
        }
        return result;

    }

    @Override
    @CacheEvict(value = "myCache", key = "#evict")
    public void evictConfig(String evict) {
        log.info("清空配置缓存!");
    }

}
