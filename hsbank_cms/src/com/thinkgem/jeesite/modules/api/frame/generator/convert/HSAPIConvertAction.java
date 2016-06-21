package com.thinkgem.jeesite.modules.api.frame.generator.convert;

import com.thinkgem.jeesite.common.utils.ArrayUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.ApiConstant;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;

/**
 * Created by 万端瑞 on 2016/5/20.
 */
public abstract class HSAPIConvertAction<V> extends APIConvertAction<V> {
    @Override
    public API toAPIByNode(APINode apiNode) {
        API hsApi = getSuccessBaseAPI(true);
        if (apiNode != null){
            hsApi.putDataNode(apiNode);
        }
        return hsApi;
    }

    @Override
    public  APINodeConvertAction<Object> getDefaultConvertAction() {
        return super.getDefaultConvertAction();
    }

    @Override
    public API getSuccessBaseAPI(Boolean yesOrNo,Integer code,String... text) {

        String message = ArrayUtils.toStringWithSeparator(text,",");

        String realText = (StringUtils.isNotBlank(message)? message:(yesOrNo?ApiConstant.API_STATUS_TEXT_VALUE:"fail"));
        Integer realCode = (code != null?code:(yesOrNo?ApiConstant.API_RESULT_SUCCESS:2));

        API hsApi = new API("data");
        hsApi.putNodeWithObject("code", realCode);
        hsApi.putNodeWithObject("text",realText);
        return hsApi;
    }
}
