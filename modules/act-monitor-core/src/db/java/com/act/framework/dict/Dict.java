package com.act.framework.dict;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.act.framework.util.DbUtil;
import com.act.mapper.entity.EntityTable;
import com.act.mapper.mapperhelper.EntityHelper;
import com.kpr.kui.util.SelectSqlParser;


@XmlRootElement (name="Dict")
public class Dict {
	public enum Type{
		list,
		table
	}
	//类型
	private Type type = Type.table;
	
//	private String id;
	private String idField;
	private String nameField;
	private String from;
	//where中的字段需要带表名前缀，在生成DisplaySql时需要将表名前缀替换为a$
	private String where;
	private String orderBy;
	// 字典项定义
	private List<DictItem> items = new ArrayList<DictItem>();
	
	@Transient
	private transient String tableAlias;
	@Transient
	private transient String displaySql;
	
	public Dict() {
	}
	
	public Dict(DictItem[] values) {
		this.type = Type.list;
		if (values!=null){
			for (DictItem cdv : values){
				items.add(cdv);
			}
		}
	}

	public Dict(String from,String idField,String nameField,
			String where,String orderBy){
		this.idField = idField;
		this.nameField = nameField;
		this.from = from;
		this.where = where;
		this.orderBy = orderBy;
	}
	

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	@XmlElementWrapper(name="items")
	@XmlElement(name="item")
	public List<DictItem> getItems() {
		return items;
	}

	public void setItems(List<DictItem> items) {
		this.items = items;
	}
	
//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
	
	public String getIdField() {
		return idField;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public String getNameField() {
		return nameField;
	}

	public void setNameField(String nameField) {
		this.nameField = nameField;
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}
	
	@Transient
	public String getTableAlias() {
		if (this.tableAlias==null){
			SelectSqlParser sp = new SelectSqlParser("select 1 FROM " + this.from);
			this.tableAlias = sp.getFirstTableAlias();
		}
		return this.tableAlias;
	}

	@Transient
	public boolean isTableDict() {
		return Dict.Type.table==this.type;
	}
	private String[] split(String str, char sep){
		List<String> list = new ArrayList<String>();
		StringBuffer rst = new StringBuffer();
		int bracketCount = 0;
		boolean inQuote = false;
		for (int i=0;i<str.length();i++){
			char c = str.charAt(i);
			if (inQuote){
				rst.append(c);
				if (c=='\'')
					inQuote = false;
			}
			else if (c=='\''){
				rst.append(c);
				inQuote = true;
			}
			else if (c=='('){
				bracketCount++;
				rst.append(c);
			}
			else if (c==')'){
				bracketCount--;
				rst.append(c);
			}
			else if (bracketCount>0){
				rst.append(c);
			}
			else if (c==sep){
				list.add(rst.toString());
				rst.setLength(0);
			}
			else
				rst.append(c);
		}
		if (rst.length()>0)
			list.add(rst.toString());
		return list.toArray(new String[list.size()]);
	}
	
	@Transient
	public String getDisplaySql(){
		if (Dict.Type.table!=getType())
			throw new RuntimeException("Dict is not table type!");
		if (displaySql!=null)
			return displaySql;
		
		if (nameField==null||nameField.length()==0)
			throw new RuntimeException("No name field!");
		String[] columns = split(nameField, ',');
		if (columns.length==0)
			throw new RuntimeException("No column in name field!");
		EntityTable entityTable = null;
		if (getFrom()!=null&&getFrom().indexOf(" ")<0){
			entityTable = EntityHelper.getEntityTable(getFrom());
		}
		String[] sqls = new String[columns.length];
		for (int i=0;i<columns.length;i++) {
			String column = columns[i];
			if (column==null||column.length()==0)
				throw new RuntimeException("Null column in name field!");
			if (entityTable!=null&&column.indexOf(".")<0){
				String dictId = DictManager.getDictId(getFrom(), column);
				if (dictId!=null&&dictId.length()>0){
					sqls[i] = "("+DictManager.getDisplaySql(dictId, getFrom()+"."+column)+")";
				}
				else{
					sqls[i] = column;
				}
			}
			else {
				if (column.indexOf(".")<0)
					sqls[i] = getTableAlias()+"."+column;
				else
					sqls[i] = column;
			}
		}
		if (sqls.length==1)
			displaySql =  sqls[0];
		else
			displaySql =  DbUtil.concat_ws(sqls, "-");
		return displaySql;

	}
	
	public boolean hasItem(String value){
		if (this.items==null||items.size()==0)
			return false;
		for (DictItem item: items){
			if (item.getValue()!=null && item.getValue().equals(value)){
				return true;
			}
		}
		return false;
	}
	
	public boolean checkAndAddItem(DictItem item){
		if (hasItem(item.getValue()))
			return false;
		if (this.items==null)
			this.items = new ArrayList<DictItem>();
		items.add(item);
		return true;
	}

}
