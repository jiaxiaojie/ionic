package com.thinkgem.jeesite.modules.api.frame.spring.controller.base;

import com.thinkgem.jeesite.common.service.ServiceException;
import com.thinkgem.jeesite.modules.api.ApiUtil;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy.APIControllerProxy;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy.ControllerProxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/6/3.
 */
@NController
public class NAPIBaseController  implements FactoryBean {

    @Resource
    private ControllerProxy apiControllerProxy;

    /**
     * 后台异常
     * @return
     */
    @ResponseBody
    @ExceptionHandler({Exception.class})
    public Map<String,Object> exception(Exception e) {
        Map<String, Object> map = new HashMap<String, Object>();

        if(e instanceof ServiceException){
            map = APIGenerator.createResultAPI(false,e.getMessage());
        }else{
            ApiUtil.sysExceptionRespMap(map);
        }
        e.printStackTrace();

        return map;
    }

    @Override
    public Object getObject() throws Exception {
        return apiControllerProxy.getProxy(this);
    }
    @Override
    public Class<?> getObjectType() {
        return this.getClass();
    }

    @Override
    public boolean isSingleton() {
        return false;
    }

}
