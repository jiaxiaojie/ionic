package com.thinkgem.jeesite.modules.api.frame.format.foramter;

import com.thinkgem.jeesite.common.utils.*;


import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.convert.ConvertorServiceFactory;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APICollectionNode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.format.expression.WanExpression;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.*;

/**
 * API格式化工具
 * 作者 万端瑞 on 2016/5/19.
 */
public class APIForamter {


    public static Object formatAPIByFormatPath(Map<String,Object> apiNode, String formatPath){

        APIItemElement apiItemElement = FormatConfigData.getAPIConfig().get(formatPath);
        if(apiItemElement == null){
            throw new RuntimeException("没有找到format path："+ formatPath +"对应的格式化配置");
        }
        return formatAPI(apiNode, apiItemElement.getData().getFormat());
    }

    /**
     * 根据指定格式 对数据进行格式化
     * @param apiNode api节点数据
     * @param format  json格式数据 api数据节点的格式
     * @param
     * @return
     */
    public static Object formatAPI(Object apiNode, String format){
        return covertAPINodeData(apiNode, JsonUtils.getObjectByJson(format));
    }



    /**
     *  根据类型mapping将api节点中的数据转换为指定类型
     * @param typeMappingObj
     * @param valueObject
     * @return
     */
    public static  Object convertValueObjectData(Object valueObject, Object typeMappingObj){
        Object result = null;

        Map<String,Object> typeMapping = null;
        if(typeMappingObj instanceof JSONArray){
            typeMapping = ((List<Map<String,Object>>)typeMappingObj).get(0);
        }else if(typeMappingObj instanceof JSONObject){//如果字段是对象类型
            typeMapping = (Map<String,Object>)typeMappingObj;
        }

        Class typeClazz = APIConfigUtils.getClazzByType(typeMapping.get("type"));
        if(valueObject instanceof Object[]){
            result = doCovertValueData((Object[])valueObject, typeClazz);
        }else{
            result = doCovertValueData(valueObject, typeClazz);
        }



        return result;
    }

    /**
     * 将节点按照typeMapping的格式进行转换，apiNode类型有两种：1.值maping（例如javabean，map），2.值mapping的集合（例如javabean，map的数组或Collection），apiNode的类型是根据typeMapping来推断的。
     * @param apiNode
     * @param typeMapping
     * @return
     */
    private static Object covertAPINodeData(Object apiNode, Object typeMapping){
        Object resultApiNode = null;
        if(typeMapping instanceof  Map){
            resultApiNode = covertObjectTypeData(new APIObjectNode(typeMapping), new APIObjectNode(apiNode));
        }else if(typeMapping instanceof  List){
            resultApiNode = covertCollectionTypeData(APICollectionNode.generateObjectNodeCollectionInstance(typeMapping).get(0), APICollectionNode.generateObjectNodeCollectionInstance(apiNode));
        }
        return resultApiNode;
    }



    private static Object[] doCovertValueData(Object[] datas, Class clazz){
        Object[] results = null;
        if(datas != null){
            results = new Object[datas.length];
            for(int i = 0; i< datas.length; i++){
                results[i] = doCovertValueData( datas[i],  clazz);
            }
        }

        return results;
    }

    /**
     * 将基本数据转换为指定类型
     * @param data
     * @param clazz
     * @return
     */
    private static Object doCovertValueData(Object data, Class clazz){
        Object result = data;

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
        return result;
    }



    /**
     * 转换map类型节点的数据类型
     * @param apiObjectNode 数据节点
     * @param typeMapping 数据节点对应的格式化描述信息
     * @return
     */
    private static Map<String,Object> covertObjectTypeData(Map<String,Object> typeMapping,Map<String,Object> apiObjectNode){
        Map<String,Object> result = new LinkedHashMap<String,Object>();
        //遍历数据类型json的字段列表
        for(String key : typeMapping.keySet()){
            Object typeNode = typeMapping.get(key);

            //保存当前类型节点的值在apiObjectNode中的key值
            String valuePath = key;

            //如果此类型节点是值节点，则判断此节点下有没有dataColumn字段，有则将valuePath改为dataColumn字段的值
            if(typeNode instanceof JSONObject && isDataNode(typeNode)){
                Object dataColumn = ((Map<String,Object>)typeNode).get("dataColumn");
                if(dataColumn!= null){
                    valuePath = dataColumn.toString();
                }
            }


            //得到当前字段待转换的值
            Object value = ValueObjectUtils.getValueByPath(apiObjectNode,valuePath);

            //如果此类型节点是值节点，则将带转换的值变成value字段指定的表达式的值
            if(isDataNode(typeNode)){
                //获取表达式
                String valueExpression = (String) ValueObjectUtils.getValueByColumnName(typeNode,"value");
                //执行表达式并返回执行结果
                Object valueExpressionVal = WanExpression.getValueByExpression(valueExpression, apiObjectNode, typeMapping);

                //如果只不为空则将其设置为当前字段待转换的值
                if(valueExpressionVal != null){
                    value = valueExpressionVal;
                }
            }

            //得到转换后的数据
            if(isDataNode(typeNode)){
                value = convertValueObjectData(value,typeNode);
            }else{
                value = covertAPINodeData(value, typeNode);
            }

            if(value != null){
                result.put(key,value);
            }

        }

        return result;
    }

    public static Boolean isDataNode(Object typeMappingMap){
        Boolean result = false;
        if(typeMappingMap != null){
            if(typeMappingMap instanceof Map){
                Object type = ((Map<String,Object>)typeMappingMap).get("type");
                result = type instanceof String && APIConfigUtils.getClazzByType((String)type) != null;
            }else if(typeMappingMap instanceof List){
                List tempList = (List)typeMappingMap;
                if(tempList.size()>0){
                    result = isDataNode(tempList.get(0));
                }

            }
        }


        return result;
    }

    /**
     * 转换map类型节点的数据类型
     * @param dataItems
     * @param typeMappingMap
     * @return
     */
    private static List<Object> covertCollectionTypeData(APIObjectNode typeMappingMap,APICollectionNode<APIObjectNode> dataItems){
        List<Object> result =  null;
        if(dataItems != null){
            result = new LinkedList<Object>();
            for(APIObjectNode dataItem : dataItems){
                result.add(covertAPINodeData(dataItem, typeMappingMap));
            }
        }
        return result;
    }

}
