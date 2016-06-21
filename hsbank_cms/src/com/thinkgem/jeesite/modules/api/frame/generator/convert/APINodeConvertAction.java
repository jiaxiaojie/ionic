package com.thinkgem.jeesite.modules.api.frame.generator.convert;

import com.thinkgem.jeesite.common.utils.MapUtils;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;

import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/19.
 */
public abstract class APINodeConvertAction<V> {


    public  APINodeConvertAction<Object> getDefaultConvertAction(){

        return new APINodeConvertAction<Object>(){

            @Override
            public Map<String, Object> convert(Object dataObject) {
                Map<String,Object> result = null;
                if(dataObject instanceof Map){
                    result = (Map<String,Object>)dataObject;
                }else{
                    result = MapUtils.bean2map(dataObject);
                }
                return result;
            }
        };
    }

    /**
     * 数据对象转api的map形式的实际处理方法
     * @param dataObject
     * @return 就是转换后的api的map形式
     */
    public abstract Map<String, Object> convert(V dataObject);

    public int compare(APIObjectNode o1, APIObjectNode o2) {
        return 0;
    }
}
