package com.thinkgem.jeesite.modules.api.frame.format.expression;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.convert.ConvertorServiceFactory;
import com.thinkgem.jeesite.modules.api.frame.util.NValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public class WanExpression {

    /**
     * 通过表达式 获得值
     * @param valueExpression 表达式支持方法调用，参数类型可以是类似el表达式的path，来获得值，比如hello.world表示获得dataItem中key为hello的值val1，然后再获得val1中key为world的值，目前只支持string类型，且参数值不能包含''（）等有特殊含义的字符， TODO 以后会改进
     * @param dataItem
     * @param typeMappingMap
     * @return
     */
    public static Object getValueByExpression(String valueExpression, Map<String,Object> dataItem, Map<String,Object> typeMappingMap){
        Object result = null;
        if(valueExpression != null){
            Integer indexLeftBracket = valueExpression.indexOf("(");
            if(indexLeftBracket != -1){//说明有函数调用,暂时不允许参数中包含括号
                Integer indexRightBracket = valueExpression.lastIndexOf(")");
                if(indexRightBracket<=indexLeftBracket){
                    throw new RuntimeException("表达式错误");
                }

                String methodName = valueExpression.substring(0,indexLeftBracket);
                String[] paramStrs = StringUtils.split(valueExpression.substring(indexLeftBracket+1,indexRightBracket),",");
                //String endExpression = valueExpression.substring(indexRightBracket+1);

                //查找对应函数
                Function function = getFunctionELementByMethodName(methodName);

                Object[] params = covertParamsType(function.getParamTypes(),paramStrs,dataItem, typeMappingMap);

                result = function.invoke(params);

            }else{//说明没有函数调用，则将表达式当成path处理
                //result = String.valueOf(ValueObjectUtils.getValueByPath(dataItem, jsonPathToDataPath(currentParam,typeMappingMa)));
                result = ValueObjectUtils.getValueByPath(dataItem, valueExpression);
            }
        }

        return result;
    }

    public static Object returnValueByExpression(String valueExpression, Object dataItem){
        return getValueByExpression(valueExpression,  NValueObjectUtils.toMap(dataItem),null);
    }

    /**
     * 得到表达式中所用方法的对象
     * @param methodName
     * @return
     */
    public static Function getFunctionELementByMethodName(String methodName){
        HashMap<String,Function> apiConfigMap = FormatConfigData.getFunctionConfig();
        Function apiItemElement = apiConfigMap.get(methodName);
        return apiItemElement;
    }


    /**
     * 转换表达式中的参数为指定类型
     * @param paramTypes
     * @param params
     * @param dataItem
     * @param typeMappingMa
     * @return
     */
    private static Object[] covertParamsType(Class[] paramTypes, String[] params, Map<String,Object> dataItem, Map<String,Object> typeMappingMa){

        //保存返回值
        Object[] result = new Object[params.length];

        //如果参数类型和参数的个数不匹配，则返回null
        if(paramTypes.length != params.length){
            return null;
        }

        //遍历参数，挨个转换
        for(int i = 0; i < params.length; i++){

            //保存当前转换结果
            Object currentResult = null;

            //当前待转换的参数
            String currentParam = params[i].trim();

            Integer leftmark = StringUtils.indexOf(currentParam,"'");
            Integer rightmark = StringUtils.lastIndexOf(currentParam,"'");
            Integer currentParamLength = currentParam.length();

            //参数如果是字符串
            if(leftmark==0 && rightmark == currentParamLength-1){
                if(paramTypes[i] == String.class){
                    currentResult = currentParam.substring(1,rightmark);
                }
            }
            else{//否则认为是path
                currentResult = ValueObjectUtils.getValueByPath(dataItem, currentParam);

                //转换数据类型
                currentResult = ConvertorServiceFactory.getConvertService().covert(currentResult,paramTypes[i]);

                /*if(paramTypes[i] == String.class){
                    currentResult = String.valueOf(currentResult);
                }*/
            }
            result[i] = currentResult;
        }

        return result;
    }

    /**
    public static String jsonPathToDataPath(String jsonPath, Map<String,Object> typeMappingMa){
        String resultPath = jsonPath;
        Object node = ValueObjectUtils.getValueByPath(typeMappingMa,jsonPath);
        if(APIForamter.isDataNode(node)){
            Object dataColumn = ValueObjectUtils.getValueByColumnName(node,"dataColumn");
            if(dataColumn != null && dataColumn instanceof String && !"".equals(dataColumn)){
                resultPath = (String)dataColumn;
            }
        }
        return resultPath;
    }**/



}
