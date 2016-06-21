package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.utils.Reflections;
import com.thinkgem.jeesite.common.utils.StringUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public class ValueObjectUtils {
    /**
     * 通过列名获得对象的字段值，对象可以是map类型或javabean
     * @param dataItem
     * @param columnName
     * @return
     */
    public static Object getValueByColumnName(Object dataItem, String columnName){
        Object result = null;
        try{
            if(dataItem instanceof Map){
                result = ((Map<String,Object>)dataItem).get(columnName);
            }
            else {
                result = Reflections.invokeGetter(dataItem,columnName);
            }
        }
        catch (Exception e){
            //throw new RuntimeException("读取数据失败，通过列名："+columnName);
        }

        return result;
    }

    /**
     * 通过列名向dataObj对象写入字段值，dataObj对象可以是map类型或javabean
     * @param dataObj
     * @param columnName
     * @param val
     */
    public static void putValueByColumnName(Object dataObj, String columnName,Object val){
        try{
            if(dataObj instanceof Map){
                ((Map<String,Object>)dataObj).put(columnName,val);
            }
            else {
                Reflections.invokeSetter(dataObj,columnName,val);
            }
        }
        catch (Exception e){
            e.printStackTrace();
            throw new RuntimeException("读取put失败，通过列名："+columnName);
        }
    }


    /**
     * 通过path向数据对象的根节点写入值
     * @param dataObj
     * @param path
     * @param val
     */
    public static void putWithPath(Object dataObj, String path,Object val){
        if(path == null || "".equals(path.trim())){
            return;
        }

        String[] nodes = getPathNodes(path);
        Object currentDataObj = dataObj;
        int i = 0;
        for(; i < nodes.length-1; i++){
            Object value = getValueByColumnName(currentDataObj,nodes[i]);
            //如果节点不存在则创建
            if(value == null){
                value = (Object)new LinkedHashMap<String,Object>();
                putValueByColumnName(currentDataObj,nodes[i],value);
            }

            currentDataObj = value;
        }
        putValueByColumnName(currentDataObj,nodes[i],val);
    }



    /**
     * 获得path的节点集合
     * @param path
     * @return
     */
    private static String[] getPathNodes(String path){
        return (path.indexOf(".") != -1? StringUtils.split(path,"."):new String[]{path});
    }

    /**
     * 通过类似el表达式的路径字符串获得map中某一层的值
     * @param dataItem
     * @param path
     * @return
     */
    public static Object getValueByPath(Map<String,Object> dataItem, String path){
        Object result = null;
        try{
            String[] columns = getPathNodes(path);

            if(columns.length > 0){
                Object currentDataItem = dataItem;
                int i;
                for(i = 0; i < columns.length-1; i++){

                    currentDataItem = ValueObjectUtils.getValueByColumnName(currentDataItem,columns[i]);


                }
                result = ValueObjectUtils.getValueByColumnName(currentDataItem,columns[i]);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

}
