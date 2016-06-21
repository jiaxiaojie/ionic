package com.thinkgem.jeesite.modules.api.frame.util;

/**
 * Created by pc on 2016/6/15.
 */
public class WStringUtils {
    public static StringBuffer complement(StringBuffer str, Integer toSize, char symbol){
        Integer length = str.length();
        if(length > toSize){
            throw new RuntimeException("参数1的长度不能大于参数2！");
        }

        Integer index = toSize - length;
        for(int i = 0; i < index; i++){
            str.append(symbol);
        }
        return str;
    }
}
