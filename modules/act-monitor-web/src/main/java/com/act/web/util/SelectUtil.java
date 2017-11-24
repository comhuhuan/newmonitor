/**
 * @Title: selectUtil.java 
 * @Package com.act.web.util 
 * @Description: select组件查询条件
 * @author   fmj
 * @modifier fmj
 * @date 2017-5-15 下午3:44:28
 * @version V1.0   
 */
package com.act.web.util;


public class SelectUtil<T> {
	private T value;
	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

}
