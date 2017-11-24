package com.act.framework.dict;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.Cache.ValueWrapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.util.StringUtils;

import com.act.framework.entity.BaseEntity;
import com.act.framework.entity.ITreeEntity;
import com.act.framework.util.DbUtil;
import com.act.framework.util.SpringUtil;
import com.act.framework.util.XmlUtil;
import com.act.mapper.entity.ColumnRef;
import com.act.mapper.entity.EntityColumn;
import com.act.mapper.entity.EntityTable;
import com.act.mapper.mapperhelper.EntityHelper;
import com.kpr.kui.definition.Field;
import com.kpr.kui.definition.Tab;
import com.kpr.kui.definition.TreeConfig;
import com.kpr.kui.runtime.IdNamePair;
import com.kpr.kui.runtime.SqlFormater;
import com.kpr.kui.runtime.TreeNode;
import com.kpr.kui.runtime.WindowEnv;
import com.kpr.kui.util.DBDateFormater;
import com.kpr.kui.util.SelectSqlParser;
import com.kpr.kui.util.StringUtil;



public class DictManager {
	//程序定义字典
	private final static HashMap<String,Dict> runtimeDicts = new HashMap<String,Dict>();
	
	static {
        registerDict("boolean",new Dict(new DictItem[]{new DictItem("1","是"),new DictItem("0","否")}));
	}

	private static String idToPath(String id){
		return StringUtil.replace(id, ".", "/");
				
	}
	
	public static Dict getDict(String dictId){
		return getDict(dictId, false);
	}
	
	public static Dict getDict(String dictId,boolean reload){
		if (dictId==null||dictId.length()==0)
			throw new RuntimeException("Null dict id");
		Dict dict = null;
		Cache cache= SpringUtil.getCache("ui.dict");
		if (!reload){
			ValueWrapper obj = cache.get(dictId);
			dict = obj!=null?(Dict)obj.get():null;
		}
		if (dict==null){
			dict = loadDict(dictId);
			cache.put(dictId, dict);
		}
		return dict;
		
	}
	
	public static Dict loadDict(String dictId) {
		
		if (dictId==null||dictId.length()==0)
			throw new RuntimeException("Null dict id");
		
		
		if (dictId.startsWith("dict:")){
			//数据库字典表
			String dictValue = dictId.substring(5);
			Dict dict = new Dict();
			dict.setIdField("value");
			dict.setNameField("name");
			dict.setFrom("sys_dict_line");
			String where  = "sys_dict_line.dict_id="+SqlFormater.TO_STRING(dictValue);
			dict.setWhere(where);
			dict.setOrderBy("sys_dict_line.seq_no,sys_dict_line.value");
			return dict;
		}
		else if (dictId.startsWith("table:")){
			//用户表引用字典
			String tableName = dictId.substring(6);
			String columnName = null;
			if (tableName.indexOf(".")>=0){
				columnName = tableName.substring(tableName.indexOf(".")+1);
				tableName = tableName.substring(0,tableName.indexOf("."));
			}
			return DictManager.createTableDict(tableName, columnName);
		}
		//引用指定表与列的字典
		else if (dictId.startsWith("ref:")){
			String tableName = dictId.substring(4);
			String columnName = null;
			if (tableName.indexOf(".")>=0){
				columnName = tableName.substring(tableName.indexOf(".")+1);
				tableName = tableName.substring(0,tableName.indexOf("."));
				String refDictId = DictManager.getDictId(tableName, columnName);
				if (refDictId==null||refDictId.length()==0){
					throw new RuntimeException(tableName+"."+ columnName+" has no reference");
				}
				if (refDictId.equals(dictId)){
					throw new RuntimeException("Reference dict cycle loop: " + dictId);
				}
				return loadDict(refDictId);
			}
			else
				throw new RuntimeException("Invalid dictId " + dictId);
			
		}
		else {
			//程序定义字典
			Dict dict = getRuntimeDict(dictId);
			if (dict!=null)
				return dict;
			//xml字典
			return loadDictFromFile(dictId);
		}
	}
	
	private static Dict loadDictFromFile(String dictId){
		try {
			InputStream in = Dict.class.getResourceAsStream("/kui/dict/" + idToPath(dictId)+".xml");
			if (in==null)
				throw new RuntimeException("缺少字典定义文件：" + idToPath(dictId)+".xml");
			try {
				return XmlUtil.unmarshal(Dict.class, in);
			}finally {
				IOUtils.closeQuietly(in);
			}
		}catch (RuntimeException e){
			throw e;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static TreeDict getTreeDict(String dictId){
		return getTreeDict(dictId, false);
	}

	public static TreeDict getTreeDict(String dictId,boolean reload){
		if (dictId==null||dictId.length()==0)
			throw new RuntimeException("Null dict id");
		TreeDict dict = null;
		Cache cache= SpringUtil.getCache("ui.treeDict");
		if (!reload){
			ValueWrapper obj = cache.get(dictId);
			dict = obj!=null?(TreeDict)obj.get():null;
		}
		if (dict==null){
			dict = loadTreeDict(dictId);
			cache.put(dictId, dict);
		}
		return dict;
		
	}

	public static TreeDict loadTreeDict(String dictId) {
		if (dictId==null||dictId.length()==0)
			throw new RuntimeException("Null dict id");
		else if (dictId.startsWith("table:")){
			//table dict;
			String tableName = dictId.substring(6);
			if (tableName.indexOf(".")>=0){
				tableName = tableName.substring(0,tableName.indexOf("."));
			}
			
			Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(tableName);
			if (entityClass==null){
				throw new RuntimeException("No entity class found for " + tableName);
			}
			BaseEntity entity = BaseEntity.newInstance(entityClass);
			if (!(entity instanceof ITreeEntity)){
				throw new RuntimeException("Entity is not a tree entity: " + entityClass.getName());
			}
			TreeConfig tc = ((ITreeEntity)entity).getTreeConfig();
			if (tc==null){
				throw new RuntimeException("Entity has no tree config: " + entityClass.getName());
			}
			TreeDict dict = new TreeDict();
			//dict.setId(dictId);
			dict.setIdField(tc.getIdField());
			dict.setNameField(tc.getDisplayField());
			dict.setFrom(tableName);
			dict.setParentField(tc.getParentField());
			dict.setLeftOrderField(tc.getLeftNoField());
			dict.setRightOrderField(tc.getRightNoField());
			dict.setLeafField(tc.getLeafField());
			return dict;
		}
		//引用指定表与列的字典
		else if (dictId.startsWith("ref:")){
			String tableName = dictId.substring(4);
			String columnName = null;
			if (tableName.indexOf(".")>=0){
				columnName = tableName.substring(tableName.indexOf(".")+1);
				tableName = tableName.substring(0,tableName.indexOf("."));
				String refDictId = DictManager.getDictId(tableName, columnName);
				if (refDictId==null){
					throw new RuntimeException(tableName+"."+ columnName+" has no reference");
				}
				return loadTreeDictFromFile(refDictId);
			}
			else
				throw new RuntimeException("Invalid dictId " + dictId);
		}
		else {
			//程序定义字典
			Dict dict = getRuntimeDict(dictId);
			if (dict!=null){
				if (Dict.Type.table!=dict.getType())
					throw new RuntimeException("Dict type is not table: " + dictId);
				Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(dict.getFrom());
				if (entityClass==null){
					throw new RuntimeException("No entity class found for " + dict.getFrom());
				}
				if (!ITreeEntity.class.isAssignableFrom(entityClass)){
					throw new RuntimeException("Entity class is not tree entity: " + entityClass.getName());
				}
				BaseEntity entity = BaseEntity.newInstance(entityClass);
				if (!(entity instanceof ITreeEntity)){
					throw new RuntimeException("Entity is not a tree entity: " + entityClass.getName());
				}
				TreeConfig tc = ((ITreeEntity)entity).getTreeConfig();
				if (tc==null){
					throw new RuntimeException("Entity has no tree config: " + entityClass.getName());
				}
				TreeDict tdict = new TreeDict();
				//dict.setId(dictId);
				tdict.setIdField(tc.getIdField());
				tdict.setNameField(tc.getDisplayField());
				tdict.setFrom(dict.getFrom());
				tdict.setParentField(tc.getParentField());
				tdict.setLeftOrderField(tc.getLeftNoField());
				tdict.setRightOrderField(tc.getRightNoField());
				tdict.setLeafField(tc.getLeafField());
				return tdict;
			}
			else {
				//xml字典
				return loadTreeDictFromFile(dictId);
			}
		}
	}	
	
	private static TreeDict loadTreeDictFromFile(String dictId){
		try {
			InputStream in = Dict.class.getResourceAsStream("/kui/dict/" + idToPath(dictId)+".xml");
			if (in==null)
				throw new RuntimeException("缺少字典定义文件：" + idToPath(dictId)+".xml");
			try {
				TreeDict tdict = XmlUtil.unmarshal(TreeDict.class, in);
				return tdict;
			}finally {
				IOUtils.closeQuietly(in);
			}
		}catch (RuntimeException e){
			throw e;
		}catch (Exception e){
			throw new RuntimeException(e);
		}
	}
	
	public static String getDisplaySql(String dictId,String columnName) {
		Dict dict = getDict(dictId);
		if (Dict.Type.list==dict.getType()){
			StringBuffer sql = new StringBuffer();
			if (dict.getItems()!=null&&dict.getItems().size()>0){
				sql.append("CASE " + columnName);
				for (DictItem dv: dict.getItems()){
					sql.append(" WHEN '"+dv.getValue()+"' THEN '"+dv.getDisplay()+"'");
				}
				sql.append(" ELSE '' END");
				return sql.toString();
			}else{
				//No item defined
				return columnName;
			}
		}
		else {
			StringBuffer sql = new StringBuffer();
			String nameSql = dict.getDisplaySql();
			if (nameSql==null)
				throw new RuntimeException("No name field for dict: " + dictId);
			sql.append("SELECT ").append(nameSql);
			sql.append(" FROM ").append(dict.getFrom());
			if (dict.getWhere()!=null && dict.getWhere().length()>0){
				sql.append(" WHERE ").append(dict.getWhere());
			}
			
			SelectSqlParser sp = new SelectSqlParser(sql.toString());
			String tableAlias = sp.getFirstTableAlias();
			if (tableAlias==null||tableAlias.length()==0)
				throw new RuntimeException("No table alias for dict: "+ dictId);
			sp.changeTableAlias(tableAlias, tableAlias+"$", true);
			StringBuffer newSql = new StringBuffer(sp.getSql());
			if (dict.getWhere()!=null && dict.getWhere().length()>0){
				newSql.append( " AND ");
			}
			else
				newSql.append(" WHERE ");
			newSql.append(tableAlias+"$" + "." + dict.getIdField()).append("=").append(columnName);
			return newSql.toString();
		}		
	}	
	
	public static List<IdNamePair> getDictList(String dictId){
		return getDictList(dictId, null, null,null);
	}

	public static List<IdNamePair> getDictList(String dictId, String dataAccessLevel){
		return getDictList(dictId, null, dataAccessLevel,null);
	}
	
	public static List<IdNamePair> getDictList(String dictId, String validation, String dataAccessLevel,Map<String, Object> env){
		Dict dict = getDict(dictId);
		if (dict==null)
			throw new RuntimeException("Dict not found: " + dictId);
		if (Dict.Type.list==dict.getType()){
			//list
			List<IdNamePair> rst = new ArrayList<IdNamePair>();
			for (DictItem dv : dict.getItems()){
				IdNamePair p = new IdNamePair(dv.getValue(),dv.getDisplay());
				rst.add(p);
			}
			return rst;
		}
		else {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append(dict.getIdField()).append(",").append(dict.getDisplaySql());
			sql.append(" FROM ").append(dict.getFrom());
			StringBuffer where = new StringBuffer();
			if (dict.getWhere()!=null)
				where.append(dict.getWhere());
			Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(dict.getFrom());
			if (entityClass!=null){
				EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
				if (entityTable!=null && entityTable.getEntityColumnByColumn("is_enabled")!=null){
					if (where.length()>0)
						where.append(" AND ");
					where.append(dict.getFrom()+"."+"is_enabled=1");
				}
			}
			if (validation!=null && validation.length()>0){
				if (where.length()>0)
					where.append( " AND ");
				where.append(validation);
			}
			if (where.length()>0){
				sql.append(" WHERE ").append(where);
			}
			if (dict.getOrderBy()!=null && dict.getOrderBy().length()>0){
				sql.append(" ORDER BY ").append(dict.getOrderBy());
			}
			else {
				sql.append(" ORDER BY 1");
			}
			WindowEnv windowEnv = new WindowEnv();
			if (env!=null)
				windowEnv.getEnv().putAll(env);
			String finalSql = windowEnv.parse(sql.toString(), false, "NULL", new DBDateFormater());
			if (finalSql==null||finalSql.length()==0)
				throw new RuntimeException("Parse sql failed: " + sql.toString());
			String finalSql1 = null;
			finalSql1 = finalSql;
			SqlRowSet rs = DbUtil.query(finalSql1);
			List<IdNamePair> rst = new ArrayList<IdNamePair>();
			while(rs.next()){
				IdNamePair p = new IdNamePair(rs.getString(1), rs.getString(2));
				rst.add(p);
			}
			return rst;
		}
	}

	
	public static List<TreeNode> getTreeDictList(String dictId, String validation, String treeValidation,String node, String dataAccessLevel,Map<String, Object> env){
		TreeDict dict = getTreeDict(dictId);
		if (dict==null)
			throw new RuntimeException("Dict not found: " + dictId);
		if (Dict.Type.table!=dict.getType())
			throw new RuntimeException("Dict type is not table!");
		WindowEnv windowEnv = new WindowEnv();
		if (env!=null)
			windowEnv.getEnv().putAll(env);
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(dict.getIdField()).append(",").append(dict.getDisplaySql());
		//sql.append(",").append(dict.getLeftField()).append(",").append(dict.getRightField()).append(",").append(dict.getParentField());
		sql.append(",").append(dict.getLeafField());
		sql.append(" FROM ").append(dict.getFrom());
		StringBuffer where = new StringBuffer();
		if (dict.getWhere()!=null)
			where.append(dict.getWhere());
		if (treeValidation!=null&&treeValidation.length()>0){
			if (where.length()>0)
				where.append(" and ");
			where.append(treeValidation);
		}
		if (validation!=null && validation.length()>0){
			StringBuffer subSql = new StringBuffer();
			subSql.append("SELECT ").append("1");
			subSql.append(" FROM ").append(dict.getFrom()).append(" sub");
			StringBuffer subWhere = new StringBuffer();
			if (dict.getWhere()!=null && dict.getWhere().length()>0)
				subWhere.append(StringUtils.replace(dict.getWhere(), dict.getFrom()+".", "sub."));
			if (subWhere.length()>0)
				subWhere.append(" AND ");
			subWhere.append(StringUtils.replace(validation, dict.getFrom()+".", "sub."));
			subWhere.append(" AND (")
			.append("sub." + dict.getLeftOrderField()+">="+dict.getFrom()+"."+dict.getLeftOrderField())
			.append(" AND ")
			.append("sub." + dict.getRightOrderField()+"<="+dict.getFrom()+"."+dict.getRightOrderField())
			.append(")");
			if (subWhere.length()>0){
				subSql.append(" WHERE ").append(subWhere);
			}
			//必须是满足Validation条件节点的父节点
			if (where.length()>0){
				where.append( " AND ");
			}
			where.append(" EXISTS (").append(subSql.toString()).append(")");
		}

		Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(dict.getFrom());
		if (entityClass!=null){
			EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
			if (entityTable!=null && entityTable.getEntityColumnByColumn("is_enabled")!=null){
				if (where.length()>0)
					where.append(" AND ");
				where.append(dict.getFrom()+"."+"is_enabled=1");
			}
		}
		if (where.length()>0)
			where.append( " AND ");
		if (node==null||node.length()==0||"root".equals(node)){
			//root node
			where.append(dict.getParentField()).append(" IS NULL");
		}
		else {
			where.append(dict.getParentField()).append("=").append(SqlFormater.TO_STRING(node));
		}
		
		if (where.length()>0){
			sql.append(" WHERE ").append(where);
		}
		sql.append(" ORDER BY ").append(dict.getLeftOrderField()); //只能按树状排序
		String finalSql = windowEnv.parse(sql.toString(), false, "NULL", new DBDateFormater());
		if (finalSql==null||finalSql.length()==0)
			throw new RuntimeException("Parse sql failed: " + sql.toString());
		String finalSql1 =finalSql;
		SqlRowSet rs = DbUtil.query(finalSql1);
		List<TreeNode> rst = new ArrayList<TreeNode>();
		while(rs.next()){
			TreeNode p = new TreeNode();
			p.setId(rs.getString(1));
			p.setText(rs.getString(2));
			p.setLeaf(rs.getBoolean(3));
			rst.add(p);
		}
		return rst;
	}
	
	public static String translate(Class<? extends BaseEntity> entityClass,String propertyName, String value){
		return translate(entityClass, propertyName, value,null);
	}
	
	public static String translate(Class<? extends BaseEntity> entityClass,String propertyName, String value, Map<String, Object> env){
		if (value==null||value.length()==0)
			return "";
		String dictId = getDictId(entityClass, propertyName);
		if (dictId==null){
			throw new RuntimeException("No dict config for " + entityClass.getName()+"."+propertyName);
		}
		IdNamePair p = getDictDisplay(dictId, value, env);
		return p!=null?p.getName():null;
	}

	
	//将字典值翻译为名称
	public static String translate(String dictId, String value, Map<String, Object> env){
		if (value==null||value.length()==0)
			return "";
		IdNamePair p = getDictDisplay(dictId, value, env);
		return p!=null?p.getName():null;
	}
	
	public static IdNamePair getMultiComboDictDisplay(String dictId, String values, Map<String, Object> env){
		if (values==null||values.length()==0)
			return null;
		String[] valueList = StringUtil.split(values, Field.Multi_Combo_Seperator);
		if (valueList==null||valueList.length==0)
			return null;
		StringBuffer rst = new StringBuffer();
		for (String value : valueList){
			if (rst.length()>0)
				rst.append(",");
			IdNamePair ip = getDictDisplay(dictId, value, env);
			if (ip!=null){
				rst.append(ip.getName());
			}
		}
		return new IdNamePair(values,rst.toString());
	}
	
	public static IdNamePair getTreeDictDisplay(String dictId, String value, Map<String, Object> env){
		if (value==null||value.length()==0)
			return null;
		//process parent name
		TreeDict dict = getTreeDict(dictId);
		if (dict==null)
			throw new RuntimeException("Tree dict not found: " + dictId);
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT ").append(dict.getDisplaySql()).append(",").append(dict.getLeftOrderField()).append(",").append(dict.getRightOrderField())
		.append( " FROM ").append(dict.getFrom()).append(" " ).append(dict.getTableAlias())
		.append(" WHERE ").append(dict.getTableAlias()).append(".").append(dict.getIdField()).append("=?");
		SqlRowSet rs = DbUtil.query(sql.toString(), value);
		if (!rs.next()){
			return null;
		}
		String display = rs.getString(1);
		int leftOrder = rs.getInt(2);
		int rightOrder = rs.getInt(3);
		
		StringBuffer parentSql = new StringBuffer();
		parentSql.append("SELECT ").append(dict.getDisplaySql()).append( " FROM ").append(dict.getFrom()).append(" " ).append(dict.getTableAlias())
		.append(" WHERE ").append(dict.getTableAlias()).append(".").append(dict.getLeftOrderField()).append("<?")
		.append(" AND ").append(dict.getTableAlias()).append(".").append(dict.getRightOrderField()).append(">?");
		if (dict.getWhere()!=null && dict.getWhere().length()>0){
			parentSql.append(" AND (").append(dict.getWhere()).append(")");
		}
		parentSql.append(" ORDER BY ").append(dict.getTableAlias()).append(".").append(dict.getLeftOrderField());
		
		SqlRowSet parentRs = DbUtil.query(parentSql.toString(), leftOrder,rightOrder);
		while(parentRs.next()){
			display = parentRs.getString(1)+Field.Tree_Combo_Seperator + display;
		}
		return new IdNamePair(value,display);
	}

	public static IdNamePair getDictDisplay(String dictId, String displayType, String value, Map<String, Object> env){
		if (Field.DisplayType_MultiComboBox.equals(displayType))
			return getMultiComboDictDisplay(dictId, value, env);
		else if (Field.DisplayType_TreeComboBox.equals(displayType))
			return getTreeDictDisplay(dictId, value, env);
		else
			return getDictDisplay(dictId, value, env);
	}
	
	public static IdNamePair getDictDisplay(String dictId, String value, Map<String, Object> env){
		Dict dict = getDict(dictId);
		if (dict==null)
			throw new RuntimeException("Dict not found: " + dictId);
		if (Dict.Type.list==dict.getType()){
			for (DictItem dv : dict.getItems()){
				if (dv.getValue()!=null&&dv.getValue().equals(value)){
					return new IdNamePair(dv.getValue(),dv.getDisplay());
				}
			}
			return null;
		}
		else {
			StringBuffer sql = new StringBuffer();
			sql.append("SELECT ").append(dict.getIdField()).append(",").append(dict.getDisplaySql());
			sql.append(" FROM ").append(dict.getFrom());
			StringBuffer where = new StringBuffer();
			if (dict.getWhere()!=null&&dict.getWhere().length()>0)
				where.append(dict.getWhere());
			if (where.length()>0)
					where.append( " AND ");
			where.append(dict.getTableAlias()+"."+dict.getIdField()).append("=").append(SqlFormater.TO_STRING(value));
			sql.append(" WHERE ").append(where);
			WindowEnv windowEnv = new WindowEnv();
			if (env!=null)
				windowEnv.getEnv().putAll(env);
			String finalSql = windowEnv.parse(sql.toString(), false, "NULL", new DBDateFormater());
			if (finalSql==null||finalSql.length()==0)
				throw new RuntimeException("Parse sql failed: " + sql.toString());
			//字典值翻译不做权限控制
			//String finalSql1 = SqlAccessUtil.addAccess(finalSql, false,Tab.DataAccessLevel_Auth);
			SqlRowSet rs = DbUtil.query(finalSql);
			if(rs.next()){
				IdNamePair p = new IdNamePair(rs.getString(1), rs.getString(2));
				return p;
			}
			else
				return null;
		}
	}

	public static void registerDict(String dictId , Dict dc){
		runtimeDicts.put(dictId,dc);
	}

	//对指定字典，注册字典值
	public static void registerDictItem(String dictId , DictItem item){
		Dict dict = runtimeDicts.get(dictId);
		if (dict==null){
			dict = new Dict();
			dict.setType(Dict.Type.list);
			runtimeDicts.put(dictId, dict);
		}
		dict.checkAndAddItem(item);
	}
		
	/**
	 * 获得系统预定义字典
	 * @param dictId
	 * @return
	 */
	public static Dict getRuntimeDict(String dictId){
		Dict dict = runtimeDicts.get(dictId);
		return dict;
	}

	public static String getDictId(String tableName,String columnName){
		Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(tableName);
		if (entityClass==null){
			throw new RuntimeException("No EntityClass for " + tableName);
		}
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		if (table==null)
			throw new RuntimeException("No EntityTable for " + entityClass.getName());
		EntityColumn column = table.getEntityColumnByColumn(columnName);
		if (column==null)
			throw new RuntimeException("No column " + columnName + " in " + tableName);
		ColumnRef cr = column.getColumnRef();
		if (cr==null){
			return null;
			//throw new RuntimeException("Column " + (tableName+"." + columnName) + " has no reference!");
		}
		else if (cr.getDict()!=null&&cr.getDict().length()>0){
			return cr.getDict();
		}
		else if (cr.getTable()!=null&&cr.getTable().length()>0
				&& cr.getColumn()!=null&&cr.getColumn().length()>0){
			return "table:"+cr.getTable()+"."+cr.getColumn();
		}
		else
			throw new RuntimeException("Column " + (tableName+"." + columnName) + "'s reference is invalid!");
	}

	public static String getDictId(Class<? extends BaseEntity> entityClass,String propertyName){
		EntityTable table = EntityHelper.getEntityTable(entityClass);
		if (table==null)
			throw new RuntimeException("No EntityTable for " + entityClass.getName());
		EntityColumn column = table.getEntityColumnByProperty(propertyName);
		if (column==null)
			throw new RuntimeException("Property not found!");
		ColumnRef cr = column.getColumnRef();
		if (cr==null){
			return null;
			//throw new RuntimeException("Column " + (tableName+"." + columnName) + " has no reference!");
		}
		else if (cr.getDict()!=null&&cr.getDict().length()>0){
			return cr.getDict();
		}
		else if (cr.getTable()!=null&&cr.getTable().length()>0
				&& cr.getColumn()!=null&&cr.getColumn().length()>0){
			return "table:"+cr.getTable()+"."+cr.getColumn();
		}
		else
			throw new RuntimeException((entityClass.getName()+"." + propertyName) + "'s reference is invalid!");
	}	
	
	public static Dict createTableDict(String tableName,String column){
		Class<? extends BaseEntity> entityClass = EntityHelper.getEntityClass(tableName);
		if (entityClass==null){
			throw new RuntimeException("No entity class found for " + tableName);
		}
		BaseEntity entity = BaseEntity.newInstance(entityClass);
		String[] displayColumns = entity.getDisplayColumns();
		if (displayColumns==null||displayColumns.length==0){
			throw new RuntimeException("Entity has no display column : " + entityClass.getName());
		}
		Dict dict = new Dict();
		if (column!=null&&column.length()>0){
			dict.setIdField(column);
		}
		else {
			Set<EntityColumn> keyColumnSet = entity.getEntityTable().getEntityClassPKColumns();
			if (keyColumnSet==null||keyColumnSet.size()==0)
				throw new RuntimeException("Entity has no key column : " + entityClass.getName());
			
			if (keyColumnSet.size()>1)
				throw new RuntimeException("Entity has multiple key column : " + entityClass.getName());
			
			EntityColumn[] keyColumns = keyColumnSet.toArray(new EntityColumn[1]);
			dict.setIdField(keyColumns[0].getColumn());
		}
		StringBuffer str = new StringBuffer();
		for (String displayColumn: displayColumns){
			if (str.length()>0)
				str.append(",");
			str.append(displayColumn);
//			String dictId = DictManager.getDictId(tableName, displayColumn);
//			if (dictId!=null&&dictId.length()>0){
//				str.append("("+getDisplaySql(dictId, tableName+"."+displayColumn)+")");
//			}
//			else{
//				str.append(displayColumn);
//			}
		}
		dict.setNameField(str.toString());
		dict.setFrom(tableName);
		dict.setOrderBy("2");
		return dict;
	}
}
