package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.web.constant.CommonContant;
import com.act.web.module.monitor.service.ProcessService;
import com.act.web.module.monitor.vo.*;
import com.act.web.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProcessServiceImpl implements ProcessService {

    private final Logger log = LoggerFactory.getLogger(ProcessServiceImpl.class);

    /*
     * (非 Javadoc) <p>Title: initializeTitle</p>
     * <p>Description: 初始化页面进程状态统计 </p>
     *
     * @param uuId 唯一用户ID
     * @param deviceId 设备ID(euId/cuIp)
     * @param houseId 机房ID
     * @param elementType 状态类型
     * @param queryType elementType为euDevice或cuDevice时才有此参数
     *
     * @return
     *
     * @see com.act.web.module.monitor.service.ProcessService#initialize()
     */
    @Override
    public Object initializeTitle(String uuId, String deviceId, String houseId, String elementType, String queryType) {

        String tableType = SqlUtil.getTableType(elementType, queryType);
        String tableName = SqlUtil.queryTableIndex(uuId, tableType);
        Long iHouseId = null;
        Object[] param = null;
        int deviceType = 0;
        String statusType = "";
        if (null != houseId && !"".equals(houseId.trim())) {
            iHouseId = Long.valueOf(houseId);
        }
        if (tableName != null && !"".equals(tableName)) {
            String idType = DeviceType(elementType);
            StringBuffer sql = new StringBuffer();
            String whereString = null;
            String whereString1 = null;
            String netTableName = null;

            if (CommonContant.EU_DEVICE.equals(elementType) || CommonContant.EU_TO_CU.equals(elementType) || CommonContant.EU_TO_DU.equals(elementType)
                    || CommonContant.LINK_STATUS.equals(elementType) || CommonContant.NETWORK_STATUS.equals(elementType) || CommonContant.ACCESS_LOG.equals(elementType)) {
                whereString = " WHERE  uuid = '" + uuId + "'  and houseID = " + iHouseId + "  AND " + idType + " = '" + deviceId + "' ";
                whereString1 = "  on a.uuid = b.uuid and a.houseID = b.houseID and a.euID = b.euID WHERE  a.uuid = '" + uuId + "'  and a.houseID = " + iHouseId + "  AND a." + idType.trim() + " = '" + deviceId + "' ";
                netTableName = SqlUtil.queryTableIndex(uuId, CommonContant.EU_DEVICE_TYPE);

                param = new Object[3];
                param[0] = uuId;
                param[1] = iHouseId;
                param[2] = deviceId;
                deviceType = 2;

            } else {
                whereString = " WHERE  uuid = '" + uuId + "'  AND " + idType + " = '" + deviceId + "' ";
                whereString1 = "   on a.uuid = b.uuid and a.cuIP = b.cuIP  WHERE  a.uuid = '" + uuId + "'  AND a." + idType.trim() + " = '" + deviceId + "' ";
                netTableName = SqlUtil.queryTableIndex(uuId, CommonContant.CU_DEVICE_TYPE);

                param = new Object[2];
                param[0] = uuId;
                param[1] = deviceId;
                deviceType = 1;
            }

            if (CommonContant.EU_TO_CU.equals(elementType) || CommonContant.EU_TO_DU.equals(elementType)) {    //euTocu
                statusType = "";
                sql.append("SELECT '").append(elementType).append("' as elementType,b.euID,b.euIP,b.soft_version,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S')  as recordTime,a.status");
                sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                        .append(whereString1).append(" order by a.recordTime desc limit 1");
                BasicVo baiscVo = DbUtil.queryForObject(sql.toString(), BasicVo.class);
                return baiscVo;
            } else if (CommonContant.LINK_STATUS.equals(elementType)) { //链路
                sql.append("SELECT '").append(elementType).append("' as elementType,b.euID,b.euIP,b.soft_version,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.status,a.inflow");
                sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                        .append(whereString1).append(" order by a.recordTime desc limit 1");
                BasicVo baiscVo = DbUtil.queryForObject(sql.toString(), BasicVo.class);
                return baiscVo;
            } else if (CommonContant.NETWORK_STATUS.equals(elementType)) { //网卡
                String devTableName = SqlUtil.queryTableIndex(uuId, CommonContant.EU_DEVICE_TYPE);
                sql.append("SELECT '").append(elementType).append("' as elementType," + idType + ",date_format(recordTime,'%Y-%c-%d %H:%i:%S')  as recordTime,status");
                sql.append(",mac,cardName,cardFunction,bps,connStatus ").append(" FROM ").append(tableName)
                        .append(" WHERE uuid = '" + uuId + "'  and houseID = " + iHouseId + "  AND " + idType + " = '" + deviceId + "'").append(" order by recordTime desc");
                List<NetWorkInfoVo> netWorkInfoList = DbUtil.queryForObjectList(sql.toString(), NetWorkInfoVo.class);

                StringBuffer devInfoSql = new StringBuffer("select a.soft_version,a.euID,a.euIP,a.houseID,b.houseName from ").append(devTableName).append(" a left join monsys_all_housename_info b ");
                devInfoSql.append(" on a.uuid = b.uuid and a.houseID = b.houseID ")
                        .append(" WHERE  a.uuid = '" + uuId + "'  and a.houseID = " + iHouseId + "  AND a." + idType + " = '" + deviceId + "'").append(" limit 1");
                NetWorkVo netWorkVo = DbUtil.queryForObject(devInfoSql.toString(), NetWorkVo.class);
                netWorkVo.setElementType(elementType);
                NetWorkVo netWorkVo1 = DbUtil.queryForObject("SELECT  eu.network_stat as `status` FROM `eu_static` eu WHERE eu.UUID= '" + uuId + "' and eu.euID = '" + deviceId + "';", NetWorkVo.class);
                System.out.println(netWorkVo1);
                netWorkVo.setStatus(netWorkVo1.getStatus());

                if (null != netWorkInfoList && netWorkInfoList.size() > 0) {
                    netWorkVo.setRecordTime(netWorkInfoList.get(0).getRecordTime());
                }
                netWorkVo.setNetworkInfo(netWorkInfoList);
                return netWorkVo;
            } else if (CommonContant.CU_TO_SMMS.equals(elementType) || CommonContant.CU_TO_DU.equals(elementType)) {
                if (CommonContant.CU_TO_SMMS.equals(elementType)) {
                    statusType = "cu_du_comm_stat";
                } else {
                    statusType = "cu_smms_comm_stat";
                }
                //String whereString = " WHERE  uuid = '"+uuId+"'  AND "+idType+" = '"+deviceId+"'";
                sql.append("SELECT '").append(elementType).append("' as elementType,'CU' as houseName,a." + idType + ",date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.status,b.cuIP,b.soft_version,b.cuType");

                sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                        .append(whereString1).append(" order by a.recordTime desc limit 1");
                BasicVo baiscVo = DbUtil.queryForObject(sql.toString(), BasicVo.class);
                //重新设置查询的状态
                /*int status = getStatus(uuId,deviceType,statusType,param);
                baiscVo.setStatus(status);*/
                return baiscVo;
            } else if (CommonContant.ACCESS_LOG.equals(elementType)) {
                //String whereString = " WHERE  uuid = '"+uuId+"'  and houseID = "+iHouseId+ "  AND "+idType+" = '"+deviceId+"'";
                sql.append("SELECT '").append(elementType).append("' as elementType,a." + idType + ",date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.actLog_uploadNum,a.actlog_num,")
                        .append("a.monitorLog_uploadNum,a.blockLog_uploadNum,a.statLog_uploadNum,a.resLog_uploadNum,a.8080occupation as occupation,a.status,b.euID,b.euIP,b.soft_version");
                sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                        .append(whereString1).append(" order by a.recordTime desc limit 1");
                ServiceVo serviceVo = DbUtil.queryForObject(sql.toString(), ServiceVo.class);

                return serviceVo;
            } else if (CommonContant.EU_DEVICE.equals(elementType) || CommonContant.CU_DEVICE.equals(elementType)) {    //euDevice
                String queryString = "";
                if (CommonContant.CU_DEVICE.equals(elementType)) {//查询类型为CU设备时需要查询cuType
                    queryString = ",b.cuType";
                }
                if ("cpu".equals(queryType)) {    //cpu

                    Map<String, Object> cpuRateMap = getCpuRateMap(tableName, whereString);
                    if ("euID".equalsIgnoreCase(idType)) {
                        sql.append("SELECT 'euDevice' as elementType,a.coreNum,a.cputotal_Rate as cpuTotalRate,'cpu' as queryType,b.euID,b.euIP,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.status");

                    } else {
                        sql.append("SELECT 'euDevice' as elementType,a.coreNum,a.cputotal_Rate as cpuTotalRate,'cpu' as queryType,a." + idType + ",date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.status");
                    }
                    sql.append(",b.cpuFrequcy,b.memoryTotal,b.cpuModel,b.soft_version");
                    sql.append(queryString);
                    sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                            .append(whereString1).append(" order by a.recordTime desc limit 1");
                    CpuVo cpuVo = DbUtil.queryForObject(sql.toString(), CpuVo.class);
                    //重新设置查询的状态
                    statusType = "cpurate_stat";
                    int status = getStatus(uuId, deviceType, statusType, param);
                    cpuVo.setStatus(status);
                    cpuVo.setCoreRate(cpuRateMap);
                    return cpuVo;
                } else if ("process".equals(queryType)) {    //process
                    String sql_badTotal = "SELECT count(1) FROM " + tableName + whereString + " AND pro_status = 1";
                    String sql_total = "SELECT count(process_name) FROM " + tableName + whereString + " AND pro_status <>-1";
                    int badTotal = DbUtil.queryForInt(sql_badTotal);
                    int total = DbUtil.queryForInt(sql_total);

                    List<Map<String, Object>> processMap = getProcessMap(tableName, whereString);

                    if ("euID".equalsIgnoreCase(idType)) {
                        sql.append("SELECT DISTINCT 'euDevice' as elementType,'process' as queryType,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,b.euID,b.euIP,b.soft_version ");
                    } else {
                        sql.append("SELECT DISTINCT 'euDevice' as elementType,a." + idType + ",'process' as queryType,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,b.soft_version ");
                    }
                    sql.append(queryString);

                    sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                            .append(whereString1).append(" and a.pro_status <> -1").append(" order by a.recordTime desc limit 1");

                    ProcessVo processVo = DbUtil.queryForObject(sql.toString(), ProcessVo.class);
                    processVo.setBadtotal(badTotal);
                    processVo.setTotal(total);
                    processVo.setProcess(processMap);
                    //重新设置查询的状态
                    statusType = "process_stat";
                    int status = getStatus(uuId, deviceType, statusType, param);
                    processVo.setStatus(status);
                    return processVo;
                } else if ("disk".equals(queryType)) {    //disk
                    List<Map<String, Object>> diskInfoMap = getDiskInfoMap(tableName, whereString);

                    String sql_diskNum = "SELECT count(diskName) FROM " + tableName + whereString;
                    int diskNum = DbUtil.queryForInt(sql_diskNum);


                    if ("euID".equalsIgnoreCase(idType)) {
                        sql.append("SELECT DISTINCT 'euDevice' as elementType,'disk' as queryType,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,b.euID,b.euIP,b.soft_version ");
                    } else {
                        sql.append("SELECT DISTINCT 'euDevice' as elementType,a." + idType + ",'disk' as queryType,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,b.soft_version ");
                    }
                    sql.append(queryString);

                    sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                            .append(whereString1).append(" order by a.recordTime desc limit 1");
                    DiskVo diskVo = DbUtil.queryForObject(sql.toString(), DiskVo.class);

                    diskVo.setDiskNum(diskNum);
                    diskVo.setDiskInfo(diskInfoMap);
                    //重新设置查询的状态
                    statusType = "disk_stat";
                    int status = getStatus(uuId, deviceType, statusType, param);
                    diskVo.setStatus(status);
                    return diskVo;
                } else if ("memory".equals(queryType)) {    //memory
                    if ("euID".equalsIgnoreCase(idType)) {
                        sql.append("SELECT 'euDevice' as elementType,'memory' as queryType,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.memoryBuffer as memoryBuffer,a.memoryFree as memoryFree,a.memory_Rate as memoryRate,a.memoryUsed as memoryUsed,a.status,b.euID,b.euIP,b.soft_version ");
                    } else {
                        sql.append("SELECT 'euDevice' as elementType,'memory' as queryType,a." + idType + ",date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.memoryBuffer as memoryBuffer,a.memoryFree as memoryFree,a.memory_Rate as memoryRate,a.memoryUsed as memoryUsed,a.status,b.soft_version ");

                    }
                    sql.append(queryString);

                    sql.append(" FROM ").append(tableName).append(" a left join ").append(netTableName).append(" b ")
                            .append(whereString1).append(" order by a.recordTime desc limit 1");
                    MemoryVo memoryVo = DbUtil.queryForObject(sql.toString(), MemoryVo.class);

                    return memoryVo;
                }
            }
        }
        return null;
    }

    //查询设备状态的公共方法
    private int getStatus(String uuid, int devceType, String statusType, Object[] param) {
        StringBuffer sql = null;
        if (1 == devceType) {//CU
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);
            sql = new StringBuffer("select ").append(statusType).append("  from ");
            sql.append(tableName).append(" where uuid = ?  and cuIP = ? limit 1");

        } else if (2 == devceType) {//EU
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);
            sql = new StringBuffer("select ").append(statusType).append("  from ");
            sql.append(tableName).append(" where uuid = ?  and  houseID = ? and euID = ? limit 1");
        }
        return DbUtil.queryForInt(sql.toString(), param);
    }

    /**
     * @param tableName   表名
     * @param UUID        唯一用户ID
     * @param deviceId    设备ID(euID/cuIP)
     * @param elementType 状态类型
     * @title getCpuRateMap
     * @Description 获取cpu使用率Map
     * @author liuyang
     * @createTime 2017年6月29日10:57:22
     */
    private Map<String, Object> getCpuRateMap(String tableName, String where) {

        StringBuffer sql_cpuNum = new StringBuffer("SELECT coreNum FROM ");
        sql_cpuNum.append(tableName).append(where);
        int cpuNum = DbUtil.queryForInt(sql_cpuNum.toString());

        StringBuffer sql = new StringBuffer("select ");

        for (int i = 0; i < cpuNum; i++) {
            if (i == (cpuNum - 1)) {
                sql.append("cpu" + i);
            } else {
                sql.append("cpu" + i + ",");
            }
        }

        sql.append(" from ").append(tableName)
                .append(where).append("  limit 1");

        Map<String, Object> result = DbUtil.queryForLinekHashMap(sql.toString());
        return result;
    }

    /**
     * @param tableName   表名
     * @param UUID        唯一用户ID
     * @param deviceId    设备ID(euID/cuIP)
     * @param elementType 状态类型
     * @title getProcessMap
     * @Description 获取进程信息Map
     * @author liuyang
     * @createTime 2017年6月29日10:57:51
     */
    private List<Map<String, Object>> getProcessMap(String tableName, String where) {
        StringBuffer sql = new StringBuffer("SELECT process_name,pro_status,date_format(recordTime,'%Y-%c-%d %H:%i:%S') as recordTime FROM ");
        sql.append(tableName).append(where).append(" and status = 1 ").append(" and pro_status = 1").append(" order by recordTime desc");
        ;
        List<Map<String, Object>> result = DbUtil.queryForMapList(sql.toString());
        return result;
    }

    /**
     * @param tableName   表名
     * @param UUID        唯一用户ID
     * @param deviceId    设备ID(euID/cuIP)
     * @param elementType 状态类型
     * @title getDiskInfoMap
     * @Description 获取磁盘信息Map
     * @author liuyang
     * @createTime 2017年6月29日11:54:00
     */
    private List<Map<String, Object>> getDiskInfoMap(String tableName, String where) {
        StringBuffer sql = new StringBuffer("SELECT diskName,diskTotalSize,status,useRate FROM ");
        sql.append(tableName).append(where).append(" group by diskName order by diskName desc");
        List<Map<String, Object>> result = DbUtil.queryForMapList(sql.toString());

        return result;
    }

    /**
     * @param elementType
     * @return
     * @title DeviceType
     * @Description 根据状态类型判断设备ID类型
     */
    private String DeviceType(String elementType) {
        if (CommonContant.EU_TO_CU.equals(elementType) || CommonContant.EU_TO_DU.equals(elementType)
                || CommonContant.ACCESS_LOG.equals(elementType) || CommonContant.LINK_STATUS.equals(elementType)
                || CommonContant.NETWORK_STATUS.equals(elementType) || CommonContant.EU_DEVICE.equals(elementType)) {
            return "euID";
        } else {
            return "cuIP";
        }
    }
}