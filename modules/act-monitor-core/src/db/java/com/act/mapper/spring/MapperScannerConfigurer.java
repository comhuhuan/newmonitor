package com.act.mapper.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import com.act.mapper.entity.Config;
import com.act.mapper.mapperhelper.MapperHelper;
import com.act.mapper.util.StringUtil;

import java.util.Properties;


public class MapperScannerConfigurer extends org.mybatis.spring.mapper.MapperScannerConfigurer {
    private MapperHelper mapperHelper;

    /**
     * 属性注入
     *
     * @param properties
     */
    public void setProperties(Properties properties) {
        mapperHelper = new MapperHelper();
        mapperHelper.setProperties(properties);
    }

    /**
     * Config方式注入
     *
     * @param config
     */
    public void setConfig(Config config) {
        mapperHelper = new MapperHelper();
        mapperHelper.setConfig(config);
    }

    /**
     * 注册完成后，对MapperFactoryBean的类进行特殊处理
     *
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) {
        super.postProcessBeanDefinitionRegistry(registry);
        if (mapperHelper == null) {
            mapperHelper = new MapperHelper();
        }
        String[] names = registry.getBeanDefinitionNames();
        GenericBeanDefinition definition;
        for (String name : names) {
            BeanDefinition beanDefinition = registry.getBeanDefinition(name);
            if (beanDefinition instanceof GenericBeanDefinition) {
                definition = (GenericBeanDefinition) beanDefinition;
                if (StringUtil.isNotEmpty(definition.getBeanClassName())
                        && definition.getBeanClassName().equals("org.mybatis.spring.mapper.MapperFactoryBean")) {
                    definition.setBeanClass(MapperFactoryBean.class);
                    definition.getPropertyValues().add("mapperHelper", this.mapperHelper);
                }
            }
        }
    }
    
    /**
     * Add by James Zhao
     * @return
     */
    public MapperHelper getMapperHelper() {
    	return mapperHelper;
    }
}