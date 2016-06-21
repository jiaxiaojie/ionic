package com.thinkgem.jeesite.modules.api.frame.format.config.init.spring.listener;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.expression.Function;
import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APISetElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionSetElement;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.Resource;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Properties;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 项目启动时读取api配置，保存到ServletContext中，以key apiConfig。
 * Created by 万端瑞 on 2016/5/17.
 */
public class APIContextListener implements ApplicationContextAware {
    private Resource[]  configPaths;
    private Resource[]  functionsConfigPaths;
    private Resource typeMappingPath;

    public Resource getTypeMappingPath() {
        return typeMappingPath;
    }

    public void setTypeMappingPath(Resource typeMappingPath) {
        this.typeMappingPath = typeMappingPath;
    }

    public Resource[] getFunctionsConfigPaths() {
        return functionsConfigPaths;
    }

    public void setFunctionsConfigPaths(Resource[] functionsConfigPaths) {
        this.functionsConfigPaths = functionsConfigPaths;
    }

    public Resource[] getConfigPaths() {
        return configPaths;
    }

    public void setConfigPaths(Resource[] configPaths) {
        this.configPaths = configPaths;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try{
            //初始化API配置
            //HashMap<String,Function> apiFunctionsConfig = initFunctionsConfig(functionsConfigPaths);
            //HashMap<String,APIItemElement> apiConfig = initApiConfig(configPaths);
           // HashMap<String,Class> typeMapping = initTypeMapping(typeMappingPath);
            //FormatConfigData.initByConfig(apiConfig,apiFunctionsConfig,typeMapping);

            //设置配置文件更改监听，用以刷新配置
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true){
                        try {
                            FormatConfigData.refreshConfig(configPaths,functionsConfigPaths,typeMappingPath);
                            Thread.sleep(1000*5);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }).start();

        }catch (Exception e){
            e.printStackTrace();
        }

    }








}
