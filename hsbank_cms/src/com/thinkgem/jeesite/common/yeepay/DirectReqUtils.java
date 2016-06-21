/**
 * 
 */
package com.thinkgem.jeesite.common.yeepay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.common.utils.StringUtils;

/**
 * @author yangtao
 *
 */
@Component()
public class DirectReqUtils {
	@Autowired
	private CloseableHttpClient httpClient;
	
	public static Logger logger = Logger.getLogger(DirectReqUtils.class);
	/**
	 * 直接请求服务
	 * @param serviceCode
	 * @param reqMes
	 * @param service
	 * @return
	 */
	public String dirReq(String serviceCode,String reqMes, String serviceName) {
		String retString = "";
		String err="";
		String signData = SignUtil.sign(reqMes);
		// 创建httppost
		HttpPost httpPost = new HttpPost(YeepayConstant.YEEPAY_DIRECT_URL_PREFIX);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("req", reqMes));
		formparams.add(new BasicNameValuePair("sign", signData));
		formparams.add(new BasicNameValuePair("service", serviceName));
		UrlEncodedFormEntity uefEntity = null;
		CloseableHttpResponse response = null;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httpPost.setEntity(uefEntity);
			response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			retString = EntityUtils.toString(entity, "UTF-8");
			EntityUtils.consume(entity);
		} catch(Exception e) {
			err=e.getMessage();
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		if(StringUtils.isNotBlank(err)) {
			logger.error("err mes is "+err);
		}
		return retString;
	}
	
	public String get(String url) {
		String strJson = "";
		HttpGet httpGet = new HttpGet(url);
		System.out.println("executing request " + httpGet.getURI());
		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			// 打印响应状态
			System.out.println(response.getStatusLine());
			if (entity != null) {
				strJson = EntityUtils.toString(entity);
				logger.debug("strJson:" + strJson);
				EntityUtils.consume(entity);
			}
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return strJson;
	}
}
