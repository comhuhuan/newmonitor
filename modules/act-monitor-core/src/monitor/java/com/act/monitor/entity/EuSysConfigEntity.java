package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "eu_sys_config")
public class EuSysConfigEntity extends StandardEntity {
    /**
     * 配置参数名称
     */
    @Id
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("配置参数名称")
    private String configid;

    /**
     * 配置默认值
     */
    @NotEmpty
    @Length(max=2048)
    @ColumnTitle("配置默认值")
    private String defaultval;

    @NotEmpty
    @Length(max=2048)
    private String configval;

    /**
     * 页面显示说明
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("页面显示说明")
    private String title;

    /**
     * 配置描述
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("配置描述")
    private String remark;

    /**
     * 0-输入框，1-下拉框(暂不支持), 2-开关
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("0-输入框，1-下拉框(暂不支持),")
    private String showtype;

    /**
     * 0-不可配置 1-可配
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("0-不可配置")
    private String hidden;

    /**
     * 排序字段
     */
    @ColumnTitle("排序字段")
    private Integer showseq;

    public static final String Property_Title = "title";

    public static final String Property_Defaultval = "defaultval";

    public static final String Property_Remark = "remark";

    public static final String Property_Configid = "configid";

    public static final String Property_Hidden = "hidden";

    public static final String Property_Showseq = "showseq";

    public static final String Property_Showtype = "showtype";

    public static final String Property_Configval = "configval";

    /**
     * 获取配置参数名称
     *
     * @return configid - 配置参数名称
     */
    public String getConfigid() {
        return configid;
    }

    /**
     * 设置配置参数名称
     *
     * @param configid 配置参数名称
     */
    public void setConfigid(String configid) {
        addChangeField("configid",this.configid,configid);
        this.configid = configid;
    }

    /**
     * 获取配置默认值
     *
     * @return defaultval - 配置默认值
     */
    public String getDefaultval() {
        return defaultval;
    }

    /**
     * 设置配置默认值
     *
     * @param defaultval 配置默认值
     */
    public void setDefaultval(String defaultval) {
        addChangeField("defaultval",this.defaultval,defaultval);
        this.defaultval = defaultval;
    }

    /**
     * @return configval
     */
    public String getConfigval() {
        return configval;
    }

    /**
     * @param configval
     */
    public void setConfigval(String configval) {
        addChangeField("configval",this.configval,configval);
        this.configval = configval;
    }

    /**
     * 获取页面显示说明
     *
     * @return title - 页面显示说明
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置页面显示说明
     *
     * @param title 页面显示说明
     */
    public void setTitle(String title) {
        addChangeField("title",this.title,title);
        this.title = title;
    }

    /**
     * 获取配置描述
     *
     * @return remark - 配置描述
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置配置描述
     *
     * @param remark 配置描述
     */
    public void setRemark(String remark) {
        addChangeField("remark",this.remark,remark);
        this.remark = remark;
    }

    /**
     * 获取0-输入框，1-下拉框(暂不支持), 2-开关
     *
     * @return showtype - 0-输入框，1-下拉框(暂不支持), 2-开关
     */
    public String getShowtype() {
        return showtype;
    }

    /**
     * 设置0-输入框，1-下拉框(暂不支持), 2-开关
     *
     * @param showtype 0-输入框，1-下拉框(暂不支持), 2-开关
     */
    public void setShowtype(String showtype) {
        addChangeField("showtype",this.showtype,showtype);
        this.showtype = showtype;
    }

    /**
     * 获取0-不可配置 1-可配
     *
     * @return hidden - 0-不可配置 1-可配
     */
    public String getHidden() {
        return hidden;
    }

    /**
     * 设置0-不可配置 1-可配
     *
     * @param hidden 0-不可配置 1-可配
     */
    public void setHidden(String hidden) {
        addChangeField("hidden",this.hidden,hidden);
        this.hidden = hidden;
    }

    /**
     * 获取排序字段
     *
     * @return showseq - 排序字段
     */
    public Integer getShowseq() {
        return showseq;
    }

    /**
     * 设置排序字段
     *
     * @param showseq 排序字段
     */
    public void setShowseq(Integer showseq) {
        addChangeField("showseq",this.showseq,showseq);
        this.showseq = showseq;
    }
}