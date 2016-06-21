package com.thinkgem.jeesite.modules.api.frame.format.foramter;

import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 作者 万端瑞 on 2016/5/23.
 */
public class WebAPIForamter extends APIForamter {

    /**
     * 根据web上下文，取得指定的api格式，对数据进行格式化
     * @param apiData
     * @return
     */
    public static Object formatAPI(Map<String,Object> apiData){
        Object result = apiData;

        if(apiData != null){
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            String url = request.getServletPath();
            HashMap<String,APIItemElement> apiConfigMap = FormatConfigData.getAPIConfig();
            APIItemElement apiItemElement = apiConfigMap.get(url);

            if(apiItemElement != null){
                String format = apiItemElement.getData().getFormat();
                result = formatAPI(apiData,format);
            }
        }
        return result;
    }
}
