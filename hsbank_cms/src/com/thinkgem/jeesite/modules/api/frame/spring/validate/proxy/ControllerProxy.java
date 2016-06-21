package com.thinkgem.jeesite.modules.api.frame.spring.validate.proxy;

import net.sf.cglib.proxy.MethodInterceptor;

/**
 * Created by pc on 2016/6/6.
 */
public interface ControllerProxy extends MethodInterceptor {
    public Object getProxy(Object apiControllerObj);
}
