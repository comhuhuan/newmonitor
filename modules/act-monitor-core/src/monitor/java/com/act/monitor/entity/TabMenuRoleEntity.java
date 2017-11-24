package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "tab_menu_role")
public class TabMenuRoleEntity extends StandardEntity {
    @Id
    @NotEmpty
    @Column(name = "menu_id")
    private int menuId;

    @NotEmpty
    @Column(name = "parent_id")
    private int parentId;

    @NotEmpty
    @Length(max=200)
    @Column(name = "menu_name_ch")
    private String menuNameCh;

    @NotEmpty
    @Length(max=100)
    @Column(name = "menu_url")
    private String menuUrl;

    @NotEmpty
    @Column(name = "show_seq")
    private Byte showSeq;

    /**
     * Y -表启用 -N不启用
     */
    @NotEmpty
    @Length(max=1)
    @Column(name = "is_active")
    @ColumnTitle("Y")
    private String isActive;

    public static final String Property_IsActive = "isActive";

    public static final String Property_MenuNameCh = "menuNameCh";

    public static final String Property_ParentId = "parentId";

    public static final String Property_ShowSeq = "showSeq";

    public static final String Property_MenuUrl = "menuUrl";

    public static final String Property_MenuId = "menuId";

    /**
     * @return menu_id
     */
    public int getMenuId() {
        return menuId;
    }

    /**
     * @param menuId
     */
    public void setMenuId(int menuId) {
        addChangeField("menuId",this.menuId,menuId);
        this.menuId = menuId;
    }

    /**
     * @return parent_id
     */
    public int getParentId() {
        return parentId;
    }

    /**
     * @param parentId
     */
    public void setParentId(int parentId) {
        addChangeField("parentId",this.parentId,parentId);
        this.parentId = parentId;
    }

    /**
     * @return menu_name_ch
     */
    public String getMenuNameCh() {
        return menuNameCh;
    }

    /**
     * @param menuNameCh
     */
    public void setMenuNameCh(String menuNameCh) {
        addChangeField("menuNameCh",this.menuNameCh,menuNameCh);
        this.menuNameCh = menuNameCh;
    }

    /**
     * @return menu_url
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * @param menuUrl
     */
    public void setMenuUrl(String menuUrl) {
        addChangeField("menuUrl",this.menuUrl,menuUrl);
        this.menuUrl = menuUrl;
    }

    /**
     * @return show_seq
     */
    public Byte getShowSeq() {
        return showSeq;
    }

    /**
     * @param showSeq
     */
    public void setShowSeq(Byte showSeq) {
        addChangeField("showSeq",this.showSeq,showSeq);
        this.showSeq = showSeq;
    }

    /**
     * 获取Y -表启用 -N不启用
     *
     * @return is_active - Y -表启用 -N不启用
     */
    public String getIsActive() {
        return isActive;
    }

    /**
     * 设置Y -表启用 -N不启用
     *
     * @param isActive Y -表启用 -N不启用
     */
    public void setIsActive(String isActive) {
        addChangeField("isActive",this.isActive,isActive);
        this.isActive = isActive;
    }
}