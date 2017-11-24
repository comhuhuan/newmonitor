package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(name = "tab_secondary_menu")
public class TabSecondaryMenuEntity extends StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sec_menu_id")
    private int secMenuId;

    @NotEmpty
    @Column(name = "menu_id")
    private int menuId;

    @NotEmpty
    @Length(max=30)
    @Column(name = "program_id")
    private String programId;

    /**
     * 二级菜单页面下所具有的所有操作
     */
    @NotEmpty
    @Length(max=50)
    @Column(name = "purview_list")
    @ColumnTitle("二级菜单页面下所具有的所有操作")
    private String purviewList;

    public static final String Property_ProgramId = "programId";

    public static final String Property_PurviewList = "purviewList";

    public static final String Property_MenuId = "menuId";

    public static final String Property_SecMenuId = "secMenuId";

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
}