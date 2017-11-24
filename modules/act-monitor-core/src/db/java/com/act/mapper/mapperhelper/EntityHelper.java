package com.act.mapper.mapperhelper;

import javax.persistence.*;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.act.framework.entity.BaseEntity;
import com.act.framework.util.DbUtil;
import com.act.mapper.annotation.CacheEntity;
import com.act.mapper.annotation.ColumnTitle;
import com.act.mapper.annotation.NameStyle;
import com.act.mapper.annotation.Reference;
import com.act.mapper.code.IdentityDialect;
import com.act.mapper.code.Style;
import com.act.mapper.entity.ColumnRef;
import com.act.mapper.entity.EntityColumn;
import com.act.mapper.entity.EntityTable;
import com.act.mapper.util.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.*;

/**
 * 实体类工具类 - 处理实体和数据库表以及字段关键的一个类
 * <p/>
 * <p>项目地址 : <a href="https://github.com/abel533/Mapper" target="_blank">https://github.com/abel533/Mapper</a></p>
 *
 * @author liuzh
 */
public class EntityHelper {
	private static Logger log = LoggerFactory.getLogger(EntityHelper.class);

    /**
     * 实体类 => 表对象
     */
    private static final Map<Class<?>, EntityTable> entityTableMap = new HashMap<Class<?>, EntityTable>();
    /**
     * 表名 => 实体类
     */
    private static final Map<String,Class<? extends BaseEntity>> entityClassMap = new HashMap<String,Class<?  extends BaseEntity>>();

    /**
     * 模块列表
     */
    private static final List<String> modules = new ArrayList<String>();

    public static List<String> getModuleList() {
    	return modules;
    }
    
    public static EntityTable getEntityTable(String tableName) {
    	Class<? extends BaseEntity> entityClass = getEntityClass(tableName);
    	if (entityClass==null)
    		throw new RuntimeException("No entity class for table " + tableName);
    	return getEntityTable(entityClass);
    }
    /**
     * 获取表对象
     *
     * @param entityClass
     * @return
     */
    public static EntityTable getEntityTable(Class<? extends BaseEntity> entityClass) {
        EntityTable entityTable = entityTableMap.get(entityClass);
        if (entityTable==null){
			EntityHelper.initEntityNameMap(entityClass);
			entityTable = EntityHelper.getEntityTable(entityClass);
        }
        if (entityTable == null) {
            throw new RuntimeException("无法获取实体类" + entityClass.getCanonicalName() + "对应的表名!");
        }
        return entityTable;
    }

    /**
     * 获取默认的orderby语句
     *
     * @param entityClass
     * @return
     */
    public static String getOrderByClause(Class<? extends BaseEntity> entityClass) {
        EntityTable table = getEntityTable(entityClass);
        if (table.getOrderByClause() != null) {
            return table.getOrderByClause();
        }
        StringBuilder orderBy = new StringBuilder();
        for (EntityColumn column : table.getEntityClassColumns()) {
            if (column.getOrderBy() != null) {
                if (orderBy.length() != 0) {
                    orderBy.append(",");
                }
                orderBy.append(column.getColumn()).append(" ").append(column.getOrderBy());
            }
        }
        table.setOrderByClause(orderBy.toString());
        return table.getOrderByClause();
    }

    /**
     * 获取全部列
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getColumns(Class<? extends BaseEntity> entityClass) {
        return getEntityTable(entityClass).getEntityClassColumns();
    }

    /**
     * 获取主键信息
     *
     * @param entityClass
     * @return
     */
    public static Set<EntityColumn> getPKColumns(Class<? extends BaseEntity> entityClass) {
        return getEntityTable(entityClass).getEntityClassPKColumns();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getSelectColumns(Class<? extends BaseEntity> entityClass) {
        EntityTable entityTable = getEntityTable(entityClass);
        if (entityTable.getBaseSelect() != null) {
            return entityTable.getBaseSelect();
        }
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        boolean skipAlias = Map.class.isAssignableFrom(entityClass);
        for (EntityColumn entityColumn : columnList) {
            selectBuilder.append(entityColumn.getColumn());
            if (!skipAlias && !entityColumn.getColumn().equalsIgnoreCase(entityColumn.getProperty())) {
                //不等的时候分几种情况，例如`DESC`
                if (entityColumn.getColumn().substring(1, entityColumn.getColumn().length() - 1).equalsIgnoreCase(entityColumn.getProperty())) {
                    selectBuilder.append(",");
                } else {
                    selectBuilder.append(" AS ").append(entityColumn.getProperty()).append(",");
                }
            } else {
                selectBuilder.append(",");
            }
        }
        entityTable.setBaseSelect(selectBuilder.substring(0, selectBuilder.length() - 1));
        return entityTable.getBaseSelect();
    }

    /**
     * 获取查询的Select
     *
     * @param entityClass
     * @return
     */
    public static String getAllColumns(Class<? extends BaseEntity> entityClass,boolean excludeIdentity) {
        Set<EntityColumn> columnList = getColumns(entityClass);
        StringBuilder selectBuilder = new StringBuilder();
        for (EntityColumn entityColumn : columnList) {
        	if (entityColumn.isIdentity()&&excludeIdentity)
        		continue;
            selectBuilder.append(entityColumn.getColumn()).append(",");
        }
        return selectBuilder.substring(0, selectBuilder.length() - 1);
    }

    /**
     * 获取主键的Where语句
     *
     * @param entityClass
     * @return
     */
    public static String getPrimaryKeyWhere(Class<? extends BaseEntity> entityClass, String tableAlias) {
        Set<EntityColumn> entityColumns = getPKColumns(entityClass);
        StringBuilder whereBuilder = new StringBuilder();
        for (EntityColumn column : entityColumns) {
        	if (tableAlias!=null && tableAlias.length()>0)
        		whereBuilder.append(tableAlias + "." + column.getColumn()).append(" = ?").append(" AND ");
        	else
        		whereBuilder.append(column.getColumn()).append(" = ?").append(" AND ");
        }
        return whereBuilder.substring(0, whereBuilder.length() - 4);
    }
    
    private static String getTableName(Class<?> entityClass){
        if (entityClass.isAnnotationPresent(Table.class)) {
            Table table = entityClass.getAnnotation(Table.class);
            if (!table.name().equals("")) {
                return table.name();
            }
        }
        
        if (entityClass.getSuperclass()!=null) {
        	return getTableName(entityClass.getSuperclass());
        }
        return null;
    	
    }

    /**
     * 初始化实体属性
     *
     * @param entityClass
     * @param style
     */
    public static synchronized void initEntityNameMap(Class<? extends BaseEntity> entityClass) {
        if (entityTableMap.get(entityClass) != null) {
            return;
        }
        log.info("init entity " + entityClass.getName());
        //style，该注解优先于全局配置
        Style style;
        if(entityClass.isAnnotationPresent(NameStyle.class)){
            NameStyle nameStyle = entityClass.getAnnotation(NameStyle.class);
            style = nameStyle.value();
        }
        else 
        	style = Style.camelhump;
        //表名
        EntityTable entityTable = null;
//      //20150910 changed by James Zhao
        String tableName = getTableName(entityClass);
        if (tableName==null){
            tableName = StringUtil.convertByStyle(entityClass.getSimpleName(), style);
        }
        entityTable = new EntityTable();
        entityTable.setName(tableName);
		Package classPackage = entityClass.getPackage();
		String classPackageName = classPackage.getName();
		if (classPackageName.endsWith(".model"))
			classPackageName = classPackageName.substring(0,classPackageName.length()-6);
		else if (classPackageName.endsWith(".entity"))
			classPackageName = classPackageName.substring(0,classPackageName.length()-7);
		else
			throw new RuntimeException("Entity class " + entityClass.getName() + " has incorrect package, should be **.model.* or **.entity.*");
		String module;
		if (classPackageName.indexOf(".")>=0){
			module = classPackageName.substring(classPackageName.lastIndexOf(".")+1);
		}
		else 
			module = classPackageName;
		entityTable.setModule(module);
		if (modules.indexOf(module)<0)
			modules.add(module);
//        if (entityClass.isAnnotationPresent(Table.class)) {
//            Table table = entityClass.getAnnotation(Table.class);
//            if (!table.name().equals("")) {
//                entityTable = new EntityTable();
//                entityTable.setTable(table);
//            }
//        }
//        
//        //try super class
//        if (entityTable == null && entityClass.getSuperclass()!=null) {
//            if (entityClass.getSuperclass().isAnnotationPresent(Table.class)) {
//                Table table = entityClass.getSuperclass().getAnnotation(Table.class);
//                if (!table.name().equals("")) {
//                    entityTable = new EntityTable();
//                    entityTable.setTable(table);
//                }
//            }
//        	
//        }
//        
//        if (entityTable == null) {
//            entityTable = new EntityTable();
//            //可以通过stye控制
//            entityTable.setName(StringUtil.convertByStyle(entityClass.getSimpleName(), style));
//        }

        //列
        List<Field> fieldList = getAllField(entityClass, null);
        Set<EntityColumn> columnSet = new LinkedHashSet<EntityColumn>();
        Set<EntityColumn> pkColumnSet = new LinkedHashSet<EntityColumn>();
        
        for (Field field : fieldList) {
            //排除字段
            if (field.isAnnotationPresent(Transient.class)) {
                continue;
            }
            //Id
            EntityColumn entityColumn = new EntityColumn(entityTable);
            if (field.isAnnotationPresent(Id.class)) {
                entityColumn.setId(true);
            }
            //Column
            String columnName = null;
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                columnName = column.name();
            }
            if (columnName == null || columnName.equals("")) {
                columnName = StringUtil.convertByStyle(field.getName(), style);
            }
            entityColumn.setProperty(field.getName());
            entityColumn.setColumn(columnName);
            entityColumn.setJavaType(field.getType());
            if (field.isAnnotationPresent(Length.class)) {
                Length length = field.getAnnotation(Length.class);
                entityColumn.setMaxLength(length.max());
            }
            if (field.isAnnotationPresent(Reference.class)) {
            	Reference ref = field.getAnnotation(Reference.class);
                ColumnRef cr = new ColumnRef();
                cr.setTable(ref.table());
                cr.setColumn(ref.column());
                cr.setDict(ref.dict());
                if (StringUtil.isEmpty(cr.getDict())){
                	if (StringUtil.isEmpty(cr.getTable()))
                		throw new RuntimeException("Reference of column " + tableName+ "." + field.getName()
                				+" has no table defined!");
                	if (StringUtil.isEmpty(cr.getColumn()))
                		throw new RuntimeException("Reference of column " + tableName+ "." + field.getName()
                				+" has no column defined!");
                }
                entityColumn.setColumnRef(cr);
            }
            //Title
            if (field.isAnnotationPresent(ColumnTitle.class)) {
                ColumnTitle title = field.getAnnotation(ColumnTitle.class);
                entityColumn.setTitle(title.value());
            }
            //Mandatory
            if (field.isAnnotationPresent(NotEmpty.class)) {
            	entityColumn.setMandatory(true);
            }
            else {
            	entityColumn.setMandatory(false);
            }

            //OrderBy
            if (field.isAnnotationPresent(OrderBy.class)) {
                OrderBy orderBy = field.getAnnotation(OrderBy.class);
                if (orderBy.value().equals("")) {
                    entityColumn.setOrderBy("ASC");
                } else {
                    entityColumn.setOrderBy(orderBy.value());
                }
            }
            //主键策略 - Oracle序列，MySql自动增长，UUID
            if (field.isAnnotationPresent(SequenceGenerator.class)) {
                SequenceGenerator sequenceGenerator = field.getAnnotation(SequenceGenerator.class);
                if (sequenceGenerator.sequenceName().equals("")) {
                    throw new RuntimeException(entityClass + "字段" + field.getName() + "的注解@SequenceGenerator未指定sequenceName!");
                }
                entityColumn.setSequenceName(sequenceGenerator.sequenceName());
            } else if (field.isAnnotationPresent(GeneratedValue.class)) {
                GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
                if (generatedValue.generator().equals("UUID")) {
                    entityColumn.setUuid(true);
                } else if (generatedValue.generator().equals("JDBC")) {
                    entityColumn.setIdentity(true);
                    entityColumn.setGenerator("JDBC");
                    entityTable.setKeyProperties(entityColumn.getProperty());
                    entityTable.setKeyColumns(entityColumn.getColumn());
                } else {
                    //允许通过generator来设置获取id的sql,例如mysql=CALL IDENTITY(),hsqldb=SELECT SCOPE_IDENTITY()
                    //允许通过拦截器参数设置公共的generator
                    if (generatedValue.strategy() == GenerationType.IDENTITY) {
                        //mysql的自动增长
                        entityColumn.setIdentity(true);
                        if (!generatedValue.generator().equals("")) {
                            String generator = null;
                            IdentityDialect identityDialect =DbUtil.getIdentityDialect();
                            if (identityDialect != null) {
                                generator = identityDialect.getIdentityRetrievalStatement();
                            } else {
                                generator = generatedValue.generator();
                            }
                            entityColumn.setGenerator(generator);
                        }
                    } else {
                        throw new RuntimeException(field.getName()
                                + " - 该字段@GeneratedValue配置只允许以下几种形式:" +
                                "\n1.全部数据库通用的@GeneratedValue(generator=\"UUID\")" +
                                "\n2.useGeneratedKeys的@GeneratedValue(generator=\\\"JDBC\\\")  " +
                                "\n3.类似mysql数据库的@GeneratedValue(strategy=GenerationType.IDENTITY[,generator=\"Mysql\"])");
                    }
                }
            }
            columnSet.add(entityColumn);
            if (entityColumn.isId()) {
                pkColumnSet.add(entityColumn);
            }
        }
        entityTable.setEntityClassColumns(columnSet);
        if (pkColumnSet.size() == 0) {
            entityTable.setEntityClassPKColumns(columnSet);
        } else {
            entityTable.setEntityClassPKColumns(pkColumnSet);
        }
        //缓存
        entityTableMap.put(entityClass, entityTable);
        entityClassMap.put(entityTable.getName().toLowerCase(), entityClass);
        if (entityClass.isAnnotationPresent(CacheEntity.class)) {
            entityTable.setEnableCache(true);
            entityTable.setCacheName(entityClass.getAnnotation(CacheEntity.class).name());
        }
    }
    
    public static Class<? extends BaseEntity> getEntityClass(String tableName){
    	Assert.notNull(tableName);
    	return entityClassMap.get(tableName.toLowerCase());
    }


    /**
     * 获取全部的Field
     *
     * @param entityClass
     * @param fieldList
     * @return
     */
    private static List<Field> getAllField(Class<?> entityClass, List<Field> fieldList) {
        if (fieldList == null) {
            fieldList = new LinkedList<Field>();
        }
        if (entityClass.equals(Object.class)) {
            return fieldList;
        }
        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            //排除静态字段，解决bug#2
            if (!Modifier.isStatic(field.getModifiers())) {
                fieldList.add(field);
            }
        }
        Class<?> superClass = entityClass.getSuperclass();
        if (superClass != null
                && !superClass.equals(Object.class)
                && (superClass.isAnnotationPresent(Entity.class)
                || (!Map.class.isAssignableFrom(superClass)
                && !Collection.class.isAssignableFrom(superClass)))) {
            return getAllField(entityClass.getSuperclass(), fieldList);
        }
        return fieldList;
    }
}