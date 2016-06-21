package com.thinkgem.jeesite.modules.api.frame.format.foramter;

import com.thinkgem.jeesite.modules.api.frame.exception.APIException;
import com.thinkgem.jeesite.modules.api.frame.exception.APINodeException;
import com.thinkgem.jeesite.modules.api.frame.exception.MessageException;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ArrayUtils;
import com.thinkgem.jeesite.modules.api.frame.util.NValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.ExpressionCalculator;
import com.thinkgem.jeesite.modules.api.frame.util.expression.context.WAPIContext;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 作者 万端瑞 on 2016/6/6.
 * API 迭代器
 */
public abstract class APIIterator {
    protected static final String TYPE = "type";

    protected enum DataNodeType{
        ATTR_OBJ,ATTR_OBJ_ARRAY,ATTR_VAL,ATTR_VAL_ARRAY
    }

    /**
     * 此字段用于获得当前遍历到的字段
     */
    //protected String currentKey;

    /**
     * 判断是否为【属性值】节点
     * @param format
     * @return
     */
    protected Boolean isAttrValNode(Object format){
        Boolean result = false;
        if(format != null){
            if(format instanceof Map){
                Object type = ((Map<String,Object>) format).get(TYPE);
                result = type instanceof String && WAPIContext.getTypeConfig().getTypeByName((String)type) != null;
            }
        }
        return result;
    }

    /**
     * 判断是否为【属性值集合】节点
     * @param format
     * @return
     */
    protected Boolean isAttrValArrayNode(Object format){
        Boolean result = false;
        if(format != null){
            if(format instanceof List  || format instanceof Object[]){
                List tempList = ArrayUtils.toList(format, Object.class);
                if(tempList.size()>0){
                    result = isAttrValNode(tempList.get(0));
                }
            }
        }
        return result;
    }

    /**
     * 判断是否为【属性对象】节点
     * @param format
     * @return
     */
    protected Boolean isAttrObjectNode(Object format){
        Boolean result = false;
        if(format != null){
            if(!(format instanceof List) && !(format instanceof Object[])){
                Boolean keyIsStringType = true;
                if(format instanceof Map){
                    for (Object key : ((Map)format).keySet()){
                        if(!(key instanceof String)){
                            keyIsStringType = false;
                            break;
                        }
                    }
                }
                if(keyIsStringType && !isAttrValNode(format)){
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 判断是否为【属性对象集合】节点
     * @param format
     * @return
     */
    protected Boolean isAttrObjectArrayNode(Object format){
        Boolean result = false;
        if(format != null){
            if(format instanceof List || format instanceof Object[]){
                List list = ArrayUtils.toList(format,Object.class);
                if( list.size()>0 && isAttrObjectNode(list.get(0)) ){
                    result = true;
                }
            }
        }
        return result;
    }


    /**
     * 返回节点类型
     * @param format
     * @return
     */
    protected DataNodeType returnDataNodeType(Object format){
        DataNodeType result = null;
        if(isAttrObjectNode(format)){
            result = DataNodeType.ATTR_OBJ;
        }else if(isAttrObjectArrayNode(format)){
            result = DataNodeType.ATTR_OBJ_ARRAY;
        } else if(isAttrValNode(format)){
            result = DataNodeType.ATTR_VAL;
        } else if(isAttrValArrayNode(format)){
            result = DataNodeType.ATTR_VAL_ARRAY;
        }
        return result;
    }


    /**
     * 得到获得节点值的表达式
     * @return
     */
    protected String getValueExpression(String key ,Object format){
        return key;
    }


    /**
     * 返回节点的值
     * @param data 当前节点所在【属性对象】
     * @param nodeKey 当前节点在【属性对象】中的key
     * @param nodeFormat 当前结点的格式描述
     * @return
     */
    protected Object returnNodeValue(Object data, String nodeKey, Object nodeFormat){



        String valueExpression = getValueExpression(nodeKey,nodeFormat);
        Object nodeValue = ExpressionCalculator.calculateValue(valueExpression, data);
        return nodeValue;
    }

    /**
     * 迭代【属性对象】节点
     * @param data 可以是javabean也可能是map
     * @param format
     * @return
     */
    protected Object iterationAttrObjectNode(Object data, Map<String, Object> format){
        //Map<String, Object> dataMap = ValueObjectUtils.toMap(data);
        LinkedHashMap<String,Object> result = new LinkedHashMap<String,Object>();
        for(String key : format.keySet()){
            //currentKey = key;
            //获得当前结点的format
            Object nodeFormat = format.get(key);

            //获得当前结点的值表达式
            Object nodeValue = returnNodeValue(data,key,nodeFormat);

            //格式化当前结点的值
            Object formatVal = null;
            try {
                formatVal = iteration(nodeValue,nodeFormat);
            }catch (APINodeException e){
                throw new APINodeException(e.getMessage(), e.getNodePath(), key);
            }catch (APIException e){
                throw new APINodeException(e.getMessage(), null, key);
            }
            result.put(key, formatVal);

            //currentKey = key;
        }
        return result;
    }

    /**
     * 迭代【属性对象的集合】
     * @param data
     * @param format
     * @return
     */
    protected Object iterationAttrObjectArrayNode(List<Object> data, List<Map<String,Object>> format){
        List<Object> result = new LinkedList<>();
        if(format == null || format.size() == 0){
            throw new RuntimeException("格式化【属性对象的集合】节点失败，格式化参数不合法！");
        }

        Map<String,Object> simpleFormat = format.get(0);
        for (Object dataItem : data){
            result.add(iterationAttrObjectNode(dataItem,simpleFormat));
        }
        return result;
    }

    /**
     * 迭代【属性值】节点，属性值节点data的类型有三种，List和Object[]和Object
     * @param data
     * @param format
     * @return
     */
    protected Object iterationAttrValueNode(Object data, Object format){
        List<Object> result = new LinkedList<>();

        List<Object> dataList = ArrayUtils.toList(data,Object.class);
        Boolean isSingle = (dataList.get(0)==data);

        Map<String,Object> simpleFormat = NValueObjectUtils.toMapList(format).get(0);
        for(int i = 0; i < dataList.size(); i++){//Object dataItem : datas
            Object dataItem = dataList.get(i);
            Object dataItemHandleResult = returnNodeValue(dataItem,"this",simpleFormat);
            dataItemHandleResult = iteration(dataItem, simpleFormat);
            result.add(dataItemHandleResult);
        }
        return isSingle?result.get(0):result;
    }



    /**
     * 处理值节点的值
     * @param dataItem
     * @param simpleFormat
     * @return
     */
    protected abstract Object handleAttrValueNode(Object dataItem,Map<String,Object> simpleFormat);


    /**
     * 用format对data进行格式化，
     * data类型：1.【属性对象】，2.【属性对象】的集合（集合支持list和Object[]），3.【属性】的值。其中【属性对象】，【属性对象】的集合，属性】的值我们称其为数据的节点
     * format类型：1.【属性对象】的格式化描述，2.【属性】的格式化描述，3属性值的描述
     * 这个方法是以foramt为标准去data中找满足要求的数据，这点切记
     * @param data
     * @param format
     */
    protected Object iteration(Object data,Object format){
        Object result = null;

        //获得节点类型
        DataNodeType dataNodeType = returnDataNodeType(format);
        //如果是【属性对象】节点
        if(dataNodeType == DataNodeType.ATTR_OBJ){
            result = iterationAttrObjectNode(data,NValueObjectUtils.toMap(format));
        }//如果是【属性对象的集合】
        else if(dataNodeType == DataNodeType.ATTR_OBJ_ARRAY){
            List<Object> datas = ArrayUtils.toList(data,Object.class);
            List<Object> formats = ArrayUtils.toList(format,Object.class);
            result = iterationAttrObjectArrayNode(datas,NValueObjectUtils.toMapList(formats));
        }//如果是【属性的值】 //如果是【属性的值的集合】
        else if(dataNodeType == DataNodeType.ATTR_VAL_ARRAY){
            result = iterationAttrValueNode(data,format);
        }
        else if(dataNodeType == DataNodeType.ATTR_VAL){
            result = handleAttrValueNode(data,NValueObjectUtils.toMap(format));
        }

        return result;
    }
}
