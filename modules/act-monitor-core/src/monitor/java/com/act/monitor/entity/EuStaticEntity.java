package com.act.monitor.entity;

import com.act.framework.entity.StandardEntity;
import com.act.mapper.annotation.ColumnTitle;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "eu_static")
public class EuStaticEntity extends StandardEntity {
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
    @Column(name = "houseID")
    private long houseid;

    @Id
    @NotEmpty
    @Length(max=32)
    @Column(name = "euID")
    private String euid;

    @NotEmpty
    @Length(max=16)
    @Column(name = "euIP")
    private String euip;

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
     * EU到CU通信状态 (1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "eu_cu_comm_stat")
    @ColumnTitle("EU到CU通信状态")
    private int euCuCommStat;

    /**
     * EU到CU通信状态当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "eu_cu_comm_stat_his")
    @ColumnTitle("EU到CU通信状态当天是否发生异常(1-异常，0-正常)")
    private int euCuCommStatHis;

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
    @Column(name = "memory_stat")
    @ColumnTitle("内存状态(1-异常，0-正常)")
    private Integer memoryStat;

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
     * 运行当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "runinfo_stat_his")
    @ColumnTitle("运行当天是否发生异常(1-异常，0-正常)")
    private int runinfoStatHis;

    /**
     * EU到DU通信状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "eu_du_comm_stat")
    @ColumnTitle("EU到DU通信状态(1-异常，0-正常)")
    private int euDuCommStat;

    /**
     * EU到DU通信是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "eu_du_comm_stat_his")
    @ColumnTitle("EU到DU通信是否发生异常(1-异常，0-正常)")
    private int euDuCommStatHis;

    /**
     * 链路状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "link_stat")
    @ColumnTitle("链路状态(1-异常，0-正常)")
    private int linkStat;

    /**
     * 链路当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "link_stat_his")
    @ColumnTitle("链路当天是否发生异常(1-异常，0-正常)")
    private int linkStatHis;

    /**
     * 网卡状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "network_stat")
    @ColumnTitle("网卡状态(1-异常，0-正常)")
    private int networkStat;

    /**
     * 网卡当天是否发生异常(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "network_stat_his")
    @ColumnTitle("网卡当天是否发生异常(1-异常，0-正常)")
    private int networkStatHis;

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
     * EU机器状态(1-异常，0-正常)
     */
    @NotEmpty
    @ColumnTitle("EU机器状态(1-异常，0-正常)")
    private int status;

    /**
     * EU机器状态(1-异常，0-正常)
     */
    @NotEmpty
    @Column(name = "status_his")
    @ColumnTitle("EU机器状态(1-异常，0-正常)")
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

    public static final String Property_EuDuCommStatHis = "euDuCommStatHis";

    public static final String Property_Euip = "euip";

    public static final String Property_DeviceStat = "deviceStat";

    public static final String Property_RuninfoStatHis = "runinfoStatHis";

    public static final String Property_DiskStat = "diskStat";

    public static final String Property_DeviceStatHis = "deviceStatHis";

    public static final String Property_CpurateStatHis = "cpurateStatHis";

    public static final String Property_Houseid = "houseid";

    public static final String Property_DiskStatHis = "diskStatHis";

    public static final String Property_Idcid = "idcid";

    public static final String Property_PartialStat = "partialStat";

    public static final String Property_Recordtime = "recordtime";

    public static final String Property_MemoryStat = "memoryStat";

    public static final String Property_Euid = "euid";

    public static final String Property_MemoryStatHis = "memoryStatHis";

    public static final String Property_EuDuCommStat = "euDuCommStat";

    public static final String Property_LinkStatHis = "linkStatHis";

    public static final String Property_ProcessStat = "processStat";

    public static final String Property_Status = "status";

    public static final String Property_NetworkStat = "networkStat";

    public static final String Property_ProcessStatHis = "processStatHis";

    public static final String Property_Idcname = "idcname";

    public static final String Property_NetworkStatHis = "networkStatHis";

    public static final String Property_EuCuCommStatHis = "euCuCommStatHis";

    public static final String Property_Provid = "provid";

    public static final String Property_Reserve1 = "reserve1";

    public static final String Property_LinkStat = "linkStat";

    public static final String Property_Reserve0 = "reserve0";

    public static final String Property_CpurateStat = "cpurateStat";

    public static final String Property_Uuid = "uuid";

    public static final String Property_EuCuCommStat = "euCuCommStat";

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
     * @return euID
     */
    public String getEuid() {
        return euid;
    }

    /**
     * @param euid
     */
    public void setEuid(String euid) {
        addChangeField("euid",this.euid,euid);
        this.euid = euid;
    }

    /**
     * @return euIP
     */
    public String getEuip() {
        return euip;
    }

    /**
     * @param euip
     */
    public void setEuip(String euip) {
        addChangeField("euip",this.euip,euip);
        this.euip = euip;
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
     * 获取EU到CU通信状态 (1-异常，0-正常)
     *
     * @return eu_cu_comm_stat - EU到CU通信状态 (1-异常，0-正常)
     */
    public int getEuCuCommStat() {
        return euCuCommStat;
    }

    /**
     * 设置EU到CU通信状态 (1-异常，0-正常)
     *
     * @param euCuCommStat EU到CU通信状态 (1-异常，0-正常)
     */
    public void setEuCuCommStat(int euCuCommStat) {
        addChangeField("euCuCommStat",this.euCuCommStat,euCuCommStat);
        this.euCuCommStat = euCuCommStat;
    }

    /**
     * 获取EU到CU通信状态当天是否发生异常(1-异常，0-正常)
     *
     * @return eu_cu_comm_stat_his - EU到CU通信状态当天是否发生异常(1-异常，0-正常)
     */
    public int getEuCuCommStatHis() {
        return euCuCommStatHis;
    }

    /**
     * 设置EU到CU通信状态当天是否发生异常(1-异常，0-正常)
     *
     * @param euCuCommStatHis EU到CU通信状态当天是否发生异常(1-异常，0-正常)
     */
    public void setEuCuCommStatHis(int euCuCommStatHis) {
        addChangeField("euCuCommStatHis",this.euCuCommStatHis,euCuCommStatHis);
        this.euCuCommStatHis = euCuCommStatHis;
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
    public Integer getMemoryStat() {
        return memoryStat;
    }

    /**
     * 设置内存状态(1-异常，0-正常)
     *
     * @param memoryStat 内存状态(1-异常，0-正常)
     */
    public void setMemoryStat(Integer memoryStat) {
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
     * 获取运行当天是否发生异常(1-异常，0-正常)
     *
     * @return runinfo_stat_his - 运行当天是否发生异常(1-异常，0-正常)
     */
    public int getRuninfoStatHis() {
        return runinfoStatHis;
    }

    /**
     * 设置运行当天是否发生异常(1-异常，0-正常)
     *
     * @param runinfoStatHis 运行当天是否发生异常(1-异常，0-正常)
     */
    public void setRuninfoStatHis(int runinfoStatHis) {
        addChangeField("runinfoStatHis",this.runinfoStatHis,runinfoStatHis);
        this.runinfoStatHis = runinfoStatHis;
    }

    /**
     * 获取EU到DU通信状态(1-异常，0-正常)
     *
     * @return eu_du_comm_stat - EU到DU通信状态(1-异常，0-正常)
     */
    public int getEuDuCommStat() {
        return euDuCommStat;
    }

    /**
     * 设置EU到DU通信状态(1-异常，0-正常)
     *
     * @param euDuCommStat EU到DU通信状态(1-异常，0-正常)
     */
    public void setEuDuCommStat(int euDuCommStat) {
        addChangeField("euDuCommStat",this.euDuCommStat,euDuCommStat);
        this.euDuCommStat = euDuCommStat;
    }

    /**
     * 获取EU到DU通信是否发生异常(1-异常，0-正常)
     *
     * @return eu_du_comm_stat_his - EU到DU通信是否发生异常(1-异常，0-正常)
     */
    public int getEuDuCommStatHis() {
        return euDuCommStatHis;
    }

    /**
     * 设置EU到DU通信是否发生异常(1-异常，0-正常)
     *
     * @param euDuCommStatHis EU到DU通信是否发生异常(1-异常，0-正常)
     */
    public void setEuDuCommStatHis(int euDuCommStatHis) {
        addChangeField("euDuCommStatHis",this.euDuCommStatHis,euDuCommStatHis);
        this.euDuCommStatHis = euDuCommStatHis;
    }

    /**
     * 获取链路状态(1-异常，0-正常)
     *
     * @return link_stat - 链路状态(1-异常，0-正常)
     */
    public int getLinkStat() {
        return linkStat;
    }

    /**
     * 设置链路状态(1-异常，0-正常)
     *
     * @param linkStat 链路状态(1-异常，0-正常)
     */
    public void setLinkStat(int linkStat) {
        addChangeField("linkStat",this.linkStat,linkStat);
        this.linkStat = linkStat;
    }

    /**
     * 获取链路当天是否发生异常(1-异常，0-正常)
     *
     * @return link_stat_his - 链路当天是否发生异常(1-异常，0-正常)
     */
    public int getLinkStatHis() {
        return linkStatHis;
    }

    /**
     * 设置链路当天是否发生异常(1-异常，0-正常)
     *
     * @param linkStatHis 链路当天是否发生异常(1-异常，0-正常)
     */
    public void setLinkStatHis(int linkStatHis) {
        addChangeField("linkStatHis",this.linkStatHis,linkStatHis);
        this.linkStatHis = linkStatHis;
    }

    /**
     * 获取网卡状态(1-异常，0-正常)
     *
     * @return network_stat - 网卡状态(1-异常，0-正常)
     */
    public int getNetworkStat() {
        return networkStat;
    }

    /**
     * 设置网卡状态(1-异常，0-正常)
     *
     * @param networkStat 网卡状态(1-异常，0-正常)
     */
    public void setNetworkStat(int networkStat) {
        addChangeField("networkStat",this.networkStat,networkStat);
        this.networkStat = networkStat;
    }

    /**
     * 获取网卡当天是否发生异常(1-异常，0-正常)
     *
     * @return network_stat_his - 网卡当天是否发生异常(1-异常，0-正常)
     */
    public int getNetworkStatHis() {
        return networkStatHis;
    }

    /**
     * 设置网卡当天是否发生异常(1-异常，0-正常)
     *
     * @param networkStatHis 网卡当天是否发生异常(1-异常，0-正常)
     */
    public void setNetworkStatHis(int networkStatHis) {
        addChangeField("networkStatHis",this.networkStatHis,networkStatHis);
        this.networkStatHis = networkStatHis;
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
     * 获取EU机器状态(1-异常，0-正常)
     *
     * @return status - EU机器状态(1-异常，0-正常)
     */
    public int getStatus() {
        return status;
    }

    /**
     * 设置EU机器状态(1-异常，0-正常)
     *
     * @param status EU机器状态(1-异常，0-正常)
     */
    public void setStatus(int status) {
        addChangeField("status",this.status,status);
        this.status = status;
    }

    /**
     * 获取EU机器状态(1-异常，0-正常)
     *
     * @return status_his - EU机器状态(1-异常，0-正常)
     */
    public int getStatusHis() {
        return statusHis;
    }

    /**
     * 设置EU机器状态(1-异常，0-正常)
     *
     * @param statusHis EU机器状态(1-异常，0-正常)
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