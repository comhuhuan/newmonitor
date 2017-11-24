package com.act.framework.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;

import org.apache.commons.beanutils.PropertyUtils;

@SuppressWarnings("rawtypes")
public class PropertyUtil {
	private static HashMap<String,HashMap<String,PropertyDescriptor>> fieldMaps = new HashMap<String,HashMap<String,PropertyDescriptor>> ();
	private static HashMap<String,PropertyDescriptor> getPropertyMap(Class clazz){
		HashMap<String,PropertyDescriptor> map = fieldMaps.get(clazz.getName());
		if (map==null){
			PropertyDescriptor[] fields = PropertyUtils.getPropertyDescriptors(clazz);
			map = new HashMap<String,PropertyDescriptor> ();
			for (PropertyDescriptor field: fields){
				map.put(field.getName(), field);
			}
			synchronized(fieldMaps){
				fieldMaps.put(clazz.getName(), map);
			}
		}
		return map;
	}
	private static PropertyDescriptor getPropertyDescriptor(Class clazz, String propertyName){
		HashMap<String,PropertyDescriptor> map = getPropertyMap(clazz);
		return map.get(propertyName);
	}
	
	public static boolean hasProperty(Class clazz, String propertyName){
		PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
		return pd!=null;
	}

	
	public static Class getPropertyType(Class clazz, String propertyName){
		PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
		if (pd==null)
			throw new RuntimeException("Property " + propertyName + " not found!");
		else
			return pd.getPropertyType();
	}

	public static Method getPropertyWriteMethod(Class clazz, String propertyName){
		PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
		if (pd==null)
			throw new RuntimeException("Property " + propertyName + " not found!");
		else
			return pd.getWriteMethod();
	}

	public static Method getPropertyReadMethod(Class clazz, String propertyName){
		PropertyDescriptor pd = getPropertyDescriptor(clazz, propertyName);
		if (pd==null)
			throw new RuntimeException("Property " + propertyName + " not found!");
		else
			return pd.getReadMethod();
	}
}
