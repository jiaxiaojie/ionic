package com.thinkgem.jeesite.modules.api.frame.format.convert;

/**
 * Created by pc on 2016/5/31.
 */
public interface ConvertService {
    public <V,T> T covert(V Object, Class<T> resultType);
}
