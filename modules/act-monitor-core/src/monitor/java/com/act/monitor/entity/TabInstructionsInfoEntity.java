package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "tab_instructions_info")
public class TabInstructionsInfoEntity extends StandardEntity {
    /**
     * 主键自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnTitle("主键自增ID")
    private int instructionid;

    /**
     * 下发失败和下发成功
     */
    @Length(max=255)
    @ColumnTitle("下发失败和下发成功")
    private String status;

    /**
     * CU配置EU配置和WEB配置
     */
    @Length(max=255)
    @ColumnTitle("CU配置EU配置和WEB配置")
    private String type;

    /**
     * 指令下发ip
     */
    @Length(max=255)
    @ColumnTitle("指令下发ip")
    private String ip;

    /**
     * 指令下发时间
     */
    @ColumnTitle("指令下发时间")
    private Date date;

    public static final String Property_Status = "status";

    public static final String Property_Instructionid = "instructionid";

    public static final String Property_Date = "date";

    public static final String Property_Type = "type";

    public static final String Property_Ip = "ip";

    /**
     * 获取主键自增ID
     *
     * @return instructionid - 主键自增ID
     */
    public int getInstructionid() {
        return instructionid;
    }

    /**
     * 设置主键自增ID
     *
     * @param instructionid 主键自增ID
     */
    public void setInstructionid(int instructionid) {
        addChangeField("instructionid",this.instructionid,instructionid);
        this.instructionid = instructionid;
    }

    /**
     * 获取下发失败和下发成功
     *
     * @return status - 下发失败和下发成功
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置下发失败和下发成功
     *
     * @param status 下发失败和下发成功
     */
    public void setStatus(String status) {
        addChangeField("status",this.status,status);
        this.status = status;
    }

    /**
     * 获取CU配置EU配置和WEB配置
     *
     * @return type - CU配置EU配置和WEB配置
     */
    public String getType() {
        return type;
    }

    /**
     * 设置CU配置EU配置和WEB配置
     *
     * @param type CU配置EU配置和WEB配置
     */
    public void setType(String type) {
        addChangeField("type",this.type,type);
        this.type = type;
    }

    /**
     * 获取指令下发ip
     *
     * @return ip - 指令下发ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * 设置指令下发ip
     *
     * @param ip 指令下发ip
     */
    public void setIp(String ip) {
        addChangeField("ip",this.ip,ip);
        this.ip = ip;
    }

    /**
     * 获取指令下发时间
     *
     * @return date - 指令下发时间
     */
    public Date getDate() {
        return date;
    }

    /**
     * 设置指令下发时间
     *
     * @param date 指令下发时间
     */
    public void setDate(Date date) {
        addChangeField("date",this.date,date);
        this.date = date;
    }
}