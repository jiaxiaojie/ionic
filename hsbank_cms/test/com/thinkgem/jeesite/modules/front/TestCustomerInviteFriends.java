package com.thinkgem.jeesite.modules.front;

import com.thinkgem.jeesite.modules.FrontBaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public class TestCustomerInviteFriends extends FrontBaseTest {

	@Override
	public void setUp() {
		
	}
	
	/**
	 * 测试访问我的收益
	 * @throws Exception
	 */
	@Test
	public void TestMyEaring() throws Exception {
		login("13122633697", "123123");
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/f/customer/inviteFriends/myEarning"))
				.andExpect(MockMvcResultMatchers.view().name("modules/front/wdzh/inviteFriends/myEarning"))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertNotNull("测试访问我的收益：model中包含收益分页信息", result.getModelAndView().getModel().get("page"));
		Assert.assertNotNull("测试访问我的收益：model中包含查询信息", result.getModelAndView().getModel().get("pageSearchBean"));
		Assert.assertNotNull("测试访问我的收益：model中包含用户账号信息", result.getModelAndView().getModel().get("customerAccount"));
	}

	@Override
	public void clear() {
		
	}
}
