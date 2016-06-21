package com.thinkgem.jeesite.modules.api.frame.generator.convert;

import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;

/**
 * Created by 万端瑞 on 2016/5/20.
 */
public class APIBuilder {
    private APIObjectNode apiObjectNode;
    private APIConvertAction covertAction;
    public APIBuilder(APIConvertAction covertAction){
        this.covertAction = covertAction;
    }

    public APIBuilder putDataChildNode(String path, Object apiNode){
        if(apiObjectNode == null){
            apiObjectNode = new APIObjectNode();
        }
        apiObjectNode.putNodeWithObject(path,apiNode);
        return this;
    }

    public API build(APINode apiNode){
        return covertAction.toAPIByNode(apiNode);
    }

    public API build(){
        return covertAction.toAPIByNode(apiObjectNode);
    }
}
