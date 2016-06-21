package com.thinkgem.jeesite.modules.api.frame.format.foramter;


import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.modules.api.frame.exception.APIException;
import com.thinkgem.jeesite.modules.api.frame.exception.APINodeException;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIFormat;
import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import com.thinkgem.jeesite.modules.api.frame.type.Type;
import com.thinkgem.jeesite.modules.api.frame.type.TypeConvertor;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.context.WAPIContext;

import java.util.Map;

/**
 *  API格式化器
 * 作者 万端瑞 on 2016/6/6.
 *
 * 定义：将javabean和map这种一个key对应一个value的称作【属性对象】，每一个key value对的形式称为【属性】
 */
public  class NAPIFormater extends APIIterator {

    /**
     * 格式化API
     * @param data
     * @param formatPath
     * @return
     */
    public Object format(Object data, String formatPath){
        APIFormat apiItemElement = WAPIContext.getFormatConfig().getForamtByPath(formatPath);

        Object result = null;
        try {
            result = this.iteration( data, apiItemElement.getForamt());
        }catch (APINodeException e){
            throw new APIException(e.getNodePath().toString() + ":"+e.getMessage());
        }

        return result;

    }


    /**
     * 重写获取值表达式，使其支持value和dataColumn说明字段
     * @param key
     * @param format
     * @return
     */
    @Override
    protected  String getValueExpression(String key ,Object format){
        String valueExpression = null;
        Object simpleFormat = format;//ArrayUtils.toList(format,Object.class).get(0);
        if(isAttrValNode(simpleFormat)){
            try{
                valueExpression = (String) ValueObjectUtils.getValueByColumnName(simpleFormat,"value");
            }catch (Exception e){}

            if(valueExpression == null){
                try{
                    valueExpression = (String)ValueObjectUtils.getValueByColumnName(simpleFormat,"dataColumn");
                }catch (Exception e){}
            }
        }
        if(valueExpression == null){
            valueExpression = key;
        }
        return valueExpression;
    }

    /**
     * 重写API迭代器的方法，对属性值进行转换
     * @param dataItem
     * @param simpleFormat
     * @return
     */
    @Override
    protected Object handleAttrValueNode(Object dataItem, Map<String, Object> simpleFormat) {
        Object typeName = ValueObjectUtils.getValueByColumnName(simpleFormat,TYPE);
        Type type = WAPIContext.getTypeConfig().getTypeByName(typeName==null?null:(String)typeName);
        if(type == null){
            throw new APIException("获取【属性值】节点值类型失败");
        }
        if(dataItem == null){
            throw new APIException("节点值不能为空");
        }

        Object resultValue = TypeConvertor.covert(type, dataItem);

        return resultValue;
    }
}
