package com.thinkgem.jeesite.modules.api.frame.format.config;

import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APISetElement;
import com.thinkgem.jeesite.modules.api.frame.type.Type;
import com.thinkgem.jeesite.modules.api.frame.type.TypeConfig;
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
 * Created by 万端瑞 on 2016/6/21.
 */
public class APIFormatConfig {
    public HashMap<String,APIFormat> formats;
    private static Long lastRefreshTime = 0L; //最后一次刷新配置文件时间
    public static Logger log = Logger.getLogger(APIFormatConfig.class);

    public APIFormat getForamtByPath(String path){
        return formats.get(path);
    }



    public APIFormatConfig(File...files) {
        refreshConfig(files);
    }

    public Integer refreshConfig(File...files){
        if(this.formats == null){
            this.formats = new HashMap<String,APIFormat>();
        }
        File[] refreshFiles =  FileUtils.getEditOnAfterLastRefreshTimeFiles(lastRefreshTime, files);
        this.formats.putAll(loadFormatConfig(refreshFiles));

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

    private HashMap<String,APIFormat> loadFormatConfig(File...files){
        HashMap<String,APIFormat> apiMap = new HashMap<String,APIFormat>();
        if (!isEmpty(files)) {
            for (File mapperLocation : files) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    //配置文件转对象
                    APISetElement apiSetElement = XmlUtils.fileToObject(new FileInputStream(mapperLocation), APISetElement.class);

                    //将其放入map中方便查找
                    for(APIItemElement apiItemElement : apiSetElement.getApiItems()){
                        //获得apiItem的全路径
                        String apiPath = apiSetElement.getBasePath() +"/" + apiItemElement.getPath();
                        //String frontPath = Global.getConfig("frontPath");
                        apiPath = APIConfigUtils.getRealPath(apiPath);

                        if(apiMap.containsKey(apiPath)){
                            throw new RuntimeException("API格式化配置中包含相同path配置项。path:"+apiPath+"file:"+mapperLocation.getName());
                        }
                        apiMap.put(apiPath,new APIFormat(apiItemElement,apiPath));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return apiMap;
    }
}
