package com.thinkgem.jeesite.modules.api.frame.util;

import com.thinkgem.jeesite.common.utils.StringUtils;

import java.util.LinkedList;

/**
 * Created by pc on 2016/6/17.
 */
public class BooleanUtils {

    /**
     * 字符串按字符顺序转boolean列表，0代表否 其他代表是
     * @param str
     * @return
     */
    public static LinkedList<Boolean> toList(String str){
        LinkedList<Boolean> result = new LinkedList<>();

        if(StringUtils.isNotBlank(str)){
            char[] chars = str.toCharArray();
            for (char c : chars){
                if(c == '0'){
                    result.add(false);
                }else {
                    result.add(true);
                }
            }
        }


        return  result;
    }

    /**
     *
     * @param booleanList
     * @return
     */
    public static Integer getCount(LinkedList<Boolean> booleanList, Boolean countObject){
        Integer count = 0;
        for (Boolean BooleanItem : booleanList){
            if(BooleanItem == countObject){
                count += 1;
            }
        }
        return count;
    }

    public static void main(String[] args){
        System.out.println(BooleanUtils.toList("011"));
    }
}
