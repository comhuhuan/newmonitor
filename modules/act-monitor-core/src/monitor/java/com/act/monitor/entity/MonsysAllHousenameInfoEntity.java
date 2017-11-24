package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "monsys_all_housename_info")
public class MonsysAllHousenameInfoEntity extends StandardEntity {
    @Id
    @NotEmpty
    @Length(max=128)
    @Column(name = "UUID")
    private String uuid;

    @Id
    @NotEmpty
    @Column(name = "houseID")
    private long houseid;

    @NotEmpty
    @Length(max=128)
    @Column(name = "idcID")
    private String idcid;

    @NotEmpty
    @Length(max=512)
    @Column(name = "houseName")
    private String housename;

    @Length(max=128)
    private String reserve0;

    @Length(max=128)
    private String reserve1;

    @Length(max=128)
    private String reserve2;

    @Length(max=128)
    private String reserve3;

    @NotEmpty
    @Column(name = "recordTime")
    private Date recordtime;

    public static final String Property_Houseid = "houseid";

    public static final String Property_Housename = "housename";

    public static final String Property_Idcid = "idcid";

    public static final String Property_Uuid = "uuid";

    public static final String Property_Recordtime = "recordtime";

    public static final String Property_Reserve3 = "reserve3";

    public static final String Property_Reserve2 = "reserve2";

    public static final String Property_Reserve1 = "reserve1";

    public static final String Property_Reserve0 = "reserve0";

    /**
     * @return UUID
     */
    public String getUuid() {
        return uuid;
    }

    /**
     * @param uuid
     */
    public void setUuid(String uuid) {
        addChangeField("uuid",this.uuid,uuid);
        this.uuid = uuid;
    }

    /**
     * @return houseID
     */
    public long getHouseid() {
        return houseid;
    }

    /**
     * @param houseid
     */
    public void setHouseid(long houseid) {
        addChangeField("houseid",this.houseid,houseid);
        this.houseid = houseid;
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
     * @return houseName
     */
    public String getHousename() {
        return housename;
    }

    /**
     * @param housename
     */
    public void setHousename(String housename) {
        addChangeField("housename",this.housename,housename);
        this.housename = housename;
    }

    /**
     * @return reserve0
     */
    public String getReserve0() {
        return reserve0;
    }

    /**
     * @param reserve0
     */
    public void setReserve0(String reserve0) {
        addChangeField("reserve0",this.reserve0,reserve0);
        this.reserve0 = reserve0;
    }

    /**
     * @return reserve1
     */
    public String getReserve1() {
        return reserve1;
    }

    /**
     * @param reserve1
     */
    public void setReserve1(String reserve1) {
        addChangeField("reserve1",this.reserve1,reserve1);
        this.reserve1 = reserve1;
    }

    /**
     * @return reserve2
     */
    public String getReserve2() {
        return reserve2;
    }

    /**
     * @param reserve2
     */
    public void setReserve2(String reserve2) {
        addChangeField("reserve2",this.reserve2,reserve2);
        this.reserve2 = reserve2;
    }

    /**
     * @return reserve3
     */
    public String getReserve3() {
        return reserve3;
    }

    /**
     * @param reserve3
     */
    public void setReserve3(String reserve3) {
        addChangeField("reserve3",this.reserve3,reserve3);
        this.reserve3 = reserve3;
    }

    /**
     * @return recordTime
     */
    public Date getRecordtime() {
        return recordtime;
    }

    /**
     * @param recordtime
     */
    public void setRecordtime(Date recordtime) {
        addChangeField("recordtime",this.recordtime,recordtime);
        this.recordtime = recordtime;
    }
}