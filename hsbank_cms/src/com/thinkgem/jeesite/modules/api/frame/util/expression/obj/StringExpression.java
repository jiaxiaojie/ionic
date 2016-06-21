package com.thinkgem.jeesite.modules.api.frame.util.expression.obj;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.util.expression.obj.Expression;

import java.util.Map;

/**
 * Created by 万端瑞 on 2016/6/12.
 */
public class StringExpression  implements Expression {
    private String value;
    private String stringExpression;

    public void setStringExpression(String stringExpression) {
        this.stringExpression = stringExpression;
        this.value = filterEscape(stringExpression.substring(1,stringExpression.length()-1));
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    /**
     *
     * @param stringExpression
     * @return
     */
    private String filterEscape(String stringExpression){
        String result = stringExpression;

        String[] escapes = new String[]{",","(",")","'"};
        for(String escape : escapes){
            String esc = "\\"+escape;
            if(stringExpression.indexOf(esc) != -1){
                result = stringExpression.replace(esc,escape);
            }
        }
        return result;
    }

    public static void main(String[] args){
        String str = new StringExpression().filterEscape("hello\\,");
        System.out.println(str);
    }
}
