package com.thinkgem.jeesite.modules.api.frame.format.convert;

/**
 * Created by pc on 2016/5/31.
 */
public interface Convertor<V,R> {

    R convert(V data);
}
