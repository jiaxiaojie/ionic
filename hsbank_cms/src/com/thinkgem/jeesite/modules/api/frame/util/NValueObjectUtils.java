package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.utils.*;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.exception.APIException;
import com.thinkgem.jeesite.modules.api.frame.format.convert.ConvertorServiceFactory;
import com.thinkgem.jeesite.modules.api.frame.util.expression.path.PathNode;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by pc on 2016/6/12.
 */
public class NValueObjectUtils {
    /**
     * 将基本数据转换为指定类型
     * @param data
     * @param clazz
     * @return
     */
    public static Object doCovertValueData(Object data, Class clazz){
        Object result = data;
        try {
            if(data != null){

                if(clazz!=null && clazz != data.getClass()){
                    if(clazz == String.class){
                        if(data instanceof Date){
                            Date dateCur = (Date)data;
                            String dateFormat = "yyyy-MM-dd HH:mm:ss";
                            if("00:00:00".equals(DateUtils.formatDate(dateCur, "HH:mm:ss"))){
                                dateFormat = "yyyy-MM-dd";
                            }
                            result = DateUtils.formatDate((Date)data,dateFormat);
                        }else{
                            result =  StringUtils.toStr(data);
                        }
                    }
                    else if(clazz == Double.class){
                        if(data instanceof Double){
                            result = NumberUtils.formatNotRoundWithScale( (Double)data,2);
                        }if(data instanceof String){
                            result = new Double(NumberUtils.formatNotRoundWithScale((String)data,2));
                        }
                        else{
                            result = NumberUtils.formatNotRoundWithScale(Double.parseDouble(StringUtils.toStr(data)),2);
                        }
                    }
                    else if(clazz == Integer.class){
                        result = Integer.valueOf(StringUtils.toStr(data));
                    }else if(clazz == Long.class){
                        result = Long.valueOf(StringUtils.toStr(data));
                    }else{//否则使用spring类型转换器进行转换
                        result = ConvertorServiceFactory.getConvertService().covert(data,clazz);
                    }
                }
            }
        }catch (Exception e){
            throw new APIException("节点值类型转换失败");
        }
        return result;
    }

    /**
     * 将data转换成map
     * @param dataObject
     * @return
     */
    public static Map<String,Object> toMap(Object dataObject){
        Map<String,Object> result = new LinkedHashMap<>();
        if(dataObject instanceof Map) {
            Map dataObjectMap = (Map)dataObject;
            for(Object curKey : dataObjectMap.keySet()){
                if(!(curKey instanceof String)){
                    throw new RuntimeException("初始化参数Map的key只能是String类型");
                }
            }
            result.putAll(dataObjectMap);
        }
        else {
            result.putAll(MapUtils.bean2map(dataObject));
        }
        return result;
    }

    public static List<Map<String,Object>> toMapList(Object data){
        List<Map<String,Object>> result = new LinkedList<>();
        if(data instanceof List){
            List list = (List)data;
            for(Object listItem : list){
                result.add(toMap(listItem));
            }
        }else{
            result.add(toMap(data));
        }

        return result;
    }

    public static PathNode ParseColumn(String column){
        PathNode result = null;
        try {
            StringBuffer realPath = new StringBuffer(column);
            Pattern pattern = Pattern.compile("\\s*(\\w+)\\[([0-9]+)\\]\\s*");
            Matcher matcher = pattern.matcher(column);

            while (matcher.find()){
                String columnName = matcher.group(1);
                Integer index = Integer.parseInt(matcher.group(2));
                result = new PathNode(columnName,index);
            }

            if(result == null){
                result = new PathNode(column,null);
            }
        }catch (Exception e){
            throw new RuntimeException("path节点解析失败！");
        }


        return result;
    }

    /**
     * 通过列名获得对象的字段值，对象可以是map类型或javabean
     * @param dataItem
     * @param columnName
     * @return
     */
    public static Object getValueByColumnName(Object dataItem, String columnName){
        Object result = null;
        PathNode pathNode = ParseColumn(columnName);



        return getValueByColumnName( dataItem,  pathNode);
    }

    public static Object getValueByColumnName(Object dataItem, PathNode pathNode){
        Object result = null;
        try{
            if(dataItem instanceof Map){
                result = ((Map<String,Object>)dataItem).get(pathNode.getColumnName());
            }
            else {
                result = Reflections.invokeGetter(dataItem,pathNode.getColumnName());
            }

            if(pathNode.getIndex() != null){
                if(result instanceof Object[]){
                    result = ((Object[])result)[pathNode.getIndex()];
                }else if(result instanceof List){
                    result = ((List)result).get(pathNode.getIndex());
                }else {
                    throw new RuntimeException("列"+pathNode.getColumnName()+"不是数组或list");
                }
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
        PathNode pathNode = ParseColumn(columnName);


        if(pathNode.getIndex() != null){
            dataObj = ArrayUtils.toList(dataObj,Object.class).get(pathNode.getIndex());
        }
        try{
            if(dataObj instanceof Map){
                ((Map<String,Object>)dataObj).put(pathNode.getColumnName(),val);
            }
            else {
                Reflections.invokeSetter(dataObj,pathNode.getColumnName(),val);
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
    public static  Object getValueByPath(Map<String,Object> dataItem, String path){
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

    public static void main(String[] args){
        ParseColumn("hehe[]");
        //ValueObjectUtils.getValueByColumnName()
    }
}
