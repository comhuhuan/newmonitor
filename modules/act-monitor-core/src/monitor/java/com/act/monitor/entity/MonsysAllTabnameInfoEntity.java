package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "monsys_all_tabname_info")
public class MonsysAllTabnameInfoEntity extends StandardEntity {
    /**
     * 唯一用户ID
     */
    @Id
    @NotEmpty
    @Length(max=128)
    @Column(name = "UUID")
    @ColumnTitle("唯一用户ID")
    private String uuid;

    /**
     * 表名
     */
    @Id
    @NotEmpty
    @Length(max=128)
    @Column(name = "tableName")
    @ColumnTitle("表名")
    private String tablename;

    @NotEmpty
    @Length(max=128)
    @Column(name = "idcID")
    private String idcid;

    @NotEmpty
    @Length(max=256)
    @Column(name = "idcName")
    private String idcname;

    /**
     * 省份ID
     */
    @NotEmpty
    @Column(name = "provId")
    @ColumnTitle("省份ID")
    private int provid;

    /**
     * 省份名称
     */
    @NotEmpty
    @Length(max=256)
    @Column(name = "provName")
    @ColumnTitle("省份名称")
    private String provname;

    /**
     * 表类型代号
     */
    @NotEmpty
    @Column(name = "tableType")
    @ColumnTitle("表类型代号")
    private int tabletype;

    /**
     * 记录时间
     */
    @NotEmpty
    @Column(name = "recordTime")
    @ColumnTitle("记录时间")
    private Date recordtime;

    public static final String Property_Idcname = "idcname";

    public static final String Property_Tablename = "tablename";

    public static final String Property_Idcid = "idcid";

    public static final String Property_Provname = "provname";

    public static final String Property_Tabletype = "tabletype";

    public static final String Property_Uuid = "uuid";

    public static final String Property_Recordtime = "recordtime";

    public static final String Property_Provid = "provid";

    /**
     * 获取唯一用户ID
     *
     * @return UUID - 唯一用户ID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * 设置唯一用户ID
     *
     * @param uuid 唯一用户ID
     */
    public void setUuid(String uuid) {
        addChangeField("uuid",this.uuid,uuid);
        this.uuid = uuid;
    }

    /**
     * 获取表名
     *
     * @return tableName - 表名
     */
    public String getTablename() {
        return tablename;
    }

    /**
     * 设置表名
     *
     * @param tablename 表名
     */
    public void setTablename(String tablename) {
        addChangeField("tablename",this.tablename,tablename);
        this.tablename = tablename;
    }

    /**
     * @return idcID
     */
    public String getIdcid() {
        return idcid;
    }

    /**
     * @param idcid
     */
    public void setIdcid(String idcid) {
        addChangeField("idcid",this.idcid,idcid);
        this.idcid = idcid;
    }

    /**
     * @return idcName
     */
    public String getIdcname() {
        return idcname;
    }

    /**
     * @param idcname
     */
    public void setIdcname(String idcname) {
        addChangeField("idcname",this.idcname,idcname);
        this.idcname = idcname;
    }

    /**
     * 获取省份ID
     *
     * @return provId - 省份ID
     */
    public int getProvid() {
        return provid;
    }

    /**
     * 设置省份ID
     *
     * @param provid 省份ID
     */
    public void setProvid(int provid) {
        addChangeField("provid",this.provid,provid);
        this.provid = provid;
    }

    /**
     * 获取省份名称
     *
     * @return provName - 省份名称
     */
    public String getProvname() {
        return provname;
    }

    /**
     * 设置省份名称
     *
     * @param provname 省份名称
     */
    public void setProvname(String provname) {
        addChangeField("provname",this.provname,provname);
        this.provname = provname;
    }

    /**
     * 获取表类型代号
     *
     * @return tableType - 表类型代号
     */
    public int getTabletype() {
        return tabletype;
    }

    /**
     * 设置表类型代号
     *
     * @param tabletype 表类型代号
     */
    public void setTabletype(int tabletype) {
        addChangeField("tabletype",this.tabletype,tabletype);
        this.tabletype = tabletype;
    }

    /**
     * 获取记录时间
     *
     * @return recordTime - 记录时间
     */
    public Date getRecordtime() {
        return recordtime;
    }

    /**
     * 设置记录时间
     *
     * @param recordtime 记录时间
     */
    public void setRecordtime(Date recordtime) {
        addChangeField("recordtime",this.recordtime,recordtime);
        this.recordtime = recordtime;
    }
}