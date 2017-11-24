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
import com.act.framework.util.PageResult;
import com.act.web.constant.CommonContant;
import com.act.web.module.monitor.service.DeviceService;
import com.act.web.module.monitor.vo.DeviceVo;
import com.act.web.module.monitor.vo.HouseVo;
import com.act.web.module.monitor.vo.IdcVo;
import com.act.web.module.monitor.vo.ProvinceVo;
import com.act.web.util.ExcelJsonUtil;
import com.act.web.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService {

    private final Logger log = LoggerFactory.getLogger(DeviceServiceImpl.class);

    private static final String FILE_NAME = "设备详情";


    /**
     * 导出类型 查询数据
     */
    private static final String EXPORT_TYPE_QUERY = "1";

    /**
     * 导出类型 全部数据
     */
    private static final String EXPOR_TYPE_ALL = "-1";

    @Override
    public PageResult<DeviceVo> pagingList(PageResult<DeviceVo> page,
                                           DeviceVo deviceVo) {
        String tableName = "";
        String deviceID_type = "";
        StringBuffer sql = null;
        String fieldName = getFieldName(deviceVo.getStateType());

        if (deviceVo != null) {
            String exportType = deviceVo.getExportType();
            if (EXPOR_TYPE_ALL.equals(exportType)) {
                page.setPageIndex(0);
                page.setPageSize(-1);
            } else {
                if (EXPORT_TYPE_QUERY.equals(exportType)) {
                    page.setPageIndex(0);
                    page.setPageSize(19);
                }

            }

        }

        if (1 == deviceVo.getDeviceType()) {    //CU
            /*//如果查询所有，在查询完成后需要把查询状态改成查询设备状态，应为详情页面需要用到这参数
            if(CommonContant.DEVIC_ALL.equalsIgnoreCase(deviceVo.getStateType())){
				deviceVo.setStateType(CommonContant.CU_DEVICE);
			}*/
            tableName = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.CU_STAT_TYPE);
            deviceID_type = "cuIP";
            sql = new StringBuffer("SELECT a.UUID as uuid,process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat , b.PROV_NAME as provName,a.idcName as idcName,'" + deviceVo.getStateType() + "' as stateType,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as timeStamp,a.cuIP as deviceId,'0' as houseId,'CU' as houseName,'1' as deviceType,a." + fieldName + " as state FROM ");
            sql.append(tableName).append(" a LEFT JOIN td_province b on a.provID = b.prov_code WHERE 1=1 ");
        } else if (2 == deviceVo.getDeviceType()) {    //EU
            /*//如果查询所有，在查询完成后需要把查询状态改成查询设备状态，应为详情页面需要用到这参数
			if(CommonContant.DEVIC_ALL.equalsIgnoreCase(deviceVo.getStateType())){
				deviceVo.setStateType(CommonContant.EU_DEVICE);
			}*/
            if ("du_accesslog_stat".equals(fieldName)) {//EU服务
                tableName = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.EU_STAT_TYPE);
                String tableName1 = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.EU_RUNINFO_TYPE);
                deviceID_type = "euID";
                sql = new StringBuffer("SELECT a.UUID as uuid,process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat ,    b.PROV_NAME as provName,'" + deviceVo.getStateType() + "' as stateType,a.idcName as idcName,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as timeStamp,a.euID as deviceId,a.houseID as houseId,c.houseName as houseName,'2' as deviceType,d.status as state FROM ");
                sql.append(tableName).append(" a LEFT JOIN td_province b on a.provID = b.prov_code LEFT JOIN monsys_all_housename_info c on a.houseID=c.houseID and a.uuid = c.uuid left join " + tableName1
                        + " d on a.uuid = d.uuid and a.houseId = d.houseId and a.euID = d.euID  WHERE 1=1 ");
            } else {
                tableName = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.EU_STAT_TYPE);
                deviceID_type = "euID";
                sql = new StringBuffer("SELECT a.UUID as uuid,process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat ,   b.PROV_NAME as provName,'" + deviceVo.getStateType() + "' as stateType,a.idcName as idcName,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as timeStamp,a.euID as deviceId,a.houseID as houseId,c.houseName as houseName,'2' as deviceType,a." + fieldName + " as state FROM ");
                sql.append(tableName).append(" a LEFT JOIN td_province b on a.provID = b.prov_code LEFT JOIN monsys_all_housename_info c on a.houseID=c.houseID and a.uuid = c.uuid WHERE 1=1 ");
            }

        }

        List<Object> params = new ArrayList<Object>();
        if (deviceVo != null) {

            String provIdQuery = deviceVo.getProvId();
            if (provIdQuery != null && provIdQuery.trim().length() > 0) {
                sql.append(" AND a.provID = ?");
                params.add(provIdQuery.trim());
            }

            String houseId = deviceVo.getHouseId();
            if (houseId != null && houseId.trim().length() > 0 && 2 == deviceVo.getDeviceType()) {
                sql.append(" AND a.houseId = ?");
                params.add(houseId.trim());
            }

            String uuIdQuery = deviceVo.getUuid();
            if (uuIdQuery != null && uuIdQuery.trim().length() > 0) {
                sql.append(" AND a.uuid = ?");
                params.add(uuIdQuery.trim());
            }

            String deviceIdQuery = deviceVo.getDeviceId();
            if (deviceIdQuery != null && deviceIdQuery.trim().length() > 0) {
                sql.append(" AND a.").append(deviceID_type).append(" = ?");
                params.add(deviceIdQuery.trim());
            }

            Integer isWrong = deviceVo.getIsWrong();
            if (null != isWrong && !"".equals(isWrong)) {
                if (fieldName != null && fieldName.trim().length() > 0) {
                    if ("du_accesslog_stat".equals(fieldName)) {
                        sql.append(" AND d.status = ?");
                        params.add(isWrong);
                    } else {
                        sql.append(" AND a.").append(fieldName).append(" = ?");
                        params.add(isWrong);
                    }
                }
            }

        }
        String stateType = deviceVo.getStateType();
        if (CommonContant.CU_TO_SMMS.equals(stateType) || CommonContant.CU_TO_DU.equals(stateType)) {
            sql.append(" AND a.cuType").append(" = ?");
            params.add(1);
        }

        sql.append(" order by a.recordTime desc");
        if (!"".equals(tableName)) {
            page = DbUtil.queryPageForObjectPageResult(sql.toString(),
                    DeviceVo.class, page.getPageIndex(), page.getPageSize(),
                    params.toArray());
        }
		/*
		Object[] param = new Object[params.size()];
		for (int i = 0; i < params.size(); i++) {
			param[i] = params.get(i);
		}*/

        return page;
    }

    /**
     * @param stateType
     * @return
     * @title getFieldName
     * @Description 获取状态统计表中对应状态字段名
     * @createTime 2017年6月29日19:41:14
     */
    private String getFieldName(String stateType) {
        String fieldName = "";

        if (CommonContant.MECHINE_STATUS.equals(stateType)) {
            fieldName = "mechine_stat";
        } else if (CommonContant.EU_DEVICE.equals(stateType) || CommonContant.CU_DEVICE.equals(stateType)) {
            fieldName = "partial_stat";
        } else if (CommonContant.EU_TO_DU.equals(stateType)) {
            fieldName = "eu_du_comm_stat";
        } else if (CommonContant.ACCESS_LOG.equals(stateType)) {
            fieldName = "du_accesslog_stat";
        } else if (CommonContant.EU_TO_CU.equals(stateType)) {
            fieldName = "eu_cu_comm_stat";
        } else if (CommonContant.LINK_STATUS.equals(stateType)) {
            fieldName = "link_stat";
        } else if (CommonContant.NETWORK_STATUS.equals(stateType)) {
            fieldName = "network_stat";
        } else if (CommonContant.CU_TO_SMMS.equals(stateType)) {
            fieldName = "cu_smms_comm_stat";
        } else if (CommonContant.CU_TO_DU.equals(stateType)) {
            fieldName = "cu_du_comm_stat";
        } else if (CommonContant.DEVIC_ALL.equalsIgnoreCase(stateType)) {
            fieldName = "status";
        }
        return fieldName;
    }

    @Override
    public Object provList(DeviceVo deviceVo) {
        StringBuffer sql = null;
        String uuid = deviceVo.getUuid();
        List<ProvinceVo> province = null;
        if (null != uuid && !"".equals(uuid.trim())) {//IDC用户
            StringBuffer provIdQuery = new StringBuffer("select provId FROM monsys_all_idc_info where uuid = ? limit 1");
            Object[] globleParams = new Object[1];
            Object[] params = new Object[1];
            params[0] = uuid;
            Integer provId = DbUtil.queryForInt(provIdQuery.toString(), params);
            sql = new StringBuffer("select PROV_NAME as provName,PROV_CODE as provId FROM td_province where PROV_ACTIVE = 1 and PROV_CODE =?");
            globleParams[0] = provId + "";
            province = DbUtil.queryForObjectList(sql.toString(), ProvinceVo.class, globleParams);
        } else {//admin用户
            sql = new StringBuffer("select PROV_NAME as provName,PROV_CODE as provId FROM td_province where PROV_ACTIVE = 1");
            province = DbUtil.queryForObjectList(sql.toString(), ProvinceVo.class);
        }
        return province;
    }

    @Override
    public boolean provIdCheck(DeviceVo deviceVo) {
        boolean flage = true;
        String uuid = deviceVo.getUuid();
        if (null != uuid && !"".equals(uuid.trim())) {//IDC用户
            StringBuffer provIdQuery = new StringBuffer("select provId FROM monsys_all_idc_info where uuid = ? limit 1");
            Object[] params = new Object[1];
            params[0] = uuid;
            Integer provId = DbUtil.queryForInt(provIdQuery.toString(), params);
            if (null == provId || "".equals(provId)) {
                flage = false;
            }
        }
        return flage;
    }

    @Override
    public Object getIdcByProv(DeviceVo deviceVo) {    //TODO
        List<IdcVo> result = null;
        String uuid = deviceVo.getUuid();
        String provId = deviceVo.getProvId();
        if (null != uuid && !"".equals(uuid.trim())) {//IDC用户
            Object[] param = new Object[2];
            param[0] = provId;
            param[1] = uuid;
            StringBuffer sql = new StringBuffer("SELECT model.idcName,model.uuid FROM monsys_all_idc_info model WHERE model.provId = ? and model.uuid = ?");
            result = DbUtil.queryForObjectList(sql.toString(), IdcVo.class, param);
        } else {//admin用户
            Object[] param = new Object[1];
            param[0] = provId;
            StringBuffer sql = new StringBuffer("SELECT model.idcName,model.uuid FROM monsys_all_idc_info model WHERE provId = ?");
            result = DbUtil.queryForObjectList(sql.toString(), IdcVo.class, param);
        }

        return result;
    }

    @Override
    public Object getHouseByIdc(String provId, String uuId) {//TODO
        Object[] param = new Object[1];
        param[0] = uuId;
        String sql = "select houseName as houseName,houseID as houseId from monsys_all_housename_info where  uuid=?";
        List<HouseVo> result = DbUtil.queryForObjectList(sql, HouseVo.class, param);
        return result;
    }

    @Override
    public ExcelJsonUtil<DeviceVo> exportByJson(
            PageResult<DeviceVo> page, DeviceVo DeviceVo) {
        List<DeviceVo> dataList = pagingList(page, DeviceVo).getRows();
        String[] header = new String[]{"省份", "运营商", "机房", "设备", "状态",
                "状态更新时间"};// excel表头
        String[] dataName = new String[]{"provName", "idcName", "houseName",
                "deviceId", "state", "timeStamp"};// excel数据属性
        ExcelJsonUtil<DeviceVo> result = new ExcelJsonUtil<DeviceVo>(
                header, dataName, dataList, FILE_NAME);
        return result;
    }

}
