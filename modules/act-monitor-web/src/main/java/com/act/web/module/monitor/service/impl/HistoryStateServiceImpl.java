/**
 * @Title: HistoryStateServiceImpl.java
 * @Package com.act.web.module.monitor.service
 * @Description: 设备历史页面service
 * @author liuyang
 * @modifier liuyang
 * @date 2017年6月27日21:04:48
 * @version V1.0
 */
package com.act.web.module.monitor.service.impl;

import com.act.framework.util.DbUtil;
import com.act.framework.util.PageResult;
import com.act.web.constant.CommonContant;
import com.act.web.module.monitor.service.HistoryStateService;
import com.act.web.module.monitor.vo.DeviceVo;
import com.act.web.util.ExcelJsonUtil;
import com.act.web.util.SqlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HistoryStateServiceImpl implements HistoryStateService {

    private final Logger log = LoggerFactory.getLogger(HistoryStateServiceImpl.class);

    /**
     * CU设备类型
     */
    private static final Integer CU = 1;
    /**
     * 导出名称
     */
    private static final String FILE_NAME = "设备历史状态";


    /**
     * 导出类型 查询数据
     */
    private static final String EXPORT_TYPE_QUERY = "1";

    /**
     * 导出类型 全部数据
     */
    private static final String EXPOR_TYPE_ALL = "-1";

    /**
     * EU设备类型
     */
    private static final Integer EU = 2;

    /*
     * (非 Javadoc) <p>Title: pagingList</p>
     * <p>Description: 设备历史 分页查询</p>
     *
     * @param page 分页属性
     *
     * @param deviceVo 查询条件
     *
     * @return
     *
     * @see
     * com.act.web.module.monitor.service.HistoryStateService#pagingList(com.act.framework
     * .util.PageResult, com.act.web.module.monitor.vo.DeviceVo)
     */
    @Override
    public PageResult<DeviceVo> pagingList(PageResult<DeviceVo> page,
                                           DeviceVo deviceVo) throws Exception {

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
        String tableName = "";
        String deviceID_type = "";
        String fieldName = getFieldName(deviceVo.getStateType());
        List<String> tableList = null;
        List<String> tableList1 = null;
        List<String> timeSuffix = SqlUtil.getDateList(deviceVo.getDateQuery());


        StringBuffer sql = new StringBuffer("SELECT DISTINCT a.uuid as uuid,a.idcName as idcName,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as timeStamp,'").append(deviceVo.getStateType()).append("' as stateType,");
        if (CU == deviceVo.getDeviceType()) {    //CU
            deviceID_type = "cuIP";
            sql.append("a.cuIP as deviceId,'1' as deviceType,a." + fieldName + " as state");
            tableName = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.CU_STAT_TYPE);
            tableList = SqlUtil.getExistTable(timeSuffix, tableName);    //判断流水表是否存在
            if ((tableList != null && tableList.size() > 0)) {
                sql.append(" FROM ").append(tableList.get(0)).append(" a  WHERE 1=1  ");
            }

        } else if (EU == deviceVo.getDeviceType()) {    //EU
            deviceID_type = "euID";
            tableName = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.EU_STAT_TYPE);
            tableList = SqlUtil.getExistTable(timeSuffix, tableName);    //判断流水表是否存在
            if ("du_accesslog_stat".equals(fieldName)) {//EU服务
                String tableName1 = SqlUtil.queryTableIndex(deviceVo.getUuid(), CommonContant.EU_RUNINFO_TYPE);
                tableList1 = SqlUtil.getExistTable(timeSuffix, tableName1);    //判断流水表是否存在
                sql.append("a.euID as deviceId,a.houseID as houseId,'2' as deviceType,b.status as state");

                if ((tableList != null && tableList.size() > 0) && (tableList1 != null && tableList1.size() > 0)) {
                    sql.append(" FROM ").append(tableList.get(0)).append(" a left join " + tableList1.get(0) + " b on a.uuid = b.uuid and a.houseId = b.houseId and a.euID = b.euID WHERE 1=1  ");
                }
                /*sql = new StringBuffer("SELECT a.UUID as uuid,b.PROV_NAME as provName,'"+deviceVo.getStateType()+"' as stateType,a.idcName as idcName,a.recordTime as timeStamp,a.euID as deviceId,a.houseID as houseId,c.houseName as houseName,'2' as deviceType,d.status as state FROM ");
                sql.append(tableName).append(" a LEFT JOIN td_province b on a.provID = b.prov_code LEFT JOIN monsys_all_housename_info c on a.houseID=c.houseID and a.uuid = c.uuid left join "+tableName1
						+" d on a.uuid = d.uuid and a.houseId = d.houseId and a.euID = d.euID  WHERE 1=1 ");*/
            } else {
                sql.append("a.euID as deviceId,a.houseID as houseId,'2' as deviceType,a." + fieldName + " as state");
                if ((tableList != null && tableList.size() > 0)) {
                    sql.append(" FROM ").append(tableList.get(0)).append(" a  WHERE 1=1  ");
                }
            }
        }


        if (tableList != null && tableList.size() > 0) {

            List<Object> params = new ArrayList<Object>();

            if (deviceVo != null) {

                String provIdQuery = deviceVo.getProvId();
                if (provIdQuery != null && provIdQuery.trim().length() > 0) {
                    sql.append(" AND a.provID = ?");
                    params.add(provIdQuery.trim());
                }

                String idcIdQuery = deviceVo.getIdcId();
                if (idcIdQuery != null && idcIdQuery.trim().length() > 0) {
                    sql.append(" AND a.idcID = ?");
                    params.add(idcIdQuery.trim());
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
                            sql.append(" AND b.status = ?");
                            params.add(isWrong);
                        } else {
                            sql.append(" AND a.").append(fieldName).append(" = ?");
                            params.add(isWrong);
                        }
                    }
                }

            }
            sql.append(" order by a.recordTime desc");
            page = DbUtil.queryPageForObjectPageResult(sql.toString(), DeviceVo.class, page.getPageIndex(), page.getPageSize(), params.toArray());
        }

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

    public ExcelJsonUtil<DeviceVo> exportByJson(
            PageResult<DeviceVo> page, DeviceVo DeviceVo) throws Exception {
        List<DeviceVo> dataList = pagingList(page, DeviceVo).getRows();
        String[] header = new String[]{"设备", "状态", "状态类型",
                "状态更新时间"};// excel表头
        String[] dataName = new String[]{"deviceId", "state", "stateType",
                "timeStamp",};// excel数据属性
        ExcelJsonUtil<DeviceVo> result = new ExcelJsonUtil<DeviceVo>(
                header, dataName, dataList, FILE_NAME);
        return result;
    }
}
