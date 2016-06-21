package com.thinkgem.jeesite.modules.api.frame.format.annotation;

import java.lang.annotation.*;

/**
 * Created by 万端瑞 on 2016/5/26.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface Formater {
    String path()  default "";
}
