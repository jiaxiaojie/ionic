package com.thinkgem.jeesite.modules;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.Assert;
import org.springframework.web.context.WebApplicationContext;

import com.thinkgem.jeesite.common.mapper.JsonMapper;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "webapp")
@ContextHierarchy({
		@ContextConfiguration(name = "parent", locations = "classpath:spring-context*.xml"),
		@ContextConfiguration(name = "child", locations = "classpath:spring-mvc*.xml") })
public abstract class BaseTest {
	protected static final String FILED_DAO = "dao";
	protected Random random;
	
	@Autowired
	private WebApplicationContext wac;
	public MockMvc mockMvc;

	/**
	 * 初始化函数
	 */
	public void before() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
	}
	/**
	 * 初始化（由子类实现）
	 */
	public abstract void setUp();
	
	/**
	 * 清理函数
	 */
	public void after() {
		Subject subject = ThreadContext.getSubject();
		if(subject != null) {
			subject.logout();
		}
	}
	/**
	 * 清理函数（由子类实现）
	 */
	public abstract void clear();
	
	/**
	 * 设置类中属性的值
	 * @param target
	 * @param fieldValue
	 */
	protected void setField(Object target, Object fieldValue) {
		try {
			setField(target, fieldValue, fieldValue.getClass().getSimpleName());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置类中属性的值
	 * @param target
	 * @param fieldValue
	 * @param fieldName
	 */
	protected void setField(Object target, Object fieldValue, String fieldName) {
		fieldName = StringUtils.lowerCaseFirstLetter(fieldName);
		try {
			Field field = fieldName.equals(FILED_DAO) ? target.getClass().getSuperclass().getDeclaredField(fieldName) : target.getClass().getDeclaredField(fieldName);
			field.setAccessible(true);
			field.set(target, fieldValue);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 获取MvcResult body部分 并将内容转化为map
	 * @param result
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getBody(MvcResult result) throws UnsupportedEncodingException {
		return (Map<String, Object>)JsonMapper.fromJsonString(result.getResponse().getContentAsString(), Map.class);
	}
	
	/**
	 * 模拟Get请求
	 * @param urlTemplate 请求路径
	 * @return
	 */
	protected MockHttpServletRequestBuilder mockGet(String urlTemplate) {
		return mockGet(urlTemplate, new HashMap<String,Object>());
	}
	
	/**
	 * 模拟带参数的Get请求
	 * @param urlTemplate 请求路径
	 * @param params 参数
	 * @return
	 */
	protected MockHttpServletRequestBuilder mockGet(String urlTemplate, Map<String,Object> params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(urlTemplate);
		for (Map.Entry<String,Object> entry : params.entrySet()) {
			String name = entry.getKey().toString();
			String value = entry.getValue().toString();
			mockHttpServletRequestBuilder = mockHttpServletRequestBuilder.param(name, value);
		}
		return mockHttpServletRequestBuilder;
	}

	/**
	 * 模拟Post请求
	 * @param urlTemplate 请求路径
	 * @param params 参数
	 * @return
	 */
	protected MockHttpServletRequestBuilder mockPost(String urlTemplate) {
		return mockPost(urlTemplate, new HashMap<String,Object>());
	}
	
	/**
	 * 模拟Post请求
	 * @param urlTemplate 请求路径
	 * @return
	 */
	protected MockHttpServletRequestBuilder mockPost(String urlTemplate, Map<String,Object> params) {
		MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.post(urlTemplate);
		for (Map.Entry<String,Object> entry : params.entrySet()) {
			String name = entry.getKey().toString();
			String value = entry.getValue().toString();
			mockHttpServletRequestBuilder = mockHttpServletRequestBuilder.param(name, value);
		}
		return mockHttpServletRequestBuilder;
	}
	
	/**
	 * 生成一个有两位小数点的double数据
	 * @return
	 */
	protected double randomDoubleWithTwoScale() {
		return randomDoubleWithScale(2);
	}
	
	/**
	 * 生成一个有四位小数点的double数据
	 * @return
	 */
	protected double randomDoubleWithFourScale() {
		return randomDoubleWithScale(4);
	}
	
	/**
	 * 生成一个有scale位小数点的double数据
	 * @return
	 */
	protected double randomDoubleWithScale(int scale) {
		return NumberUtils.formatWithScale(random.nextDouble() * 30000, scale);
	}
	
	/**
	 * 随机生成一个在时间段内的时间
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	protected Date randomDate(String beginDate, String endDate) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date start = format.parse(beginDate);
			Date end = format.parse(endDate);
			return randomDate(start, end);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 随机生成一个在时间段内的时间
	 * @param beginDate 开始日期
	 * @param endDate 结束日期
	 * @return
	 */
	protected Date randomDate(Date beginDate, Date endDate) {
		long date = random(beginDate.getTime(), endDate.getTime());
		return new Date(date);
	}

	private static long random(long begin, long end) {
		long rtnn = begin + (long) (Math.random() * (end - begin));
		return rtnn;
	}
}
