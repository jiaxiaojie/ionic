package com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy;

import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.foramter.FormatValidator;
import com.thinkgem.jeesite.modules.api.frame.util.NValueObjectUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by pc on 2016/6/6.
 */
public class TestAPIControllerProxy extends APIControllerProxy {

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        Object result = super.intercept(o, method, args, methodProxy);

        //对返回结果进行格式化校验
        Formater formater = method.getAnnotation(Formater.class);
        if(formater != null && result != null && result instanceof Map){
            //到这里表示需要对返回结果进行格式化
            //1.获取格式化配置ID
            String path = formater.path();
            if(StringUtils.isNotBlank(path)){
                String formatPath = APIConfigUtils.getRealPath(path);
                NValueObjectUtils.putValueByColumnName(result,"text",0);
                new FormatValidator().valid((Map)result,formatPath);
            }

        }



        return result;
    }
}
