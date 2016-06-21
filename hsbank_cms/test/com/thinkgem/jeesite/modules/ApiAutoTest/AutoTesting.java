package com.thinkgem.jeesite.modules.ApiAutoTest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.JunitTest;
import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API 自动化测试
 * Created by liuguoqing on 2016/5/23.
 */
public class AutoTesting extends JunitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

//    private String baseUrl = "http://localhost:8080/f/api";
    private String baseUrl = "http://139.196.5.141:7007/hsbank/f/api";

    public List<String> getFieldValueList(Class<? extends Object> clazz) {
        List<String> resultList = new ArrayList<String>();
        try {
            Object instance = clazz.newInstance();
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                field.setAccessible(true);
                resultList.add(field.get(instance).toString());
            }
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
        return resultList;
    }

    @Test
    public void check() {
        //获取所有测试的url
        List<String> urlList = this.getFieldValueList(UrlConstant.class);
//        List<String> urlList = new ArrayList<>();
//        urlList.add("/myAccount/investmentTrend");
//        urlList.add("/myAccount/frozenAmountByInvestFail");
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("projectId", "1");
        long begin = System.currentTimeMillis();
        logger.info("=== Begin Testing ===");
        for (String url : urlList) {
            String result = super.httpPost(baseUrl + url, paramsMap);
            JSONObject json = null;
            try {
                json = JSON.parseObject(result);
            } catch (JSONException je) {
                //logger.error("xxx Not is Json Object, url:【{}】, json: {}", url, json);
                //logger.error(je.getMessage(),je );
            }
            if (null != json) {
                String code = String.valueOf(json.get("code"));
                String text = (String) json.get("text");
                if (StringUtils.isNotBlank(code) && StringUtils.isNotBlank(text)) {
                    logger.info("=== url:【{}】, Post Result: {}", url, result);
                } else {
                    logger.info("=== Code Or Text is null, url: 【{}】", url);
                }
            }
        }
        logger.info("=== end Testing,cost: {}ms  ===", (System.currentTimeMillis() - begin));
    }

}
