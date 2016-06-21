package com.thinkgem.jeesite.modules.api.frame.format.config.init;

import com.thinkgem.jeesite.common.mapper.JaxbMapper;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APISetElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionElement;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.FunctionSetElement;
import com.thinkgem.jeesite.modules.api.frame.format.expression.Function;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.thread.Runnable;
import org.apache.log4j.Logger;
import org.springframework.core.io.Resource;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

import static oracle.net.aso.C01.i;
import static org.springframework.util.ObjectUtils.addObjectToArray;
import static org.springframework.util.ObjectUtils.isEmpty;

/**
 * Created by pc on 2016/5/26.
 */
public  class FormatConfigData {
    public static Logger log = Logger.getLogger(FormatConfigData.class);

    public  HashMap<String,APIItemElement> apiConfig;
    public HashMap<String,Function> functionConfig;
    public HashMap<String,Class> typeMapping;
    private static FormatConfigData instance;
    private Long lastRefreshTime = 0L; //最后一次刷新配置文件时间

    private FormatConfigData(){
        apiConfig = new HashMap<>();
        functionConfig = new HashMap<String,Function>();
        typeMapping = new HashMap<String,Class>();
    }
    private FormatConfigData(HashMap<String,APIItemElement> apiConfig, HashMap<String,Function> functionConfig, HashMap<String,Class> typeMapping){

        this.apiConfig = apiConfig;
        this.functionConfig = functionConfig;
        this.typeMapping = typeMapping;
    }

    public static HashMap<String,APIItemElement> getAPIConfig(){
        HashMap<String,APIItemElement> result = null;
        if(instance == null){
            throw new RuntimeException("请先调用initByConfig初始化APIConfigData");
        }else{
            result = instance.apiConfig;
        }
        return result;
    }

    public static HashMap<String,Function> getFunctionConfig(){
        HashMap<String,Function> result = null;
        if(instance == null){
            throw new RuntimeException("请先调用initByConfig初始化APIConfigData");
        }else{
            result = instance.functionConfig;
        }
        return result;
    }

    public static HashMap<String,Class> getTypeMapping(){
        HashMap<String,Class> result = null;
        if(instance == null){
            throw new RuntimeException("请先调用initByConfig初始化APIConfigData");
        }else{
            result = instance.typeMapping;
        }
        return result;
    }

    public static synchronized void initByConfig(HashMap<String,APIItemElement> apiConfig, HashMap<String,Function> functionConfig,HashMap<String,Class> typeMapping){
        if(instance == null){
            if(apiConfig != null && functionConfig != null && typeMapping != null){
                instance = new FormatConfigData(apiConfig,functionConfig,typeMapping);
            }
        }
    }

    private static HashMap<String,Class> initTypeMapping(Resource typeMappingPath){


        LinkedHashMap<String,Class> typeMapping = new LinkedHashMap<String,Class>();

        if(typeMappingPath == null){
            return typeMapping;
        }

        //PropertiesLoader propertiesLoader = new PropertiesLoader(typeMappingPath.getFilename());
        Properties properties = new Properties();
        try {
            properties.load(typeMappingPath.getInputStream());
        } catch (IOException e) {
            new RuntimeException("加载API类型映射文件失败");
        }

        for(String name : properties.stringPropertyNames()){

            Class type = null;
            String clazzStr = null;
            try {
                clazzStr = properties.getProperty(name);
                type = Class.forName(clazzStr);
            } catch (ClassNotFoundException e) {
                new RuntimeException("API类型映射类型+"+name+"+解析失败："+clazzStr);
            }
            typeMapping.put(name,type);
        }
        return typeMapping;
    }

    private static HashMap<String,Function> initFunctionsConfig(Resource[]  functionsConfigPaths){
        HashMap<String,Function> functionsMap = new HashMap<String,Function>();

        if (!isEmpty(functionsConfigPaths)) {
            for (Resource mapperLocation : functionsConfigPaths) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    //配置文件转对象
                    FunctionSetElement functionSetElement = fileToObject(mapperLocation.getInputStream(), FunctionSetElement.class);

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

    private static HashMap<String,APIItemElement> initApiConfig(Resource[]  configPaths){
        HashMap<String,APIItemElement> apiMap = new HashMap<String,APIItemElement>();

        if (!isEmpty(configPaths)) {
            for (Resource mapperLocation : configPaths) {
                if (mapperLocation == null) {
                    continue;
                }

                try {
                    //配置文件转对象
                    APISetElement apiSetElement = fileToObject(mapperLocation.getInputStream(), APISetElement.class);

                    //将其放入map中方便查找
                    for(APIItemElement apiItemElement : apiSetElement.getApiItems()){
                        //获得apiItem的全路径
                        String apiPath = apiSetElement.getBasePath() +"/" + apiItemElement.getPath();
                        //String frontPath = Global.getConfig("frontPath");
                        apiPath = APIConfigUtils.getRealPath(apiPath);

                        if(apiMap.containsKey(apiPath)){

                            throw new RuntimeException("API格式化配置中包含相同path配置项。path:"+apiPath+"file:"+mapperLocation.getFile().getName());

                        }

                        apiMap.put(apiPath,apiItemElement);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        //ServletContextUtils.getServletContext().setAttribute("apiConfig",apiMap);
        //FormatConfigData.initByConfig(apiMap);

        return apiMap;
    }

    public static <T> T fileToObject(InputStream is, Class<?> clazz) {
        try {

            BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = br.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line).append("\r\n");
            }
            if (is != null) {
                is.close();
            }
            if (br != null) {
                br.close();
            }
            return (T) JaxbMapper.fromXml(sb.toString(), clazz);
        } catch (IOException e) {

        }
        return null;
    }

    /**
     * 刷新配置
     * @param apiConfig
     * @param functionsConfigPaths
     * @param typeMappingPath
     */
    public static void refreshConfig(Resource[] apiConfig, Resource[] functionsConfigPaths, Resource typeMappingPath) {



        //如果没有配置实例则创建
        if(instance == null){
           instance = new FormatConfigData();
        }

        //1.刷新apiConfig
        Resource[] needRefreshApiConfigs = getNeedRefreshResource(apiConfig);
        instance.apiConfig.putAll(initApiConfig(needRefreshApiConfigs));

        //2.刷新functionConfig
        Resource[] needRefreshFunctionsConfigs = getNeedRefreshResource(functionsConfigPaths);
        instance.functionConfig.putAll(initFunctionsConfig(needRefreshFunctionsConfigs));

        //3.刷新typeMapping
        Resource[] needRefreshTypeMapping = getNeedRefreshResource(typeMappingPath);
        instance.typeMapping.putAll(initTypeMapping(needRefreshTypeMapping.length>0?needRefreshTypeMapping[0]:null));

        //计算刷新的配置文件数
        //int refreshCount = needRefreshApiConfigs.length + needRefreshFunctionsConfigs.length + needRefreshTypeMapping.length;



        Resource[] refreshResources = ArrayUtils.addAll(ArrayUtils.addAll(needRefreshApiConfigs,needRefreshFunctionsConfigs),needRefreshTypeMapping);


        //设置最后刷新时间
        if (refreshResources.length > 0) {
            instance.lastRefreshTime = System.currentTimeMillis();
            System.out.println("刷新API相关配置共"+refreshResources.length+"个");
            for(Resource refreshResource : refreshResources){
                try {
                    log.debug("refresh file:" + refreshResource.getFile().getAbsolutePath());
                    log.debug("refresh filename:" + refreshResource.getFile().getName());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




    }

    private static Resource[] getNeedRefreshResource(Resource... configs) {
        List<Resource> result = new ArrayList<Resource>();
        if(configs != null && configs.length > 0){
            for(Resource config : configs){
                try {
                    if(config.lastModified() > instance.lastRefreshTime){
                        result.add(config);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result.toArray(new Resource[]{});
    }
}
