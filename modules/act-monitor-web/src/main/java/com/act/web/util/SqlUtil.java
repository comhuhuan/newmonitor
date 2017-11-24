/**
 * @Title: SqlUtil.java 
 * @Package com.act.web.util 
 * @Description: sql语句工具类 
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-11 上午10:50:58
 * @version V1.0   
 */
package com.act.web.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.act.framework.util.SpringUtil;
import com.act.web.constant.CommonContant;

public class SqlUtil {

	/**
	 * @Title: verifySQL
	 * @Description: 检验sql语句合法性
	 * @param sql
	 *            被检验sql
	 * @return boolean 合法返回true 非法返回false
	 * @create 2017-5-11 上午10:58:28
	 * @update 2017-5-11 上午10:58:28
	 */
	public static boolean verifySQL(String sql) {
		String countSql = "select count(*) from (" + sql + ") t";
		try {
			SpringUtil.getJdbcTemplate().queryForList(countSql);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	/**
	 * @Title: queryTableIndex
	 * @Description: 根据UUID和type从索引表中查询对应的表名
	 * @param uuid,type
	 * @return String 返回表名
	 * @create 2017-5-11 上午10:58:28
	 * @update 2017-5-11 上午10:58:28
	 */
	public static String queryTableIndex(String uuid,String type) {
		String sql = "select tableName from monsys_all_tabname_info where uuid=? and tableType=?";
		try {
			Object[] param = {uuid,type};
			Map<String,Object> map = SpringUtil.getJdbcTemplate().queryForMap(sql,param);
			if(null != map){
				Object table = map.get("tableName");
				if(null != table && !"".equals(table)){
					return "`"+table.toString()+"`".trim();
				}else{
					return null;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当前时间前的指定的时间天数
	 * @param dateNum
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> getDateList(int dateNum)throws Exception {
		List<String> dateList = new ArrayList<String>();
		// 假如用户没有输入结束日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = new Date();
		Calendar startCalendar = Calendar.getInstance();
		startCalendar.setTime(date);
		for(int i=0;i<dateNum;i++){
			if(i == 0){
				startCalendar.add(Calendar.DAY_OF_MONTH, -0);
			}else{
				startCalendar.add(Calendar.DAY_OF_MONTH, -1);
			}
			
			String tempDate = sdf.format(startCalendar.getTime());
			dateList.add(tempDate);
		}
		return dateList;
	}
	
	/**
	 * 获取指定日期
	 * @param timeStamp 指定日期
	 * @return List<String>
	 * @throws Exception
	 */
	public static List<String> getDateList(String timeStamp)throws Exception {
		List<String> dateList = new ArrayList<String>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		if(null == timeStamp || "".equals(timeStamp)){
			Date date = new Date();
			dateList.add(sdf.format(date));
		}else{
			if(timeStamp.contains("-")){
				timeStamp = timeStamp.replaceAll("-","");
			}
			dateList.add(timeStamp);
		}
		return dateList;
	}
	
	
	/**
	 * 查询表名是否存在 
	 * @param dateList
	 * @param tableName
	 * @return List<String>
	 * @throws 
	 */
	public static List<String> getExistTable(List<String> dateList, String tableName) {
		List<String> result = new ArrayList<String>();
		ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
		String tableSchema = conf.getTableSchema();
		Iterator<String> it = dateList.iterator();
		if(null != tableName){
			tableName = tableName.replaceAll("`","");
		}
		while (it.hasNext()) {
			String item = it.next();
			String sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME='"
					+ tableName + "_" + item
					+ "' and table_schema='"
					+ tableSchema + "'";
			@SuppressWarnings("rawtypes")
			List list = SpringUtil.getJdbcTemplate().queryForList(sql);
			if (null != list && list.size() > 0) {
				
				result.add("`"+tableName + "_" + item+"`".trim());
			}
		}
		return result;
	}
	
	/**
	 * 查询表名是否存在 
	 * @param dateList
	 * @param tableName
	 * @return List<String>
	 * @throws 
	 */
	public static boolean getExistTable(String tableName) {
		ConfigLoadUtil conf = SpringContextUtil.getBean("configLoadUtil");
		String tempTable = null;
		String tableSchema = conf.getTableSchema();
		if(null != tableName){
			tempTable = tableName.replaceAll("`","");
		}
		String sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME='"
				+ tempTable + "' and table_schema='"+ tableSchema + "'";
		@SuppressWarnings("rawtypes")
		List list = SpringUtil.getJdbcTemplate().queryForList(sql);
		if (null != list && list.size() > 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 拼接表名
	 * @param dateList
	 * @param tableName
	 * @return List<String>
	 * @throws 
	 */
	public static List<String> getTableName(List<String> dateList, String tableName) {
		List<String> result = new ArrayList<String>();
		Iterator<String> it = dateList.iterator();
		if(null != tableName){
			tableName = tableName.replaceAll("`","");
		}
		while (it.hasNext()) {
			String item = it.next();
			/*String sql = "select TABLE_NAME from INFORMATION_SCHEMA.TABLES where TABLE_NAME='"
					+ tableName + "_" + item
					+ "' and table_schema='"
					+ tableSchema + "'";
			@SuppressWarnings("rawtypes")
			List list = SpringUtil.getJdbcTemplate().queryForList(sql);
			if (null != list && list.size() > 0) {
				
				result.add("`"+tableName + "_" + item+"`".trim());
			}*/
			result.add("`"+tableName + "_" + item+"`".trim());
		}
		Collections.sort(result, String.CASE_INSENSITIVE_ORDER);
		//Collections.sort(result, Collections.reverseOrder());
		return result;
	}
	
	/**
	 * @title getTableType
	 * @Description 获取对应表名索引
	 * @param elementType
	 * @param queryType
	 * @return
	 */
	public static String getTableType(String elementType, String queryType) {
		String tableType = null;
		
		if (CommonContant.EU_TO_CU.equals(elementType)) {
			tableType = CommonContant.EU_CU_COMM_TYPE;
		}else if (CommonContant.EU_TO_DU.equals(elementType)) {
			tableType = CommonContant.EU_DU_COMM_TYPE;
		}else if (CommonContant.ACCESS_LOG.equals(elementType)) {
			tableType = CommonContant.EU_RUNINFO_TYPE;
		}else if (CommonContant.LINK_STATUS.equals(elementType)) {
			tableType = CommonContant.EU_LINK_TYPE;
		}else if (CommonContant.NETWORK_STATUS.equals(elementType)) {
			tableType = CommonContant.EU_NETWORK_TYPE;
		}else if (CommonContant.CU_TO_SMMS.equals(elementType)) {
			tableType = CommonContant.CU_SMMS_COMM_TYPE;
		}else if (CommonContant.CU_TO_DU.equals(elementType)) {
			tableType = CommonContant.CU_DU_COMM_TYPE;
		}else if (CommonContant.EU_DEVICE.equals(elementType)) {
			if ("cpu".equals(queryType)) {
				tableType = CommonContant.EU_CPURATE_TYPE;
			}else if ("disk".equals(queryType)) {
				tableType = CommonContant.EU_DISK_TYPE;
			}else if ("memory".equals(queryType)) {
				tableType = CommonContant.EU_MEMORY_TYPE;
			}else if ("process".equals(queryType)) {
				tableType = CommonContant.EU_PROCESS_TYPE;
			}else{
				tableType = CommonContant.EU_CPURATE_TYPE;
			}
			//tableType = CommonContant.EU_DEVICE_TYPE;
		}else if (CommonContant.CU_DEVICE.equals(elementType)) {
			if ("cpu".equals(queryType)) {
				tableType = CommonContant.CU_CPURATE_TYPE;
			}else if ("disk".equals(queryType)) {
				tableType = CommonContant.CU_DISK_TYPE;
			}else if ("memory".equals(queryType)) {
				tableType = CommonContant.CU_MEMORY_TYPE;
			}else if ("process".equals(queryType)) {
				tableType = CommonContant.CU_PROCESS_TYPE;
			}
			else{
				tableType = CommonContant.CU_CPURATE_TYPE;
			}
			//tableType = CommonContant.CU_DEVICE_TYPE;
		}
		
		return tableType;
	}
}
