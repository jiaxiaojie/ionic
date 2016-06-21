package com.thinkgem.jeesite.modules.api.frame.generator.obj;

import java.util.*;

/**
 * Created by 万端瑞 on 2016/5/20.
 */
public class APICollectionNode<T extends APINode> extends LinkedList<T> implements APINode,Collection<T> {
    public APICollectionNode(){}


    public static APICollectionNode<APIObjectNode> generateObjectNodeCollectionInstance(Object collectionData){
        //将参数转为list
        List<Object> objParams = new LinkedList<>();
        if(collectionData instanceof  Collection){
            objParams.addAll((Collection)collectionData);
        }else if(collectionData instanceof Object[]){
            objParams.addAll(Arrays.asList((Object[])collectionData));
        }

        APICollectionNode<APIObjectNode> apiCollectionNode = new APICollectionNode();

        for (Object paramItem : objParams){
            APIObjectNode apiObjectNode = new APIObjectNode(paramItem);
            apiCollectionNode.add(apiObjectNode);
        }

        return apiCollectionNode;
    }
}
