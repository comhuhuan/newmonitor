/**
 * @Title: DomainCodeServiceImpl.java
 * @Package com.act.web.module.monitor.service
 * @Description: 设备搜索service
 * @author liuyang
 * @modifier liuyang
 * @date 2017年6月21日09:18:07
 * @version V1.0
 */
package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.web.constant.CommonContant;
import com.act.web.module.monitor.service.DeviceStatusService;
import com.act.web.module.monitor.vo.DeviceStatusVo;
import com.act.web.util.SqlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceStatusServiceImpl implements DeviceStatusService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(DeviceStatusServiceImpl.class);

    @Override
    public List<DeviceStatusVo> tuopu(DeviceStatusVo deviceVo) {
        List<DeviceStatusVo> resultList = new ArrayList<DeviceStatusVo>();
        Map<Long, DeviceStatusVo> resultMap = new HashMap<Long, DeviceStatusVo>();
        Integer devceType = deviceVo.getDevceType();
        String uuid = deviceVo.getUuid();
        Object[] param = new Object[1];
        param[0] = uuid;

        if (null == devceType || "".equals(devceType)) {//查询所有设备
            String tableName_cu = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);//需要确认表对应的TYPE
            String tableName_eu = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);//需要确认表对应的TYPE

            StringBuffer sql_cu = new StringBuffer("select uuid, cuIP as cuID,idcID, status from ")
                    .append(tableName_eu).append(" group by houseID having uuid=? ORDER BY cuIP desc");
            StringBuffer sql_eu = new StringBuffer("select uuid,euID,euIP,houseID,houseName ,idcID,idcName status from ")
                    .append(tableName_cu).append(" group by houseID having uuid=? ORDER BY euIP desc");

            List<DeviceStatusVo> list_cu = DbUtil.queryForObjectList(sql_cu.toString(), DeviceStatusVo.class, param);
            List<DeviceStatusVo> list_eu = DbUtil.queryForObjectList(sql_eu.toString(), DeviceStatusVo.class, param);
            //合并结果集
            if ((null != list_cu && list_cu.size() > 0) && (null != list_eu && list_eu.size() > 0)) {
                for (DeviceStatusVo cuVo : list_cu) {
                    Long cuHouseID = cuVo.getHouseID();
                    for (DeviceStatusVo euVo : list_eu) {
                        Long euHouseID = euVo.getHouseID();
                        if (euHouseID == cuHouseID) {
                            int tempStatus = cuVo.getStatus() + euVo.getStatus();
                            cuVo.setStatus(tempStatus);
                            euVo.setStatus(tempStatus);
                        }
                    }
                }
            }
            //合并结果集
            if (null != list_cu && list_cu.size() > 0) {
                for (DeviceStatusVo cuVo : list_cu) {
                    resultMap.put(cuVo.getHouseID(), cuVo);
                }
            }
            if (null != list_eu && list_eu.size() > 0) {
                for (DeviceStatusVo euVo : list_eu) {
                    resultMap.put(euVo.getHouseID(), euVo);
                }
            }
            Iterator it = resultMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                resultList.add(resultMap.get(key));
            }
            return resultList;
        } else if (1 == devceType) {
            //查询CU设备
            String tableName_cu = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);//需要确认表对应的TYPE
            //String sqlCoulunm_cu = "device_stat+cu_du_comm_stat+cu_smms_comm_stat";
            StringBuffer sql_cu = new StringBuffer("select uuid, cuIP as cuID ,'CU' as houseName, sum(status) as status from ")
                    .append(tableName_cu).append(" where uuid=? ORDER BY recordTime desc");
            List<DeviceStatusVo> list = DbUtil.queryForObjectList(sql_cu.toString(), DeviceStatusVo.class, param);
            return list;
        } else if (2 == devceType) {
            //查询EU设备
            String tableName_eu = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);//需要确认表对应的TYPE
            //String sqlCoulunm_eu = "eu_cu_comm_stat+device_stat+process_stat+cpurate_stat+memory_stat+runinfo_stat+disk_stat+eu_du_comm_stat";
            StringBuffer sql_eu = new StringBuffer("select a.uuid,a.euID, a.houseID,b.houseName,sum(status) as status from ")
                    .append(tableName_eu).append(" a left join monsys_all_housename_info b on a.uuid = b.uuid and a.houseID = b.houseID ").append(" where a.uuid=? group by houseID ORDER BY a.recordTime desc");
            List<DeviceStatusVo> list = DbUtil.queryForObjectList(sql_eu.toString(), DeviceStatusVo.class, param);
            return list;
        }
        return null;
    }

    @Override
    public List<DeviceStatusVo> queryDeviceByHouse(DeviceStatusVo deviceVo) {
        List<DeviceStatusVo> resultList = new ArrayList<DeviceStatusVo>();
        Map<Long, DeviceStatusVo> resultMap = new HashMap<Long, DeviceStatusVo>();
        Integer devceType = deviceVo.getDevceType();
        String uuid = deviceVo.getUuid();
        Long houseId = deviceVo.getHouseID();

        if (null == devceType || "".equals(devceType)) {//查询所有设备
            String tableName_cu = SqlUtil.queryTableIndex(uuid, CommonContant.CU_RUNINFO_TYPE);//需要确认表对应的TYPE
            String tableName_eu = SqlUtil.queryTableIndex(uuid, CommonContant.EU_RUNINFO_TYPE);//需要确认表对应的TYPE

            StringBuffer sql_cu = new StringBuffer("select uuid, cuIP as cuID, houseID,houseName ,idcID,idcName, status from ")
                    .append(tableName_cu).append(" group by houseID having uuid=? ORDER BY cuIP desc");
            StringBuffer sql_eu = new StringBuffer("select uuid, euID, houseID,houseName ,idcID, as status from ")
                    .append(tableName_eu).append(" group by houseID having uuid=?  ORDER BY euIP desc");
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = houseId;
            List<DeviceStatusVo> list_cu = DbUtil.queryForObjectList(sql_cu.toString(), DeviceStatusVo.class, param);
            List<DeviceStatusVo> list_eu = DbUtil.queryForObjectList(sql_eu.toString(), DeviceStatusVo.class, param);
            //合并结果集
            if ((null != list_cu && list_cu.size() > 0) && (null != list_eu && list_eu.size() > 0)) {
                for (DeviceStatusVo cuVo : list_cu) {
                    Long cuHouseID = cuVo.getHouseID();
                    for (DeviceStatusVo euVo : list_eu) {
                        Long euHouseID = euVo.getHouseID();
                        if (euHouseID == cuHouseID) {
                            int tempStatus = cuVo.getStatus() + euVo.getStatus();
                            cuVo.setStatus(tempStatus);
                            euVo.setStatus(tempStatus);
                        }
                    }
                }
            }
            //合并结果集
            if (null != list_cu && list_cu.size() > 0) {
                for (DeviceStatusVo cuVo : list_cu) {
                    resultMap.put(cuVo.getHouseID(), cuVo);
                }
            }
            if (null != list_eu && list_eu.size() > 0) {
                for (DeviceStatusVo euVo : list_eu) {
                    resultMap.put(euVo.getHouseID(), euVo);
                }
            }
            Iterator it = resultMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next().toString();
                resultList.add(resultMap.get(key));
            }
            return resultList;
        } else if (1 == devceType) {//查询CU设备
            String tableName_cu = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);//需要确认表对应的TYPE
            StringBuffer sql_cu = new StringBuffer("select uuid, cuIP as cuID,cuIP as ip,0 as houseID,'CU' as houseName,1 as devceType,idcID,   ")
                    .append("process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat ,")
                    .append("idcName,cu_smms_comm_stat as cuToSmms,cu_du_comm_stat as cuToDu,date_format(recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,status as status,partial_stat as mechineStatus,cuType from")
                    .append(tableName_cu).append(" where uuid=?  ORDER BY cuIP desc");
            Object[] param = new Object[1];
            param[0] = uuid;
            List<DeviceStatusVo> list = DbUtil.queryForObjectList(sql_cu.toString(), DeviceStatusVo.class, param);
            return list;
        } else if (2 == devceType) {//查询EU设备
            String tableName_eu = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);//需要确认表对应的TYPE
            StringBuffer sql_eu = new StringBuffer("select a.uuid, a.euID as euID,a.euIP as ip, a.houseID,b.houseName,2 as devceType,a.idcID,a.idcName,")
                    .append("process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat,")
                    .append("a.eu_cu_comm_stat as euToCu,a.eu_du_comm_stat as euToDu,a.runinfo_stat as euServiceStat,a.link_stat as linkStatus,a.network_stat as networkStatus,")
                    .append("date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime,a.status as status,partial_stat as mechineStatus from ")
                    .append(tableName_eu)
                    .append(" a left join monsys_all_housename_info b  on a.uuid = b.uuid and a.houseID = b.houseID ")
                    .append(" where a.uuid=? and a.houseID=?");
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = houseId;
            List<DeviceStatusVo> list = DbUtil.queryForObjectList(sql_eu.toString(), DeviceStatusVo.class, param);
            return list;
        }
        return null;
    }

}
