package com.act.mapper.entity;

import javax.persistence.Table;

import com.act.mapper.util.StringUtil;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 数据库表
 *
 * @author liuzh
 */
public class EntityTable {
    private String name;
    private String catalog;
    private String schema;
    private String orderByClause;
    private String baseSelect;
    //实体类 => 全部列属性
    private Set<EntityColumn> entityClassColumns;
    //实体类 => 主键信息
    private Set<EntityColumn> entityClassPKColumns;
    //useGenerator包含多列的时候需要用到
    private List<String> keyProperties;
    private List<String> keyColumns;
    
    //数据字段名到EntityColumn的索引
    private Map<String,EntityColumn> columnMap = new HashMap<String,EntityColumn> ();

    //属性名到EntityColumn的索引
    private Map<String,EntityColumn> propertyMap = new HashMap<String,EntityColumn> ();

    //是否开启缓存
    //通过缓存注解实现
    private boolean enableCache = false;
    private String cacheName;
    
    //属于模块名
    //根据实体类的父包名确定
    private String module;
    
    public EntityTable(){
    	
    }
    
    public boolean isEnableCache() {
		return enableCache;
	}

	public void setEnableCache(boolean enableCache) {
		this.enableCache = enableCache;
	}

	public String getCacheName() {
		return cacheName;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public void setTable(Table table) {
        this.name = table.name();
        this.catalog = table.catalog();
        this.schema = table.schema();
    }

    public void setKeyColumns(List<String> keyColumns) {
        this.keyColumns = keyColumns;
    }

    public void setKeyProperties(List<String> keyProperties) {
        this.keyProperties = keyProperties;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatalog() {
        return catalog;
    }

    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public String getBaseSelect() {
        return baseSelect;
    }

    public void setBaseSelect(String baseSelect) {
        this.baseSelect = baseSelect;
    }

    public String getPrefix() {
        if (StringUtil.isNotEmpty(catalog)) {
            return catalog;
        }
        if (StringUtil.isNotEmpty(schema)) {
            return catalog;
        }
        return "";
    }

    public Set<EntityColumn> getEntityClassColumns() {
        return entityClassColumns;
    }

    public void setEntityClassColumns(Set<EntityColumn> entityClassColumns) {
        this.entityClassColumns = entityClassColumns;
        this.columnMap.clear();
        this.propertyMap.clear();
        for (EntityColumn column: entityClassColumns){
        	this.columnMap.put(column.getColumn().toLowerCase(), column);
        	this.propertyMap.put(column.getProperty(), column);
        }
    }

    public Set<EntityColumn> getEntityClassPKColumns() {
        return entityClassPKColumns;
    }

    public void setEntityClassPKColumns(Set<EntityColumn> entityClassPKColumns) {
        this.entityClassPKColumns = entityClassPKColumns;
    }

    public String[] getKeyProperties() {
        if (keyProperties != null && keyProperties.size() > 0) {
            return keyProperties.toArray(new String[]{});
        }
        return new String[]{};
    }

    public void setKeyProperties(String keyProperty) {
        if (this.keyProperties == null) {
            this.keyProperties = new LinkedList<String>();
            this.keyProperties.add(keyProperty);
        } else {
            this.keyProperties.add(keyProperty);
        }
    }

    public String[] getKeyColumns() {
        if (keyColumns != null && keyColumns.size() > 0) {
            return keyColumns.toArray(new String[]{});
        }
        return new String[]{};
    }

    public void setKeyColumns(String keyColumn) {
        if (this.keyColumns == null) {
            this.keyColumns = new LinkedList<String>();
            this.keyColumns.add(keyColumn);
        } else {
            this.keyColumns.add(keyColumn);
        }
    }
    
    public EntityColumn getEntityColumnByColumn(String column){
    	return columnMap.get(column.toLowerCase());
    }

    public EntityColumn getEntityColumnByProperty(String property){
    	return propertyMap.get(property);
    }
    
    public boolean hasProperty(String property){
    	return propertyMap.containsKey(property);
    }

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}
    
//    public EntityColumn getEntityVersionColumn() {
//    	for (EntityColumn column: this.entityClassColumns){
//    		if (column.isEntityVersionColumn())
//    			return column;
//    	}
//    	return null;
//    }
    
}
