package com.thinkgem.jeesite.modules.api.frame.format.convert;

import com.thinkgem.jeesite.common.utils.SpringContextHolder;
import org.springframework.core.convert.ConversionService;

/**
 * Created by pc on 2016/5/31.
 */
public class SpringConvertService implements ConvertService {
    private ConversionService conversionService;
    public SpringConvertService(){
        this.conversionService = SpringContextHolder.getBean("conversionService");
        //System.out.println("");
    }

    public <V,T> T covert(V Object, Class<T> resultType)
    {
        return this.conversionService.convert(Object,resultType);
    }
}
