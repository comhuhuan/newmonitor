package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(name = "tab_sysmanageinfo")
public class TabSysManageInfoEntity extends StandardEntity {
    /**
     * 子系统ID号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "syamanage_id")
    @ColumnTitle("子系统ID号")
    private Byte syamanageId;

    /**
     * 子系统名称
     */
    @NotEmpty
    @Length(max=64)
    @Column(name = "sysmanage_name")
    @ColumnTitle("子系统名称")
    private String sysmanageName;

    /**
     * 显示图片及路径
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("显示图片及路径")
    private String picname;

    /**
     * 系统路径-存相对路径
     */
    @NotEmpty
    @Length(max=255)
    @ColumnTitle("系统路径-存相对路径")
    private String pathurl;

    /**
     * 打开方式 self–本地打开 blank-远程打开
     */
    @NotEmpty
    @Length(max=10)
    @ColumnTitle("打开方式")
    private String opentype;

    /**
     * 使用标识 Y-系统正在使用 N-系统暂停使用
     */
    @NotEmpty
    @Length(max=1)
    @Column(name = "show_mk")
    @ColumnTitle("使用标识")
    private String showMk;

    /**
     * 子系统访问路径
     */
    @NotEmpty
    @Length(max=50)
    @Column(name = "child_system_path")
    @ColumnTitle("子系统访问路径")
    private String childSystemPath;

    public static final String Property_ShowMk = "showMk";

    public static final String Property_SyamanageId = "syamanageId";

    public static final String Property_ChildSystemPath = "childSystemPath";

    public static final String Property_Opentype = "opentype";

    public static final String Property_SysmanageName = "sysmanageName";

    public static final String Property_Pathurl = "pathurl";

    public static final String Property_Picname = "picname";

    /**
     * 获取子系统ID号
     *
     * @return syamanage_id - 子系统ID号
     */
    public Byte getSyamanageId() {
        return syamanageId;
    }

    /**
     * 设置子系统ID号
     *
     * @param syamanageId 子系统ID号
     */
    public void setSyamanageId(Byte syamanageId) {
        addChangeField("syamanageId",this.syamanageId,syamanageId);
        this.syamanageId = syamanageId;
    }

    /**
     * 获取子系统名称
     *
     * @return sysmanage_name - 子系统名称
     */
    public String getSysmanageName() {
        return sysmanageName;
    }

    /**
     * 设置子系统名称
     *
     * @param sysmanageName 子系统名称
     */
    public void setSysmanageName(String sysmanageName) {
        addChangeField("sysmanageName",this.sysmanageName,sysmanageName);
        this.sysmanageName = sysmanageName;
    }

    /**
     * 获取显示图片及路径
     *
     * @return picname - 显示图片及路径
     */
    public String getPicname() {
        return picname;
    }

    /**
     * 设置显示图片及路径
     *
     * @param picname 显示图片及路径
     */
    public void setPicname(String picname) {
        addChangeField("picname",this.picname,picname);
        this.picname = picname;
    }

    /**
     * 获取系统路径-存相对路径
     *
     * @return pathurl - 系统路径-存相对路径
     */
    public String getPathurl() {
        return pathurl;
    }

    /**
     * 设置系统路径-存相对路径
     *
     * @param pathurl 系统路径-存相对路径
     */
    public void setPathurl(String pathurl) {
        addChangeField("pathurl",this.pathurl,pathurl);
        this.pathurl = pathurl;
    }

    /**
     * 获取打开方式 self–本地打开 blank-远程打开
     *
     * @return opentype - 打开方式 self–本地打开 blank-远程打开
     */
    public String getOpentype() {
        return opentype;
    }

    /**
     * 设置打开方式 self–本地打开 blank-远程打开
     *
     * @param opentype 打开方式 self–本地打开 blank-远程打开
     */
    public void setOpentype(String opentype) {
        addChangeField("opentype",this.opentype,opentype);
        this.opentype = opentype;
    }

    /**
     * 获取使用标识 Y-系统正在使用 N-系统暂停使用
     *
     * @return show_mk - 使用标识 Y-系统正在使用 N-系统暂停使用
     */
    public String getShowMk() {
        return showMk;
    }

    /**
     * 设置使用标识 Y-系统正在使用 N-系统暂停使用
     *
     * @param showMk 使用标识 Y-系统正在使用 N-系统暂停使用
     */
    public void setShowMk(String showMk) {
        addChangeField("showMk",this.showMk,showMk);
        this.showMk = showMk;
    }

    /**
     * 获取子系统访问路径
     *
     * @return child_system_path - 子系统访问路径
     */
    public String getChildSystemPath() {
        return childSystemPath;
    }

    /**
     * 设置子系统访问路径
     *
     * @param childSystemPath 子系统访问路径
     */
    public void setChildSystemPath(String childSystemPath) {
        addChangeField("childSystemPath",this.childSystemPath,childSystemPath);
        this.childSystemPath = childSystemPath;
    }
}