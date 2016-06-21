package com.thinkgem.jeesite.modules.api.frame.util.expression.context;

import com.thinkgem.jeesite.modules.api.frame.format.config.APIFormatConfig;
import com.thinkgem.jeesite.modules.api.frame.type.TypeConfig;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * API上下文
 * Created by 万端瑞 on 2016/6/12.
 */
public class WAPIContext {
    private static FunctionConfig functionConfig;
    private static TypeConfig typeConfig;
    private static APIFormatConfig formatConfig;
    private static Long lastRefreshTime = 0L; //最后一次刷新配置文件时间



    /**
     * 刷新配置
     */
    public static void refreshConfig(File[] functionConfigFiles, File[] typeConfigFiles, File[] formatConfigFiles) {

        if(functionConfig == null){
            functionConfig = new FunctionConfig(null);
        }

        if(typeConfig == null){
            typeConfig = new TypeConfig(null);
        }

        if(formatConfig == null){
            formatConfig = new APIFormatConfig(null);
        }

        //1.刷新apiConfig
        functionConfig.refreshConfig(functionConfigFiles);

        //2.刷新functionConfig
        typeConfig.refreshConfig(typeConfigFiles);

        //3.刷新typeMapping
        formatConfig.refreshConfig(formatConfigFiles);
    }






    public static FunctionConfig getFunctionConfig() {
        initVerify(functionConfig, FunctionConfig.class);
        return functionConfig;
    }

    public static TypeConfig getTypeConfig() {
        initVerify(typeConfig, TypeConfig.class);
        return typeConfig;
    }

    public static APIFormatConfig getFormatConfig() {
        initVerify(formatConfig, APIFormatConfig.class);
        return formatConfig;
    }

    private static void initVerify(Object config,Class clazz){
        if(config == null){
            throw new RuntimeException("配置文件"+clazz.getName()+"尚未初始化");
        }
    }

}
