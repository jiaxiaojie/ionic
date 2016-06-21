package com.thinkgem.jeesite.modules.api.frame.generator.obj;

import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public class API extends APIObjectNode {
    private String dataNodePath;
    public API(String dataNodePath){
        this.dataNodePath = dataNodePath;
    }

    public API(Map<String,Object> api){
        this.putAll(api);
    }

    public API putDataChildNode(String path, Object apiNode){
        this.putNodeWithObject(dataNodePath+"."+path,apiNode);
        return this;
    }

    public void putDataNode(Object apiNode){
        this.putNodeWithObject(dataNodePath,apiNode);
    }
}
