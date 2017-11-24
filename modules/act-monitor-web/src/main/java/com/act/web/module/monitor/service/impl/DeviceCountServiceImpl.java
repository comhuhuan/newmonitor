/**
 * @Title: DeviceCountServiceImpl.java
 * @Package com.act.web.module.monitor.service
 * @Description: 设备搜索service
 * @author liuyang
 * @modifier liuyang
 * @date 2017年6月21日09:18:07
 * @version V1.0
 */
package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.web.constant.CommonContant;
import com.act.web.module.monitor.service.DeviceCountService;
import com.act.web.module.monitor.vo.DeviceCountVo;
import com.act.web.module.monitor.vo.DeviceHistoryVo;
import com.act.web.module.monitor.vo.DeviceInfoVo;
import com.act.web.util.SqlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeviceCountServiceImpl implements DeviceCountService {

    private final org.slf4j.Logger logger = LoggerFactory.getLogger(DeviceCountServiceImpl.class);

//	static{
//		StringBuffer sql = new StringBuffer("select PROV_NAME,PROV_CODE FROM td_province where PROV_ACTIVE = 1");
//		province = DbUtil.queryForMapList(sql.toString(), null);
//	}

    @Override
    public List<DeviceCountVo> deviceCount(DeviceCountVo deviceVo) {
        StringBuffer sql = new StringBuffer("select case when sum(cuDeviceNum+euDeviceNum) > 0 then sum(cuBadDeviceNum+euBadDeviceNum)/sum(cuDeviceNum+euDeviceNum) else 0 end as percent, sum(cuDeviceNum+euDeviceNum) as dviceNum,sum(cuBadDeviceNum+euBadDeviceNum) as badDviceNum ,provId from monsys_all_idc_info GROUP BY provId having 1=1");
        List<DeviceCountVo> list = DbUtil.queryForObjectList(sql.toString(), DeviceCountVo.class);
        convert(list);
        return list;
    }

    @Override
    public PageResult<DeviceCountVo> pageList(PageResult<DeviceCountVo> page, DeviceCountVo deviceVo) {
        StringBuffer sql = new StringBuffer("select sum(cuDeviceNum+euDeviceNum) as dviceNum,sum(cuBadDeviceNum+euBadDeviceNum) as badDviceNum")
                .append(",idcID").append(",idcName").append(",provId").append(",date_format(recordTime,'%Y-%c-%d %H:%i:%S')  as recordTime").append(",uuid")
                .append("  from monsys_all_idc_info  GROUP BY uuid having 1=1");
        List<Object> param = new ArrayList<Object>();
        if (null != deviceVo && (null != deviceVo.getProvId() && !"".equals(deviceVo.getProvId()))) {
            String proveid = deviceVo.getProvId();
            //查询指定的
            sql.append(" and provId =? ");
            param.add(proveid);
        }
        if (null != deviceVo && (null != deviceVo.getIdcName() && !"".equals(deviceVo.getIdcName()))) {
            String idcName = deviceVo.getIdcName();
            //查询指定的
            sql.append(" and idcName like ? ");
            param.add("%" + idcName + "%");
        }
        sql.append(" ORDER BY recordTime desc");
        logger.info("首页查询全国故障设备情况sql：" + sql.toString());
        //logger.info("首页查询全国故障设备情况参数："+param[0]);
        page = DbUtil.queryPageForObjectPageResult(sql.toString(), DeviceCountVo.class, page.getPageIndex(), page.getPageSize(), param.toArray());
        return page;
    }

    @Override
    public List<DeviceInfoVo> deviceInfo(DeviceInfoVo deviceVo) {
        String uuid = deviceVo.getUuid();
        Integer devceType = deviceVo.getDevceType();
        Object[] param = new Object[1];
        param[0] = uuid;
        List<DeviceInfoVo> list = null;
        StringBuffer sql = null;
        //查询索引表，获取数据源表
        if (1 == devceType) {
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);
            sql = new StringBuffer("select status,idcID, count(DISTINCT cuIP) as dviceNum from ")
                    .append(tableName).append(" where uuid = ?  GROUP BY status");
        } else if (2 == devceType) {
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);
            sql = new StringBuffer("select status,idcID, count(DISTINCT euID) as dviceNum from ")
                    .append(tableName).append(" where uuid = ?  GROUP BY status");
        }
        list = DbUtil.queryForObjectList(sql.toString(), DeviceInfoVo.class, param);

        return list;
    }

    @Override
    public List<DeviceHistoryVo> cuDeviceInfoHistory(DeviceHistoryVo deviceVo) throws Exception {
        List<DeviceHistoryVo> result = new ArrayList<DeviceHistoryVo>();
        String uuid = deviceVo.getUuid();
        Object[] param = new Object[1];
        param[0] = uuid;
        String elementType = deviceVo.getElementType();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");

        String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);
        String statusType = "";
        if (CommonContant.CU_DEVICE.equals(elementType)) {//CU设备状态
            statusType = "device_stat";
        } else if (CommonContant.CU_TO_SMMS.equals(elementType)) {//CU到管局状态
            statusType = "cu_smms_comm_stat";
        } else if (CommonContant.CU_TO_DU.equals(elementType)) {//CU到DU
            statusType = "cu_du_comm_stat";
        } else {
            statusType = "status";
        }
        List<String> dateList = SqlUtil.getDateList(deviceVo.getDateNum());
        List<String> existTableList = SqlUtil.getTableName(dateList, tableName);
        if (null != existTableList && existTableList.size() > 0) {
            for (String table : existTableList) {
                StringBuffer sql_bad = new StringBuffer("select count(DISTINCT cuIP) as badDviceNum,date_format(recordTime,'%m-%d')  as recordTime from ");
                sql_bad.append(table).append(" where uuid = ?  and ").append(statusType).append(" = 1");
                StringBuffer sql_totl = new StringBuffer("select count(DISTINCT cuIP) as dviceNum from ");
                sql_totl.append(table).append(" where uuid = ?");
                try {
                    DeviceHistoryVo deviceHistoryVo = DbUtil.queryForObject(sql_bad.toString(), DeviceHistoryVo.class, param);
                    int dviceNum = DbUtil.queryForInt(sql_totl.toString(), param);
                    int index = table.lastIndexOf("_");
                    String strDate = table.substring(index + 1, table.length() - 1);
                    Date date = sdf.parse(strDate);
                    String recordTime = sdf1.format(date);
                    deviceHistoryVo.setRecordTime(recordTime);
                    deviceHistoryVo.setDviceNum(dviceNum);
                    result.add(deviceHistoryVo);
                } catch (Exception e) {
                    DeviceHistoryVo deviceHistoryVo = new DeviceHistoryVo();
                    int index = table.lastIndexOf("_");
                    String strDate = table.substring(index + 1, table.length() - 1);
                    Date date = sdf.parse(strDate);
                    String recordTime = sdf1.format(date);
                    deviceHistoryVo.setBadDviceNum(0);
                    deviceHistoryVo.setDviceNum(0);
                    deviceHistoryVo.setRecordTime(recordTime);
                    result.add(deviceHistoryVo);
                }

            }
        }
        return result;
    }

    @Override
    public List<DeviceHistoryVo> euDeviceInfoHistory(DeviceHistoryVo deviceVo) throws Exception {
        List<DeviceHistoryVo> result = new ArrayList<DeviceHistoryVo>();
        String uuid = deviceVo.getUuid();
        Object[] param = new Object[1];
        param[0] = uuid;
        String elementType = deviceVo.getElementType();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM-dd");
        String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);
        String statusType = "";
        if (CommonContant.EU_DEVICE.equals(elementType)) {//EU设备状态
            statusType = "device_stat";
        } else if (CommonContant.EU_TO_DU.equals(elementType)) {//EU到DU通信状态
            statusType = "eu_du_comm_stat";
        } else if (CommonContant.ACCESS_LOG.equals(elementType)) {//EU到DU的访问日志
            statusType = "runinfo_stat";
        } else if (CommonContant.EU_TO_CU.equals(elementType)) {//EU到CU的通信状态
            statusType = "eu_cu_comm_stat";
        } else if (CommonContant.LINK_STATUS.equals(elementType)) {//EU链路状态
            statusType = "link_stat";
        } else if (CommonContant.NETWORK_STATUS.equals(elementType)) {//EU网卡状态
            statusType = "network_stat";
        } else {
            statusType = "status";
        }
        List<String> dateList = SqlUtil.getDateList(deviceVo.getDateNum());
        List<String> existTableList = SqlUtil.getTableName(dateList, tableName);
        if (null != existTableList && existTableList.size() > 0) {
            for (String table : existTableList) {
                StringBuffer sql_bad = new StringBuffer("select count(DISTINCT euID) as badDviceNum,date_format(recordTime,'%m-%d')  as recordTime from ");
                sql_bad.append(table).append(" where uuid = ?  and ").append(statusType).append(" = 1");
                StringBuffer sql_totl = new StringBuffer("select count(DISTINCT euID) as dviceNum from ");
                sql_totl.append(table).append(" where UUID = ?");
                try {
                    DeviceHistoryVo deviceHistoryVo = DbUtil.queryForObject(sql_bad.toString(), DeviceHistoryVo.class, param);
                    int dviceNum = DbUtil.queryForInt(sql_totl.toString(), param);
                    int index = table.lastIndexOf("_");
                    String strDate = table.substring(index + 1, table.length() - 1);
                    Date date = sdf.parse(strDate);
                    String recordTime = sdf1.format(date);
                    deviceHistoryVo.setRecordTime(recordTime);
                    deviceHistoryVo.setDviceNum(dviceNum);
                    result.add(deviceHistoryVo);
                } catch (Exception e) {
                    DeviceHistoryVo deviceHistoryVo = new DeviceHistoryVo();
                    int index = table.lastIndexOf("_");
                    String strDate = table.substring(index + 1, table.length() - 1);
                    Date date = sdf.parse(strDate);
                    String recordTime = sdf1.format(date);
                    deviceHistoryVo.setBadDviceNum(0);
                    deviceHistoryVo.setDviceNum(0);
                    deviceHistoryVo.setRecordTime(recordTime);
                    result.add(deviceHistoryVo);
                }
            }
        }
        return result;
    }

    @Override
    public PageResult<DeviceInfoVo> badDevicePageList(PageResult<DeviceInfoVo> page, DeviceInfoVo deviceVo) throws Exception {
        String uuid = deviceVo.getUuid();
        int devceType = deviceVo.getDevceType();
        Object[] param = new Object[1];
        param[0] = uuid;
        //查询索引表，获取数据源表
        if (1 == devceType) {//查询CU设备
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);
            List<String> dateList = SqlUtil.getDateList(1);
            List<String> existTableList = SqlUtil.getTableName(dateList, tableName);
            if (null != existTableList && existTableList.size() > 0) {
                tableName = existTableList.get(0);
            } else {
                tableName = null;
            }

            boolean result = SqlUtil.getExistTable(tableName);
            if (result) {
                StringBuffer sql = new StringBuffer("SELECT * from ( select cuIP as cuID,cuIP as cuName,idcID,uuid,status, date_format(recordTime,'%Y-%c-%d %H:%i:%S')  as recordTime  from ")
                        .append(tableName).append(" where status=1 ").append(" and uuid = ? ").append("  ORDER BY recordTime desc  ) aaa GROUP BY aaa.cuID ");
                logger.info("CU和EU最近故障设备列表sql：" + sql.toString());
                logger.info("CU和EU最近故障设备列表参数：" + param[0]);
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DeviceInfoVo.class, page.getPageIndex(), page.getPageSize(), param);
            }

        } else if (2 == devceType) {//查询EU设备
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);
            List<String> dateList = SqlUtil.getDateList(1);
            List<String> existTableList = SqlUtil.getTableName(dateList, tableName);
            if (null != existTableList && existTableList.size() > 0) {
                tableName = existTableList.get(0);
            } else {
                tableName = null;
            }

            boolean result = SqlUtil.getExistTable(tableName);
            if (result) {
                StringBuffer sql = new StringBuffer("SELECT * from (  select a.euId,b.houseName,a.houseID,a.idcID,a.uuid,a.status, date_format(a.recordTime,'%Y-%c-%d %H:%i:%S')  as recordTime  from ")
                        .append(tableName).append(" a left join monsys_all_housename_info b on a.uuid = b.uuid and a.houseID = b.houseID ")
                        .append(" where a.status=1 ").append(" and a.uuid = ? ").append("  ORDER BY a.recordTime desc  ) aaa GROUP BY aaa.euId ");
                logger.info("CU和EU最近故障设备列表sql：" + sql.toString());
                logger.info("CU和EU最近故障设备列表参数：" + param[0]);
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DeviceInfoVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        }
        return page;
    }

    @Override
    public DeviceCountVo cuInter(DeviceCountVo deviceVo) {
        String sql = "select cuIP  from cu_du_yhsj2017 where process_stat='1'";
        DeviceCountVo deviceCountVo = DbUtil.queryForObject(sql.toString(), DeviceCountVo.class);
        return deviceCountVo;

    }

    // 把省份设置对应的名称
    private void convert(List<DeviceCountVo> list) {
        List<Map<String, Object>> province = initProvinceList();
        if (null != list && null != province) {
            for (DeviceCountVo vo : list) {
                for (Map<String, Object> map : province) {
                    String provName = map.get("prov_name").toString();
                    String provCode = map.get("prov_code").toString();
                    if (provCode.equals(vo.getProvId())) {
                        vo.setProvName(provName);
                        break;
                    }
                }
            }
        }
    }

    /**
     * @Title: initProvinceList
     * @Description:加载省份列表
     * @create 2017年7月28日17:52:31
     * @update 2017年7月28日17:52:31
     */
    public List<Map<String, Object>> initProvinceList() {
        StringBuffer sql = new StringBuffer(
                "select PROV_NAME,PROV_CODE FROM td_province where PROV_ACTIVE = 1");
        List<Map<String, Object>> province = DbUtil.queryForMapList(sql.toString());
        return province;
    }
}
