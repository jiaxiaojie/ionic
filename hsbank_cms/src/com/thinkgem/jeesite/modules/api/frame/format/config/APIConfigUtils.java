package com.thinkgem.jeesite.modules.api.frame.format.config;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public class APIConfigUtils {
    /**
     * 通过字段类型关键字，获取其对应的java类型Class
     * @param type
     * @return
     */
    public static Class getClazzByType(Object type){
        Class clazz = null;
        if(type != null && type instanceof String){
            String typeName = type.toString().trim().toLowerCase();
            clazz = FormatConfigData.getTypeMapping().get(typeName);
        }
        return clazz;
    }

    /**
     * 得到api配置中path的真实值
     * @param path
     * @return
     */
    public static String getRealPath(String path){
        StringBuffer realPath = new StringBuffer(path);
        Pattern pattern = Pattern.compile("(\\$\\{\\s*(\\w+)\\s*\\})");
        Matcher matcher = pattern.matcher(path);


        while (matcher.find()){
            String el = matcher.group(1);
            String elEp = matcher.group(2);
            String val = Global.getConfig(elEp);
            if(StringUtils.isNotBlank(val)){
                StringUtils.replaceFirst(realPath,el,val);
            }
        }
        return realPath.toString();
    }



    public static void main(String[] args){
        System.out.println(APIConfigUtils.getRealPath("qwe${ asc }e${asc}wr${132}"));
    }
}
