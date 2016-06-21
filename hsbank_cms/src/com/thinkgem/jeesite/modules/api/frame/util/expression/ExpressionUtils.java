package com.thinkgem.jeesite.modules.api.frame.util.expression;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 万端瑞 on 2016/6/12.
 */
public class ExpressionUtils {

    public static Boolean isStringExpression(String expression){
        return "".equals((expression).replaceAll("^'.*'$",""));
    }

    public static Boolean isMethodExpression(String expression){
        Boolean result = false;
        try {
            StringBuffer realPath = new StringBuffer(expression);
            Pattern pattern = Pattern.compile("^\\s*(\\w+)\\((.*)\\)\\s*$");
            Matcher matcher = pattern.matcher(expression);

            if (matcher.find()){
                result = true;
            }
        }catch (Exception e){
        }
        return result;
    }

    public static void main(String[] args){
        String str = "'marketing_product_type_dict'";
        System.out.println(ExpressionUtils.isStringExpression(str));
    }
}
