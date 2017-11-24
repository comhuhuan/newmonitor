package com.act.framework.dict;

public class DictItem {
	/**
	 * 字典值
	 */
	private String value;
	/**
	 * 显示名称
	 */
	private String display;
	
	/**
	 * 常量标识符
	 * 如果定义了常量标识符，在生成entity时，会自动生成常量
	 */
	private String identifier;
	
	public DictItem() {
		
	}

	public DictItem(String value,String display){
		this.value = value;
		this.display = display;
	}
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
