package com.thinkgem.jeesite.modules.project.task;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.entity.MarketingActivityAwardRecord;
import com.thinkgem.jeesite.modules.entity.MarketingActivityInfo;
import com.thinkgem.jeesite.modules.entity.ProjectInvestmentRecord;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityAwardRecordDao;
import com.thinkgem.jeesite.modules.marketing.dao.MarketingActivityInfoDao;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityAwardRecordService;
import com.thinkgem.jeesite.modules.marketing.service.MarketingActivityInfoService;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import com.thinkgem.jeesite.modules.project.service.task.AutoGiveDoubleEggPrize;

public class TestAutoGiveDoubleEggPrize extends FrontBaseTest {
	
	@Mock
	private MarketingActivityAwardRecordDao marketingActivityAwardRecordDao;
	@Mock
	private ProjectInvestmentRecordDao projectInvestmentRecordDao;
	@Mock
	private MarketingActivityInfoDao marketingActivityInfoDao;
	@InjectMocks
	private MarketingActivityAwardRecordService marketingActivityAwardRecordService;
	@InjectMocks
	private ProjectInvestmentRecordService projectInvestmentRecordService;
	@InjectMocks
	private MarketingActivityInfoService marketingActivityInfoService;
	
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
	private AutoGiveDoubleEggPrize autoGiveDoubleEggPrize;
	
	private CustomerAccount awardAccount;
	private List<CustomerAccount> customerAccountList;
	private List<ProjectInvestmentRecord> projectInvestmentRecordList;
	private List<CustomerBalanceHis> customerBalanceHisList = new ArrayList<CustomerBalanceHis>();
	private CustomerBalance customerBalance;
	private CustomerBalance beforeCustomerBalance;
	private List<MarketingActivityAwardRecord> marketingActivityAwardRecordList;
	private List<MarketingActivityAwardRecord> beforeMarketingActivityAwardRecordList;
	private MarketingActivityInfo marketingActivityInfo;

	@Override
	public void setUp() {
		reset(marketingActivityAwardRecordDao);
		reset(projectInvestmentRecordDao);
		reset(customerAccountDao);
		reset(customerBalanceDao);
		reset(customerBalanceHisDao);
		reset(marketingActivityInfoDao);
		setField(marketingActivityAwardRecordService, marketingActivityAwardRecordDao, FILED_DAO);
		setField(projectInvestmentRecordService, projectInvestmentRecordDao, FILED_DAO);
		setField(marketingActivityInfoService, marketingActivityInfoDao, FILED_DAO);
		setField(autoGiveDoubleEggPrize, marketingActivityAwardRecordService);
		setField(autoGiveDoubleEggPrize, projectInvestmentRecordService);
		setField(autoGiveDoubleEggPrize, marketingActivityInfoService);
		setField(yeepayCommonHandlerTest, customerBalanceHandler);
		setField(marketingActivityAwardRecordService, yeepayCommonHandlerTest, "yeepayCommonHandler");
		
		marketingActivityInfo = createMarketingActivityInfo();
		awardAccount = createAwardAccount();
		customerAccountList = createCustomerAccountList();
		projectInvestmentRecordList = createProjectInvestmentRecordList();
		customerBalance = createCustomerBalance();
		beforeCustomerBalance = createCustomerBalance();
		marketingActivityAwardRecordList = createMarketingActivityAwardRecordList();
		beforeMarketingActivityAwardRecordList = createMarketingActivityAwardRecordList();
	}

	@Test
	public void testJob() {
		when(projectInvestmentRecordDao.findDoubleEggList(any(Date.class), any(Date.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						List<ProjectInvestmentRecord> recordList = new ArrayList<ProjectInvestmentRecord>();
						for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
							CustomerAccount customerAccount = getByAccountId(projectInvestmentRecord.getInvestmentUserId());
							if(isInRange(projectInvestmentRecord.getOpDt(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate())
									&& (projectInvestmentRecord.getStatus().equals("0") || projectInvestmentRecord.getStatus().equals("3"))
									&& projectInvestmentRecord.getAmount().doubleValue() >= 1000d
									&& isInRange(customerAccount.getRegisterDt(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate())
									&& projectInvestmentRecord.getTransferProjectId().longValue() == 0L) {
								recordList.add(projectInvestmentRecord);
							}
						}
						return recordList;
					}
				}
			);
		when(marketingActivityInfoDao.getByBizClassName(anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return marketingActivityInfo;
					}
				}
			);
		when(marketingActivityAwardRecordDao.insert(any(MarketingActivityAwardRecord.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						marketingActivityAwardRecordList.add((MarketingActivityAwardRecord)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Mockito.when(customerAccountDao.getRecommenderAccountIdByAccountId(anyLong())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CustomerAccount recommender = getRecommenderByAccountId((Long)invocation.getArguments()[0]);
						return recommender == null ? null : recommender.getAccountId();
					}
				}
			);
		Mockito.when(marketingActivityAwardRecordDao.findListByAccountIdAndActivityId(anyLong(), anyLong())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return marketingActivityAwardRecordList;
					}
				}
			);
		
		Mockito.when(customerBalanceDao.updateBalance(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
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
						return customerBalance;
					}
				}
			);
		Mockito.when(customerAccountDao.get(Mockito.anyLong())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return awardAccount;
					}
				}
			);
		autoGiveDoubleEggPrize.job();
		assertTrue("账户余额数据正确", verifyCustomerBalanceDataCorrect());
		assertTrue("账户余额历史数据正确", verifyCustomerBalanceHisDataCorrect());
		assertTrue("营销活动奖励记录数据正确", verifyMarketingActivityAwardRecordDataCorrect());
	}

	private boolean verifyCustomerBalanceDataCorrect() {
		double deltaAmount = 0;
		int j = 0;
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			if(isShouldAward(projectInvestmentRecord)) {
				double awardAmount = getAwardAmount(j++);
				deltaAmount = NumberUtils.add(deltaAmount, awardAmount);
			}
		}
		return customerBalance.getGoldBalance().equals(NumberUtils.add(beforeCustomerBalance.getGoldBalance(), deltaAmount));
	}
	
	private boolean verifyCustomerBalanceHisDataCorrect() {
		int shouldInsertRecord = 0;
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			if(isShouldAward(projectInvestmentRecord)) {
				shouldInsertRecord++;
			}
		}
		return customerBalanceHisList.size() == shouldInsertRecord;
	}
	
	private boolean verifyMarketingActivityAwardRecordDataCorrect() {
		int shouldInsertRecord = 0;
		double deltaAmount = 0;
		int j = 0;
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			if(isShouldAward(projectInvestmentRecord)) {
				double awardAmount = getAwardAmount(j++);
				deltaAmount = NumberUtils.add(deltaAmount, awardAmount);
				shouldInsertRecord++;
			}
		}
		double actualAmount = 0;
		for(MarketingActivityAwardRecord marketingActivityAwardRecord : marketingActivityAwardRecordList) {
			actualAmount = NumberUtils.add(actualAmount, Double.parseDouble(marketingActivityAwardRecord.getAwardValue()));
		}
		for(MarketingActivityAwardRecord beforeMarketingActivityAwardRecord : beforeMarketingActivityAwardRecordList) {
			actualAmount = NumberUtils.sub(actualAmount, Double.parseDouble(beforeMarketingActivityAwardRecord.getAwardValue()));
		}
		return (marketingActivityAwardRecordList.size() - beforeMarketingActivityAwardRecordList.size()) == shouldInsertRecord
				&& deltaAmount == actualAmount;
	}

	private boolean isShouldAward(ProjectInvestmentRecord projectInvestmentRecord) {
		CustomerAccount customerAccount = getByAccountId(projectInvestmentRecord.getInvestmentUserId());
		CustomerAccount recommender = getRecommenderByAccountId(projectInvestmentRecord.getInvestmentUserId());
		return (projectInvestmentRecord.getStatus().equals("0") || projectInvestmentRecord.getStatus().equals("3"))
			&& projectInvestmentRecord.getAmount().doubleValue() >= 1000d
			&& isInRange(projectInvestmentRecord.getOpDt(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate())
			&& isInRange(customerAccount.getRegisterDt(), marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate())
			&& projectInvestmentRecord.getTransferProjectId().equals(0L)
			&& !isAwarded(projectInvestmentRecord.getId())
			&& recommender != null
			&& recommender.getStatusCode().equals("0");
	}

	private double getAwardAmount(int hasAwardedTimes) {
		switch(hasAwardedTimes) {
		case 1: return 20d;
		case 2: return 30d;
		case 3: return 40d;
		case 4: return 50d;
		default: return 10d;
		}
	}
	
	private CustomerAccount getByAccountId(Long accountId) {
		if(awardAccount.getAccountId().equals(accountId)) {
			return awardAccount;
		}
		for(CustomerAccount customerAccount : customerAccountList) {
			if(customerAccount.getAccountId().equals(accountId)) {
				return customerAccount;
			}
		}
		return null;
	}

	private CustomerAccount getRecommenderByAccountId(Long accountId) {
		CustomerAccount customerAccount = getByAccountId(accountId);
		if(customerAccount.getRecommendAccountId() == null) {
			return null;
		}
		return getByAccountId(Long.parseLong(customerAccount.getRecommendAccountId()));
	}

	private boolean isInRange(Date date, Date beginDate, Date endDate) {
		if(date == null) {
			return false;
		}
		return date.getTime() >= beginDate.getTime() && date.getTime() <= endDate.getTime();
	}

	private boolean isAwarded(String projectInvestmentRecordId) {
		for(MarketingActivityAwardRecord marketingActivityAwardRecord : marketingActivityAwardRecordList) {
			if(marketingActivityAwardRecord.getCauseId().equals(projectInvestmentRecordId)) {
				return true;
			}
		}
		return false;
	}
	
	private CustomerAccount createAwardAccount() {
		CustomerAccount customerAccount = new CustomerAccount();
		customerAccount.setAccountId(1L);
		customerAccount.setHasOpenThirdAccount("1");
		customerAccount.setStatusCode("0");
		return customerAccount;
	}

	private CustomerBalance createCustomerBalance() {
		CustomerBalance customerBalance = new CustomerBalance();
		customerBalance.setAccountId(awardAccount.getAccountId());
		customerBalance.setGoldBalance(123.23);
		return customerBalance;
	}

	private List<CustomerAccount> createCustomerAccountList() {
		List<CustomerAccount> customerAccountList = new ArrayList<CustomerAccount>();
		for(int i = 2; i < 20; i++) {
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setAccountId(new Long(i));
			if(i % 5 != 0) {
				customerAccount.setRecommendAccountId(String.valueOf(awardAccount.getAccountId()));
			}
			if(i % 3 != 0) {
				customerAccount.setRegisterDt(randomDate(marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate()));
			}
			customerAccountList.add(customerAccount);
		}
		return customerAccountList;
	}

	private List<ProjectInvestmentRecord> createProjectInvestmentRecordList() {
		List<ProjectInvestmentRecord> projectInvestmentRecordList = new ArrayList<ProjectInvestmentRecord>();
		int i = 0;
		for(CustomerAccount investmentUserId : customerAccountList) {
			i++;
			ProjectInvestmentRecord projectInvestmentRecord = new ProjectInvestmentRecord();
			projectInvestmentRecord.setId(String.valueOf(i));
			projectInvestmentRecord.setInvestmentUserId(investmentUserId.getAccountId());
			if(i % 5 != 0) {
				projectInvestmentRecord.setTransferProjectId(0L);
			} else {
				projectInvestmentRecord.setTransferProjectId(1L);
			}
			if(i % 8 != 0) {
				projectInvestmentRecord.setOpDt(randomDate(marketingActivityInfo.getBeginDate(), marketingActivityInfo.getEndDate()));
			}
			if(i % 2 != 0 || i % 3 != 0) {
				projectInvestmentRecord.setAmount(1000d);
			} else {
				projectInvestmentRecord.setAmount(999d);
			}
			if(i % 6 == 0) {
				projectInvestmentRecord.setStatus("1");
			} else {
				projectInvestmentRecord.setStatus("0");
			}
			projectInvestmentRecordList.add(projectInvestmentRecord);
		}
		return projectInvestmentRecordList;
	}

	private List<MarketingActivityAwardRecord> createMarketingActivityAwardRecordList() {
		List<MarketingActivityAwardRecord> marketingActivityAwardRecordList = new ArrayList<MarketingActivityAwardRecord>();
		int i = 0;
		for(ProjectInvestmentRecord projectInvestmentRecord : projectInvestmentRecordList) {
			i++;
			if(i > 3) {
				break;
			}
			MarketingActivityAwardRecord marketingActivityAwardRecord = new MarketingActivityAwardRecord();
			marketingActivityAwardRecord.setCauseId(Long.parseLong(projectInvestmentRecord.getId()));
			marketingActivityAwardRecord.setAwardValue(String.valueOf((i + 1)));
		}
		return marketingActivityAwardRecordList;
	}

	private MarketingActivityInfo createMarketingActivityInfo() {
		MarketingActivityInfo marketingActivityInfo = new MarketingActivityInfo();
		marketingActivityInfo.setId("1");
		marketingActivityInfo.setBeginDate(new Date());
		marketingActivityInfo.setEndDate(new Date());
		return marketingActivityInfo;
	}
	
	@Override
	public void clear() {
		
	}

}
