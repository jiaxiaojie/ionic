package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.utils.Reflections;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;

/**
 * Created by 万端瑞 on 2016/6/3.
 */
public class ObjectUtils {
    public static void copyFields(Object rtuObject, Object object){
        Class classType = object.getClass();
        Class rtuClassType = rtuObject.getClass();


        Field fields[] =getAllFileds(classType);
        classType.getSuperclass();

        for(int i=0;i<fields.length;i++){
            Field field = fields[i];
            String fieldName = field.getName();
            Object value = Reflections.getFieldValue(object,fieldName);
            Reflections.setFieldValue(rtuObject,fieldName,value);
        }
    }

    /**
     * 获得class所有字段对象
     * @param clazz
     * @return
     */
    private static Field[] getAllFileds(Class clazz){
        Field[] fields =clazz.getDeclaredFields();
        if(clazz != Object.class){
            Field[] parentFields = getAllFileds(clazz.getSuperclass());
            fields = ArrayUtils.addAll(fields,parentFields);
        }

        return fields;
    }
}
