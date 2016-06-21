package com.thinkgem.jeesite.modules.api.frame.spring.validate.login;

import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * Created by 万端瑞 on 2016/6/2.
 */
public enum OperTerm {
    WEB{public  String getOperTermCode(){
        return ProjectConstant.OP_TERM_DICT_PC;
    }},

    WECHAT{public  String getOperTermCode(){
        return ProjectConstant.OP_TERM_DICT_WEIXIN;
    }},

    ANDROID{public  String getOperTermCode(){
        return ProjectConstant.OP_TERM_DICT_ANDROID;
    }},

    IOS{public  String getOperTermCode(){
        return ProjectConstant.OP_TERM_DICT_IOS;
    }};

    public abstract String getOperTermCode();

    public static  OperTerm getByTypeString(String type){
        OperTerm result = WEB;
        String typeDaxie = (type!= null?type.toUpperCase():"");
        switch (typeDaxie){
            case ApiConstant.OP_TERM_DICT_WEB:
                result = WEB;
                break;
            case ApiConstant.OP_TERM_DICT_WECHAT:
                result = WECHAT;
                break;
            case ApiConstant.OP_TERM_DICT_ANDROID:
                result = ANDROID;
                break;
            case ApiConstant.OP_TERM_DICT_IOS:
                result = IOS;
                break;
        }
        return result;
    }
}
