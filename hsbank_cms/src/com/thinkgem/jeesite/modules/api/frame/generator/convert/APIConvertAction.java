package com.thinkgem.jeesite.modules.api.frame.generator.convert;

import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public abstract class APIConvertAction<V> extends APINodeConvertAction<V> {

    public abstract API toAPIByNode(APINode apiNode);

    public  API getSuccessBaseAPI(Boolean yesOrNo,String...text){
        return getSuccessBaseAPI(yesOrNo,null,text);
    }
    public abstract API getSuccessBaseAPI(Boolean yesOrNo,Integer code,String...text);
    public  API getSuccessBaseAPI(Boolean yesOrNo){
        return getSuccessBaseAPI(yesOrNo,null);
    }
}
