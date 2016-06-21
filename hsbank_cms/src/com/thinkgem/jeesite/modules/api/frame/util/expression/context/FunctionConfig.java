package com.thinkgem.jeesite.modules.api.frame.util.expression.context;

import com.thinkgem.jeesite.modules.api.frame.format.config.APIFormat;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionSetElement;
import com.thinkgem.jeesite.modules.api.frame.format.expression.Function;
import com.thinkgem.jeesite.modules.api.frame.type.TypeConfig;
import com.thinkgem.jeesite.modules.api.frame.util.FileUtils;
import com.thinkgem.jeesite.modules.api.frame.util.XmlUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Created by pc on 2016/6/12.
 */
public class FunctionConfig {
    private  HashMap<String,Function> functions;
    private static Long lastRefreshTime = 0L; //最后一次刷新配置文件时间
    public static Logger log = Logger.getLogger(TypeConfig.class);

    public HashMap<String, Function> getFunctions() {
        return functions;
    }

    public Function getFunction(String functionName){
        return functions.get(functionName);
    }



    public Integer refreshConfig(File...files){
        if(this.functions == null){
            this.functions = new HashMap<String,Function>();
        }
        File[] refreshFiles =  FileUtils.getEditOnAfterLastRefreshTimeFiles(lastRefreshTime, files);
        functions.putAll(initFunctionsConfig(refreshFiles));

        //设置最后刷新时间
        if (refreshFiles.length > 0) {
            lastRefreshTime = System.currentTimeMillis();
            System.out.println("刷新API相关配置共"+refreshFiles.length+"个");
            for(File refreshFile : refreshFiles){
                log.debug("refresh file:" + refreshFile.getAbsolutePath());
                log.debug("refresh filename:" + refreshFile.getName());
            }
        }
        return refreshFiles.length;
    }

    public FunctionConfig(File...files){
        refreshConfig(files);
    }



    private HashMap<String,Function> initFunctionsConfig(File[]  functionsConfigPaths){
        HashMap<String,Function> functionsMap = new HashMap<String,Function>();

        if (!isEmpty(functionsConfigPaths)) {
            for (File mapperLocation : functionsConfigPaths) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    //配置文件转对象
                    FunctionSetElement functionSetElement = XmlUtils.fileToObject(new FileInputStream(mapperLocation), FunctionSetElement.class);

                    //将其放入map中方便查找
                    for(FunctionElement functionElement : functionSetElement.getFunctionItems()){

                        if(functionsMap.containsKey(functionElement.getMethod())){
                            throw new RuntimeException("API函数配置文件包含相同方法名的方法:"+functionElement.getMethod());
                        }
                        functionsMap.put(functionElement.getMethod(),new Function(functionElement));
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }
        //ServletContextUtils.getServletContext().setAttribute("apiFunctionsConfig",functionsMap);
        return functionsMap;
    }
}
