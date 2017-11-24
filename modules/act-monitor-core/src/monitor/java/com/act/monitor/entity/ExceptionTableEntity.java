package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "exception_table")
public class ExceptionTableEntity extends StandardEntity {
    /**
     * 设备类型EU或CU
     */
    @Id
    @NotEmpty
    @Length(max=8)
    @ColumnTitle("设备类型EU或CU")
    private String type;

    /**
     * 设备标识
     */
    @Id
    @NotEmpty
    @Length(max=64)
    @ColumnTitle("设备标识")
    private String host;

    /**
     * 异常详细类型
     */
    @Id
    @NotEmpty
    @ColumnTitle("异常详细类型")
    private int childclass;

    /**
     * 实例对象标识
     */
    @NotEmpty
    @Length(max=256)
    @ColumnTitle("实例对象标识")
    private String instance;

    /**
     * 告警指标
     */
    @NotEmpty
    @Length(max=256)
    @ColumnTitle("告警指标")
    private String parameter;

    /**
     * 告警类别名称
     */
    @NotEmpty
    @Length(max=256)
    @ColumnTitle("告警类别名称")
    private String warningclass;

    /**
     * 告警级别
     */
    @NotEmpty
    @Length(max=20)
    @ColumnTitle("告警级别")
    private String status;

    /**
     * 异常主要类型
     */
    @NotEmpty
    @ColumnTitle("异常主要类型")
    private int parentclass;

    /**
     * 是否有效  0-无效 1-有效
     */
    @NotEmpty
    @ColumnTitle("是否有效")
    private int valid;

    /**
     * 告警正文或当前值
     */
    @NotEmpty
    @Length(max=256)
    @ColumnTitle("告警正文或当前值")
    private String value;

    /**
     * 告警影响描述或高门限值
     */
    @Length(max=256)
    @ColumnTitle("告警影响描述或高门限值")
    private String max;

    /**
     * 备用参数或低门限值
     */
    @Length(max=256)
    @ColumnTitle("备用参数或低门限值")
    private String min;

    /**
     * 告警产生时间
     */
    @Length(max=256)
    @ColumnTitle("告警产生时间")
    private String occurtime;

    /**
     * 网管告警原始序列号
     */
    @Length(max=40)
    @ColumnTitle("网管告警原始序列号")
    private String alarmid;

    /**
     * 业务系统
     */
    @Length(max=256)
    @ColumnTitle("业务系统")
    private String appsystem;

    /**
     * 告警标题
     */
    @Length(max=400)
    @ColumnTitle("告警标题")
    private String alarmtitle;

    /**
     * 附件名
     */
    @Length(max=512)
    @ColumnTitle("附件名")
    private String attachments;

    public static final String Property_Min = "min";

    public static final String Property_Valid = "valid";

    public static final String Property_Host = "host";

    public static final String Property_Max = "max";

    public static final String Property_Alarmid = "alarmid";

    public static final String Property_Status = "status";

    public static final String Property_Type = "type";

    public static final String Property_Parentclass = "parentclass";

    public static final String Property_Appsystem = "appsystem";

    public static final String Property_Occurtime = "occurtime";

    public static final String Property_Childclass = "childclass";

    public static final String Property_Alarmtitle = "alarmtitle";

    public static final String Property_Parameter = "parameter";

    public static final String Property_Value = "value";

    public static final String Property_Attachments = "attachments";

    public static final String Property_Warningclass = "warningclass";

    public static final String Property_Instance = "instance";

    /**
     * 获取设备类型EU或CU
     *
     * @return type - 设备类型EU或CU
     */
    public String getType() {
        return type;
    }

    /**
     * 设置设备类型EU或CU
     *
     * @param type 设备类型EU或CU
     */
    public void setType(String type) {
        addChangeField("type",this.type,type);
        this.type = type;
    }

    /**
     * 获取设备标识
     *
     * @return host - 设备标识
     */
    public String getHost() {
        return host;
    }

    /**
     * 设置设备标识
     *
     * @param host 设备标识
     */
    public void setHost(String host) {
        addChangeField("host",this.host,host);
        this.host = host;
    }

    /**
     * 获取异常详细类型
     *
     * @return childclass - 异常详细类型
     */
    public int getChildclass() {
        return childclass;
    }

    /**
     * 设置异常详细类型
     *
     * @param childclass 异常详细类型
     */
    public void setChildclass(int childclass) {
        addChangeField("childclass",this.childclass,childclass);
        this.childclass = childclass;
    }

    /**
     * 获取实例对象标识
     *
     * @return instance - 实例对象标识
     */
    public String getInstance() {
        return instance;
    }

    /**
     * 设置实例对象标识
     *
     * @param instance 实例对象标识
     */
    public void setInstance(String instance) {
        addChangeField("instance",this.instance,instance);
        this.instance = instance;
    }

    /**
     * 获取告警指标
     *
     * @return parameter - 告警指标
     */
    public String getParameter() {
        return parameter;
    }

    /**
     * 设置告警指标
     *
     * @param parameter 告警指标
     */
    public void setParameter(String parameter) {
        addChangeField("parameter",this.parameter,parameter);
        this.parameter = parameter;
    }

    /**
     * 获取告警类别名称
     *
     * @return warningclass - 告警类别名称
     */
    public String getWarningclass() {
        return warningclass;
    }

    /**
     * 设置告警类别名称
     *
     * @param warningclass 告警类别名称
     */
    public void setWarningclass(String warningclass) {
        addChangeField("warningclass",this.warningclass,warningclass);
        this.warningclass = warningclass;
    }

    /**
     * 获取告警级别
     *
     * @return status - 告警级别
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置告警级别
     *
     * @param status 告警级别
     */
    public void setStatus(String status) {
        addChangeField("status",this.status,status);
        this.status = status;
    }

    /**
     * 获取异常主要类型
     *
     * @return parentclass - 异常主要类型
     */
    public int getParentclass() {
        return parentclass;
    }

    /**
     * 设置异常主要类型
     *
     * @param parentclass 异常主要类型
     */
    public void setParentclass(int parentclass) {
        addChangeField("parentclass",this.parentclass,parentclass);
        this.parentclass = parentclass;
    }

    /**
     * 获取是否有效  0-无效 1-有效
     *
     * @return valid - 是否有效  0-无效 1-有效
     */
    public int getValid() {
        return valid;
    }

    /**
     * 设置是否有效  0-无效 1-有效
     *
     * @param valid 是否有效  0-无效 1-有效
     */
    public void setValid(int valid) {
        addChangeField("valid",this.valid,valid);
        this.valid = valid;
    }

    /**
     * 获取告警正文或当前值
     *
     * @return value - 告警正文或当前值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置告警正文或当前值
     *
     * @param value 告警正文或当前值
     */
    public void setValue(String value) {
        addChangeField("value",this.value,value);
        this.value = value;
    }

    /**
     * 获取告警影响描述或高门限值
     *
     * @return max - 告警影响描述或高门限值
     */
    public String getMax() {
        return max;
    }

    /**
     * 设置告警影响描述或高门限值
     *
     * @param max 告警影响描述或高门限值
     */
    public void setMax(String max) {
        addChangeField("max",this.max,max);
        this.max = max;
    }

    /**
     * 获取备用参数或低门限值
     *
     * @return min - 备用参数或低门限值
     */
    public String getMin() {
        return min;
    }

    /**
     * 设置备用参数或低门限值
     *
     * @param min 备用参数或低门限值
     */
    public void setMin(String min) {
        addChangeField("min",this.min,min);
        this.min = min;
    }

    /**
     * 获取告警产生时间
     *
     * @return occurtime - 告警产生时间
     */
    public String getOccurtime() {
        return occurtime;
    }

    /**
     * 设置告警产生时间
     *
     * @param occurtime 告警产生时间
     */
    public void setOccurtime(String occurtime) {
        addChangeField("occurtime",this.occurtime,occurtime);
        this.occurtime = occurtime;
    }

    /**
     * 获取网管告警原始序列号
     *
     * @return alarmid - 网管告警原始序列号
     */
    public String getAlarmid() {
        return alarmid;
    }

    /**
     * 设置网管告警原始序列号
     *
     * @param alarmid 网管告警原始序列号
     */
    public void setAlarmid(String alarmid) {
        addChangeField("alarmid",this.alarmid,alarmid);
        this.alarmid = alarmid;
    }

    /**
     * 获取业务系统
     *
     * @return appsystem - 业务系统
     */
    public String getAppsystem() {
        return appsystem;
    }

    /**
     * 设置业务系统
     *
     * @param appsystem 业务系统
     */
    public void setAppsystem(String appsystem) {
        addChangeField("appsystem",this.appsystem,appsystem);
        this.appsystem = appsystem;
    }

    /**
     * 获取告警标题
     *
     * @return alarmtitle - 告警标题
     */
    public String getAlarmtitle() {
        return alarmtitle;
    }

    /**
     * 设置告警标题
     *
     * @param alarmtitle 告警标题
     */
    public void setAlarmtitle(String alarmtitle) {
        addChangeField("alarmtitle",this.alarmtitle,alarmtitle);
        this.alarmtitle = alarmtitle;
    }

    /**
     * 获取附件名
     *
     * @return attachments - 附件名
     */
    public String getAttachments() {
        return attachments;
    }

    /**
     * 设置附件名
     *
     * @param attachments 附件名
     */
    public void setAttachments(String attachments) {
        addChangeField("attachments",this.attachments,attachments);
        this.attachments = attachments;
    }
}