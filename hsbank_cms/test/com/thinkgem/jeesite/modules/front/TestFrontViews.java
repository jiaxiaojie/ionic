package com.thinkgem.jeesite.modules.front;

import com.thinkgem.jeesite.modules.FrontBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @author ydt
 * 前端页面测试类
 */
public class TestFrontViews extends FrontBaseTest {

	@Override
	public void setUp() {
		
	}
	
	/**
	 * 测试访问首页：
	 * @throws Exception
	 */
	@Test
	public void TestToIndex() throws Exception {
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/f/index"))
				.andExpect(MockMvcResultMatchers.view().name("modules/front/index"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertNotNull("测试访问首页：model中包含【最新公告】", result.getModelAndView().getModel().get("pageZxgg"));
		Assert.assertNotNull("测试访问首页：model中包含【花生新闻】", result.getModelAndView().getModel().get("pageFdxw"));
		Assert.assertNotNull("测试访问首页：model中包含【推荐项目列表】", result.getModelAndView().getModel().get("projectTransferInfoList"));
		Assert.assertNotNull("测试访问首页：model中包含【推荐转让项目列表】", result.getModelAndView().getModel().get("projectTransferInfoList"));
	}

	/**
	 * 测试能登录成功：
	 * @throws Exception
	 */
	@Test
	public void TestCanLoginSuccess() throws Exception {
		login("customer1", "123123");
	}

	@Override
	public void clear() {
		
	}
}