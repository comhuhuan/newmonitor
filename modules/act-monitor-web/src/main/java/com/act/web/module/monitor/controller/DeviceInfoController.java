/**
 * @Title: DeviceController.java
 * @Package com.act.web.module.monitor.controller
 * @Description: 设备搜索
 * @author liuyang
 * @modifier liuyang
 * @date 2017年6月20日16:46:32
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
import com.act.web.module.common.controller.BaseController;
import com.act.web.module.monitor.service.DeviceInfoService;
import com.act.web.module.monitor.vo.DevicePagesVo;
import com.act.web.module.monitor.vo.StatusInfoVo;

@Controller
@RequestMapping("/monitor/deviceInfo")
public class DeviceInfoController extends BaseController {

    private final Logger log = LoggerFactory.getLogger(DeviceInfoController.class);

    @Resource
    private DeviceInfoService deviceService;


    /**
     * @Title: cuStatusInfo
     * @Description:cu到管局状态、cu设备状态查询 //TODO
     * @create 2017年6月24日
     * @update 2017年6月24日
     */
    @ResponseBody
    @RequestMapping(value = "/statusHistory.do")
    public Object statusHistory(StatusInfoVo deviceVo) {
        StatusInfoVo statusInfoVo = null;
        try {
            String uuid = deviceVo.getUuid();
            Long houseID = deviceVo.getHouseID();
            String id = deviceVo.getDeviceId();
            String elementType = deviceVo.getElementType();
            if (null == uuid || "".equals(uuid) || null == houseID || "".equals(houseID) || null == elementType || "".equals(elementType) || null == id || "".equals(id)) {
                log.error("查询失败!", "参数错误");
                return ajax(Status.error, "参数错误");
            }
            statusInfoVo = deviceService.statusHistory(deviceVo);
        } catch (Exception e) {
            log.error("查询失败!", e);
            e.printStackTrace();
            return ajax(Status.error, "查询失败!");
        }
        return ajax(Status.success, statusInfoVo);
    }


    /**
     * @Title: cuStatusPageList
     * @Description:cu到管局状态、cu设备最新数据查询 //TODO
     * @create 2017年6月20日16:50:52
     * @update 2017年6月20日16:50:52
     */
    @ResponseBody
    @RequestMapping(value = "/statusPageList.do")
    public Object statusPageList(PageResult<DevicePagesVo> page, DevicePagesVo deviceVo) {
        try {
            String uuid = deviceVo.getUuid();
            Long houseID = deviceVo.getHouseID();
            String id = deviceVo.getDeviceId();
            String elementType = deviceVo.getElementType();
            String queryType = deviceVo.getQueryType();
            if (null == uuid || "".equals(uuid) || null == houseID || "".equals(houseID) || null == elementType || "".equals(elementType) || null == id || "".equals(id)) {
                log.error("查询失败!", "参数错误");
                return ajax(Status.error, "参数错误");
            }
            page = deviceService.statusPageList(page, deviceVo);
        } catch (Exception e) {
            log.error("查询失败!", e);
            return ajax(Status.error, "查询失败!");
        }
        return ajax(Status.success, page);
    }


    @ResponseBody
    @RequestMapping(value = "/labelInfo.do")
    public Object labelInfo(DevicePagesVo deviceVo) {
        DevicePagesVo DevicePagesVo;
        try {
            DevicePagesVo = deviceService.labelInfo(deviceVo);
        } catch (Exception e) {
            log.error("查询失败!", e);
            return ajax(Status.error, "查询失败!");
        }
        return ajax(Status.success, DevicePagesVo);
    }

}
