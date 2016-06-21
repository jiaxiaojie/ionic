package com.thinkgem.jeesite.modules.api.frame.type;

import com.thinkgem.jeesite.modules.api.frame.exception.APIException;
import com.thinkgem.jeesite.modules.api.frame.util.NValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.ExpressionCalculator;

/**
 * 作者 万端瑞 on 2016/6/20.
 */
public class TypeConvertor {
    public static Object covert(Type type, Object value){
        if(type == null){
            throw new APIException("类型转换失败,目的类型不能为Null");
        }

        Object val = ExpressionCalculator.calculateValue(type.getExpression(), value);
        if(val.getClass() != type.getJavaType()){
            val = NValueObjectUtils.doCovertValueData(val, type.getJavaType());
        }

        if(val == null || val.getClass() != type.getJavaType()){
            throw new APIException("类型转换失败");
        }
        return val;
    }
}
