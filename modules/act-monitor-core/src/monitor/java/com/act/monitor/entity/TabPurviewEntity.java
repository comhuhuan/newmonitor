package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(name = "tab_purview")
public class TabPurviewEntity extends StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotEmpty
    @Column(name = "role_id")
    private int roleId;

    @NotEmpty
    @Length(max=30)
    @Column(name = "program_id")
    private String programId;

    @NotEmpty
    @Column(name = "sec_menu_id")
    private int secMenuId;

    /**
     * 二级菜单页面下所具有的所有操作
     */
    @NotEmpty
    @Length(max=50)
    @Column(name = "purview_list")
    @ColumnTitle("二级菜单页面下所具有的所有操作")
    private String purviewList;

    @Length(max=1)
    @Column(name = "purview_str")
    private String purviewStr;

    @Length(max=1)
    @Column(name = "is_usb_verify")
    private String isUsbVerify;

    public static final String Property_PurviewStr = "purviewStr";

    public static final String Property_Id = "id";

    public static final String Property_ProgramId = "programId";

    public static final String Property_IsUsbVerify = "isUsbVerify";

    public static final String Property_PurviewList = "purviewList";

    public static final String Property_SecMenuId = "secMenuId";

    public static final String Property_RoleId = "roleId";

    /**
     * @return id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(int id) {
        addChangeField("id",this.id,id);
        this.id = id;
    }

    /**
     * @return role_id
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * @param roleId
     */
    public void setRoleId(int roleId) {
        addChangeField("roleId",this.roleId,roleId);
        this.roleId = roleId;
    }

    /**
     * @return program_id
     */
    public String getProgramId() {
        return programId;
    }

    /**
     * @param programId
     */
    public void setProgramId(String programId) {
        addChangeField("programId",this.programId,programId);
        this.programId = programId;
    }

    /**
     * @return sec_menu_id
     */
    public int getSecMenuId() {
        return secMenuId;
    }

    /**
     * @param secMenuId
     */
    public void setSecMenuId(int secMenuId) {
        addChangeField("secMenuId",this.secMenuId,secMenuId);
        this.secMenuId = secMenuId;
    }

    /**
     * 获取二级菜单页面下所具有的所有操作
     *
     * @return purview_list - 二级菜单页面下所具有的所有操作
     */
    public String getPurviewList() {
        return purviewList;
    }

    /**
     * 设置二级菜单页面下所具有的所有操作
     *
     * @param purviewList 二级菜单页面下所具有的所有操作
     */
    public void setPurviewList(String purviewList) {
        addChangeField("purviewList",this.purviewList,purviewList);
        this.purviewList = purviewList;
    }

    /**
     * @return purview_str
     */
    public String getPurviewStr() {
        return purviewStr;
    }

    /**
     * @param purviewStr
     */
    public void setPurviewStr(String purviewStr) {
        addChangeField("purviewStr",this.purviewStr,purviewStr);
        this.purviewStr = purviewStr;
    }

    /**
     * @return is_usb_verify
     */
    public String getIsUsbVerify() {
        return isUsbVerify;
    }

    /**
     * @param isUsbVerify
     */
    public void setIsUsbVerify(String isUsbVerify) {
        addChangeField("isUsbVerify",this.isUsbVerify,isUsbVerify);
        this.isUsbVerify = isUsbVerify;
    }
}