package com.thinkgem.jeesite.modules.api.frame.format.foramter;

import com.thinkgem.jeesite.common.utils.ArrayUtils;
import com.thinkgem.jeesite.common.utils.JsonUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.APIConfigUtils;
import com.thinkgem.jeesite.modules.api.frame.format.config.init.FormatConfigData;
import com.thinkgem.jeesite.modules.api.frame.format.config.xml.entity.APIItemElement;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APICollectionNode;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APIObjectNode;
import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * API格式校验器
 * 作者 万端瑞 on 2016/6/5.
 *
 */
public class FormatValidator extends APIIterator  {
    private Map<String,String> message = new LinkedHashMap<String, String>();
    public  void valid(Map<String,Object> api, String formatPath){


        APIItemElement apiItemElement = FormatConfigData.getAPIConfig().get(formatPath);
        Object format = JsonUtils.getObjectByJson(apiItemElement.getData().getFormat());

        try {
            iteration(api,format);
        }catch (Exception e){
            message.clear();
            message.put("message","校验失败！");
            e.printStackTrace();
        }

        if(message.size()>0){
            throw new RuntimeException(ArrayUtils.toStringWithSeparator(message.values().toArray(),","));
        }


    }



    @Override
    public String getValueExpression(String key, Object format) {
        return key;
    }

    @Override
    public Object handleAttrValueNode(Object dataItem, Map<String, Object> simpleFormat) {
        Class typeClazz = APIConfigUtils.getClazzByType(ValueObjectUtils.getValueByColumnName(simpleFormat,TYPE));
        if(typeClazz == null){
            throw new RuntimeException("获取【属性值】节点值类型失败");
        }
        if(dataItem.getClass() != typeClazz){
            //this.message.put(currentKey,"字段"+currentKey+"值的格式不正确！");
        }
        return dataItem;
    }


}
