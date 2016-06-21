package com.thinkgem.jeesite.modules.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.thinkgem.jeesite.modules.FrontBaseTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountInterestChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectRepayRecordDao;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectHoldInfoService;
import com.thinkgem.jeesite.modules.current.task.CurrentProjectAutoRepayInterestTask;
import com.thinkgem.jeesite.modules.entity.CurrentAccountInterestChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRepayRecord;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

public class TestCurrentProjectAutoRepayInterestTask extends FrontBaseTest {

	@Mock
	private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
	@Mock
	private CurrentAccountSummaryDao currentAccountSummaryDao;
	@Mock
	private CurrentProjectInfoDao currentProjectInfoDao;
	@Mock
	private CurrentAccountInterestChangeHisDao currentAccountInterestChangeHisDao;
	@Mock
	private CurrentProjectRepayRecordDao currentProjectRepayRecordDao;
	@Mock
	private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
	@InjectMocks
	private CurrentProjectHoldInfoService currentProjectHoldInfoService;
	@Autowired
	private CurrentProjectAutoRepayInterestTask currentProjectAutoRepayInterestTask;
	
	List<CurrentProjectInfo> projectList = null;
	List<CustomerAccount> accountList = null;
	List<CurrentProjectHoldInfo> holdList = null;
	List<CurrentAccountSummary> summaryList = null;

	List<CurrentProjectHoldInfo> beforeHoldList = null;
	List<CurrentAccountSummary> beforeSummaryList = null;
	
	List<CurrentAccountInterestChangeHis> currentAccountInterestChangeHisList = new ArrayList<CurrentAccountInterestChangeHis>();
	List<CurrentAccountInterestChangeHis> beforeCurrentAccountInterestChangeHisList = new ArrayList<CurrentAccountInterestChangeHis>();
	
	List<CurrentProjectRepayRecord> currentProjectRepayRecordList = null;

	List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshotList = null;
	List<CurrentProjectExecuteSnapshot> beforeCurrentProjectExecuteSnapshotList = null;

	@Override
	public void setUp() {
		Mockito.reset(currentProjectHoldInfoDao);
		Mockito.reset(currentAccountSummaryDao);
		Mockito.reset(currentProjectInfoDao);
		Mockito.reset(currentAccountInterestChangeHisDao);
		Mockito.reset(currentProjectRepayRecordDao);
		Mockito.reset(currentProjectExecuteSnapshotDao);
		setField(currentProjectHoldInfoService, currentProjectHoldInfoDao, "dao");
		setField(currentProjectAutoRepayInterestTask, currentProjectHoldInfoService);
		projectList = createProjectList();
		accountList = createAccountList();
		random = new Random(47);
		holdList = createHoldList();
		summaryList = createSummaryList();
		currentProjectExecuteSnapshotList = createcurrentProjectExecuteSnapshotList();
		//重置random为了生成相同内容的before对象
		random = new Random(47);
		beforeHoldList = createHoldList();
		beforeSummaryList = createSummaryList();
		beforeCurrentProjectExecuteSnapshotList = createcurrentProjectExecuteSnapshotList();

		CurrentAccountInterestChangeHis currentAccountInterestChangeHis = new CurrentAccountInterestChangeHis();
		currentAccountInterestChangeHis.setId("1");
		currentAccountInterestChangeHis.setAccountId(1L);
		currentAccountInterestChangeHis.setProjectId(1L);
		currentAccountInterestChangeHis.setChangeValue(1.2899);
		currentAccountInterestChangeHis.setChangeType(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST);
		currentAccountInterestChangeHis.setOpTerm(ProjectConstant.OP_TERM_DICT_PC);
		currentAccountInterestChangeHis.setOpDt(new Date());
		currentAccountInterestChangeHisList.add(currentAccountInterestChangeHis);
		currentAccountInterestChangeHisList = new ArrayList<CurrentAccountInterestChangeHis>();
		currentAccountInterestChangeHisList.add(currentAccountInterestChangeHis);
		beforeCurrentAccountInterestChangeHisList = new ArrayList<CurrentAccountInterestChangeHis>();
		beforeCurrentAccountInterestChangeHisList.add(currentAccountInterestChangeHis);
		
		currentProjectRepayRecordList = new ArrayList<CurrentProjectRepayRecord>();
	}

	@Test
	public void testJob() throws Exception {
		Mockito.when(currentProjectHoldInfoDao.findListByStatuses(Mockito.anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						List<CurrentProjectHoldInfo> result = new ArrayList<CurrentProjectHoldInfo>();
						for(CurrentProjectHoldInfo currentProjectHoldInfo : holdList) {
							if(currentProjectHoldInfo.getStatus().equals((String)invocation.getArguments()[0])) {
								result.add(currentProjectHoldInfo);
							}
						}
						return result;
					}
				}
			);
		Mockito.when(currentProjectHoldInfoDao.findListByStatuses(Mockito.anyString(), Mockito.anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						List<CurrentProjectHoldInfo> result = new ArrayList<CurrentProjectHoldInfo>();
						for(CurrentProjectHoldInfo currentProjectHoldInfo : holdList) {
							if(currentProjectHoldInfo.getStatus().equals((String)invocation.getArguments()[0])
									|| currentProjectHoldInfo.getStatus().equals((String)invocation.getArguments()[1])) {
								result.add(currentProjectHoldInfo);
							}
						}
						return result;
					}
				}
			);
		Mockito.when(currentProjectInfoDao.get(Mockito.anyString())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return getCurrentProjectInfoById(Long.parseLong((String)invocation.getArguments()[0]));
					}
				}
			);
		Mockito.when(currentProjectHoldInfoDao.updateByRepay(Mockito.anyString(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentProjectHoldInfo currentProjectHoldInfo = getCurrentProjectHoldInfoById(holdList, Long.parseLong((String)invocation.getArguments()[0]));
						currentProjectHoldInfo.setInterest(NumberUtils.add(currentProjectHoldInfo.getInterest(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(currentAccountSummaryDao.updateByRepay(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentAccountSummary currentAccountSummary = getCurrentAccountSummaryByAccountId(summaryList, (Long)invocation.getArguments()[0] + "");
						currentAccountSummary.setTotalGetInterest(NumberUtils.add(currentAccountSummary.getTotalGetInterest(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		Mockito.when(currentAccountInterestChangeHisDao.insert(Mockito.any(CurrentAccountInterestChangeHis.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentAccountInterestChangeHisList.add((CurrentAccountInterestChangeHis)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Mockito.when(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyString(), Mockito.any(Date.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						return getCountByAccountIdAndProjectId((Long)invocation.getArguments()[0],
								(Long)invocation.getArguments()[1],
								(String)invocation.getArguments()[2],
								(Date)invocation.getArguments()[3]);
					}
				}
			);
		Mockito.when(currentProjectRepayRecordDao.insert(Mockito.any(CurrentProjectRepayRecord.class))).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						currentProjectRepayRecordList.add((CurrentProjectRepayRecord)invocation.getArguments()[0]);
						return null;
					}
				}
			);
		Mockito.when(currentProjectExecuteSnapshotDao.updateByRepay(Mockito.anyLong(), Mockito.anyDouble())).then(
				new Answer<Object>() {
					@Override
					public Object answer(InvocationOnMock invocation) throws Throwable {
						CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = getCurrentProjectExecuteSnapshotByProjectId(currentProjectExecuteSnapshotList, (Long)invocation.getArguments()[0]);
						currentProjectExecuteSnapshot.setHasRepaidMoney(NumberUtils.add(currentProjectExecuteSnapshot.getHasRepaidMoney(), (Double)invocation.getArguments()[1]));
						return null;
					}
				}
			);
		currentProjectAutoRepayInterestTask.job();
		Mockito.verify(currentAccountInterestChangeHisDao, Mockito.atLeast(0)).insert(Mockito.any(CurrentAccountInterestChangeHis.class));
		holdList = currentProjectHoldInfoService.findListByStatuses(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL, CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_WINDING_UPED);
		Assert.assertTrue("活期账户总览数据正确", verifyCurrentAccountSummaryDataCorrect());
		Assert.assertTrue("活期产品持有信息数据正确", verifyCurrentProjectHoldInfoDataCorrect());
		Assert.assertTrue("活期账户利息变更历史数据正确", verifyCurrentAccountInterestChangeHisDataCorrect());
		Assert.assertTrue("活期产品付款记录数据正确", verifyCurrentProjectRepayRecordDataCorrect());
		Assert.assertTrue("活期项目执行快照数据正确", verifyCurrentProjectExecuteSnapshotDataCorrect());
	}

	@Override
	public void clear() {
		
	}

	
	private boolean verifyCurrentAccountSummaryDataCorrect() {
		for(int i = 1; i < beforeSummaryList.size(); i++) {
			CurrentAccountSummary beforeCurrentAccountSummary = getCurrentAccountSummaryByAccountId(beforeSummaryList, i + "");
			CurrentAccountSummary afterCurrentAccountSummary = getCurrentAccountSummaryByAccountId(summaryList, i + "");
			if(!verifyCurrentAccountSummaryDataCorrect(beforeCurrentAccountSummary, afterCurrentAccountSummary)) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyCurrentAccountSummaryDataCorrect(CurrentAccountSummary beforeCurrentAccountSummary, CurrentAccountSummary afterCurrentAccountSummary) {
		double deltaTotalGetInterest = 0d;
		for(CurrentProjectHoldInfo currentProjectHoldInfo : beforeHoldList) {
			if(currentProjectHoldInfo.getStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)
					&& currentProjectHoldInfo.getAccountId().equals(beforeCurrentAccountSummary.getAccountId())
					&& NumberUtils.compare(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(), currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date()), 0L) == 0) {
				CurrentProjectInfo currentProjectInfo = getCurrentProjectInfoById(currentProjectHoldInfo.getProjectId());
				deltaTotalGetInterest += NumberUtils.formatWithScale(NumberUtils.mul(NumberUtils.div(currentProjectInfo.getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)), NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
			}
		}
		return NumberUtils.compare(NumberUtils.add(beforeCurrentAccountSummary.getTotalGetInterest(), deltaTotalGetInterest), afterCurrentAccountSummary.getTotalGetInterest()) == 0
				&& NumberUtils.compare(beforeCurrentAccountSummary.getCurrentPrincipal(), afterCurrentAccountSummary.getCurrentPrincipal()) == 0;
	}

	private boolean verifyCurrentProjectHoldInfoDataCorrect() {
		for(int i = 1; i < beforeHoldList.size(); i++) {
			CurrentProjectHoldInfo beforeCurrentProjectHoldInfo = getCurrentProjectHoldInfoById(beforeHoldList, new Long(i));
			CurrentProjectHoldInfo afterCurrentProjectHoldInfo = getCurrentProjectHoldInfoById(holdList, new Long(i));
			if(!verifyCurrentProjectHoldInfoDataCorrect(beforeCurrentProjectHoldInfo, afterCurrentProjectHoldInfo)) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyCurrentProjectHoldInfoDataCorrect(CurrentProjectHoldInfo beforeCurrentProjectHoldInfo, CurrentProjectHoldInfo afterCurrentProjectHoldInfo) {
		double deltaInterest = 0d;
		double deltaPrincipal = 0d;
		double deltaApplyRedeemPrincipal = 0d;
		for(CurrentProjectHoldInfo currentProjectHoldInfo : beforeHoldList) {
			if(currentProjectHoldInfo.getStatus().equals(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL)
					&& currentProjectHoldInfo.getId().equals(beforeCurrentProjectHoldInfo.getId())
					&& NumberUtils.compare(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(), currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date()), 0L) == 0) {
				CurrentProjectInfo currentProjectInfo = getCurrentProjectInfoById(currentProjectHoldInfo.getProjectId());
				deltaInterest += NumberUtils.formatWithScale(NumberUtils.mul(NumberUtils.div(currentProjectInfo.getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)), NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
			}
		}
		return NumberUtils.compare(NumberUtils.add(beforeCurrentProjectHoldInfo.getInterest(), deltaInterest), afterCurrentProjectHoldInfo.getInterest()) == 0
				&& NumberUtils.compare(NumberUtils.add(beforeCurrentProjectHoldInfo.getPrincipal(), deltaPrincipal), afterCurrentProjectHoldInfo.getPrincipal()) == 0
				&& NumberUtils.compare(NumberUtils.add(beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal(), deltaApplyRedeemPrincipal), afterCurrentProjectHoldInfo.getApplyRedeemPrincipal()) == 0;
	}

	private boolean verifyCurrentAccountInterestChangeHisDataCorrect() {
		int shouldInsertRecord = 0;
		double shouldChangeTotalValue = 0;
		for(CurrentProjectHoldInfo currentProjectHoldInfo : beforeHoldList) {
			if(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL.equals(currentProjectHoldInfo.getStatus())
					&& NumberUtils.compare(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(), currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date()), 0L) == 0) {
				double shouldReayInterest = NumberUtils.formatWithScale(NumberUtils.mul(
						NumberUtils.div(currentProjectInfoDao.get(currentProjectHoldInfo.getProjectId() + "").getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)),
						NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
				if(NumberUtils.compare(shouldReayInterest, 0d) == 1) {
					shouldInsertRecord++;
				}
				shouldChangeTotalValue = NumberUtils.add(shouldChangeTotalValue, shouldReayInterest);
			}
		}
		int actualInsertRecord = 0;
		double actualChangeTotalValue = 0;
		for(CurrentAccountInterestChangeHis currentAccountInterestChangeHis : currentAccountInterestChangeHisList) {
			if("1".equals(currentAccountInterestChangeHis.getId())) {
				continue;
			}
			if(currentAccountInterestChangeHis.equals(CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST)) {
				return false;
			}
			actualInsertRecord++;
			actualChangeTotalValue = NumberUtils.add(actualChangeTotalValue, currentAccountInterestChangeHis.getChangeValue());
		}
		return (shouldInsertRecord == actualInsertRecord) && NumberUtils.compare(shouldChangeTotalValue, actualChangeTotalValue) == 0;
	}

	private boolean verifyCurrentProjectRepayRecordDataCorrect() {
		for(CurrentProjectInfo currentProjectInfo : projectList) {
			double shouldRepayInterest = 0;
			for(CurrentProjectHoldInfo currentProjectHoldInfo : beforeHoldList) {
				if(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL.equals(currentProjectHoldInfo.getStatus())
						&& NumberUtils.compare(currentProjectHoldInfo.getProjectId(), Long.parseLong(currentProjectInfo.getId())) == 0
						&& NumberUtils.compare(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(), currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date()), 0L) == 0) {
					double shouldReayInterest = NumberUtils.formatWithScale(NumberUtils.mul(
							NumberUtils.div(currentProjectInfoDao.get(currentProjectHoldInfo.getProjectId() + "").getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)),
							NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
					shouldRepayInterest = NumberUtils.add(shouldRepayInterest, shouldReayInterest);
				}
			}
			CurrentProjectRepayRecord currentProjectRepayRecord = getCurrentProjectRepayRecordByProjectId(Long.parseLong(currentProjectInfo.getId()));
			if(NumberUtils.compare(currentProjectRepayRecord.getInterest(), shouldRepayInterest) != 0) {
				return false;
			}
		}
		return true;
	}

	private boolean verifyCurrentProjectExecuteSnapshotDataCorrect() {
		for(CurrentProjectInfo currentProjectInfo : projectList) {
			double shouldRepayInterest = 0;
			for(CurrentProjectHoldInfo currentProjectHoldInfo : beforeHoldList) {
				if(CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL.equals(currentProjectHoldInfo.getStatus())
						&& NumberUtils.compare(currentProjectHoldInfo.getProjectId(), Long.parseLong(currentProjectInfo.getId())) == 0
						&& NumberUtils.compare(currentAccountInterestChangeHisDao.getCountByAccountIdAndProjectId(currentProjectHoldInfo.getAccountId(), currentProjectHoldInfo.getProjectId(), CurrentProjectConstant.CURRENT_ACCOUNT_INTEREST_CHANGE_TYPE_GET_INTEREST, new Date()), 0L) == 0) {
					double repayInterest = NumberUtils.formatWithScale(NumberUtils.mul(
							NumberUtils.div(currentProjectInfoDao.get(currentProjectHoldInfo.getProjectId() + "").getRate(), new Double(CurrentProjectConstant.DAYS_IN_YEAR)),
							NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), currentProjectHoldInfo.getApplyRedeemPrincipal())), 4);
					shouldRepayInterest = NumberUtils.add(shouldRepayInterest, repayInterest);
				}
			}
			CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = getCurrentProjectExecuteSnapshotByProjectId(currentProjectExecuteSnapshotList, Long.parseLong(currentProjectInfo.getId()));
			CurrentProjectExecuteSnapshot beforeCurrentProjectExecuteSnapshot = getCurrentProjectExecuteSnapshotByProjectId(beforeCurrentProjectExecuteSnapshotList, Long.parseLong(currentProjectInfo.getId()));
			if(NumberUtils.compare(currentProjectExecuteSnapshot.getHasRedeemInterest(), beforeCurrentProjectExecuteSnapshot.getHasRedeemInterest()) != 0
					|| NumberUtils.compare(currentProjectExecuteSnapshot.getHasRepaidMoney(), NumberUtils.add(beforeCurrentProjectExecuteSnapshot.getHasRepaidMoney(), shouldRepayInterest)) != 0) {
				return false;
			}
		}
		return true;
	}
	
	private CurrentProjectExecuteSnapshot getCurrentProjectExecuteSnapshotByProjectId(List<CurrentProjectExecuteSnapshot> list, long projectId) {
		for(CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot : list) {
			if(NumberUtils.compare(projectId, currentProjectExecuteSnapshot.getProjectId()) == 0) {
				return currentProjectExecuteSnapshot;
			}
		}
		return null;
	}

	private List<CurrentProjectHoldInfo> createHoldList() {
		List<CurrentProjectHoldInfo> holdList = new ArrayList<CurrentProjectHoldInfo>();
		long i = 0;
		for(CurrentProjectInfo currentProjectInfo : projectList) {
			for(CustomerAccount customerAccount : accountList) {
				CurrentProjectHoldInfo currentProjectHoldInfo = new CurrentProjectHoldInfo();
				i++;
				currentProjectHoldInfo.setId(i + "");
				currentProjectHoldInfo.setProjectId(Long.parseLong(currentProjectInfo.getId()));
				currentProjectHoldInfo.setAccountId(customerAccount.getAccountId());
				double principal = randomDoubleWithTwoScale();
				double applyRedeemPrincipal = randomDoubleWithTwoScale();
				currentProjectHoldInfo.setPrincipal(principal);
				currentProjectHoldInfo.setInterest(randomDoubleWithFourScale());
				currentProjectHoldInfo.setApplyRedeemPrincipal(applyRedeemPrincipal > principal ? principal : applyRedeemPrincipal);
				currentProjectHoldInfo.setStatus(i % 3 == 0 ? CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_WINDING_UPED : CurrentProjectConstant.CURRENT_PROJECT_HOLD_STATUS_NORMAL);
				holdList.add(currentProjectHoldInfo);
			}
		}
		return holdList;
	}

	private List<CurrentProjectInfo> createProjectList() {
		List<CurrentProjectInfo> projectList = new ArrayList<CurrentProjectInfo>();
		for(int i = 1; i < 10; i++) {
			CurrentProjectInfo currentProjectInfo = new CurrentProjectInfo();
			currentProjectInfo.setId(i + "");
			currentProjectInfo.setRate(i % 3 == 0 ? 0.07 : 0.08);
			projectList.add(currentProjectInfo);
		}
		return projectList;
	}
	
	private List<CurrentProjectExecuteSnapshot> createcurrentProjectExecuteSnapshotList() {
		List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshotList = new ArrayList<CurrentProjectExecuteSnapshot>();
		for(CurrentProjectInfo currentProjectInfo : projectList) {
			CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = new CurrentProjectExecuteSnapshot();
			currentProjectExecuteSnapshot.setProjectId(Long.parseLong(currentProjectInfo.getId()));
			currentProjectExecuteSnapshot.setHasFinancedMoney(randomDoubleWithTwoScale());
			currentProjectExecuteSnapshot.setRealPrincipal(randomDoubleWithTwoScale());
			currentProjectExecuteSnapshot.setHasRepaidMoney(randomDoubleWithFourScale());
			currentProjectExecuteSnapshot.setHasRedeemInterest(randomDoubleWithTwoScale());
			currentProjectExecuteSnapshotList.add(currentProjectExecuteSnapshot);
		}
		return currentProjectExecuteSnapshotList;
	}

	private List<CustomerAccount> createAccountList() {
		List<CustomerAccount> accountList = new ArrayList<CustomerAccount>();
		for(int i = 1; i < 10; i++) {
			CustomerAccount customerAccount = new CustomerAccount();
			customerAccount.setAccountId(new Long(i));
			accountList.add(customerAccount);
		}
		return accountList;
	}

	private List<CurrentAccountSummary> createSummaryList() {
		List<CurrentAccountSummary> summaryList = new ArrayList<CurrentAccountSummary>();
		for(CustomerAccount customerAccount : accountList) {
			CurrentAccountSummary currentAccountSummary = new CurrentAccountSummary();
			currentAccountSummary.setAccountId(customerAccount.getAccountId());
			currentAccountSummary.setCurrentPrincipal(randomDoubleWithTwoScale());
			currentAccountSummary.setTotalGetInterest(randomDoubleWithFourScale());
			summaryList.add(currentAccountSummary);
		}
		return summaryList;
	}

	private CurrentProjectInfo getCurrentProjectInfoById(Long id) {
		for(CurrentProjectInfo currentProjectInfo : projectList) {
			if((id + "").equals(currentProjectInfo.getId())) {
				return currentProjectInfo;
			}
		}
		return null;
	}

	private CurrentAccountSummary getCurrentAccountSummaryByAccountId(List<CurrentAccountSummary> currentAccountSummaryList, String id) {
		for(CurrentAccountSummary currentAccountSummary : currentAccountSummaryList) {
			if(id.equals(currentAccountSummary.getAccountId() + "")) {
				return currentAccountSummary;
			}
		}
		return null;
	}

	private CurrentProjectHoldInfo getCurrentProjectHoldInfoById(List<CurrentProjectHoldInfo> holdList, Long id) {
		for(CurrentProjectHoldInfo currentProjectHoldInfo : holdList) {
			if(String.valueOf(id).equals(currentProjectHoldInfo.getId())) {
				return currentProjectHoldInfo;
			}
		}
		return null;
	}

	protected Long getCountByAccountIdAndProjectId(Long accountId, Long projectId, String changeType, Date date) {
		long count = 0;
		for(CurrentAccountInterestChangeHis currentAccountInterestChangeHis : beforeCurrentAccountInterestChangeHisList) {
			if(NumberUtils.compare(currentAccountInterestChangeHis.getAccountId(), accountId) == 0
					&& NumberUtils.compare(currentAccountInterestChangeHis.getProjectId(), projectId) == 0
					&& changeType.equals(currentAccountInterestChangeHis.getChangeType())
					&& DateUtils.FormatDate(date, "yyyy-MM-dd").equals(DateUtils.FormatDate(currentAccountInterestChangeHis.getOpDt(), "yyyy-MM-dd"))) {
				count++;
			}
		}
		return count;
	}

	private CurrentProjectRepayRecord getCurrentProjectRepayRecordByProjectId(long projectId) {
		for(CurrentProjectRepayRecord currentProjectRepayRecord : currentProjectRepayRecordList) {
			if(NumberUtils.compare(currentProjectRepayRecord.getProjectId(), projectId) == 0) {
				return currentProjectRepayRecord;
			}
		}
		return null;
	}
}
