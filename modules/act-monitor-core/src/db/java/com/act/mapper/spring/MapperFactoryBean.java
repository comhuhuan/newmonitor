package com.act.mapper.spring;

import com.act.mapper.mapperhelper.MapperHelper;

/**
 * 增加mapperHelper
 *
 * @param <T>
 * @author liuzh
 */
public class MapperFactoryBean<T> extends org.mybatis.spring.mapper.MapperFactoryBean<T> {

    private MapperHelper mapperHelper;

    /**
     * {@inheritDoc}
     */
    @Override
    protected void checkDaoConfig() {
        super.checkDaoConfig();
        //在MapperFactorBean在jar中引用时，DaoClass在Spring 中的class与Class.forName出来的不一样，
        //故对DaoClass不能采用Class.forName
        //BaseEntity.registerDaoClass(getObjectType().getName(),getObjectType());
        //通用Mapper
        if (mapperHelper.isExtendCommonMapper(getObjectType())) {
            mapperHelper.processConfiguration(getSqlSession().getConfiguration(), getObjectType());
        }
        else {
        	System.out.println("No dao:" + getObjectType().getName());
        }
    }

    public void setMapperHelper(MapperHelper mapperHelper) {
        this.mapperHelper = mapperHelper;
    }
}
