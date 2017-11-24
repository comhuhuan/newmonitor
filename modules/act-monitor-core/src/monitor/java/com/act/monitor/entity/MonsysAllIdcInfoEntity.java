package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "monsys_all_idc_info")
public class MonsysAllIdcInfoEntity extends StandardEntity {
    /**
     * 唯一用户ID
     */
    @Id
    @NotEmpty
    @Length(max=128)
    @Column(name = "UUID")
    @ColumnTitle("唯一用户ID")
    private String uuid;

    @NotEmpty
    @Length(max=128)
    @Column(name = "idcID")
    private String idcid;

    /**
     * IDC运营商名称(例:湖北联通)
     */
    @NotEmpty
    @Length(max=128)
    @Column(name = "idcName")
    @ColumnTitle("IDC运营商名称(例:湖北联通)")
    private String idcname;

    /**
     * 省份ID
     */
    @NotEmpty
    @Column(name = "provId")
    @ColumnTitle("省份ID")
    private int provid;

    /**
     * cu服务器总数
     */
    @NotEmpty
    @Column(name = "cuDeviceNum")
    @ColumnTitle("cu服务器总数")
    private int cudevicenum;

    /**
     * cu故障台数
     */
    @NotEmpty
    @Column(name = "cuBadDeviceNum")
    @ColumnTitle("cu故障台数")
    private int cubaddevicenum;

    /**
     * EU服务器总数
     */
    @NotEmpty
    @Column(name = "euDeviceNum")
    @ColumnTitle("EU服务器总数")
    private int eudevicenum;

    /**
     * EU故障数
     */
    @NotEmpty
    @Column(name = "euBadDeviceNum")
    @ColumnTitle("EU故障数")
    private int eubaddevicenum;

    /**
     * 记录时间
     */
    @NotEmpty
    @Column(name = "recordTime")
    @ColumnTitle("记录时间")
    private Date recordtime;

    public static final String Property_Eubaddevicenum = "eubaddevicenum";

    public static final String Property_Cudevicenum = "cudevicenum";

    public static final String Property_Cubaddevicenum = "cubaddevicenum";

    public static final String Property_Idcname = "idcname";

    public static final String Property_Idcid = "idcid";

    public static final String Property_Eudevicenum = "eudevicenum";

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
     * 获取IDC运营商名称(例:湖北联通)
     *
     * @return idcName - IDC运营商名称(例:湖北联通)
     */
    public String getIdcname() {
        return idcname;
    }

    /**
     * 设置IDC运营商名称(例:湖北联通)
     *
     * @param idcname IDC运营商名称(例:湖北联通)
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
     * 获取cu服务器总数
     *
     * @return cuDeviceNum - cu服务器总数
     */
    public int getCudevicenum() {
        return cudevicenum;
    }

    /**
     * 设置cu服务器总数
     *
     * @param cudevicenum cu服务器总数
     */
    public void setCudevicenum(int cudevicenum) {
        addChangeField("cudevicenum",this.cudevicenum,cudevicenum);
        this.cudevicenum = cudevicenum;
    }

    /**
     * 获取cu故障台数
     *
     * @return cuBadDeviceNum - cu故障台数
     */
    public int getCubaddevicenum() {
        return cubaddevicenum;
    }

    /**
     * 设置cu故障台数
     *
     * @param cubaddevicenum cu故障台数
     */
    public void setCubaddevicenum(int cubaddevicenum) {
        addChangeField("cubaddevicenum",this.cubaddevicenum,cubaddevicenum);
        this.cubaddevicenum = cubaddevicenum;
    }

    /**
     * 获取EU服务器总数
     *
     * @return euDeviceNum - EU服务器总数
     */
    public int getEudevicenum() {
        return eudevicenum;
    }

    /**
     * 设置EU服务器总数
     *
     * @param eudevicenum EU服务器总数
     */
    public void setEudevicenum(int eudevicenum) {
        addChangeField("eudevicenum",this.eudevicenum,eudevicenum);
        this.eudevicenum = eudevicenum;
    }

    /**
     * 获取EU故障数
     *
     * @return euBadDeviceNum - EU故障数
     */
    public int getEubaddevicenum() {
        return eubaddevicenum;
    }

    /**
     * 设置EU故障数
     *
     * @param eubaddevicenum EU故障数
     */
    public void setEubaddevicenum(int eubaddevicenum) {
        addChangeField("eubaddevicenum",this.eubaddevicenum,eubaddevicenum);
        this.eubaddevicenum = eubaddevicenum;
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