package com.thinkgem.jeesite.modules.task;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyDouble;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.thinkgem.jeesite.modules.FrontBaseTest;
import com.thinkgem.jeesite.modules.YeepayCommonHandlerTest;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectInfoService;
import com.thinkgem.jeesite.modules.current.task.CurrentProjectAutoDoWindingUpTask;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

/**
 * 测试完成清盘任务
 * @author ydt
 *
 */
public class TestCurrentProjectAutoDoWindingUpTask extends FrontBaseTest{
	@Mock
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Mock
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	@Mock
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Mock
	private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
	@Mock
	private CurrentAccountInterestChangeHisDao currentAccountInterestChangeHisDao;
	@InjectMocks
	private CurrentProjectInfoService currentProjectInfoService;

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
	private CurrentProjectAutoDoWindingUpTask currentProjectAutoDoWindingUpTask;
	
	private List<CurrentProjectInfo> currentProjectInfoList;
	private List<CurrentProjectInfo> beforeCurrentProjectInfoList;
	private List<CustomerBalance> customerBalanceList;
	private List<CustomerBalance> beforeCustomerBalanceList;
	private List<CurrentProjectHoldInfo> currentProjectHoldInfoList;
	private List<CurrentProjectHoldInfo> beforeCurrentProjectHoldInfoList;
	private List<CurrentAccountSummary> currentAccountSummaryList;
	private List<CurrentAccountSummary> beforeCurrentAccountSummaryList;
	private List<CurrentAccountPrincipalChangeHis> currentAccountPrincipalChangeHisList = new ArrayList<CurrentAccountPrincipalChangeHis>();
	private List<CurrentAccountInterestChangeHis> currentAccountInterestChangeHisList = new ArrayList<CurrentAccountInterestChangeHis>();
	private List<CustomerBalanceHis> customerBalanceHisList = new ArrayList<CustomerBalanceHis>();
	private List<CustomerAccount> customerAccountList;
	
	@Override
	public void setUp() {
		reset(currentProjectInfoDao);
		reset(currentProjectHoldInfoDao);
		reset(currentAccountSummaryDao);
		reset(currentAccountPrincipalChangeHisDao);
		reset(currentAccountInterestChangeHisDao);
		Mockito.reset(customerAccountDao);
		Mockito.reset(customerBalanceDao);
		Mockito.reset(customerBalanceHisDao);
		setField(currentProjectInfoService, currentProjectInfoDao, FILED_DAO);
		setField(currentProjectAutoDoWindingUpTask, currentProjectInfoService);
		setField(yeepayCommonHandlerTest, customerBalanceHandler);
		setField(currentProjectInfoService, yeepayCommonHandlerTest, "yeepayCommonHandler");
		random = new Random(47);
		currentProjectInfoList = createCurrentProjectInfoList();
		customerBalanceList = createCustomerBalanceList();
		currentProjectHoldInfoList= createCurrentProjectHoldInfoList();
		currentAccountSummaryList = createCurrentAccountSummaryList();

		random = new Random(47);
		beforeCurrentProjectInfoList = createCurrentProjectInfoList();
		beforeCustomerBalanceList = createCustomerBalanceList();
		beforeCurrentProjectHoldInfoList= createCurrentProjectHoldInfoList();
		beforeCurrentAccountSummaryList = createCurrentAccountSummaryList();
		
		customerAccountList = createCustomerAccountList();
	}

	/**
	 * 测试清盘方法
	 */
	@Test
	public void testJob() {
		when(currentProjectInfoDao.findListByWindingUpStatus(anyString())).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						List<CurrentProjectInfo> cpi = new ArrayList<CurrentProjectInfo>();
						for(CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
							if(currentProjectInfo.getWindingUpStatus().equals((String)invocation.getArguments()[0])) {
								cpi.add(currentProjectInfo);
							}
						}
						return cpi;
					}
				}
			);
		when(currentProjectInfoDao.updateByWindingUp(anyString())).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentProjectInfo currentProjectInfo = getById((String)invocation.getArguments()[0]);
						currentProjectInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_STATUS_WINDING_UPED);
						currentProjectInfo.setWindingUpStatus(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_WINDING_UPED);
						return null;
					}
				}
			);
		when(currentProjectHoldInfoDao.findListByProjectIdAndStatus(anyString(), anyString())).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						List<CurrentProjectHoldInfo> holdList = new ArrayList<CurrentProjectHoldInfo>();
						for(CurrentProjectHoldInfo currentProjectHoldInfo : currentProjectHoldInfoList) {
							if(((String)invocation.getArguments()[0]).equals(String.valueOf(currentProjectHoldInfo.getProjectId())) && currentProjectHoldInfo.getStatus().equals((String)invocation.getArguments()[1])) {
								holdList.add(currentProjectHoldInfo);
							}
						}
						return holdList;
					}
				}
			);
		when(currentProjectHoldInfoDao.updateByWindingUp(anyString())).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentProjectHoldInfo currentProjectHoldInfo = getCurrentProjectHoldInfoById((String)invocation.getArguments()[0]);
						currentProjectHoldInfo.setPrincipal(NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal()));
						currentProjectHoldInfo.setInterest(0d);
						currentProjectHoldInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_WINDING_UPED);
						return null;
					}
				}
			);
		when(currentAccountSummaryDao.updateByWindingUp(anyLong(), anyDouble(), anyDouble())).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentAccountSummary currentAccountSummary = getByAccountId((Long)invocation.getArguments()[0]);
						currentAccountSummary.setTotalRedeemPrincipal(NumberUtils.add(currentAccountSummary.getTotalRedeemPrincipal(), (Double)invocation.getArguments()[1]));
						currentAccountSummary.setTotalRedeemInterest(NumberUtils.add(currentAccountSummary.getTotalRedeemInterest(), (Double)invocation.getArguments()[2]));
						currentAccountSummary.setCurrentPrincipal(NumberUtils.sub(currentAccountSummary.getCurrentPrincipal(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		when(currentAccountPrincipalChangeHisDao.insert(any(CurrentAccountPrincipalChangeHis.class))).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentAccountPrincipalChangeHisList.add((CurrentAccountPrincipalChangeHis)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		when(currentAccountInterestChangeHisDao.insert(any(CurrentAccountInterestChangeHis.class))).then(
				new Answer<Object>() {
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentAccountInterestChangeHisList.add((CurrentAccountInterestChangeHis)invocation.getArguments()[0]);
						return null;
					}
				}
			);

		Mockito.when(customerBalanceDao.updateBalance(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CustomerBalance customerBalance = getCustomerBalanceByAccountId((Long)invocation.getArguments()[0]);
						customerBalance.setGoldBalance(NumberUtils.add(customerBalance.getGoldBalance(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(customerBalanceHisDao.insert(Mockito.any(CustomerBalanceHis.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						customerBalanceHisList.add((CustomerBalanceHis)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Mockito.when(customerBalanceDao.get(Mockito.anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return getCustomerBalanceByAccountId(Long.parseLong((String)invocation.getArguments()[0]));
					}
				}
			);
		Mockito.when(customerAccountDao.get(Mockito.anyLong())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return getCustomerAccountByAccountId((Long)invocation.getArguments()[0]);
					}
				}
			);
		currentProjectAutoDoWindingUpTask.job();
		assertTrue("活期项目信息数据正确", verifyCurrentProjectInfoDateCorrect());
		assertTrue("活期产品持有信息数据正确", verifyCurrentProjectHoldInfoDateCorrect());
		assertTrue("活期账户总览数据正确", verifyCurrentAccountSummaryDateCorrect());
		assertTrue("活期账户本金变更历史数据正确", verifyCurrentAccountPrincipalChangeHisDateCorrect());
		assertTrue("活期账户利息变更历史数据正确", verifyCurrentAccountInterestChangeHisDateCorrect());
		assertTrue("会员账户余额汇总数据正确", verifyCustomerBalanceDataCorrect());
		assertTrue("会员账户余额变更流水数据正确", verifyCustomerBalanceHisDataCorrect());
	}

	protected Object getCustomerAccountByAccountId(Long accountId) {
		for(CustomerAccount customerAccount : customerAccountList) {
			if(NumberUtils.compare(customerAccount.getAccountId(), accountId) == 0) {
				return customerAccount;
			}
		}
		return null;
	}

	private boolean verifyCustomerBalanceHisDataCorrect() {
		int shouldInsertRecord = 0;
		double shouldChangeValue = 0;
		double hasChangeValue = 0;
		for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
			CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
			if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())) {
				double changeValue = NumberUtils.add(NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()),
						NumberUtils.formatWithTwoScale(beforeCurrentProjectHoldInfo.getInterest()));
				if(changeValue != 0) {
					shouldChangeValue = NumberUtils.add(shouldChangeValue, changeValue);
					shouldInsertRecord++;
				}
			}
		}
		for(CustomerBalanceHis customerBalanceHis : customerBalanceHisList) {
			hasChangeValue = NumberUtils.add(hasChangeValue, customerBalanceHis.getChangeVal());
		}
		return shouldInsertRecord == customerBalanceHisList.size() && shouldChangeValue == hasChangeValue;
	}

	private boolean verifyCustomerBalanceDataCorrect() {
		for(CustomerBalance beforeCustomerBalance : beforeCustomerBalanceList) {
			CustomerBalance customerBalance = getCustomerBalanceByAccountId(beforeCustomerBalance.getAccountId());
			double deltaPrincipal = 0;
			double deltaInterestWithTwoScale = 0d;
			for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
				CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
				if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())
						&& NumberUtils.compare(beforeCurrentProjectHoldInfo.getAccountId(), beforeCustomerBalance.getAccountId()) == 0) {
					deltaPrincipal = NumberUtils.add(deltaPrincipal, NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()));
					deltaInterestWithTwoScale = NumberUtils.add(deltaInterestWithTwoScale, NumberUtils.formatWithTwoScale(beforeCurrentProjectHoldInfo.getInterest()));
				}
			}
			if(!customerBalance.getGoldBalance().equals(NumberUtils.add(deltaPrincipal, deltaInterestWithTwoScale, beforeCustomerBalance.getGoldBalance()))) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyCurrentAccountInterestChangeHisDateCorrect() {
		int shouldInsertRecord = 0;
		double shouldChangeValue = 0;
		double hasChangeValue = 0;
		for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
			CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
			if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())
					&& !beforeCurrentProjectHoldInfo.getInterest().equals(0d)) {
				shouldChangeValue = NumberUtils.add(shouldChangeValue, NumberUtils.formatWithTwoScale(beforeCurrentProjectHoldInfo.getInterest()));
				shouldInsertRecord++;
			}
		}
		for(CurrentAccountInterestChangeHis currentAccountInterestChangeHis : currentAccountInterestChangeHisList) {
			hasChangeValue = NumberUtils.add(hasChangeValue, currentAccountInterestChangeHis.getChangeValue());
		}
		return currentAccountInterestChangeHisList.size() == shouldInsertRecord && hasChangeValue == shouldChangeValue;
	}

	private boolean verifyCurrentAccountPrincipalChangeHisDateCorrect() {
		int shouldInsertRecord = 0;
		double shouldChangeValue = 0;
		double hasChangeValue = 0;
		for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
			CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
			if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())
					&& !NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()).equals(0d)) {
				shouldChangeValue = NumberUtils.add(shouldChangeValue, NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()));
				shouldInsertRecord++;
			}
		}
		for(CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis : currentAccountPrincipalChangeHisList) {
			hasChangeValue = NumberUtils.add(hasChangeValue, currentAccountPrincipalChangeHis.getChangeValue());
		}
		return currentAccountPrincipalChangeHisList.size() == shouldInsertRecord && hasChangeValue == shouldChangeValue;
	}

	private boolean verifyCurrentAccountSummaryDateCorrect() {
		for(CurrentAccountSummary beforeCurrentAccountSummary : beforeCurrentAccountSummaryList) {
			CurrentAccountSummary currentAccountSummary = getByAccountId(beforeCurrentAccountSummary.getAccountId());
			double deltaPrincipal = 0;
			double deltaInterest = 0;
			double deltaInterestWithTwoScale = 0d;
			for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
				CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
				if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())
						&& NumberUtils.compare(beforeCurrentProjectHoldInfo.getAccountId(), beforeCurrentAccountSummary.getAccountId()) == 0) {
					deltaPrincipal = NumberUtils.add(deltaPrincipal, NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()));
					deltaInterest = NumberUtils.add(deltaInterest, beforeCurrentProjectHoldInfo.getInterest());
					deltaInterestWithTwoScale = NumberUtils.add(deltaInterestWithTwoScale, NumberUtils.formatWithTwoScale(beforeCurrentProjectHoldInfo.getInterest()));
				}
			}
			if(!NumberUtils.add(beforeCurrentAccountSummary.getTotalRedeemPrincipal(), deltaPrincipal).equals(currentAccountSummary.getTotalRedeemPrincipal())
					|| !NumberUtils.add(beforeCurrentAccountSummary.getTotalRedeemInterest(), deltaInterestWithTwoScale).equals(currentAccountSummary.getTotalRedeemInterest())
					|| !NumberUtils.add(currentAccountSummary.getCurrentPrincipal(), deltaPrincipal).equals(beforeCurrentAccountSummary.getCurrentPrincipal())) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyCurrentProjectHoldInfoDateCorrect() {
		for(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
			CurrentProjectHoldInfo currentProjectHoldInfo = getByAccountIdAndProjectId(beforeCurrentProjectHoldInfo.getAccountId(), beforeCurrentProjectHoldInfo.getProjectId());
			CurrentProjectInfo beforeCurrentProjectInfo = getById(beforeCurrentProjectInfoList, String.valueOf(beforeCurrentProjectHoldInfo.getProjectId()));
			if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())) {
				if(NumberUtils.compare(NumberUtils.sub(beforeCurrentProjectHoldInfo.getPrincipal(), beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal()), currentProjectHoldInfo.getPrincipal()) != 0
						|| NumberUtils.compare(currentProjectHoldInfo.getInterest(), 0d) != 0) {
					return false;
				}
			}
		}
		return true;
	}

	private CurrentProjectHoldInfo getByAccountIdAndProjectId(Long accountId, Long projectId) {
		for(CurrentProjectHoldInfo currentProjectHoldInfo : currentProjectHoldInfoList) {
			if(currentProjectHoldInfo.getAccountId().equals(accountId) && currentProjectHoldInfo.getProjectId().equals(projectId)) {
				return currentProjectHoldInfo;
			}
		}
		return null;
	}

	private boolean verifyCurrentProjectInfoDateCorrect() {
		for(CurrentProjectInfo beforeCurrentProjectInfo : beforeCurrentProjectInfoList) {
			CurrentProjectInfo currentProjectInfo = getById(beforeCurrentProjectInfo.getId());
			if(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS.equals(beforeCurrentProjectInfo.getWindingUpStatus())) {
				if(!currentProjectInfo.getWindingUpStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_WINDING_UPED)
						|| !currentProjectInfo.getStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_STATUS_WINDING_UPED)) {
					return false;
				}
			}else if(!currentProjectInfo.getWindingUpStatus().equals(beforeCurrentProjectInfo.getWindingUpStatus())) {
				return false;
			}
		}
		return true;
	}

	private CurrentProjectInfo getById(String id) {
		for(CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
			if(currentProjectInfo.getId().equals(id)) {
				return currentProjectInfo;
			}
		}
		return null;
	}

	private CurrentProjectInfo getById(List<CurrentProjectInfo> currentProjectInfoList, String id) {
		for(CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
			if(currentProjectInfo.getId().equals(id)) {
				return currentProjectInfo;
			}
		}
		return null;
	}

	private CurrentAccountSummary getByAccountId(Long accountId) {
		for(CurrentAccountSummary currentAccountSummary : currentAccountSummaryList) {
			if(NumberUtils.compare(currentAccountSummary.getAccountId(), accountId) == 0) {
				return currentAccountSummary;
			}
		}
		return null;
	}
	
	protected CurrentProjectHoldInfo getCurrentProjectHoldInfoById(String id) {
		for(CurrentProjectHoldInfo currentProjectHoldInfo : currentProjectHoldInfoList) {
			if(currentProjectHoldInfo.getId().equals(id)) {
				return currentProjectHoldInfo;
			}
		}
		return null;
	}

	private CustomerBalance getCustomerBalanceByAccountId(Long accountId) {
		for(CustomerBalance customerBalance : customerBalanceList) {
			if(customerBalance.getAccountId().equals(accountId)) {
				return customerBalance;
			}
		}
		return null;
	}

	@Override
	public void clear() {
		
	}
	
	private List<CurrentProjectInfo> createCurrentProjectInfoList() {
		List<CurrentProjectInfo> currentProjectInfoList = new ArrayList<CurrentProjectInfo>();
		for(int i = 1; i < 15; i++) {
			CurrentProjectInfo currentProjectInfo = new CurrentProjectInfo();
			currentProjectInfo.setId(String.valueOf(i));
			currentProjectInfo.setWindingUpStatus(i % 2 == 0 ? CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_NO_APPLY : CurrentProjectConstant.CURRENT_PROJECT_WINDING_UP_STATUS_REVIEW_PASS);
			currentProjectInfoList.add(currentProjectInfo);
		}
		return currentProjectInfoList;
	}

	private List<CustomerBalance> createCustomerBalanceList() {
		List<CustomerBalance> customerBalanceList = new ArrayList<CustomerBalance>();
		for(int i = 1; i < 9; i++) {
			CustomerBalance customerBalance = new CustomerBalance();
			customerBalance.setAccountId(new Long(i));
			customerBalance.setGoldBalance(randomDoubleWithTwoScale());
			customerBalanceList.add(customerBalance);
		}
		return customerBalanceList;
	}

	private List<CurrentProjectHoldInfo> createCurrentProjectHoldInfoList() {
		List<CurrentProjectHoldInfo> currentProjectHoldInfoList = new ArrayList<CurrentProjectHoldInfo>();
		int i = 0;
		for(CustomerBalance customerBalance : customerBalanceList) {
			for(CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
				i++;
				CurrentProjectHoldInfo currentProjectHoldInfo = new CurrentProjectHoldInfo();
				currentProjectHoldInfo.setId(String.valueOf(i));
				currentProjectHoldInfo.setAccountId(customerBalance.getAccountId());
				currentProjectHoldInfo.setProjectId(Long.parseLong(currentProjectInfo.getId()));
				double principal = NumberUtils.add(randomDoubleWithTwoScale(), 20000d);
				double applyRedeemPrincipal = randomDoubleWithTwoScale();
				currentProjectHoldInfo.setPrincipal(i % 3 == 0 ? 0d : principal);
				currentProjectHoldInfo.setInterest(i % 4 == 0 ? 0d : randomDoubleWithFourScale());
				currentProjectHoldInfo.setApplyRedeemPrincipal(i % 3 == 0 ? 0d : applyRedeemPrincipal > principal ? principal : applyRedeemPrincipal);
				currentProjectHoldInfo.setStatus(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
				currentProjectHoldInfoList.add(currentProjectHoldInfo);
			}
		}
		return currentProjectHoldInfoList;
	}

	private List<CurrentAccountSummary> createCurrentAccountSummaryList() {
		List<CurrentAccountSummary> currentAccountSummaryList = new ArrayList<CurrentAccountSummary>();
		for(CustomerBalance customerBalance : customerBalanceList) {
			CurrentAccountSummary currentAccountSummary = new CurrentAccountSummary();
			currentAccountSummary.setAccountId(customerBalance.getAccountId());
			currentAccountSummary.setTotalRedeemPrincipal(randomDoubleWithTwoScale());
			currentAccountSummary.setTotalRedeemInterest(randomDoubleWithTwoScale());
			currentAccountSummary.setCurrentPrincipal(randomDoubleWithTwoScale());
			currentAccountSummaryList.add(currentAccountSummary);
		}
		return currentAccountSummaryList;
	}

	private List<CustomerAccount> createCustomerAccountList() {
		List<CustomerAccount> customerAccountList = new ArrayList<CustomerAccount>();
		for(CustomerBalance customerBalance : customerBalanceList) {
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setAccountId(customerBalance.getAccountId());
			customerAccount.setHasOpenThirdAccount(ProjectConstant.HASOPENED);
			customerAccountList.add(customerAccount);
		}
		return customerAccountList;
	}
}
