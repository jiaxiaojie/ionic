package com.thinkgem.jeesite.modules.api;

import com.thinkgem.jeesite.modules.FrontBaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Map;

public class TestEventController extends FrontBaseTest {

	@Override
	public void setUp() {
		
	}
	
	/**
	 * 测试抽奖
	 * @throws Exception
	 */
	@Test
	public void testLottery() throws Exception {
		String clientParaName = "client";
		String clientParaValue = "eyJ0eXBlIjoid2VjaGF0IiwidmVyc2lvbiI6IjEuMCIsIndlY2hhdCI6e319";
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/f/api/event/lottery").param(clientParaName, clientParaValue))
				.andDo(MockMvcResultHandlers.print()).andReturn();
		Map<String,Object> map = getBody(result);
		Assert.assertNotNull("测试抽奖，成功获取中奖信息", map.get(ApiConstant.API_RESP_DATA));
	}

	@Override
	public void clear() {
		
	}

}
