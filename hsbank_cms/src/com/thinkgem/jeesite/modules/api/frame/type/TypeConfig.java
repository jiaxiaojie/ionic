package com.thinkgem.jeesite.modules.api.frame.type;

import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionSetElement;
import com.thinkgem.jeesite.modules.api.frame.format.expression.Function;
import com.thinkgem.jeesite.modules.api.frame.type.xml.TypeElement;
import com.thinkgem.jeesite.modules.api.frame.type.xml.TypeSetElement;
import com.thinkgem.jeesite.modules.api.frame.util.FileUtils;
import com.thinkgem.jeesite.modules.api.frame.util.XmlUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * 作者 万端瑞 on 2016/6/20.
 */
public class TypeConfig {
    private  HashMap<String,Type> types;
    private static Long lastRefreshTime = 0L; //最后一次刷新配置文件时间
    public static Logger log = Logger.getLogger(TypeConfig.class);

    public TypeConfig(File...files) {
        refreshConfig(files);
    }
    public Integer refreshConfig(File...files){
        if(this.types == null){
            this.types = new HashMap<String,Type>();
        }

        File[] refreshFiles =  FileUtils.getEditOnAfterLastRefreshTimeFiles(lastRefreshTime, files);
        types.putAll(loadTypeConfig(refreshFiles));

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

    private HashMap<String,Type> loadTypeConfig(File...files){
        HashMap<String,Type> typesMap = new HashMap<String,Type>();

        if (!isEmpty(files)) {
            for (File mapperLocation : files) {
                if (mapperLocation == null) {
                    continue;
                }
                try {
                    //配置文件转对象
                    TypeSetElement typeSetElement = XmlUtils.fileToObject(new FileInputStream(mapperLocation), TypeSetElement.class);
                    //将其放入map中方便查找
                    for(TypeElement typeElement : typeSetElement.getTypeElements()){
                        if(typesMap.containsKey(typeElement.getName())){
                            throw new RuntimeException("API类型配置文件包含相同的类型名称:"+typeElement.getName());
                        }
                        typesMap.put(typeElement.getName().toLowerCase(), Type.createType(typeElement));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return typesMap;
    }


    public Type getTypeByName(String name) {
        if(name == null){
            throw new RuntimeException("type name 不能为Null");
        }

        if(types == null){
            throw new RuntimeException("type配置文件尚未初始化");
        }

        return types.get(name.toLowerCase());
    }
}
