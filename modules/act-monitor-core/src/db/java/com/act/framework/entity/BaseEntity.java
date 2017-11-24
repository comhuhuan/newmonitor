package com.act.framework.entity;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Transient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.Assert;

import com.act.framework.dict.DictManager;
import com.act.framework.util.ExceptionUtil;
import com.act.framework.util.SpringUtil;
import com.act.mapper.entity.EntityColumn;
import com.act.mapper.entity.EntityTable;
import com.act.mapper.mapperhelper.EntityHelper;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kpr.kui.definition.SearchConfig;
import com.kpr.kui.util.DateUtil;
import com.kpr.kui.util.UiUtils;




/**
 * Entity基础类
 *
 * @author James Zhao
 */
public abstract class BaseEntity implements Serializable{
	public enum TreeMoveType{
		UP,
		DOWN,
		TO
	}
	//标准字段

	
	
	@SuppressWarnings("rawtypes")
	private final static HashMap<Class<? extends BaseEntity>,BaseDao> _daos = new HashMap<Class<? extends BaseEntity>,BaseDao>();
 	private final static HashMap<Class<? extends BaseEntity>,Class<BaseDao<? extends BaseEntity>>> _daoClasses = new HashMap<Class<? extends BaseEntity>,Class<BaseDao<? extends BaseEntity>>>();
	
	
	private final static Map<String,List<EntityListener>> listenerMap = new HashMap<String,List<EntityListener>>();

	protected static Logger log = LoggerFactory.getLogger(BaseEntity.class);
	
	static {
	}
	
	public static void registerEntityListener(String tableName,EntityListener listener){
		List<EntityListener> listeners = listenerMap.get(tableName);
		if (listeners==null){
			listeners = new ArrayList<EntityListener>();
			listenerMap.put(tableName, listeners);
		}
		if (!listeners.contains(listener))
			listeners.add(listener);
	}
	
	public static void removeListener(String tableName,EntityListener listener){
		List<EntityListener> listeners = listenerMap.get(tableName);
		if (listeners!=null){
			listeners.remove(listener);
		}
		else {
			throw new RuntimeException("No Listener list");
		}
	}
	
	protected static void registerDao(Class<? extends BaseEntity> entityType,Class<BaseDao<? extends BaseEntity>> daoClass){
		_daoClasses.put(entityType, daoClass);
	}

	public static <T extends BaseEntity> T newInstance(Class<T> entityClass){
		if (!BaseEntity.class.isAssignableFrom(entityClass)){
			throw new RuntimeException("Class " + entityClass.getName()+ " is not extend from BaseEntity!");
		}
		try {
			return entityClass.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		
	}

	@SuppressWarnings({ "unchecked" })
	@JsonIgnore
	protected  static <T >BaseDao<T> getDao(Class<T> type) {
		BaseDao<T> dao = _daos.get(type);
		if (dao==null){
			dao = (BaseDao<T>)SpringUtil.getApplicationContext().getBean(getDaoClass((Class<? extends BaseEntity>)type));
			synchronized(_daos){
				_daos.put((Class<? extends BaseEntity>)type, dao);
			}
		}
		if (dao==null)
			throw new RuntimeException("Dao " + type.getName() + " not exists.");
		return dao;
	}
	
	@SuppressWarnings({ "unchecked" })
	@JsonIgnore
	protected static Class<BaseDao<? extends BaseEntity>> getDaoClass(Class<? extends BaseEntity> type){
		Class<BaseDao<? extends BaseEntity>> daoClass = _daoClasses.get(type);
		if (daoClass==null){
			String className = type.getName();
			String objName = className.substring(className.lastIndexOf(".")+1);
			String daoClassName = className.substring(0,className.lastIndexOf("."));
			daoClassName=daoClassName.substring(0,daoClassName.lastIndexOf("."))
					+".dao."+objName+"Dao";
			try {
				daoClass = (Class<BaseDao<? extends BaseEntity>>)Class.forName(daoClassName);
				synchronized(_daoClasses){
					_daoClasses.put(type, daoClass);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);		
			}
		}
		return daoClass;
	}
	
	@Transient
	@JsonIgnore
	/**
	 * 获得model的属性对应的字典ID
	 * @param entityClass
	 * @param property
	 * @return
	 */
	public static String getPropertyDictId(Class<? extends BaseEntity> entityClass,String property){
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		Assert.notNull(table);
		EntityColumn column = table.getEntityColumnByProperty(property);
		Assert.notNull(column);
		return DictManager.getDictId(table.getName(), column.getColumn());
	}
	
	/**
	 * 获取字典类型属性的翻译sql
	 * @param entityClass
	 * @param property
	 * @param originColumn : 查询语句中的字段名
	 * @return
	 */
	public static String getDisplaySql(Class<? extends BaseEntity> entityClass,String property,String originColumn) {
		return DictManager.getDisplaySql(getPropertyDictId(entityClass, property), originColumn);
	}
	

	
	public static boolean isEqual(Object obj1, Object obj2) {
		if (obj1 == null && obj2 == null)
			return true;
		if (obj1==null && obj2!=null && obj2 instanceof String && obj2.toString().length()==0)
			return true;
		if (obj2==null && obj1!=null && obj1 instanceof String && obj1.toString().length()==0)
			return true;
		if (obj1 == null)
			return false;
		if (obj2 == null)
			return false;
		return obj1.equals(obj2);
	}
	
	private static String getEntityCacheKey(EntityTable entityTable,Object keyValue){
		if (keyValue==null)
			throw new RuntimeException("Null key value");
		String cacheKey="";
		if (keyValue instanceof Map<?,?>){
	        Set<EntityColumn> entityColumns = entityTable.getEntityClassPKColumns();
	        for (EntityColumn column : entityColumns) {
	        	if (cacheKey.length()>0)
	        		cacheKey += "_";
	        	Object obj = ((Map<?,?>) keyValue).get(column.getProperty());
	        	if (obj!=null){
	        		cacheKey +=obj.toString();
	        	}
	        	else
	        		cacheKey += "NULL";
	        }
		}
		else if (keyValue instanceof BaseEntity){
			//try get key value from property
			BaseEntity entity = (BaseEntity)keyValue;
	        Set<EntityColumn> entityColumns = entityTable.getEntityClassPKColumns();
	        for (EntityColumn column : entityColumns) {
	        	if (cacheKey.length()>0)
	        		cacheKey += "_";
	        	Object obj = entity.getPropertyValue(column.getProperty());
	        	if (obj!=null){
	        		cacheKey +=obj.toString();
	        	}
	        	else
	        		cacheKey += "NULL";
	        }
			
		}
		else{
			cacheKey = keyValue.toString();
		}
		if (cacheKey==null||cacheKey.length()==0){
			throw new RuntimeException("No cache key for " + entityTable.getName());
		}
		return cacheKey;
	}
	
	private static Cache getEntityCache(EntityTable entityTable){
		String cacheName = entityTable.getCacheName();
		if (cacheName==null||cacheName.trim().length()==0){
			cacheName = "entity." + entityTable.getName();
		}
		Cache cache = SpringUtil.getCache(cacheName);
		if (cache==null){
			throw new RuntimeException("Cache not defined:" + cacheName);
		}
		return cache;
	}
	
	@JsonIgnore
	public static BaseEntity getByKey(Class<? extends BaseEntity> entityClass,Object keyValue){
		Assert.notNull(keyValue);
		EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
		if (entityTable!=null&&entityTable.isEnableCache()){
			Cache cache = getEntityCache(entityTable);
			String cacheKey = getEntityCacheKey(entityTable, keyValue);
			ValueWrapper value = cache.get(cacheKey);
			BaseEntity entity = value!=null?(BaseEntity)value.get():null;
			if (entity==null){
				entity= (BaseEntity)getDao(entityClass).selectByPrimaryKey(keyValue);
				if (entity!=null){
					entity.changeFields.clear();
					entity.isManagedEntity = true;
				}
				synchronized(cache){
					ValueWrapper value1 = cache.get(cacheKey);
					BaseEntity entity1 = value1!=null?(BaseEntity)value1.get():null;
					if (entity1==null)
						cache.put(cacheKey, entity);
					else{
						entity = entity1;
					}
				}
			}
			return entity;
		}
		else {
			BaseEntity entity= (BaseEntity)getDao(entityClass).selectByPrimaryKey(keyValue);
			if (entity!=null){
				entity.changeFields.clear();
				entity.isManagedEntity = true;
			}
			return entity;
		}
	}
	
	public static <T extends BaseEntity> T loadFromResultSet(Class<T> entityClass, ResultSet rs){
		EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
		if (entityTable==null)
			throw new RuntimeException("No entity table for " + entityClass.getName());
		T entity = BaseEntity.newInstance(entityClass);
		for (EntityColumn column : entityTable.getEntityClassColumns()){
			try {
				Object value;
				Class<?> javaType = column.getJavaType();
				String columnName = column.getColumn();
				if (String.class==javaType){
					value = rs.getString(columnName);
				}
				else if (Byte.class==javaType||byte.class == javaType){
					value = rs.getByte(columnName);
				}
				else if (Short.class==javaType || short.class == javaType){
					value = rs.getShort(columnName);
				}
				else if (Integer.class==javaType || int.class == javaType){
					value = rs.getInt(columnName);
				}
				else if (Long.class==javaType || long.class == javaType){
					value = rs.getLong(columnName);
				}
				else if (Float.class==javaType || float.class == javaType){
					value = rs.getFloat(columnName);
				}
				else if (Double.class==javaType || double.class == javaType){
					value = rs.getDouble(columnName);
				}
				else if (BigDecimal.class==javaType){
					value = rs.getBigDecimal(columnName);
				}
				else if (java.util.Date.class==javaType){
					value = rs.getTimestamp(columnName);
				}
				else if (java.sql.Timestamp.class==javaType){
					value = rs.getTimestamp(columnName);
				}
				else if (java.sql.Date.class==javaType){
					value = rs.getDate(columnName);
				}
				else if (boolean.class==javaType){
					value = rs.getBoolean(columnName);
				}
				else if (byte[].class==javaType){
					value = rs.getBytes(columnName);
				}
				//新增支持 java.lang.Boolean
				else if(java.lang.Boolean.class == javaType){
					value = rs.getBoolean(columnName);
				}
				else {
					throw new Exception("Unsupport type " + javaType);
				}
				if (rs.wasNull()){
					value = null;
				}
				if (javaType.isPrimitive() && value==null){
					throw new Exception("Primitive type with null value");
//					if (byte.class==javaType){
//						value = (byte)0;
//					}
//					else if (short.class==javaType){
//						value = (short)0;
//					}
//					else if (int.class==javaType){
//						value = (int)0;
//					}
//					else if (long.class==javaType){
//						value = (long)0;
//					}
//					else if (float.class==javaType){
//						value = (float)0;
//					}
//					else if (double.class==javaType){
//						value = (double)0;
//					}
				}
				entity.setPropertyValue(column.getProperty(), value);
			}catch (RuntimeException e){
				throw e;
			}catch (Exception e){
				throw new RuntimeException("Column "+ column.getColumn()+" read error",e);
			}
		}
		entity.changeFields.clear();
		entity.isManagedEntity = true;
		return entity;
		
	}
	public void reload() {
		if (changeFields.size()>0){
			throw new RuntimeException("entity has been changed,please save it first!");
		}
		if (getKeyPropertyCount()==0)
			throw new RuntimeException("No key property!");
		Map<String,Object> keyValues = new HashMap<String, Object>();
		List<String> keyNames = getKeyPropertyNames();
		for (int i=0;i<keyNames.size();i++){
			keyValues.put(keyNames.get(i), getPropertyValue(keyNames.get(i)));
		}
		BaseEntity entity = getByKey(this.getClass(), keyValues);
		BeanUtils.copyProperties(entity, this);
		changeFields.clear();
		isManagedEntity = true;
	}
	
	public static void clearCache(Class<? extends BaseEntity> entityClass){
		EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
		if (entityTable!=null&&entityTable.isEnableCache()){
			Cache cache = getEntityCache(entityTable);
			cache.clear();
		}
		
	}
	public static void evictCache(Class<? extends BaseEntity> entityClass,Object keyValue){
		EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
		if (entityTable!=null&&entityTable.isEnableCache()){
			Cache cache = getEntityCache(entityTable);
			String cacheKey = getEntityCacheKey(entityTable, keyValue);
			cache.evict(cacheKey);
			SpringUtil.clearDependentCaches(cache.getName());
		}
		
	}

	/** 变化字段值，仅在更改已有记录时有效 */
	@Transient
	protected HashMap<String, Object> changeFields = new HashMap<String, Object>();
	//只有通过标准方法获取或执行了从Resultset读取等标准初始化方法的类才是已管理的实体。
	//未管理的实体只能做插入和删除操作，不能做更新操作。
	@Transient
	protected boolean isManagedEntity = false;

	//是否处于insert/update调用内
	@Transient
	private boolean isSaving = false;
	
	
	public BaseEntity(){
		
	}
	
	@Transient
	@JsonIgnore
	protected String[] getChangeProperties() {
		return changeFields.keySet().toArray(new String[0]);
	}


	protected void addChangeField(String propertyName, Object oldValue,Object newValue) {
		if (isManagedEntity 
			&& !changeFields.containsKey(propertyName)
			&&!isEqual(oldValue, newValue)
			)
			changeFields.put(propertyName, oldValue);
	}

	public boolean isValueChanged(String propertyName) {
		return changeFields.containsKey(propertyName);
	}

	public boolean isValueChanged() {
		return changeFields.size() > 0;
	}

	//除去指定的属性之外，还有没有字段值发生改变
	public boolean isValueChangedExclude(String[] propertyNames) {
		for (String key :  changeFields.keySet()){
			boolean found=false;
			for (String p : propertyNames){
				if (key.equals(p)){
					found = true;
					break;
				}
			}
			if (!found)
				return true;
		}
		return false;
	}

	@Transient
	@JsonIgnore
	protected Object getOriginalValue(String propertyName) {
		return changeFields.get(propertyName);
	}
	
	@Transient
	@JsonIgnore
	/** 获得主键属性个数 */
	public int getKeyPropertyCount(){
		return getEntityTable().getEntityClassPKColumns().size();
	}
	
	@Transient
	@JsonIgnore
	/** 获得主键属性名称数组 */
	public List<String> getKeyPropertyNames(){
		List<String> keyFieldsNames = new ArrayList<String>();
		for (EntityColumn column : getEntityTable().getEntityClassPKColumns()){
			keyFieldsNames.add(column.getProperty());
		}
		return keyFieldsNames;
	}
	
	@Transient
	@JsonIgnore
	/** 获得主键属性值数组，顺序按照getKeyPropertyNames中的顺序 */
	public List<Object> getKeyPropertyValues(){
		List<Object> keyFieldsValues = new ArrayList<Object>();
		for (EntityColumn column : getEntityTable().getEntityClassPKColumns()){
			keyFieldsValues.add(getPropertyValue(column.getProperty()));
		}
		return keyFieldsValues;
	}
	
	public boolean hasProperty(String propertyName){
		return getEntityTable().getEntityColumnByProperty(propertyName)!=null;
	}
	
	
	
	/**
	 * preSave中不允许再调用update操作
	 * @param isInsert
	 */
	protected void preSave(boolean isInsert){
		
	}
	/**
	 * postSave中不允许再调用update操作
	 * @param isInsert
	 */
	protected void postSave(boolean isInsert){
		
	}
	
	protected void preDelete(){
		
	}
	
	protected void postDelete(){
		
	}
	
	protected void checkAccess(boolean isWrite){
	}
	
	
	@SuppressWarnings("unchecked")
	public void insert() {
		if (isSaving){
			throw new RuntimeException("Entity is saving!");
		}
		isSaving = true;
		try {
			preSave(true);
			checkAccess(true);
			
			List<EntityListener> listeners = listenerMap.get(getEntityTableName());
			if (listeners!=null){
				for (EntityListener listener : listeners){
					listener.preSave(this, true);
				}
			}
			if (isAutoTruncate()){
				truncateValue();
			}
			try {
				((BaseDao<BaseEntity>)getDao(getClass())).insert(this);
				//清除ID字段变更记录
				changeFields.clear();
				isManagedEntity = true;
				postSave(true);
				if (listeners!=null){
					for (EntityListener listener : listeners){
						listener.postSave(this, true);
					}
				}
				if (getEntityTable().isEnableCache()){
					evictCache(getClass(), this);
				}
			}catch (RuntimeException e){
				Throwable re = ExceptionUtil.getRootException(e);
				if (re.getMessage()!=null && 
					(re.getMessage().toLowerCase().indexOf("duplicate")>=0 ||
					re.getMessage().toLowerCase().indexOf("unique")>=0)){
					log.warn("保存" + getClass().getName()+"错误",e);
					throw new RuntimeException("系统中存在重复数据!");
				}
				else
					throw e;
			}
		}finally{
			isSaving = false;
		}
	}
	
	/**
	 * 设置为受管理实体
	 */
	public void setManagedEntity() {
		isManagedEntity = true;
	}

	/**
	 * 
	 * @return 
	 * true: record updated 
	 * false:nothing to update
	 */
	@SuppressWarnings("unchecked")
	public boolean update() {
		if (!isManagedEntity)
			throw new RuntimeException("实体对象未被管理！");
		
		if (isValueChanged()){
			if (isSaving){
				throw new RuntimeException("Entity is saving!");
			}
			isSaving = true;
			try {
				preSave(false);
				checkAccess(true);
				
				List<EntityListener> listeners = listenerMap.get(getEntityTableName());
				if (listeners!=null){
					for (EntityListener listener : listeners){
						listener.preSave(this, false);
					}
				}
				if (isAutoTruncate()){
					truncateValue();
				}
				try {
					int count = ((BaseDao<BaseEntity>)getDao(getClass())).updateByPrimaryKey(this);
					if (count<=0){
						throw new RuntimeException("Entity not found or version mismatch!");
					}
					else if (count>1){
						throw new RuntimeException("Multiple entity found!");
					}
				}catch (RuntimeException e){
					Throwable re = ExceptionUtil.getRootException(e);
					if (re.getMessage()!=null && 
						(re.getMessage().toLowerCase().indexOf("duplicate")>=0 ||
						re.getMessage().toLowerCase().indexOf("unique")>=0)){
						log.warn("保存" + getClass().getName()+"错误",e);
						throw new RuntimeException("系统中存在重复数据!");
					}
					else
						throw e;
				}
				
				//postSave须在清除变更信息之前执行，以使程序能获得变更数据。
				postSave(false);
				if (listeners!=null){
					for (EntityListener listener : listeners){
						listener.postSave(this, false);
					}
				}
				//清除ID字段变更记录
				changeFields.clear();
				if (getEntityTable().isEnableCache()){
					evictCache(getClass(), this);
				}
				return true;
			}finally{
				isSaving = false;
			}
		}
		else
			return false;
	}
	
	@SuppressWarnings("unchecked")
	public void delete() {
		preDelete();
		checkAccess(true);
		List<EntityListener> listeners = listenerMap.get(getEntityTableName());
		if (listeners!=null){
			for (EntityListener listener : listeners){
				listener.preDelete(this);
			}
		}
		try {
			int count = ((BaseDao<BaseEntity>)getDao(getClass())).delete(this);
			if (count<=0){
				throw new RuntimeException("Entity not found!");
			}
			else if (count>1){
				throw new RuntimeException("Multiple entity found!");
			}
		}catch (RuntimeException e){
			Throwable re = ExceptionUtil.getRootException(e);
			if (re.getMessage()!=null && 
				(re.getMessage().toLowerCase().indexOf("constraint")>=0)){
				log.warn("删除" + getClass().getName()+"错误",e);
				throw new RuntimeException("待删除的记录被其他数据引用，无法删除!");
			}
			else
				throw e;
		}
		postDelete();
		if (listeners!=null){
			for (EntityListener listener : listeners){
				listener.postDelete(this);
			}
		}
		if (getEntityTable().isEnableCache()){
			evictCache(getClass(), this);
		}
	}
	
	
	private static String objToStr(Object value){
		if (value == null ) {
			return null;
		}
		if (value instanceof Timestamp){
			return DateUtil.toString((Timestamp)value, UiUtils.LONG_DATE_FORMAT);
		}
		else if (value instanceof java.util.Date){
			return DateUtil.toString((java.util.Date)value, UiUtils.LONG_DATE_FORMAT);
		}
		else if (value instanceof java.sql.Date){
			return DateUtil.toString((java.sql.Date)value, UiUtils.DATE_FORMAT);
		}
		else if (value instanceof Boolean){
			return ((Boolean)value).booleanValue()?"1":"0";
		}
		else
			return value.toString();
	}

	@JsonIgnore
	protected String getStringKeyValue(){
		List<Object> values = getKeyPropertyValues();
		StringBuffer buf = new StringBuffer();
		for (Object value: values){
			if (buf.length()>0)
				buf.append(",");
			if (value!=null)
				buf.append(objToStr(value));
			else
				buf.append("NULL");
		}
		if (buf.length()==0)
			throw new RuntimeException("No key properties");
		return buf.toString();
	}
	
	@Transient
	@JsonIgnore
	public EntityTable getEntityTable(){
		EntityTable entityTable = EntityHelper.getEntityTable(getClass());
//		if (entityTable==null){
//			MapperScannerConfigurer sc = SpringUtil.getApplicationContext().getBean(MapperScannerConfigurer.class);
//			EntityHelper.initEntityNameMap(getClass(), sc.getMapperHelper().getConfig().getStyle());
//			entityTable = EntityHelper.getEntityTable(getClass());
//		}
		if (entityTable==null){
			throw new RuntimeException("EntityTable for " + getClass().getName()+ " not exists.");
		}
		return entityTable;
	}
	
	@Transient
	@JsonIgnore
	//根据属性名获得数据库列名
	public String getColumnNameByProperty(String propertyName){
		EntityColumn column = getEntityTable().getEntityColumnByProperty(propertyName);
		if (column==null)
			throw new RuntimeException("No property: " + propertyName);
		return column.getColumn();
	}
	
	@Transient
	@JsonIgnore
	//根据数据库列名获得属性名
	public String getPropertyNameByColumn(String columnName){
		EntityColumn column = getEntityTable().getEntityColumnByColumn(columnName);
		if (column==null)
			throw new RuntimeException("No column: " + columnName);
		return column.getProperty();
	}

	/**
	 * 返回 Map<属性：属性值>
	 * @return
	 */
	public Map<String,Object> toMap(){
        EntityTable entityTable = getEntityTable();
        Set<EntityColumn> columnList = entityTable.getEntityClassColumns();
        Map<String,Object> values = new HashMap<String,Object>();
        for (EntityColumn column : columnList){
        	Object obj = getPropertyValue(column.getProperty());
        	values.put(column.getProperty(), obj);
        }
        return values;
	}
	
	/**
	 * 从Map<属性：属性值>读取值
	 */
	public void readFromMap(Map<String,Object> values){
        EntityTable entityTable = getEntityTable();
        Set<EntityColumn> columnList = entityTable.getEntityClassColumns();
        for (EntityColumn column : columnList){
        	if (values.containsKey(column.getProperty())){
        		setPropertyValue(column.getProperty(), values.get(column.getProperty()));
        	}
        	else if (column.isMandatory()){
        		throw new RuntimeException("缺少属性: "+ column.getProperty());
        	}
        	else
        		setPropertyValue(column.getProperty(), null);
        }
        changeFields.clear();
        isManagedEntity = true;
		
	}
	
	@Transient
	public void readFromRowSet(SqlRowSet rs){
        EntityTable entityTable = getEntityTable();
        Set<EntityColumn> columnList = entityTable.getEntityClassColumns();
        try {
	        for (EntityColumn column : columnList){
	        	Object obj = null;
	        	Class<?> type = column.getJavaType();
	        	String columnName = column.getColumn();
	        	if (rs.wasNull())
	        	if (String.class==type){
	        		obj = rs.getString(columnName);
	        	}
	        	else if (BigDecimal.class==type){
	        		obj = rs.getBigDecimal(columnName);
	        	}
	        	else if (Double.class==type){
	        		obj = rs.getDouble(columnName);
	        	}
	        	else if (Float.class==type){
	        		obj = rs.getFloat(columnName);
	        	}
	        	else if (Long.class==type){
	        		obj = rs.getLong(columnName);
	        	}
	        	else if (Integer.class==type){
	        		obj = rs.getInt(columnName);
	        	}
	        	else if (Short.class==type){
	        		obj = rs.getShort(columnName);
	        	}
	        	else if (Byte.class==type){
	        		obj = rs.getByte(columnName);
	        	}
	        	else if (Boolean.class==type){
	        		obj = rs.getBoolean(columnName);
	        	}
	        	else if (byte[].class==type){
	        		obj = rs.getObject(columnName);
	        		//obj = rs.getBytes(columnName);
	        	}
	        	else if (java.sql.Date.class == type){
	        		obj = rs.getDate(columnName);
	        	}
	        	else if (java.util.Date.class==type){
	        		obj = rs.getTimestamp(columnName);
	        	}
	        	else if (java.sql.Timestamp.class==type){
	        		obj = rs.getTimestamp(columnName);
	        	}
	        	else if (java.sql.Time.class==type){
	        		obj = rs.getTime(columnName);
	        	}
	        	else
	        		obj = rs.getObject(columnName);
	        	if (rs.wasNull())
	        		obj = null;
	        	setPropertyValue(column.getProperty(), obj);
	        }
	        changeFields.clear();
	        isManagedEntity = true;
        }catch (Exception e){
        	if (e instanceof RuntimeException )
        		throw (RuntimeException)e;
        	else
        		throw new RuntimeException(e);
		}

	}

	@JsonIgnore
	public Class<?> getPropertyClass(String property){
		PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(getClass(), property);
		if (pd==null)
			throw new RuntimeException("No property " + property + " of " + getClass().getName());
		return pd.getPropertyType();
	}

	@JsonIgnore
	public Object getPropertyValue(String property){
		try {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(getClass(), property);
			if (pd==null)
				throw new RuntimeException("No property " + property + " of " + getClass().getName());
			Method readMethod = pd.getReadMethod();
			if (readMethod==null)
				throw new RuntimeException("Not read method for " + property + " of " + getClass().getName()+".");
			return readMethod.invoke(this);
        }catch (Exception e){
        	if (e instanceof RuntimeException )
        		throw (RuntimeException)e;
        	else
        		throw new RuntimeException(e);
		}
		
	}
	
	

	
	public void setPropertyValue(String property,Object value){
		try {
			PropertyDescriptor pd = BeanUtils.getPropertyDescriptor(getClass(), property);
			if (pd==null)
				throw new RuntimeException("No property " + property + " of " + getClass().getName());
			Method writeMethod = pd.getWriteMethod();
			if (writeMethod==null)
				throw new RuntimeException("Not write method for " + property + " of " + getClass().getName()+".");
			Class<?>[] paramTypes = writeMethod.getParameterTypes();
			if (paramTypes==null||paramTypes.length!=1){
				throw new RuntimeException("Property " + property+" write method parameter count error!");
			}
			if (paramTypes[0].isPrimitive() && value==null){
				if (paramTypes[0] == boolean.class) {
					value = false;
				} else if (paramTypes[0] == int.class) {
					value = 0;
				} else if (paramTypes[0] == byte.class) {
					value = (byte) 0;
				} else if (paramTypes[0] == short.class) {
					value = (short) 0;
				} else if (paramTypes[0] == long.class) {
					value = 0L;
				} else if (paramTypes[0] == double.class) {
					value = (double) 0;
				} else if (paramTypes[0] == float.class) {
					value = (float) 0;
				} else
					throw new RuntimeException("Property " + property + " has unknown primitive type: "
							+ paramTypes[0].getName());
			}
			try {
				writeMethod.invoke(this, value);
			} catch (Exception e){
				throw new RuntimeException("设置属性错误，属性=" + property+",类型="+paramTypes[0].getName()
						+",值类型="+(value!=null?value.getClass().getName():"null")+",值="+value);
			}
		}catch (RuntimeException e){
			throw e;
        }catch (Exception e){
        	throw new RuntimeException(e);
		}
			
	}
	
	//返回显示列
	@JsonIgnore
	public final String[] getDisplayColumns() {
		String[] properties = getDisplayProperties();
		if (properties==null||properties.length==0)
			return properties;
		String[] columns = new String[properties.length];
		for (int i=0;i<properties.length;i++){
			columns[i] = getColumnNameByProperty(properties[i]);
		}
		return columns;
	}
	
	@JsonIgnore
	public String[] getDisplayProperties() {
		if (hasProperty("name"))
			return new String[]{"name"};
		else if (getKeyPropertyCount()>0){
			return getKeyPropertyNames().toArray(new String[0]);
		}
		else
			throw new RuntimeException("表" + getEntityTable().getName() + "未定义显示列!");
	}

	@JsonIgnore
	public String getEntityTableName() {
		return getEntityTable().getName();
	}
	
	//是否自动截断超长的字符
	protected boolean isAutoTruncate(){
		return false;
	}
	
	//根据字段最大长度，截断超长部分
	public void truncateValue() {
		for (EntityColumn column : getEntityTable().getEntityClassColumns()){
			if (column.getMaxLength()>0 && column.getJavaType()==String.class){
				String value = (String)getPropertyValue(column.getProperty());
				if (value!=null&&value.length()>column.getMaxLength()){
					value =value.substring(0,column.getMaxLength());
					setPropertyValue(column.getProperty(), value);
				}
			}
		}
	}
	
	//返回查找框配置
	@JsonIgnore
	public SearchConfig getSearchConfig() {
		Set<EntityColumn> keyColumnSet = getEntityTable().getEntityClassPKColumns();
		if (keyColumnSet==null||keyColumnSet.size()!=1)
			throw new RuntimeException("Entity should has one primery key column : " + getClass().getName());
			//return null; //can't create default searchConfig
		
		SearchConfig sc = new SearchConfig();
		//String tableName = getEntityTableName();
		String packageName = "Kui.view.search.";
		String module = getEntityTable().getModule();
//		Package classPackage = this.getClass().getPackage();
//		String classPackageName = classPackage.getName();
//		if (classPackageName.endsWith(".model"))
//			classPackageName = classPackageName.substring(0,classPackageName.length()-6);
//		else if (classPackageName.endsWith(".entity"))
//			classPackageName = classPackageName.substring(0,classPackageName.length()-7);
//		else
//			throw new RuntimeException("Entity class " + this.getClass().getName() + " has incorrect package, should be **.model.* or **.entity.*");
//		if (classPackageName.indexOf(".")>=0){
//			classPackageName = classPackageName.substring(classPackageName.lastIndexOf(".")+1);
//		}
		if (module!=null&&module.length()>0){
			packageName += (module + ".");
		}
//		if (tableName.indexOf("_")>=0){
//			String prefix = tableName.substring(0,tableName.indexOf("_"));
//			if (prefix!=null&&prefix.length()>0)
//				packageName += (prefix.toLowerCase()+".");
//		}
		String searchClass = packageName  + getClass().getSimpleName();
		sc.setSearchClass(searchClass);
		sc.setValueField(((EntityColumn)keyColumnSet.toArray()[0]).getColumn());
		String[] displayColumns = getDisplayColumns();
		if (displayColumns==null||displayColumns.length==0){
			throw new RuntimeException("Entity has no display column : " + getClass().getName());
		}
		StringBuffer str = new StringBuffer();
		for (String displayColumn: displayColumns){
			if (str.length()>0)
				str.append(",");
			str.append(displayColumn);
		}
		sc.setDisplayField(str.toString());
		return sc;
	}
	
	
	public void save() {
		if (isManagedEntity){
			update();
		}
		else {
			insert();
		}
	}
	
	
}
