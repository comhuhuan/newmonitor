package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "cu_static")
public class CuStaticEntity extends StandardEntity {
    /**
     * 唯一用户ID
     */
    @Id
    @NotEmpty
    @Length(max=128)
    @Column(name = "UUID")
    @ColumnTitle("唯一用户ID")
    private String uuid;

    @Id
    @NotEmpty
    @Length(max=16)
    @Column(name = "cuIP")
    private String cuip;

    /**
     * 1-web 2-inter 3-db
     */
    @NotEmpty
    @Column(name = "cuType")
    @ColumnTitle("1-web")
    private int cutype;

    @NotEmpty
    @Length(max=128)
    @Column(name = "idcID")
    private String idcid;

    @NotEmpty
    @Column(name = "provID")
    private int provid;

    @NotEmpty
    @Length(max=128)
    @Column(name = "idcName")
    private String idcname;

    /**
     * 设备状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "device_stat")
    @ColumnTitle("设备状态(1-异常，0-正常)")
    private int deviceStat;

    /**
     * 设备当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "device_stat_his")
    @ColumnTitle("设备当天是否发生异常(1-异常，0-正常)")
    private int deviceStatHis;

    /**
     * 进程状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "process_stat")
    @ColumnTitle("进程状态(1-异常，0-正常)")
    private int processStat;

    /**
     * 进程当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "process_stat_his")
    @ColumnTitle("进程当天是否发生异常(1-异常，0-正常)")
    private int processStatHis;

    /**
     * CPU状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cpurate_stat")
    @ColumnTitle("CPU状态(1-异常，0-正常)")
    private int cpurateStat;

    /**
     * CPU当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cpurate_stat_his")
    @ColumnTitle("CPU当天是否发生异常(1-异常，0-正常)")
    private int cpurateStatHis;

    /**
     * 磁盘状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "disk_stat")
    @ColumnTitle("磁盘状态(1-异常，0-正常)")
    private int diskStat;

    /**
     * 磁盘当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "disk_stat_his")
    @ColumnTitle("磁盘当天是否发生异常(1-异常，0-正常)")
    private int diskStatHis;

    /**
     * 内存状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "memory_stat")
    @ColumnTitle("内存状态(1-异常，0-正常)")
    private int memoryStat;

    /**
     * 内存当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "memory_stat_his")
    @ColumnTitle("内存当天是否发生异常(1-异常，0-正常)")
    private int memoryStatHis;

    /**
     * 运行状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "runinfo_stat")
    @ColumnTitle("运行状态(1-异常，0-正常)")
    private int runinfoStat;

    /**
     * 运行当天是否发生异常(1-异常，0-正常
     */
    @NotEmpty
    @Column(name = "runinfo_stat_his")
    @ColumnTitle("运行当天是否发生异常(1-异常，0-正常")
    private int runinfoStatHis;

    /**
     * 到管局通信状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cu_smms_comm_stat")
    @ColumnTitle("到管局通信状态(1-异常，0-正常)")
    private int cuSmmsCommStat;

    /**
     * 到管局通信当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cu_smms_comm_stat_his")
    @ColumnTitle("到管局通信当天是否发生异常(1-异常，0-正常)")
    private int cuSmmsCommStatHis;

    /**
     * CU到DU通信状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cu_du_comm_stat")
    @ColumnTitle("CU到DU通信状态(1-异常，0-正常)")
    private int cuDuCommStat;

    /**
     * CU到DU通信状态当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cu_du_comm_stat_his")
    @ColumnTitle("CU到DU通信状态当天是否发生异常(1-异常，0-正常)")
    private int cuDuCommStatHis;

    /**
     * 指令状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cmd_stat")
    @ColumnTitle("指令状态(1-异常，0-正常)")
    private int cmdStat;

    /**
     * 指令当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "cmd_stat_his")
    @ColumnTitle("指令当天是否发生异常(1-异常，0-正常)")
    private int cmdStatHis;

    /**
     * 备用栏
     */
    @Length(max=128)
    @ColumnTitle("备用栏")
    private String reserve0;

    /**
     * 备用栏
     */
    @Length(max=128)
    @ColumnTitle("备用栏")
    private String reserve1;

    /**
     * EU四元状态(内存,cpu, 磁盘,进程的综合状态 1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "partial_stat")
    @ColumnTitle("EU四元状态(内存,cpu,")
    private int partialStat;

    /**
     * 机器状态(1-异常，0-正常)
     */
    @NotEmpty
    @ColumnTitle("机器状态(1-异常，0-正常)")
    private int status;

    /**
     * 机器当天是否异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "status_his")
    @ColumnTitle("机器当天是否异常(1-异常，0-正常)")
    private int statusHis;

    /**
     * 记录时间
     */
    @NotEmpty
    @Column(name = "recordTime")
    @ColumnTitle("记录时间")
    private Date recordtime;

    public static final String Property_RuninfoStat = "runinfoStat";

    public static final String Property_StatusHis = "statusHis";

    public static final String Property_DeviceStat = "deviceStat";

    public static final String Property_Cuip = "cuip";

    public static final String Property_RuninfoStatHis = "runinfoStatHis";

    public static final String Property_DiskStat = "diskStat";

    public static final String Property_CpurateStatHis = "cpurateStatHis";

    public static final String Property_DeviceStatHis = "deviceStatHis";

    public static final String Property_CuSmmsCommStatHis = "cuSmmsCommStatHis";

    public static final String Property_DiskStatHis = "diskStatHis";

    public static final String Property_Idcid = "idcid";

    public static final String Property_PartialStat = "partialStat";

    public static final String Property_CmdStat = "cmdStat";

    public static final String Property_Recordtime = "recordtime";

    public static final String Property_MemoryStat = "memoryStat";

    public static final String Property_MemoryStatHis = "memoryStatHis";

    public static final String Property_CuSmmsCommStat = "cuSmmsCommStat";

    public static final String Property_CuDuCommStat = "cuDuCommStat";

    public static final String Property_CmdStatHis = "cmdStatHis";

    public static final String Property_ProcessStat = "processStat";

    public static final String Property_CuDuCommStatHis = "cuDuCommStatHis";

    public static final String Property_Status = "status";

    public static final String Property_ProcessStatHis = "processStatHis";

    public static final String Property_Idcname = "idcname";

    public static final String Property_Provid = "provid";

    public static final String Property_Reserve1 = "reserve1";

    public static final String Property_Reserve0 = "reserve0";

    public static final String Property_Cutype = "cutype";

    public static final String Property_CpurateStat = "cpurateStat";

    public static final String Property_Uuid = "uuid";

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
     * @return cuIP
     */
    public String getCuip() {
        return cuip;
    }

    /**
     * @param cuip
     */
    public void setCuip(String cuip) {
        addChangeField("cuip",this.cuip,cuip);
        this.cuip = cuip;
    }

    /**
     * 获取1-web 2-inter 3-db
     *
     * @return cuType - 1-web 2-inter 3-db
     */
    public int getCutype() {
        return cutype;
    }

    /**
     * 设置1-web 2-inter 3-db
     *
     * @param cutype 1-web 2-inter 3-db
     */
    public void setCutype(int cutype) {
        addChangeField("cutype",this.cutype,cutype);
        this.cutype = cutype;
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
     * @return provID
     */
    public int getProvid() {
        return provid;
    }

    /**
     * @param provid
     */
    public void setProvid(int provid) {
        addChangeField("provid",this.provid,provid);
        this.provid = provid;
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
     * 获取设备状态(1-异常，0-正常)
     *
     * @return device_stat - 设备状态(1-异常，0-正常)
     */
    public int getDeviceStat() {
        return deviceStat;
    }

    /**
     * 设置设备状态(1-异常，0-正常)
     *
     * @param deviceStat 设备状态(1-异常，0-正常)
     */
    public void setDeviceStat(int deviceStat) {
        addChangeField("deviceStat",this.deviceStat,deviceStat);
        this.deviceStat = deviceStat;
    }

    /**
     * 获取设备当天是否发生异常(1-异常，0-正常)
     *
     * @return device_stat_his - 设备当天是否发生异常(1-异常，0-正常)
     */
    public int getDeviceStatHis() {
        return deviceStatHis;
    }

    /**
     * 设置设备当天是否发生异常(1-异常，0-正常)
     *
     * @param deviceStatHis 设备当天是否发生异常(1-异常，0-正常)
     */
    public void setDeviceStatHis(int deviceStatHis) {
        addChangeField("deviceStatHis",this.deviceStatHis,deviceStatHis);
        this.deviceStatHis = deviceStatHis;
    }

    /**
     * 获取进程状态(1-异常，0-正常)
     *
     * @return process_stat - 进程状态(1-异常，0-正常)
     */
    public int getProcessStat() {
        return processStat;
    }

    /**
     * 设置进程状态(1-异常，0-正常)
     *
     * @param processStat 进程状态(1-异常，0-正常)
     */
    public void setProcessStat(int processStat) {
        addChangeField("processStat",this.processStat,processStat);
        this.processStat = processStat;
    }

    /**
     * 获取进程当天是否发生异常(1-异常，0-正常)
     *
     * @return process_stat_his - 进程当天是否发生异常(1-异常，0-正常)
     */
    public int getProcessStatHis() {
        return processStatHis;
    }

    /**
     * 设置进程当天是否发生异常(1-异常，0-正常)
     *
     * @param processStatHis 进程当天是否发生异常(1-异常，0-正常)
     */
    public void setProcessStatHis(int processStatHis) {
        addChangeField("processStatHis",this.processStatHis,processStatHis);
        this.processStatHis = processStatHis;
    }

    /**
     * 获取CPU状态(1-异常，0-正常)
     *
     * @return cpurate_stat - CPU状态(1-异常，0-正常)
     */
    public int getCpurateStat() {
        return cpurateStat;
    }

    /**
     * 设置CPU状态(1-异常，0-正常)
     *
     * @param cpurateStat CPU状态(1-异常，0-正常)
     */
    public void setCpurateStat(int cpurateStat) {
        addChangeField("cpurateStat",this.cpurateStat,cpurateStat);
        this.cpurateStat = cpurateStat;
    }

    /**
     * 获取CPU当天是否发生异常(1-异常，0-正常)
     *
     * @return cpurate_stat_his - CPU当天是否发生异常(1-异常，0-正常)
     */
    public int getCpurateStatHis() {
        return cpurateStatHis;
    }

    /**
     * 设置CPU当天是否发生异常(1-异常，0-正常)
     *
     * @param cpurateStatHis CPU当天是否发生异常(1-异常，0-正常)
     */
    public void setCpurateStatHis(int cpurateStatHis) {
        addChangeField("cpurateStatHis",this.cpurateStatHis,cpurateStatHis);
        this.cpurateStatHis = cpurateStatHis;
    }

    /**
     * 获取磁盘状态(1-异常，0-正常)
     *
     * @return disk_stat - 磁盘状态(1-异常，0-正常)
     */
    public int getDiskStat() {
        return diskStat;
    }

    /**
     * 设置磁盘状态(1-异常，0-正常)
     *
     * @param diskStat 磁盘状态(1-异常，0-正常)
     */
    public void setDiskStat(int diskStat) {
        addChangeField("diskStat",this.diskStat,diskStat);
        this.diskStat = diskStat;
    }

    /**
     * 获取磁盘当天是否发生异常(1-异常，0-正常)
     *
     * @return disk_stat_his - 磁盘当天是否发生异常(1-异常，0-正常)
     */
    public int getDiskStatHis() {
        return diskStatHis;
    }

    /**
     * 设置磁盘当天是否发生异常(1-异常，0-正常)
     *
     * @param diskStatHis 磁盘当天是否发生异常(1-异常，0-正常)
     */
    public void setDiskStatHis(int diskStatHis) {
        addChangeField("diskStatHis",this.diskStatHis,diskStatHis);
        this.diskStatHis = diskStatHis;
    }

    /**
     * 获取内存状态(1-异常，0-正常)
     *
     * @return memory_stat - 内存状态(1-异常，0-正常)
     */
    public int getMemoryStat() {
        return memoryStat;
    }

    /**
     * 设置内存状态(1-异常，0-正常)
     *
     * @param memoryStat 内存状态(1-异常，0-正常)
     */
    public void setMemoryStat(int memoryStat) {
        addChangeField("memoryStat",this.memoryStat,memoryStat);
        this.memoryStat = memoryStat;
    }

    /**
     * 获取内存当天是否发生异常(1-异常，0-正常)
     *
     * @return memory_stat_his - 内存当天是否发生异常(1-异常，0-正常)
     */
    public int getMemoryStatHis() {
        return memoryStatHis;
    }

    /**
     * 设置内存当天是否发生异常(1-异常，0-正常)
     *
     * @param memoryStatHis 内存当天是否发生异常(1-异常，0-正常)
     */
    public void setMemoryStatHis(int memoryStatHis) {
        addChangeField("memoryStatHis",this.memoryStatHis,memoryStatHis);
        this.memoryStatHis = memoryStatHis;
    }

    /**
     * 获取运行状态(1-异常，0-正常)
     *
     * @return runinfo_stat - 运行状态(1-异常，0-正常)
     */
    public int getRuninfoStat() {
        return runinfoStat;
    }

    /**
     * 设置运行状态(1-异常，0-正常)
     *
     * @param runinfoStat 运行状态(1-异常，0-正常)
     */
    public void setRuninfoStat(int runinfoStat) {
        addChangeField("runinfoStat",this.runinfoStat,runinfoStat);
        this.runinfoStat = runinfoStat;
    }

    /**
     * 获取运行当天是否发生异常(1-异常，0-正常
     *
     * @return runinfo_stat_his - 运行当天是否发生异常(1-异常，0-正常
     */
    public int getRuninfoStatHis() {
        return runinfoStatHis;
    }

    /**
     * 设置运行当天是否发生异常(1-异常，0-正常
     *
     * @param runinfoStatHis 运行当天是否发生异常(1-异常，0-正常
     */
    public void setRuninfoStatHis(int runinfoStatHis) {
        addChangeField("runinfoStatHis",this.runinfoStatHis,runinfoStatHis);
        this.runinfoStatHis = runinfoStatHis;
    }

    /**
     * 获取到管局通信状态(1-异常，0-正常)
     *
     * @return cu_smms_comm_stat - 到管局通信状态(1-异常，0-正常)
     */
    public int getCuSmmsCommStat() {
        return cuSmmsCommStat;
    }

    /**
     * 设置到管局通信状态(1-异常，0-正常)
     *
     * @param cuSmmsCommStat 到管局通信状态(1-异常，0-正常)
     */
    public void setCuSmmsCommStat(int cuSmmsCommStat) {
        addChangeField("cuSmmsCommStat",this.cuSmmsCommStat,cuSmmsCommStat);
        this.cuSmmsCommStat = cuSmmsCommStat;
    }

    /**
     * 获取到管局通信当天是否发生异常(1-异常，0-正常)
     *
     * @return cu_smms_comm_stat_his - 到管局通信当天是否发生异常(1-异常，0-正常)
     */
    public int getCuSmmsCommStatHis() {
        return cuSmmsCommStatHis;
    }

    /**
     * 设置到管局通信当天是否发生异常(1-异常，0-正常)
     *
     * @param cuSmmsCommStatHis 到管局通信当天是否发生异常(1-异常，0-正常)
     */
    public void setCuSmmsCommStatHis(int cuSmmsCommStatHis) {
        addChangeField("cuSmmsCommStatHis",this.cuSmmsCommStatHis,cuSmmsCommStatHis);
        this.cuSmmsCommStatHis = cuSmmsCommStatHis;
    }

    /**
     * 获取CU到DU通信状态(1-异常，0-正常)
     *
     * @return cu_du_comm_stat - CU到DU通信状态(1-异常，0-正常)
     */
    public int getCuDuCommStat() {
        return cuDuCommStat;
    }

    /**
     * 设置CU到DU通信状态(1-异常，0-正常)
     *
     * @param cuDuCommStat CU到DU通信状态(1-异常，0-正常)
     */
    public void setCuDuCommStat(int cuDuCommStat) {
        addChangeField("cuDuCommStat",this.cuDuCommStat,cuDuCommStat);
        this.cuDuCommStat = cuDuCommStat;
    }

    /**
     * 获取CU到DU通信状态当天是否发生异常(1-异常，0-正常)
     *
     * @return cu_du_comm_stat_his - CU到DU通信状态当天是否发生异常(1-异常，0-正常)
     */
    public int getCuDuCommStatHis() {
        return cuDuCommStatHis;
    }

    /**
     * 设置CU到DU通信状态当天是否发生异常(1-异常，0-正常)
     *
     * @param cuDuCommStatHis CU到DU通信状态当天是否发生异常(1-异常，0-正常)
     */
    public void setCuDuCommStatHis(int cuDuCommStatHis) {
        addChangeField("cuDuCommStatHis",this.cuDuCommStatHis,cuDuCommStatHis);
        this.cuDuCommStatHis = cuDuCommStatHis;
    }

    /**
     * 获取指令状态(1-异常，0-正常)
     *
     * @return cmd_stat - 指令状态(1-异常，0-正常)
     */
    public int getCmdStat() {
        return cmdStat;
    }

    /**
     * 设置指令状态(1-异常，0-正常)
     *
     * @param cmdStat 指令状态(1-异常，0-正常)
     */
    public void setCmdStat(int cmdStat) {
        addChangeField("cmdStat",this.cmdStat,cmdStat);
        this.cmdStat = cmdStat;
    }

    /**
     * 获取指令当天是否发生异常(1-异常，0-正常)
     *
     * @return cmd_stat_his - 指令当天是否发生异常(1-异常，0-正常)
     */
    public int getCmdStatHis() {
        return cmdStatHis;
    }

    /**
     * 设置指令当天是否发生异常(1-异常，0-正常)
     *
     * @param cmdStatHis 指令当天是否发生异常(1-异常，0-正常)
     */
    public void setCmdStatHis(int cmdStatHis) {
        addChangeField("cmdStatHis",this.cmdStatHis,cmdStatHis);
        this.cmdStatHis = cmdStatHis;
    }

    /**
     * 获取备用栏
     *
     * @return reserve0 - 备用栏
     */
    public String getReserve0() {
        return reserve0;
    }

    /**
     * 设置备用栏
     *
     * @param reserve0 备用栏
     */
    public void setReserve0(String reserve0) {
        addChangeField("reserve0",this.reserve0,reserve0);
        this.reserve0 = reserve0;
    }

    /**
     * 获取备用栏
     *
     * @return reserve1 - 备用栏
     */
    public String getReserve1() {
        return reserve1;
    }

    /**
     * 设置备用栏
     *
     * @param reserve1 备用栏
     */
    public void setReserve1(String reserve1) {
        addChangeField("reserve1",this.reserve1,reserve1);
        this.reserve1 = reserve1;
    }

    /**
     * 获取EU四元状态(内存,cpu, 磁盘,进程的综合状态 1-异常，0-正常)
     *
     * @return partial_stat - EU四元状态(内存,cpu, 磁盘,进程的综合状态 1-异常，0-正常)
     */
    public int getPartialStat() {
        return partialStat;
    }

    /**
     * 设置EU四元状态(内存,cpu, 磁盘,进程的综合状态 1-异常，0-正常)
     *
     * @param partialStat EU四元状态(内存,cpu, 磁盘,进程的综合状态 1-异常，0-正常)
     */
    public void setPartialStat(int partialStat) {
        addChangeField("partialStat",this.partialStat,partialStat);
        this.partialStat = partialStat;
    }

    /**
     * 获取机器状态(1-异常，0-正常)
     *
     * @return status - 机器状态(1-异常，0-正常)
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置机器状态(1-异常，0-正常)
     *
     * @param status 机器状态(1-异常，0-正常)
     */
    public void setStatus(int status) {
        addChangeField("status",this.status,status);
        this.status = status;
    }

    /**
     * 获取机器当天是否异常(1-异常，0-正常)
     *
     * @return status_his - 机器当天是否异常(1-异常，0-正常)
     */
    public int getStatusHis() {
        return statusHis;
    }

    /**
     * 设置机器当天是否异常(1-异常，0-正常)
     *
     * @param statusHis 机器当天是否异常(1-异常，0-正常)
     */
    public void setStatusHis(int statusHis) {
        addChangeField("statusHis",this.statusHis,statusHis);
        this.statusHis = statusHis;
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