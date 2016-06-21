package com.thinkgem.jeesite.modules;

/**
 * Created by liuguoqing on 2016/5/17.
 */

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.common.utils.StringUtils;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 几个常用的断言方法如下, 例如：Assert.assertEquals(obj1,obj2)
 * 1  void assertEquals(Object expected, Object actual)
 *    检查两个变量或者等式是否平衡
 * 2  void assertTrue(boolean expected, boolean actual)
 *    检查条件为真
 * 3  void assertFalse(boolean condition)
 *    检查条件为假
 * 4  void assertNotNull(Object object)
 *    检查对象不为空
 * 5  void assertNull(Object object)
 *   检查对象为空
 * 6  void assertArrayEquals(expectedArray, resultArray)
 *    检查两个数组是否相等
 */
public class JunitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    public JSONObject checkCodeText(String result) {
        //断言，判断 result 不能为空
        Assert.assertNotNull(result);
        logger.info("=== Test Result: {}", result);
        JSONObject json = JSON.parseObject(result);
        //断言，判断 code 不能为空
        Assert.assertNotNull(json.get("code"));
        //断言，判断 text 不能为空
        Assert.assertNotNull(json.get("text"));
        return json;
    }

    /**
     * get方法
     */
    public String httpGet(String url) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        try {
            // 创建httpGet
            HttpGet httpGet = new HttpGet(url);
            // get请求.
            CloseableHttpResponse response = httpclient.execute(httpGet);
            try {
                // 响应实体
                HttpEntity entity = response.getEntity();
                // 响应状态
                //logger.info("=== Response Status: {}",response.getStatusLine());
                if (entity != null) {
                    // 响应内容长度
                    //logger.info("Response result length: {}", entity.getContentLength());
                    // 响应内容
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

    /**
     * post方法
     */
    public String httpPost(String url, Map<String, String> paramsMap) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String result = "";
        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        // 创建参数队列
        List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
        for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
            paramsList.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity uefEntity;
        try {
            uefEntity = new UrlEncodedFormEntity(paramsList, "UTF-8");
            httpPost.setEntity(uefEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                // 响应状态
                StatusLine  httpStatus = response.getStatusLine();
                if( httpStatus.getStatusCode() != HttpStatus.SC_OK){
                    logger.info("=== Response Result, Stauts: {}, Url: {}",  httpStatus, url);
                }
                if (entity != null) {  // 响应内容长度
                    //logger.info("Response result length: {}", entity.getContentLength());
                    result = EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            logger.error(e.getMessage(), e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return result;
    }

}
