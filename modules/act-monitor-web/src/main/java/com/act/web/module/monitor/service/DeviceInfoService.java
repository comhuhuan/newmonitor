/**
 * @Title: DeviceService.java
 * @Package com.act.web.module.monitor.service
 * @Description: 设备搜索页面service
 * @author liuyang
 * @modifier liuyang
 * @date 2017-5-12 上午11:55:23
 * @version V1.0
 */
package com.act.web.module.monitor.service;

import com.act.framework.util.PageResult;
import com.act.web.module.monitor.vo.DevicePagesVo;
import com.act.web.module.monitor.vo.StatusInfoVo;

public interface DeviceInfoService {


    /**
     * @param StatusInfoVo 查询条件
     * @Title: statusHistory
     * @Description: 查询cu设备的历史状态  //TODO
     * @create 2017年6月20日16:55:20
     * @update 2017年6月20日16:55:20
     */
    StatusInfoVo statusHistory(StatusInfoVo deviceVo) throws Exception;

    /**
     * @param deviceVo 查询条件
     * @Title: cuStatusPageList
     * @Description: 查询最新的5条cu设备状态  //TODO
     * @create 2017年6月20日16:55:20
     * @update 2017年6月20日16:55:20
     */
    public PageResult<DevicePagesVo> statusPageList(PageResult<DevicePagesVo> page, DevicePagesVo deviceVo);

    DevicePagesVo labelInfo(DevicePagesVo deviceVo);
}
