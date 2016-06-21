package com.thinkgem.jeesite.modules.front;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thinkgem.jeesite.modules.FrontBaseTest;
import com.thinkgem.jeesite.modules.YeepayCommonHandlerTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectRedemptionApplyDao;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.front.CurrentAccountController;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import com.thinkgem.jeesite.modules.sys.utils.CustomerUtils;

public class TestCurrentAccountController extends FrontBaseTest {

	@Mock
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	@Mock
	private CurrentProjectRedemptionApplyDao currentProjectRedemptionApplyDao;
	@Mock
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Mock
	private CurrentAccountInterestChangeHisDao currentAccountInterestChangeHisDao;
	@Mock
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@InjectMocks
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;
	@Mock
	private CustomerAccountDao customerAccountDao;
	@Mock
	private CustomerBalanceDao customerBalanceDao;
	@Mock
	private CustomerBalanceHisDao customerBalanceHisDao;
	@InjectMocks
	private CustomerBalanceHandler customerBalanceHandler;
	@InjectMocks
	private YeepayCommonHandlerTest yeepayCommonHandlerTest;
	@Autowired
	private CurrentAccountController currentAccountController;
	
	@Override
	public void setUp() {
		Mockito.reset(currentProjectHoldInfoDao);
		Mockito.reset(currentProjectRedemptionApplyDao);
		Mockito.reset(currentAccountSummaryDao);
		Mockito.reset(currentAccountInterestChangeHisDao);
		Mockito.reset(currentProjectExecuteSnapshotDao);
		Mockito.reset(customerAccountDao);
		Mockito.reset(customerBalanceDao);
		Mockito.reset(customerBalanceHisDao);
		setField(currentProjectHoldInfoService, currentProjectHoldInfoDao, "dao");
		setField(yeepayCommonHandlerTest, customerBalanceHandler);
		setField(currentProjectHoldInfoService, yeepayCommonHandlerTest, "yeepayCommonHandler");
		setField(currentAccountController, currentProjectHoldInfoService);
	}
	
	/**
	 * 测试提取利息成功
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPollInterestSuccess() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double principal = 151d;
		Double applyRedeemPrincipal = 52d;
		Double interest = 103d;
		Double pollInterest = 99d;

		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		CurrentProjectHoldInfo beforeCurrentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		CurrentAccountSummary currentAccountSummary = new CurrentAccountSummary();
		currentAccountSummary.setTotalRedeemInterest(12d);
		CurrentAccountSummary beforeCurrentAccountSummary = new CurrentAccountSummary();
		beforeCurrentAccountSummary.setTotalRedeemInterest(currentAccountSummary.getTotalRedeemInterest());
		CurrentAccountInterestChangeHis currentAccountInterestChangeHis = new CurrentAccountInterestChangeHis();
		CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = new CurrentProjectExecuteSnapshot();
		currentProjectExecuteSnapshot.setProjectId(projectId);
		currentProjectExecuteSnapshot.setHasRedeemInterest(12d);
		CurrentProjectExecuteSnapshot beforeCurrentProjectExecuteSnapshot = new CurrentProjectExecuteSnapshot();
		beforeCurrentProjectExecuteSnapshot.setProjectId(projectId);
		beforeCurrentProjectExecuteSnapshot.setHasRedeemInterest(12d);
		CustomerBalance customerBalance = new CustomerBalance();
		CustomerBalance beforeCustomerBalance = new CustomerBalance();
		customerBalance.setGoldBalance(123.12d);
		beforeCustomerBalance.setGoldBalance(customerBalance.getGoldBalance());
		CustomerBalanceHis customerBalanceHis = new CustomerBalanceHis();
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(customerBalance.getAccountId());
		customerAccount.setHasOpenThirdAccount(ProjectConstant.HASOPENED);
		
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
			.thenReturn(currentProjectHoldInfo);
		Mockito.when(currentAccountSummaryDao.updateByPollInterest(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentAccountSummary.setTotalRedeemInterest(NumberUtils.add(currentAccountSummary.getTotalRedeemInterest(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(currentProjectHoldInfoDao.updateByPollInterest(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentProjectHoldInfo.setInterest(NumberUtils.sub(currentProjectHoldInfo.getInterest(), (Double)invocation.getArguments()[2]));
						return null;
					}
				}
			);
		Mockito.when(currentAccountInterestChangeHisDao.insert(Mockito.any(CurrentAccountInterestChangeHis.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentAccountInterestChangeHis.setChangeType(((CurrentAccountInterestChangeHis)invocation.getArguments()[0]).getChangeType());
						currentAccountInterestChangeHis.setChangeValue(((CurrentAccountInterestChangeHis)invocation.getArguments()[0]).getChangeValue());
						return null;
					}
				}
			);
		Mockito.when(currentProjectExecuteSnapshotDao.updateByPollInterest(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentProjectExecuteSnapshot.setHasRedeemInterest(NumberUtils.add(currentProjectExecuteSnapshot.getHasRedeemInterest(), (double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(customerBalanceDao.updateBalance(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						customerBalance.setGoldBalance(NumberUtils.add(customerBalance.getGoldBalance(), (double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(customerBalanceHisDao.insert(Mockito.any(CustomerBalanceHis.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						customerBalanceHis.setChangeVal(((CustomerBalanceHis)invocation.getArguments()[0]).getChangeVal());
						customerBalanceHis.setChangeType(((CustomerBalanceHis)invocation.getArguments()[0]).getChangeType());
						return null;
					}
				}
			);
		Mockito.when(customerAccountDao.get(Mockito.anyLong())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return customerAccount;
					}
				}
			);
		Mockito.when(customerBalanceDao.get(Mockito.anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return customerBalance;
					}
				}
			);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("interest", pollInterest);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/pollInterest", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		
		Assert.assertTrue("测试提取利息成功-->提取利息成功标志为真", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试提取利息成功-->活期账户总览数据正确", NumberUtils.compare(NumberUtils.add(beforeCurrentAccountSummary.getTotalRedeemInterest(), pollInterest), currentAccountSummary.getTotalRedeemInterest()) == 0);
		Assert.assertTrue("测试提取利息成功-->活期产品持有数据正确", NumberUtils.compare(NumberUtils.add(currentProjectHoldInfo.getInterest(), pollInterest), beforeCurrentProjectHoldInfo.getInterest()) == 0);
		Assert.assertTrue("测试提取利息成功-->活期账户利息变更历史数据正确", currentAccountInterestChangeHis.getChangeType().equals(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_REDEEM) && NumberUtils.compare(NumberUtils.mul(currentAccountInterestChangeHis.getChangeValue(), -1d), pollInterest) == 0);
		Assert.assertTrue("测试提取利息成功-->活期项目执行快照数据正确", NumberUtils.compare(NumberUtils.add(beforeCurrentProjectExecuteSnapshot.getHasRedeemInterest(), pollInterest), currentProjectExecuteSnapshot.getHasRedeemInterest()) == 0);
		Assert.assertTrue("测试提取利息成功-->会员账户余额汇总数据正确", NumberUtils.compare(NumberUtils.add(beforeCustomerBalance.getGoldBalance(), pollInterest), customerBalance.getGoldBalance()) == 0);
		Assert.assertTrue("测试提取利息成功-->会员账户余额变更流水数据正确", NumberUtils.compare(customerBalanceHis.getChangeVal(), pollInterest) == 0 && customerBalanceHis.getChangeType().equals(ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_POLL_INTEREST));
	}
	
	/**
	 * 测试提取利息失败：参数错误
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPollInterestFailedByArgumentError() throws Exception {
		login("customer1", "123123");
		Long projectId = -1L;
		Double pollInterest = -99d;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("interest", pollInterest);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/pollInterest", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertFalse("测试提取利息失败：参数错误-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试提取利息失败：参数错误-->申请赎回失败提示正确", "参数错误".equals((String)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("message")));
	}
	
	/**
	 * 测试提取利息失败：提取利息大于持有利息
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testPollInterestFailedByPollInterestBiggerThenHaveInterest() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double principal = 151d;
		Double applyRedeemPrincipal = 52d;
		Double interest = 103d;
		Double pollInterest = 103.33d;
		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
			.thenReturn(currentProjectHoldInfo);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("interest", pollInterest);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/pollInterest", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertFalse("测试提取利息失败：参数错误-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试提取利息失败：参数错误-->申请赎回失败提示正确", "提取利息大于持有利息".equals((String)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("message")));
	}

	/**
	 * 测试申请赎回成功
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyRedeemSuccess() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double principal = 151d;
		Double applyRedeemPrincipal = 52d;
		Double interest = 103d;
		Double redeemPrincipal = 99d;
		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		CurrentProjectHoldInfo beforeCurrentProjectHoldInfo = 
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		List<CurrentProjectRedemptionApply> applyList = new ArrayList<CurrentProjectRedemptionApply>();
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
			.thenReturn(currentProjectHoldInfo);
		Mockito.when(currentProjectHoldInfoDao.doRedeemPrincipal(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(redeemPrincipal))).then(
				new org.mockito.stubbing.Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentProjectHoldInfo.setApplyRedeemPrincipal(currentProjectHoldInfo.getApplyRedeemPrincipal() + redeemPrincipal);
						return null;
					}
				}
			);
		Mockito.when(currentProjectRedemptionApplyDao.insert(Mockito.any())).then(
				new org.mockito.stubbing.Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						applyList.add((CurrentProjectRedemptionApply)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("principal", redeemPrincipal);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		Mockito.verify(currentProjectHoldInfoDao, Mockito.times(2)).getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		Mockito.verify(currentProjectHoldInfoDao).doRedeemPrincipal(accountId, projectId, redeemPrincipal);
		Mockito.verify(currentProjectRedemptionApplyDao).insert(Mockito.anyObject());
		Assert.assertTrue("测试申请赎回成功-->申请赎回成功标志为真", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		CurrentProjectHoldInfo afterCurrentProjectHoldInfo = currentProjectHoldInfoDao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		Assert.assertEquals("测试申请赎回成功-->持有本金不变", afterCurrentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getPrincipal(), 0);
		Assert.assertEquals("测试申请赎回成功-->申请赎回本金变化正确", currentProjectHoldInfo.getApplyRedeemPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal() + redeemPrincipal, 0);
		Assert.assertEquals("测试申请赎回成功-->持有利息不变", currentProjectHoldInfo.getInterest(), beforeCurrentProjectHoldInfo.getInterest(), 0);
		Assert.assertTrue("测试申请赎回成功-->赎回申请记录添加正确", applyList.get(applyList.size() -1).getRedeemPrincipal().equals(redeemPrincipal)
				&& applyList.get(applyList.size() -1).getRedeemInterest().equals(0d)
				&& applyList.get(applyList.size() -1).getStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_DOING));
	}
	
	/**
	 * 测试申请赎回失败：参数错误
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyRedeemFailedByArgumentError() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double beforePrincipal = 151d;
		Double beforeApplyRedeemPrincipal = 52d;
		Double beforeInterest = 103d;
		Double redeemPrincipal = -104d;
		
		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, beforePrincipal, beforeApplyRedeemPrincipal, beforeInterest);
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
		.thenReturn(currentProjectHoldInfo);
		
		Mockito.when(currentProjectHoldInfoDao.doRedeemPrincipal(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(redeemPrincipal))).thenReturn(null);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("principal", redeemPrincipal);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertFalse("测试申请赎回失败：参数错误-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试申请赎回失败：参数错误-->申请赎回失败提示正确", "参数错误".equals((String)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("message")));
	}

	/**
	 * 测试申请赎回失败：申请赎回金额大于可赎回金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyRedeemFailedByAmountTooBig() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double beforePrincipal = 151d;
		Double beforeApplyRedeemPrincipal = 52d;
		Double beforeInterest = 103d;
		Double redeemPrincipal = 154d;
		
		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, beforePrincipal, beforeApplyRedeemPrincipal, beforeInterest);
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL))).thenReturn(currentProjectHoldInfo);
		
		Mockito.when(currentProjectHoldInfoDao.doRedeemPrincipal(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(redeemPrincipal))).thenReturn(null);
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("projectId", projectId);
		params.put("principal", redeemPrincipal);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params)).andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertFalse("测试申请赎回失败：申请赎回金额大于可赎回金额-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试申请赎回失败：申请赎回金额大于可赎回金额-->申请赎回失败提示正确", "申请赎回金额大于可赎回金额".equals((String)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("message")));
	}

	/**
	 * 测试申请赎回失败：单次申请赎回金额大于单日最大赎回金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyRedeemFailedByAmountOutCanApplyAmountOneDay1() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double principal = 60001d;
		Double applyRedeemPrincipal = 1002d;
		Double interest = 30003d;
		Double redeemPrincipal = 50004d;
		
		CurrentProjectHoldInfo currentProjectHoldInfo = createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
			.thenReturn(currentProjectHoldInfo);
		Map<String,Object> params1 = new HashMap<String,Object>();
		params1.put("projectId", projectId);
		params1.put("principal", redeemPrincipal);
		MvcResult result = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params1)).andDo(MockMvcResultHandlers.print()).andReturn();
		Mockito.verify(currentProjectHoldInfoDao).getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		Assert.assertFalse("测试申请赎回失败：单次申请赎回金额大于单日最大赎回金额-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试申请赎回失败：单次申请赎回金额大于单日最大赎回金额-->申请赎回失败提示正确", "单次申请赎回金额大于单日最大赎回金额".equals((String)((Map<String,Object>)result.getModelAndView().getModel().get("result")).get("message")));
	}

	/**
	 * 测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testApplyRedeemFailedByAmountOutCanApplyAmountOneDay2() throws Exception {
		login("customer1", "123123");
		Long accountId = CustomerUtils.getCurrentAccountId();
		Long projectId = 1L;
		Double principal = 60001d;
		Double applyRedeemPrincipal = 1002d;
		Double interest = 30003d;
		Double redeemPrincipal1 = 30004d;
		Double redeemPrincipal2 = 20006d;
		CurrentProjectHoldInfo currentProjectHoldInfo =
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		CurrentProjectHoldInfo beforeCurrentProjectHoldInfo = 
				createCurrentProjectHoldInfo(accountId, projectId, principal, applyRedeemPrincipal, interest);
		List<CurrentProjectRedemptionApply> applyList = new ArrayList<CurrentProjectRedemptionApply>();
		
		Mockito.when(currentProjectHoldInfoDao.getByAccountIdAndProjectId(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)))
			.thenReturn(currentProjectHoldInfo);
		Mockito.when(currentProjectHoldInfoDao.doRedeemPrincipal(Matchers.eq(accountId), Matchers.eq(projectId), Matchers.eq(redeemPrincipal1))).then(
				new org.mockito.stubbing.Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentProjectHoldInfo.setApplyRedeemPrincipal(currentProjectHoldInfo.getApplyRedeemPrincipal() + redeemPrincipal1);
						return null;
					}
				}
			);
		Mockito.when(currentProjectRedemptionApplyDao.insert(Mockito.any())).then(
				new org.mockito.stubbing.Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						applyList.add((CurrentProjectRedemptionApply)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Mockito.when(currentProjectRedemptionApplyDao.findListByAccountId(Matchers.eq(accountId), Mockito.any(Date.class), Mockito.any(String[].class))).thenReturn(applyList);
		Map<String,Object> params1 = new HashMap<String,Object>();
		params1.put("projectId", projectId);
		params1.put("principal", redeemPrincipal1);
		MvcResult result1 = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params1)).andDo(MockMvcResultHandlers.print()).andReturn();
		Mockito.verify(currentProjectHoldInfoDao, Mockito.times(2)).getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		Mockito.verify(currentProjectHoldInfoDao).doRedeemPrincipal(accountId, projectId, redeemPrincipal1);
		Mockito.verify(currentProjectRedemptionApplyDao).insert(Mockito.anyObject());
		Assert.assertTrue("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->申请赎回成功标志为真", (Boolean)((Map<String,Object>)result1.getModelAndView().getModel().get("result")).get("isSuccess"));
		CurrentProjectHoldInfo afterCurrentProjectHoldInfo = currentProjectHoldInfoDao.getByAccountIdAndProjectId(accountId, projectId, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
		Assert.assertEquals("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->持有本金不变", afterCurrentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getPrincipal(), 0);
		Assert.assertEquals("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->申请赎回本金变化正确", currentProjectHoldInfo.getApplyRedeemPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal() + redeemPrincipal1, 0);
		Assert.assertEquals("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->持有利息变化正确", currentProjectHoldInfo.getInterest(), beforeCurrentProjectHoldInfo.getInterest(), 0);
		Assert.assertTrue("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->赎回申请记录添加正确", applyList.get(applyList.size() -1).getRedeemPrincipal().equals(redeemPrincipal1)
				&& applyList.get(applyList.size() -1).getRedeemInterest().equals(0d)
				&& applyList.get(applyList.size() -1).getStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_DOING));
		
		Map<String,Object> params2 = new HashMap<String,Object>();
		params2.put("projectId", projectId);
		params2.put("principal", redeemPrincipal2);
		MvcResult result2 = mockMvc.perform(mockGet("/f/currentAccount/applyRedeemPrincipal", params2)).andDo(MockMvcResultHandlers.print()).andReturn();
		Assert.assertFalse("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->申请赎回成功标志为假", (Boolean)((Map<String,Object>)result2.getModelAndView().getModel().get("result")).get("isSuccess"));
		Assert.assertTrue("测试申请赎回失败：当日累计申请赎回金额大于单日最大赎回金额-->申请赎回失败提示正确", "当日累计申请赎回金额大于单日最大赎回金额".equals((String)((Map<String,Object>)result2.getModelAndView().getModel().get("result")).get("message")));
	}
	
	private CurrentProjectHoldInfo createCurrentProjectHoldInfo(Long accountId, Long projectId, Double principal, Double applyRedeemPrincipal, Double interest) {
		CurrentProjectHoldInfo currentProjectHoldInfo = new CurrentProjectHoldInfo();
		currentProjectHoldInfo.setId("1");
		currentProjectHoldInfo.setAccountId(accountId);
		currentProjectHoldInfo.setProjectId(projectId);
		currentProjectHoldInfo.setPrincipal(principal);
		currentProjectHoldInfo.setApplyRedeemPrincipal(applyRedeemPrincipal);
		currentProjectHoldInfo.setInterest(interest);
		return currentProjectHoldInfo;
	}

	@Override
	public void clear() {
		
	}
}