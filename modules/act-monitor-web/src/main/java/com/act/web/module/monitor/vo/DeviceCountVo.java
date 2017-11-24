/**
 * @Title: DeviceVo.java
 * @Package com.act.web.module.monitor.vo
 * @Description: 设备状态vo
 * @author liuyang
 * @modifier liuyang
 * @date 2017年6月20日17:26:16
 * @version V1.0
 */
package com.act.web.module.monitor.vo;

import com.act.web.module.common.vo.BaseVo;

public class DeviceCountVo extends BaseVo { //TODO
    private Integer badDviceNum;    //故障服务器数量
    private Integer dviceNum;    //服务器总的数量
    private float percent;//百分比
    private String idcID;    //IDCID
    private String idcName;    //IDC名称
    private String provId;    //省份ID
    private String provName;    //省份名称
    private String recordTime;    //更新时间
    private String uuid;//运营商唯一标识
    private String cuIP;

    public String getCuIP() {
        return cuIP;
    }

    public void setCuIP(String cuIP) {
        this.cuIP = cuIP;
    }

    public Integer getBadDviceNum() {
        return badDviceNum;
    }

    public void setBadDviceNum(Integer badDviceNum) {
        this.badDviceNum = badDviceNum;
    }

    public Integer getDviceNum() {
        return dviceNum;
    }

    public void setDviceNum(Integer dviceNum) {
        this.dviceNum = dviceNum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public String getIdcID() {
        return idcID;
    }

    public void setIdcID(String idcID) {
        this.idcID = idcID;
    }

    public String getIdcName() {
        return idcName;
    }

    public void setIdcName(String idcName) {
        this.idcName = idcName;
    }

    public String getProvId() {
        return provId;
    }

    public void setProvId(String provId) {
        this.provId = provId;
    }

    public String getProvName() {
        return provName;
    }

    public void setProvName(String provName) {
        this.provName = provName;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

}
