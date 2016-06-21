/**
 * Copyright: Copyright (c)2011
 * Company: 易宝支付(YeePay)
 */
package com.thinkgem.jeesite.common.yeepay;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 * @author：张晓冬
 * @since：2013年12月9日 下午9:20:25
 * @version:
 */
public class SignUtil {
	
	public static String serviceUrl=YeepayConstant.YEEPAY_SIGN_URL_PREFIX;
	
	public static String sign(String sourceMessage) {
		
		String ret= postService("sign", sourceMessage, "");
//		System.out.println(sourceMessage);
//		System.out.println();
//		System.out.println(ret);
		return ret;
	}

	public static boolean verifySign(String sourceMessage, String signMsg) {
		String ret = postService("verify", sourceMessage, signMsg);
		if (ret.equals("ok")) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 远端请求服务
	 * 
	 * @param command
	 * @param req
	 * @param sign
	 * @return
	 */
	public static String postService(String command, String req, String sign) {
		String ret = "";
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(serviceUrl);
		// 创建参数队列
		List<NameValuePair> formparams = new ArrayList<NameValuePair>();
		formparams.add(new BasicNameValuePair("command", command));
		formparams.add(new BasicNameValuePair("req", req));
		formparams.add(new BasicNameValuePair("sign", sign));
		UrlEncodedFormEntity uefEntity;
		try {
			uefEntity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost.setEntity(uefEntity);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					ret = EntityUtils.toString(entity, "UTF-8");
				}
			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return ret == null ? "" : ret;
	}
	
	public static void main(String[] args){
		//String src="test";
		//String tar="MIIFOgYJKoZIhvcNAQcCoIIFKzCCBScCAQExCzAJBgUrDgMCGgUAMC8GCSqGSIb3DQEHAaAiBCAwOThmNmJjZDQ2MjFkMzczY2FkZTRlODMyNjI3YjRmNqCCA/wwggP4MIIDYaADAgECAhATZqkKSvPNyaVfKcyrwRfbMA0GCSqGSIb3DQEBBQUAMCoxCzAJBgNVBAYTAkNOMRswGQYDVQQKExJDRkNBIE9wZXJhdGlvbiBDQTIwHhcNMTUwNzI0MDMyNDIzWhcNMTgwNzI0MDMyNDIzWjCBkjELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEWMBQGA1UECxMNcmEueWVlcGF5LmNvbTEUMBIGA1UECxMLRW50ZXJwcmlzZXMxODA2BgNVBAMULzA0MUBaMTAwMTI0Njc1OThAc2hhbmdoYWlmdWRpbmdqaW5yb25nQDAwMDAwMDAxMIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDldluFFjAhbesRK9goaIbFLTIr5Skn2U9hlLhdgL+a7r9uk0ZaTBfjwe3HYtZ/9nKFJdSlOPSr9HH5GQRg29bIIkgY2O23Y+kyQFWdrxJRo9rGqkboyyM+0Z5gCFir+UtIvsO7k+nsk0M+/OpzQQoblIbwyLY5lrMK8nuwimFjGwIDAQABo4IBtDCCAbAwHwYDVR0jBBgwFoAU8I3ts0G7++8IHlUCwzE37zwUTs0wHQYDVR0OBBYEFN+ihiQKc4zo60+LulZUAKx4GL0aMAsGA1UdDwQEAwIE8DAMBgNVHRMEBTADAQEAMDsGA1UdJQQ0MDIGCCsGAQUFBwMBBggrBgEFBQcDAgYIKwYBBQUHAwMGCCsGAQUFBwMEBggrBgEFBQcDCDCB/wYDVR0fBIH3MIH0MFegVaBTpFEwTzELMAkGA1UEBhMCQ04xGzAZBgNVBAoTEkNGQ0EgT3BlcmF0aW9uIENBMjEMMAoGA1UECxMDQ1JMMRUwEwYDVQQDEwxjcmwxMDRfMTM2NzQwgZiggZWggZKGgY9sZGFwOi8vY2VydDg2My5jZmNhLmNvbS5jbjozODkvQ049Y3JsMTA0XzEzNjc0LE9VPUNSTCxPPUNGQ0EgT3BlcmF0aW9uIENBMixDPUNOP2NlcnRpZmljYXRlUmV2b2NhdGlvbkxpc3Q/YmFzZT9vYmplY3RjbGFzcz1jUkxEaXN0cmlidXRpb25Qb2ludDAUBgMqVgEEDRMLMTAwMTI0Njc1OTgwDQYJKoZIhvcNAQEFBQADgYEARPTomfmEgvZv+hXB7Qhj0N+KGs2KQtBSxOPfbwf5Ph+9BF3oG3ZusJjBSy3czWJP2PBnH1/MCcDDoC8Bm5dJEmPweYvjEub7E/jQ8cLUYfZYqzxzSQ8QBj4G6y1MUnNqb1inKBm++yum9YFtZOab9tT2PvkTV9RRl1udMZSWrbAxgeMwgeACAQEwPjAqMQswCQYDVQQGEwJDTjEbMBkGA1UEChMSQ0ZDQSBPcGVyYXRpb24gQ0EyAhATZqkKSvPNyaVfKcyrwRfbMAkGBSsOAwIaBQAwDQYJKoZIhvcNAQEBBQAEgYAviF/bWnbE+wtrucz1rUtFLZ5ZcIwvCTDaqk59d3xdAyQp9V5mRIprFX20zprg9Wo1BqwrjWheXOzGo6jQxyz53nkG5VFVAjFnS/cGkJx8NFmj37vXiNmwKBPK3CveD0fWsTn7JmT0fM80Lx2qAd2mXlD22t2CBpLUp4H3LCdIQw==";
		
		System.out.println(sign("1111"));
	}
}
