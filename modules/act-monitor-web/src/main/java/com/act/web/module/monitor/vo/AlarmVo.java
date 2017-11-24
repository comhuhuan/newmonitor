package com.act.web.module.monitor.vo;

public class AlarmVo {



    private String type;
    private String host;
    private String parameter;
    private String warningclass;
    private String status;
    private Integer parentclass;
    private Integer valid;
    private String childclass;
    private String value;
    private String max;
    private String min;
    private String occurtime;
    private String alarmid;
    private String appsystem;
    private String alarmtitle;
    private String attachments;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getParameter() {
        return parameter;
    }

    public void setParameter(String parameter) {
        this.parameter = parameter;
    }

    public String getWarningclass() {
        return warningclass;
    }

    public void setWarningclass(String warningclass) {
        this.warningclass = warningclass;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getParentclass() {
        return parentclass;
    }

    public void setParentclass(Integer parentclass) {
        this.parentclass = parentclass;
    }

    public Integer getValid() {
        return valid;
    }

    public void setValid(Integer valid) {
        this.valid = valid;
    }

    public String getChildclass() {
        return childclass;
    }

    public void setChildclass(String childclass) {
        this.childclass = childclass;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getOccurtime() {
        return occurtime;
    }

    public void setOccurtime(String occurtime) {
        this.occurtime = occurtime;
    }

    public String getAlarmid() {
        return alarmid;
    }

    public void setAlarmid(String alarmid) {
        this.alarmid = alarmid;
    }

    public String getAppsystem() {
        return appsystem;
    }

    public void setAppsystem(String appsystem) {
        this.appsystem = appsystem;
    }

    public String getAlarmtitle() {
        return alarmtitle;
    }

    public void setAlarmtitle(String alarmtitle) {
        this.alarmtitle = alarmtitle;
    }

    public String getAttachments() {
        return attachments;
    }

    public void setAttachments(String attachments) {
        this.attachments = attachments;
    }
}
