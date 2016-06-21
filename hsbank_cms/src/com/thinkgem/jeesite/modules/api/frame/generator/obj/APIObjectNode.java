package com.thinkgem.jeesite.modules.api.frame.generator.obj;

import com.thinkgem.jeesite.common.utils.MapUtils;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/20.
 */
public class APIObjectNode extends LinkedHashMap<String,Object> implements APINode,Map<String,Object> {

    /**
     * 根据Path向当前APIObjectNode写入子节点，如果节点不存在则创建
     * @param path
     * @param node
     * @return
     */
    public APIObjectNode putNodeWithObject(String path, Object node) {
        ValueObjectUtils.putWithPath(this, path, node);
        return this;
    }

    public <V> V get(String key, Class<V> clazz){
        return (V)this.get(key);
    }

    /**
     * 通过数据对象生成APINode
     * @param dataObject
     */
    public APIObjectNode(Object dataObject){

        if(dataObject instanceof Map) {
            Map dataObjectMap = (Map)dataObject;
            for(Object curKey : dataObjectMap.keySet()){
                if(!(curKey instanceof String)){
                    throw new RuntimeException("初始化参数Map的key只能是String类型");
                }
            }
            this.putAll(dataObjectMap);
        }
        else {
            this.putAll(MapUtils.bean2map(dataObject));
        }
    }

    public APIObjectNode(){}
}
