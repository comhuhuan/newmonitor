/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.act.mapper.provider.base;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.scripting.xmltags.*;

import com.act.framework.entity.BaseEntity;
import com.act.mapper.entity.EntityColumn;
import com.act.mapper.mapperhelper.EntityHelper;
import com.act.mapper.mapperhelper.MapperHelper;
import com.act.mapper.mapperhelper.MapperTemplate;
import com.kpr.kui.definition.TreeConfig;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * BaseUpdateProvider实现类，基础方法实现类
 *
 * @author liuzh
 */
public class BaseUpdateProvider extends MapperTemplate {

    public BaseUpdateProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    /**
     * 通过主键更新全部字段
     *
     * @param ms
     */
    public SqlNode updateByPrimaryKey(MappedStatement ms) {
		try {
	        Class<? extends BaseEntity> entityClass = getSelectReturnType(ms);
			BaseEntity entityInstance = (BaseEntity)entityClass.newInstance();
	        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
	        //update table
	        sqlNodes.add(new StaticTextSqlNode("UPDATE " + tableName(entityClass)));
	        //获取全部列
	        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
	        EntityColumn versionColumn = null;
	        List<SqlNode> ifNodes = new LinkedList<SqlNode>();
	        for (EntityColumn column : columnList) {
	            if (!column.isId()) {
	                ifNodes.add(new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, "));
	            }
	        }
	        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));
	        //获取全部的主键的列
	        columnList = EntityHelper.getPKColumns(entityClass);
	        if (columnList.size()==0)
	        	throw new RuntimeException("No primary key column defined for " + entityClass.getName());
	        List<SqlNode> whereNodes = new LinkedList<SqlNode>();
	        boolean first = true;
	        //where 主键=#{property} 条件
	        for (EntityColumn column : columnList) {
	            whereNodes.add(getColumnEqualsProperty(column, first));
	            first = false;
	        }
	        if (versionColumn!=null){
	            whereNodes.add(getColumnEqualsProperty(versionColumn, false));
	        }
	        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
    }

    /**
     * 通过主键更新不为null的字段
     *
     * @param ms
     * @return
     */
    public SqlNode updateByPrimaryKeySelective(MappedStatement ms) {
        Class<? extends BaseEntity> entityClass = getSelectReturnType(ms);
        List<SqlNode> sqlNodes = new LinkedList<SqlNode>();
        //update table
        sqlNodes.add(new StaticTextSqlNode("UPDATE " + tableName(entityClass)));
        //获取全部列
        Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
        EntityColumn versionColumn = null;
        List<SqlNode> ifNodes = new LinkedList<SqlNode>();
        //全部的if property!=null and property!=''
        for (EntityColumn column : columnList) {
            if (!column.isId()) {
                if ("entityVersion".equals(column.getProperty())){
                	versionColumn = column;
                	ifNodes.add(new StaticTextSqlNode(column.getColumn() + " =" + column.getColumn() + "+1, "));
                }
                else{
                    StaticTextSqlNode columnNode = new StaticTextSqlNode(column.getColumn() + " = #{" + column.getProperty() + "}, ");
                    ifNodes.add(getIfNotNull(column, columnNode));
                }
            }
        }
        sqlNodes.add(new SetSqlNode(ms.getConfiguration(), new MixedSqlNode(ifNodes)));
        //获取全部的主键的列
        columnList = EntityHelper.getPKColumns(entityClass);
        if (columnList.size()==0)
        	throw new RuntimeException("No primary key column defined for " + entityClass.getName());
        List<SqlNode> whereNodes = new LinkedList<SqlNode>();
        boolean first = true;
        //where 主键=#{property} 条件
        for (EntityColumn column : columnList) {
            whereNodes.add(getColumnEqualsProperty(column, first));
            first = false;
        }
        if (versionColumn!=null){
            whereNodes.add(getColumnEqualsProperty(versionColumn, false));
        }
        sqlNodes.add(new WhereSqlNode(ms.getConfiguration(), new MixedSqlNode(whereNodes)));
        return new MixedSqlNode(sqlNodes);
    }
}
