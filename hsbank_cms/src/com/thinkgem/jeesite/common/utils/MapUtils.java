package com.thinkgem.jeesite.common.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by pc on 2016/5/13.
 */
public class MapUtils {
    public static Map<String,Object> keyValsToMap(String[] dataKeys, Object[] dataVals){
        Map<String,Object> data = new LinkedHashMap<String, Object>();
        if(dataKeys != null && dataVals != null && dataVals.length == dataKeys.length){

            for(int i = 0; i < dataKeys.length; i++){
                data.put(dataKeys[i], dataVals[i]);
            }
        }

        return data;
    }

    public static Map<String, Object> bean2map(Object obj) {
        if(obj == null){
            return null;
        }
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                // 过滤class属性
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object value = null;
                    try {
                        value = getter.invoke(obj);
                    }catch (Exception e){}


                    map.put(key, value);
                }

            }
        } catch (Exception e) {
            System.out.println("bean2map Error " + e);
        }
        return map;
    }
}
