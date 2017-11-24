/**
 * @Title: ExcelJsonUtil.java 
 * @Package com.act.web.util 
 * @Description: 根据json导出excel
 * @author   fmj
 * @modifier fmj
 * @date 2017-8-28 下午2:13:24
 * @version V1.0   
 */
package com.act.web.util;

import java.util.List;

public class ExcelJsonUtil<T> {
	private String[] header;// 导出excel表头
	private String[] dataName;// 导出excel数据属性
	private List<T> dataList;// 导出excel数据
	private String fileName;// 导出excel文件名称

	public ExcelJsonUtil() {

	}

	public ExcelJsonUtil(String[] header, String[] dataName, List<T> dataList,
			String fileName) {
		this.header = header;
		this.dataName = dataName;
		this.dataList = dataList;
		this.fileName = fileName;
	}

	public String[] getHeader() {
		return header;
	}

	public void setHeader(String[] header) {
		this.header = header;
	}

	public String[] getDataName() {
		return dataName;
	}

	public void setDataName(String[] dataName) {
		this.dataName = dataName;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}