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
import com.act.web.module.monitor.service.DeviceInfoService;
import com.act.web.module.monitor.vo.*;
import com.act.web.util.SqlUtil;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DeviceInfoServiceImpl implements DeviceInfoService {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(DeviceInfoServiceImpl.class);

    @Override
    public StatusInfoVo statusHistory(StatusInfoVo deviceVo) throws Exception {
        StatusInfoVo resultVo = new StatusInfoVo();
        String elementType = deviceVo.getElementType();
        String uuid = deviceVo.getUuid();
        Long Houseid = deviceVo.getHouseID();
        String deviceid = deviceVo.getDeviceId();
        //三种属于CU设备的查询
        if (CommonContant.CU_TO_SMMS.equals(elementType)) {//CU到管局通行状态历史流水

            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_SMMS_COMM_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "1");

					/*
                    String[][] dateTime = {{"00:00","02:00"},{"02:00","04:00"},{"04:00","06:00"},{"06:00","08:00"},{"08:00","10:00"},{"10:00","12:00"},{"12:00","14:00"},{"14:00","16:00"},{"16:00","18:00"},
							{"18:00","20:00"},{"20:00","22:00"},{"22:00","24:00"}};
					for(String table:existTableList){
						StringBuffer frist_sql = new StringBuffer("select  cuIP,'"+elementType+"' as elementType, count(*) as status  from ");
						frist_sql.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ? and status =1"); 
						
						StringBuffer second_sql = new StringBuffer("select cuIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H:%i')  as recordTime from ");
						second_sql.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ?  ORDER BY recordTime desc"); 
						for(String[] time:dateTime){
							 Object[] param = new Object[2];
							 param[0] = uuid;
							 param[1] = deviceid;
							 param[2] = time[0];
							 param[3] = time[1];
							 
							 Map<String,Object> map = DbUtil.queryForMap(frist_sql.toString(), param);
							 if(null != map){
								 Object obj =  map.get("status");
								 if(null != obj && !"".equals(obj) && !"null".equalsIgnoreCase(obj.toString())){
									 HistoryStatus historyStatus = new HistoryStatus();
									 int status = Integer.valueOf(obj.toString());
									 Object cuIp = map.get("cuIP");
									 if(null != cuIp && !"".equals(cuIp)){
										 historyStatus.setCuIP(cuIp.toString());
									 }
									 historyStatus.setElementType(elementType);
									 historyStatus.setRange(time[0]+"--"+time[1]);
									 historyStatus.setStatus(status);
									 if(status > 0){
										 List<Map<String,Object>> tempResult = DbUtil.queryForMapList(second_sql.toString(), param);
										 historyStatus.setRangeInfo(tempResult);
									 }
									 resultVo.getHistoryStatus().add(historyStatus);
								 }
							 }
						}
					}
				*/
            }

        } else if (CommonContant.CU_DEVICE.equals(elementType)) {//CU设备历史流水
            String queryType = deviceVo.getQueryType();
            if ("process".equals(queryType)) {//进程流水统计
                Object[] param = new Object[2];
                param[0] = uuid;
                param[1] = deviceid;

                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_PROCESS_TYPE);
                //List<String> dateList = SqlUtil.getDateList(deviceVo.getDateNum());
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        StringBuffer badSql = new StringBuffer("select count(status) as badtotal,'" + elementType + "' as elementType,date_format(recordTime,'%H:%i')  as recordTime from ");
                        badSql.append(table).append("  where uuid=? and cuIP =? and status = 1  group by recordTime ORDER BY recordTime desc");

                        StringBuffer tatolSql = new StringBuffer("select count(status) as totalNum from ");
                        tatolSql.append(table).append(" where  uuid=? and cuIP =? and status = 1");

                        int total = DbUtil.queryForInt(tatolSql.toString(), param);
                        List<ProcessVo> tempResult = DbUtil.queryForObjectList(badSql.toString(), ProcessVo.class, param);
                        resultVo.setTotal(total);
                        resultVo.setHistoryProcess(tempResult);
                    }
                }
            } else if ("disk".equals(queryType)) {//硬盘流水统计
                Object[] param = new Object[2];
                param[0] = uuid;
                param[1] = deviceid;
                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_DISK_TYPE);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        String diskName = deviceVo.getSelectOption();

                        StringBuffer diskNameSql = new StringBuffer("select diskName from ");
                        diskNameSql.append(table).append(" where uuid=? and cuIP =?  group by diskName ORDER BY diskName desc");
                        List<String> diskNames = DbUtil.queryForObjectList(diskNameSql.toString(), String.class, param);

                        if (null != diskNames && diskNames.size() > 0) {
                            if (null == diskName || "".equals(diskName.trim())) {
                                diskName = diskNames.get(0);
                            }

                        } else {
                            return null;
                        }
                        resultVo.setOption(diskNames);

                        Object[] param1 = new Object[3];
                        param1[0] = uuid;
                        param1[1] = deviceid;
                        param1[2] = diskName;
                        StringBuffer sql = new StringBuffer("select diskName,'" + elementType + "' as elementType,diskTotalSize,status,useRate,date_format(recordTime,'%H:%i')  as recordTime from ");
                        sql.append(table).append(" where uuid=? and cuIP =? and diskName =? group by recordTime ORDER BY recordTime desc");
                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and cuIP =? and diskName =? and status = 1");
                        StringBuffer sql3 = new StringBuffer("select max(useRate) as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and cuIP =? and diskName =?");

                        int badNum = DbUtil.queryForInt(sql2.toString(), param1);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param1);
                        List<DiskVo> tempResult = DbUtil.queryForObjectList(sql.toString(), DiskVo.class, param1);

                        resultVo.setSelectOption(diskName);
                        resultVo.setTotal(badNum);
                        resultVo.setHistoryDisk(tempResult);
                        resultVo.setMaxUsrRate(maxUsrRate);
                    }
                }
            } else if ("memory".equals(queryType)) {//内存流水统计
                Object[] param = new Object[2];
                param[0] = uuid;
                param[1] = deviceid;

                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_MEMORY_TYPE);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        StringBuffer badSql = new StringBuffer("select memoryUsed,'" + elementType + "' as elementType,memoryBuffer,memoryFree,memory_rate as memoryRate,status,date_format(recordTime,'%H:%i')  as recordTime from ");
                        badSql.append(table).append(" where uuid=? and cuIP =?  group by recordTime ORDER BY recordTime desc");

                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and cuIP =?  and status = 1");
                        StringBuffer sql3 = new StringBuffer("select max(memory_rate) as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and cuIP =?");

                        int total = DbUtil.queryForInt(sql2.toString(), param);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param);
                        List<MemoryVo> tempResult = DbUtil.queryForObjectList(badSql.toString(), MemoryVo.class, param);
                        resultVo.setTotal(total);
                        resultVo.setMaxUsrRate(maxUsrRate);
                        resultVo.setHistoryMemory(tempResult);
                    }
                }
            } else if ("cpu".equals(queryType)) {
                Object[] param = new Object[2];
                param[0] = uuid;
                param[1] = deviceid;
                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_CPURATE_TYPE);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        String coulnNmae = deviceVo.getSelectOption();
                        StringBuffer numSql = new StringBuffer("select coreNum from ");
                        numSql.append(table).append(" where uuid=? and cuIP =? limit 1");
                        int num = DbUtil.queryForInt(numSql.toString(), param);
                        List<String> option = new ArrayList<String>();
                        option.add("cpuTotal_rate");
                        for (int i = 0; i < num; i++) {
                            option.add("CPU" + i);
                        }

                        if (null == coulnNmae || "".equals(coulnNmae.trim())) {
                            coulnNmae = "cpuTotal_rate";
                        }
                        resultVo.setOption(option);


                        StringBuffer sql = new StringBuffer("select " + coulnNmae + " as cpu,'" + elementType + "' as elementType,status,date_format(recordTime,'%H:%i')  as recordTime from ");
                        sql.append(table).append(" where uuid=? and cuIP =? group by recordTime ORDER BY recordTime desc");
                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and cuIP =?  and status = 1");
                        StringBuffer sql3 = new StringBuffer("select max(" + coulnNmae + ") as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and cuIP =? ");

                        int badNum = DbUtil.queryForInt(sql2.toString(), param);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param);
                        //int maxUsrRate = DbUtil.queryForInt(sql3.toString(), param1);CpuVo
                        //List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
                        List<CpuVo> tempResult = DbUtil.queryForObjectList(sql.toString(), CpuVo.class, param);
                        resultVo.setSelectOption(coulnNmae);
                        resultVo.setTotal(badNum);
                        resultVo.setHistoryCpu(tempResult);
                        resultVo.setMaxUsrRate(maxUsrRate);
                    }
                }
            }
        } else if (CommonContant.CU_TO_DU.equals(elementType)) {//CU到DU通信历史流水
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_DU_COMM_TYPE);//CU到DU状态
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = deviceid;
            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "1");
                    /*for(String table:existTableList){
                        StringBuffer sql = new StringBuffer("select cuIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
						sql.append(table).append(" where  uuid=? and cuIP =? group by recordTime ORDER BY recordTime desc"); 
						List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
						resultVo.getHistoryStatus().addAll(tempResult);
					}*/
            }
        }

        //六种属于EU设备的查询
        else if (CommonContant.EU_TO_DU.equals(elementType)) {//eu到DU通信状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_DU_COMM_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                /* Object[] param = new Object[3];
				 param[0] = uuid;
				 param[1] = Houseid;
				 param[2] = deviceid;*/
            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "2");
						/*for(String table:existTableList){
							StringBuffer sql = new StringBuffer("select euID,euIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
							sql.append(table).append(" where uuid=? and houseID =? and euID =?  group by recordTime ORDER BY recordTime desc"); 
							List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
							resultVo.getHistoryStatus().addAll(tempResult);
						}*/
            }
        } else if (CommonContant.ACCESS_LOG.equals(elementType)) {//EU-DU访问日志上报
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_RUNINFO_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = Houseid;
            param[2] = deviceid;
            if (null != existTableList && existTableList.size() > 0) {
                for (String table : existTableList) {
                    StringBuffer sql = new StringBuffer("select euID,'" + elementType + "' as elementType,status, sum(actLog_uploadNum+monitorLog_uploadNum" +
                            "+blockLog_uploadNum+statLog_uploadNum+resLog_uploadNum) as actLog_uploadNum,date_format(recordTime,'%H:%i')  as recordTime from ");
                    sql.append(table).append(" where uuid=? and houseID =? and euID =? group by recordTime  ORDER BY recordTime desc");
                    List<ServiceVo> tempResult = DbUtil.queryForObjectList(sql.toString(), ServiceVo.class, param);

                    StringBuffer sql2 = new StringBuffer("select max(actLog_uploadNum+monitorLog_uploadNum+blockLog_uploadNum+statLog_uploadNum+resLog_uploadNum) as maxUsrRate from ");
                    sql2.append(table).append("  where uuid=? and houseID =? and euID =?");
                    String maxUsrRate = DbUtil.queryForString(sql2.toString(), param);

                    resultVo.setMaxUsrRate(maxUsrRate);
                    resultVo.setHistoryAccessLog(tempResult);
                }
            }
        } else if (CommonContant.EU_TO_CU.equals(elementType)) {//eu到cu通信状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_CU_COMM_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
				 /*Object[] param = new Object[3];
				 param[0] = uuid;
				 param[1] = Houseid;
				 param[2] = deviceid;*/
            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "2");
						/*for(String table:existTableList){
							StringBuffer sql = new StringBuffer("select euID,euIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
							sql.append(table).append(" WHERE  uuid=? and houseID =? and euID =? group by recordTime ORDER BY recordTime desc"); 
							List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
							resultVo.getHistoryStatus().addAll(tempResult);
						}*/
            }
        } else if (CommonContant.LINK_STATUS.equals(elementType)) {//EU链路状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_LINK_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
				 /*Object[] param = new Object[3];
				 param[0] = uuid;
				 param[1] = Houseid;
				 param[2] = deviceid;*/
            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "2");
						/*for(String table:existTableList){
							StringBuffer sql = new StringBuffer("select euID,euIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
							sql.append(table).append(" where uuid=? and houseID =? and euID =? group by recordTime  ORDER BY recordTime desc"); 
							List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
							resultVo.getHistoryStatus().addAll(tempResult);
						}*/
            }
        } else if (CommonContant.NETWORK_STATUS.equals(elementType)) {//Eu网卡状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_NETWORK_TYPE);
            List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
            List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
				 /*Object[] param = new Object[3];
				 param[0] = uuid;
				 param[1] = Houseid;
				 param[2] = deviceid;*/

            if (null != existTableList && existTableList.size() > 0) {
                deviceStatus(existTableList, deviceVo, resultVo, "2");
						/*for(String table:existTableList){
							StringBuffer sql = new StringBuffer("select euID,euIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
							sql.append(table).append(" where uuid=? and houseID =? and euID =? group by recordTime ORDER BY recordTime desc"); 
							List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
							resultVo.getHistoryStatus().addAll(tempResult);
						}*/
            }
        } else if (CommonContant.EU_DEVICE.equals(elementType)) {//服务器设备状态状态统计
            String queryType = deviceVo.getQueryType();
            if ("process".equals(queryType)) {//进程流水统计
                Object[] param = new Object[3];
                param[0] = uuid;
                param[1] = Houseid;
                param[2] = deviceid;

                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_PROCESS_TYPE);
                //List<String> dateList = SqlUtil.getDateList(deviceVo.getDateNum());
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        StringBuffer badSql = new StringBuffer("select count(status) as badtotal,'" + elementType + "' as elementType,date_format(recordTime,'%H:%i')  as recordTime from ");
                        badSql.append(table).append(" where  uuid=? and houseID = ? and euID =? and status = 1 group by recordTime  ORDER BY recordTime desc");

                        StringBuffer tatolSql = new StringBuffer("select count(status) as totalNum from ");
                        tatolSql.append(table).append(" where  uuid=? and houseID = ? and euID =? and status = 1 limit 1");

                        int total = DbUtil.queryForInt(tatolSql.toString(), param);
                        List<ProcessVo> tempResult = DbUtil.queryForObjectList(badSql.toString(), ProcessVo.class, param);
                        resultVo.setTotal(total);
                        resultVo.setHistoryProcess(tempResult);
                    }
                }
            } else if ("disk".equals(queryType)) {//硬盘流水统计
                Object[] param = new Object[3];
                param[0] = uuid;
                param[1] = Houseid;
                param[2] = deviceid;
                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_DISK_TYPE);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        String diskName = deviceVo.getSelectOption();

                        StringBuffer diskNameSql = new StringBuffer("select diskName from ");
                        diskNameSql.append(table).append(" where uuid=? and houseID = ? and euID =? group by diskName ORDER BY diskName desc");
                        List<String> diskNames = DbUtil.queryForObjectList(diskNameSql.toString(), String.class, param);
                        if (null != diskNames && diskNames.size() > 0) {
                            if (null == diskName || "".equals(diskName.trim())) {
                                diskName = diskNames.get(0);
                            }

                        } else {
                            return null;
                        }

                        resultVo.setOption(diskNames);


                        Object[] param1 = new Object[4];
                        param1[0] = uuid;
                        param1[1] = Houseid;
                        param1[2] = deviceid;
                        param1[3] = diskName;
                        StringBuffer sql = new StringBuffer("select diskName,'" + elementType + "' as elementType,diskTotalSize,status,useRate,date_format(recordTime,'%H:%i')  as recordTime from ");
                        sql.append(table).append(" where uuid=? and houseID = ? and euID =? and diskName =? group by recordTime desc");
                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and houseID = ? and euID =? and diskName =? and status = 1 limit 1");
                        StringBuffer sql3 = new StringBuffer("select max(useRate) as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and houseID = ? and euID =? and diskName =?");

                        int badNum = DbUtil.queryForInt(sql2.toString(), param1);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param1);
                        List<DiskVo> tempResult = DbUtil.queryForObjectList(sql.toString(), DiskVo.class, param1);

                        resultVo.setSelectOption(diskName);
                        resultVo.setTotal(badNum);
                        resultVo.setHistoryDisk(tempResult);
                        resultVo.setMaxUsrRate(maxUsrRate);
                    }
                }
            } else if ("memory".equals(queryType)) {//内存流水统计
                Object[] param = new Object[3];
                param[0] = uuid;
                param[1] = Houseid;
                param[2] = deviceid;

                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_MEMORY_TYPE);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        StringBuffer badSql = new StringBuffer("select memoryUsed,'" + elementType + "' as elementType,memoryBuffer,memoryFree,memory_rate as memoryRate,status,date_format(recordTime,'%H:%i')  as recordTime from ");
                        badSql.append(table).append(" where  uuid=? and houseID = ? and euID =? group by recordTime  desc");

                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and houseID = ? and  euID =?  and status = 1 limit 1");
                        StringBuffer sql3 = new StringBuffer("select max(memory_rate) as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and houseID = ? and euID =?");

                        int total = DbUtil.queryForInt(sql2.toString(), param);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param);
                        List<MemoryVo> tempResult = DbUtil.queryForObjectList(badSql.toString(), MemoryVo.class, param);
                        resultVo.setTotal(total);
                        resultVo.setMaxUsrRate(maxUsrRate);
                        resultVo.setHistoryMemory(tempResult);
                    }
                }
            } else if ("cpu".equals(queryType)) {
                Object[] param = new Object[3];
                param[0] = uuid;
                param[1] = Houseid;
                param[2] = deviceid;
                String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_CPURATE_TYPT);
                List<String> dateList = SqlUtil.getDateList(deviceVo.getQueryDate());
                List<String> existTableList = SqlUtil.getExistTable(dateList, tableName);
                if (null != existTableList && existTableList.size() > 0) {
                    for (String table : existTableList) {
                        String coulnNmae = deviceVo.getSelectOption();

                        StringBuffer numSql = new StringBuffer("select coreNum from ");
                        numSql.append(table).append(" where uuid=? and houseID = ? and euID =? limit 1");
                        int num = DbUtil.queryForInt(numSql.toString(), param);
                        List<String> option = new ArrayList<String>();
                        option.add("cpuTotal_rate");
                        for (int i = 0; i < num; i++) {
                            option.add("CPU" + i);
                        }
                        if (null == coulnNmae || "".equals(coulnNmae)) {
                            coulnNmae = "cpuTotal_rate";
                        }

                        resultVo.setOption(option);


                        StringBuffer sql1 = new StringBuffer("select " + coulnNmae + " as cpu,'" + elementType + "' as elementType,status,date_format(recordTime,'%H:%i')  as recordTime from ");
                        sql1.append(table).append(" where  uuid=? and houseID = ? and euID =? group by recordTime ORDER BY recordTime desc");
                        StringBuffer sql2 = new StringBuffer("select count(*) from ");
                        sql2.append(table).append(" where uuid=? and houseID = ? and euID =?  and status = 1");
                        StringBuffer sql3 = new StringBuffer("select max(" + coulnNmae + ") as maxUsrRate from ");
                        sql3.append(table).append(" where uuid=? and houseID = ? and euID =? ");

                        List<CpuVo> tempResult = DbUtil.queryForObjectList(sql1.toString(), CpuVo.class, param);
                        int badNum = DbUtil.queryForInt(sql2.toString(), param);
                        String maxUsrRate = DbUtil.queryForString(sql3.toString(), param);

                        resultVo.setSelectOption(coulnNmae);
                        resultVo.setTotal(badNum);
                        resultVo.setHistoryCpu(tempResult);
                        resultVo.setMaxUsrRate(maxUsrRate);
                    }
                }
            }
        }

        return resultVo;
    }

    @Override
    public PageResult<DevicePagesVo> statusPageList(PageResult<DevicePagesVo> page, DevicePagesVo deviceVo) {
        String elementType = deviceVo.getElementType();
        String uuid = deviceVo.getUuid();
        Long houseiD = deviceVo.getHouseID();
        String deviceid = deviceVo.getDeviceId();
        String queryType = deviceVo.getQueryType();

        if (CommonContant.CU_TO_SMMS.equals(elementType)) {//CU到管局通行状态
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = deviceid;
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_SMMS_COMM_TYPE);
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                        .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }

        } else if (CommonContant.CU_DEVICE.equals(elementType)) {//CU设备
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = deviceid;

            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_STAT_TYPE);
            if (null != tableName && !"".equals(tableName)) {

                if ("cpu".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP, a.cpurate_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                            .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("memory".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.memory_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                            .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("disk".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.disk_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                            .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("process".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.process_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                            .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if (queryType == null) {

                    StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                            .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                }
            }
        } else if (CommonContant.CU_TO_DU.equals(elementType)) {//CU到DU通信历史流水
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.CU_DU_COMM_TYPE);//CU到DU状态
            Object[] param = new Object[2];
            param[0] = uuid;
            param[1] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT 'CU' as houseName,a.cuIP as deviceId,b.idcName,b.idcID,a.cuIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_idc_info b on a.UUID = b.UUID")
                        .append(" and a.uuid=?  and a.cuIP =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        }

        //六种属于EU设备的查询
        else if (CommonContant.EU_TO_DU.equals(elementType)) {//eu到DU通信状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_DU_COMM_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,'' as euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                        .append(" where a.uuid=? and a.houseID = ?  and a.euID =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        } else if (CommonContant.ACCESS_LOG.equals(elementType)) {//EU-DU访问日志上报
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_RUNINFO_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,b.idcID,'' as euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                        .append(" where a.uuid=? and a.houseID = ?  and a.euID =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        } else if (CommonContant.EU_TO_CU.equals(elementType)) {//eu到cu通信状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_CU_COMM_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                        .append(" where a.uuid=? and a.houseID = ?  and a.euID =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        } else if (CommonContant.LINK_STATUS.equals(elementType)) {//EU链路状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_LINK_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.spotIP as euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                        .append(" where a.uuid=? and a.houseID = ?  and a.euID =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        } else if (CommonContant.NETWORK_STATUS.equals(elementType)) {//Eu网卡状态
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_NETWORK_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.innerIp as euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                        .append(" where a.uuid=? and a.houseID = ?  and a.euID =?  ORDER BY a.recordTime desc");
                page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
            }
        } else if (CommonContant.EU_DEVICE.equals(elementType)) {//服务器设备状态状态统计
            String tableName = SqlUtil.queryTableIndex(uuid, CommonContant.EU_STAT_TYPE);
            Object[] param = new Object[3];
            param[0] = uuid;
            param[1] = houseiD;
            param[2] = deviceid;
            if (null != tableName && !"".equals(tableName)) {
                if ("cpu".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.cpurate_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                            .append(" where a.uuid=? and a.houseID = ?  and a.euID =?   ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("memory".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.memory_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                            .append(" where a.uuid=? and a.houseID = ?  and a.euID =?   ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("disk".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.disk_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                            .append(" where a.uuid=? and a.houseID = ?  and a.euID =?   ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if ("process".equals(queryType)) {
                    StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.process_stat as status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                            .append(" where a.uuid=? and a.houseID = ?  and a.euID =?   ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                } else if (queryType == null) {
                    StringBuffer sql = new StringBuffer("select DISTINCT b.houseName,a.euID as deviceId,a.idcID,a.euIP,a.status,date_format(a.recordTime,'%Y-%c-%d %H:%i:%S') as recordTime from ");
                    sql.append(tableName).append(" a left join monsys_all_housename_info b on a.UUID = b.UUID and a.houseID=b.houseID ")
                            .append(" where a.uuid=? and a.houseID = ?  and a.euID =?   ORDER BY a.recordTime desc");
                    page = DbUtil.queryPageForObjectPageResult(sql.toString(), DevicePagesVo.class, page.getPageIndex(), page.getPageSize(), param);
                }
            }

        }
        return page;
    }

    @Override
    public DevicePagesVo labelInfo(DevicePagesVo deviceVo) {
        String[] params = new String[3];
        params[0] = deviceVo.getDeviceId();
        params[1] = deviceVo.getUuid();
        String sql;
        if ("euDevice".equals(deviceVo.getElementType())) {
            sql = "select  process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat  from`eu_static` where uuid='" + params[1] + "' and euID ='" + params[0] + "'";
        } else {
            sql = "select  process_stat as processStat , cpurate_stat as cpuStat ,memory_stat as memStat, disk_stat as diskStat  from`cu_static` where uuid='" + params[1] + "' and cuIP ='" + params[0] + "'";
        }
        DevicePagesVo devicePagesVo = DbUtil.queryForObject(sql.toString(), DevicePagesVo.class);
        return devicePagesVo;

    }


    private void deviceStatus(List<String> existTableList, StatusInfoVo deviceVo, StatusInfoVo resultVo, String type) {
        String[][] dateTime = {{"00:00", "02:00"}, {"02:00", "04:00"}, {"04:00", "06:00"}, {"06:00", "08:00"}, {"08:00", "10:00"}, {"10:00", "12:00"}, {"12:00", "14:00"}, {"14:00", "16:00"}, {"16:00", "18:00"},
                {"18:00", "20:00"}, {"20:00", "22:00"}, {"22:00", "24:00"}};
        StringBuffer sql = null;
        StringBuffer rangeInfoSql = null;
        StringBuffer rangeInfoAll = null;
        //StringBuffer second_sql = null;
        String elementType = deviceVo.getElementType();
        for (String table : existTableList) {
            if ("1".equals(type)) {//查询CU设备
				/*frist_sql = new StringBuffer("select  cuIP, count(*) as status  from ");
				frist_sql.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ? and status =1"); */

                sql = new StringBuffer("select cuIP,count(*) as status,'" + elementType + "' as elementType from ");
                sql.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ?  and status =1");

                rangeInfoSql = new StringBuffer("select status ,date_format(recordTime,'%H:%i:%S') as recordTime from ");
                rangeInfoSql.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ? and status =1 ORDER BY recordTime desc limit 12");

                rangeInfoAll = new StringBuffer("select status ,date_format(recordTime,'%H:%i:%S') as recordTime from ");
                rangeInfoAll.append(table).append("  where uuid=?  and cuIP =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ?  ORDER BY recordTime desc limit 12");
            } else if ("2".equals(type)) {//查询EU设备
                sql = new StringBuffer("select  euID, count(status) as status ,'" + elementType + "' as elementType from ");
                sql.append(table).append("  where uuid=?  and houseID =? and euID =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ? and status =1");


                rangeInfoSql = new StringBuffer("select status ,date_format(recordTime,'%H:%i:%S') as recordTime from ");
                rangeInfoSql.append(table).append("  where uuid=?  and houseID =? and euID =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ? and status =1 ORDER BY recordTime desc limit 12");

                rangeInfoAll = new StringBuffer("select status ,date_format(recordTime,'%H:%i:%S') as recordTime from ");
                rangeInfoAll.append(table).append("  where uuid=?  and houseID =? and euID =?  and date_format(recordTime,'%H:%i') >= ? and date_format(recordTime,'%H:%i') < ?  ORDER BY recordTime desc limit 12");
            }
			/*for(String table:existTableList){
			StringBuffer sql = new StringBuffer("select euID,euIP,'"+elementType+"' as elementType,status,date_format(recordTime,'%H-%i')  as recordTime from ");
			sql.append(table).append(" where uuid=? and houseID =? and euID =?  group by recordTime ORDER BY recordTime desc"); 
			List<Map<String,Object>> tempResult = DbUtil.queryForMapList(sql.toString(), param);
			resultVo.getHistoryStatus().addAll(tempResult);
		}*/
            for (String[] time : dateTime) {
                Object[] param = null;
                if ("1".equals(type)) {//查询CU设备
                    param = new Object[4];
                    param[0] = deviceVo.getUuid();
                    param[1] = deviceVo.getDeviceId();
                    param[2] = time[0];
                    param[3] = time[1];
                } else if ("2".equals(type)) {//查询EU设备
                    param = new Object[5];
                    param[0] = deviceVo.getUuid();
                    param[1] = deviceVo.getHouseID();
                    param[2] = deviceVo.getDeviceId();
                    param[3] = time[0];
                    param[4] = time[1];
                }
                List<HistoryStatus> allList = DbUtil.queryForObjectList(rangeInfoAll.toString(), HistoryStatus.class, param);
                int allRecoder = 0;
                if (null != allList) {
                    allRecoder = allList.size();
                }
                HistoryStatus historyStatus = DbUtil.queryForObject(sql.toString(), HistoryStatus.class, param);
                if (null != historyStatus) {
                    if (allRecoder > 0) {
                        List<RangeInfoVo> rangeInfoVoList = DbUtil.queryForObjectList(rangeInfoSql.toString(), RangeInfoVo.class, param);
                        historyStatus.setRangeInfo(rangeInfoVoList);
                    }
                    historyStatus.setAllRecoder(allRecoder);
                    historyStatus.setRecordTime(time[0] + "--" + time[1]);
                    resultVo.getHistoryStatus().add(historyStatus);
                }
            }
        }
    }
}
