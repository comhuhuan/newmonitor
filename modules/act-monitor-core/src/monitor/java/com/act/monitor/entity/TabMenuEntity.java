package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;

@Table(name = "tab_menu")
public class TabMenuEntity extends StandardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Length(max=200)
    @Column(name = "menu_name_en")
    private String menuNameEn;

    @NotEmpty
    @Length(max=200)
    @Column(name = "menu_name_big")
    private String menuNameBig;

    @NotEmpty
    @Length(max=50)
    @Column(name = "menu_title_ch")
    private String menuTitleCh;

    @NotEmpty
    @Length(max=50)
    @Column(name = "menu_title_en")
    private String menuTitleEn;

    @NotEmpty
    @Length(max=50)
    @Column(name = "menu_title_big")
    private String menuTitleBig;

    @NotEmpty
    @Length(max=100)
    @Column(name = "menu_image")
    private String menuImage;

    @NotEmpty
    @Length(max=100)
    @Column(name = "menu_url")
    private String menuUrl;

    @NotEmpty
    @Length(max=100)
    @Column(name = "menu_parameter")
    private String menuParameter;

    @NotEmpty
    @Column(name = "show_seq")
    private int showSeq;

    @NotEmpty
    @Length(max=2)
    @Column(name = "op_type")
    private String opType;

    @NotEmpty
    @Length(max=2)
    private String verflag;

    public static final String Property_MenuTitleCh = "menuTitleCh";

    public static final String Property_ShowSeq = "showSeq";

    public static final String Property_MenuTitleBig = "menuTitleBig";

    public static final String Property_OpType = "opType";

    public static final String Property_MenuId = "menuId";

    public static final String Property_MenuNameEn = "menuNameEn";

    public static final String Property_MenuNameCh = "menuNameCh";

    public static final String Property_ParentId = "parentId";

    public static final String Property_MenuParameter = "menuParameter";

    public static final String Property_Verflag = "verflag";

    public static final String Property_MenuImage = "menuImage";

    public static final String Property_MenuUrl = "menuUrl";

    public static final String Property_MenuTitleEn = "menuTitleEn";

    public static final String Property_MenuNameBig = "menuNameBig";

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
     * @return menu_name_en
     */
    public String getMenuNameEn() {
        return menuNameEn;
    }

    /**
     * @param menuNameEn
     */
    public void setMenuNameEn(String menuNameEn) {
        addChangeField("menuNameEn",this.menuNameEn,menuNameEn);
        this.menuNameEn = menuNameEn;
    }

    /**
     * @return menu_name_big
     */
    public String getMenuNameBig() {
        return menuNameBig;
    }

    /**
     * @param menuNameBig
     */
    public void setMenuNameBig(String menuNameBig) {
        addChangeField("menuNameBig",this.menuNameBig,menuNameBig);
        this.menuNameBig = menuNameBig;
    }

    /**
     * @return menu_title_ch
     */
    public String getMenuTitleCh() {
        return menuTitleCh;
    }

    /**
     * @param menuTitleCh
     */
    public void setMenuTitleCh(String menuTitleCh) {
        addChangeField("menuTitleCh",this.menuTitleCh,menuTitleCh);
        this.menuTitleCh = menuTitleCh;
    }

    /**
     * @return menu_title_en
     */
    public String getMenuTitleEn() {
        return menuTitleEn;
    }

    /**
     * @param menuTitleEn
     */
    public void setMenuTitleEn(String menuTitleEn) {
        addChangeField("menuTitleEn",this.menuTitleEn,menuTitleEn);
        this.menuTitleEn = menuTitleEn;
    }

    /**
     * @return menu_title_big
     */
    public String getMenuTitleBig() {
        return menuTitleBig;
    }

    /**
     * @param menuTitleBig
     */
    public void setMenuTitleBig(String menuTitleBig) {
        addChangeField("menuTitleBig",this.menuTitleBig,menuTitleBig);
        this.menuTitleBig = menuTitleBig;
    }

    /**
     * @return menu_image
     */
    public String getMenuImage() {
        return menuImage;
    }

    /**
     * @param menuImage
     */
    public void setMenuImage(String menuImage) {
        addChangeField("menuImage",this.menuImage,menuImage);
        this.menuImage = menuImage;
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
     * @return menu_parameter
     */
    public String getMenuParameter() {
        return menuParameter;
    }

    /**
     * @param menuParameter
     */
    public void setMenuParameter(String menuParameter) {
        addChangeField("menuParameter",this.menuParameter,menuParameter);
        this.menuParameter = menuParameter;
    }

    /**
     * @return show_seq
     */
    public int getShowSeq() {
        return showSeq;
    }

    /**
     * @param showSeq
     */
    public void setShowSeq(int showSeq) {
        addChangeField("showSeq",this.showSeq,showSeq);
        this.showSeq = showSeq;
    }

    /**
     * @return op_type
     */
    public String getOpType() {
        return opType;
    }

    /**
     * @param opType
     */
    public void setOpType(String opType) {
        addChangeField("opType",this.opType,opType);
        this.opType = opType;
    }

    /**
     * @return verflag
     */
    public String getVerflag() {
        return verflag;
    }

    /**
     * @param verflag
     */
    public void setVerflag(String verflag) {
        addChangeField("verflag",this.verflag,verflag);
        this.verflag = verflag;
    }
}