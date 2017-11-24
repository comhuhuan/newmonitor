package com.act.framework.dict;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement (name="Dict")
public class TreeDict extends Dict {
	private String leftOrderField = "left_order";
	private String rightOrderField = "right_order";
	private String parentField = "parent_id";
	private String leafField = "is_leaf";
	
	public TreeDict() {
		
	}
	
	public TreeDict(DictItem[] values) {
		super(values);
	}

	public TreeDict(String tableName,String idField,String nameField,
			String where,String orderBy){
		super(tableName,idField,nameField,where,orderBy);
	}
	
	public String getLeftOrderField() {
		return leftOrderField;
	}

	public void setLeftOrderField(String leftField) {
		this.leftOrderField = leftField;
	}

	public String getRightOrderField() {
		return rightOrderField;
	}

	public void setRightOrderField(String rightField) {
		this.rightOrderField = rightField;
	}

	public String getParentField() {
		return parentField;
	}

	public void setParentField(String parentField) {
		this.parentField = parentField;
	}

	public String getLeafField() {
		return leafField;
	}

	public void setLeafField(String leafField) {
		this.leafField = leafField;
	}
	
	

}
