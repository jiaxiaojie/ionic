package com.thinkgem.jeesite.modules.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.thinkgem.jeesite.modules.FrontBaseTest;
import com.thinkgem.jeesite.modules.YeepayCommonHandlerTest;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;

import com.thinkgem.jeesite.common.utils.NumberUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountSummaryDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectExecuteSnapshotDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectHoldInfoDao;
import com.thinkgem.jeesite.modules.current.dao.CurrentProjectRedemptionApplyDao;
import com.thinkgem.jeesite.modules.current.dao.DateInfoDao;
import com.thinkgem.jeesite.modules.current.service.CurrentProjectRedemptionApplyService;
import com.thinkgem.jeesite.modules.current.task.CurrentProjectAutoPayRedeemTask;
import com.thinkgem.jeesite.modules.customer.dao.CustomerAccountDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceDao;
import com.thinkgem.jeesite.modules.customer.dao.CustomerBalanceHisDao;
import com.thinkgem.jeesite.modules.customer.handler.CustomerBalanceHandler;
import com.thinkgem.jeesite.modules.entity.CurrentAccountPrincipalChangeHis;
import com.thinkgem.jeesite.modules.entity.CurrentAccountSummary;
import com.thinkgem.jeesite.modules.entity.CurrentProjectExecuteSnapshot;
import com.thinkgem.jeesite.modules.entity.CurrentProjectHoldInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectInfo;
import com.thinkgem.jeesite.modules.entity.CurrentProjectRedemptionApply;
import com.thinkgem.jeesite.modules.entity.CustomerAccount;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.CustomerBalanceHis;
import com.thinkgem.jeesite.modules.project.ProjectConstant;

public class TestCurrentProjectAutoPayRedeemTask extends FrontBaseTest {
    @Mock
    private CustomerAccountDao customerAccountDao;
    @Mock
    private CurrentProjectRedemptionApplyDao currentProjectRedemptionApplyDao;
    @Mock
    private DateInfoDao dateInfoDao;
    @Mock
    private CurrentProjectHoldInfoDao currentProjectHoldInfoDao;
    @Mock
    private CurrentAccountSummaryDao currentAccountSummaryDao;
    @Mock
    private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;
    @Mock
    private CurrentProjectExecuteSnapshotDao currentProjectExecuteSnapshotDao;
    @Mock
    private CustomerBalanceDao customerBalanceDao;
    @Mock
    private CustomerBalanceHisDao customerBalanceHisDao;
    @InjectMocks
    private CurrentProjectRedemptionApplyService currentProjectRedemptionApplyService;
    @InjectMocks
    private YeepayCommonHandlerTest yeepayCommonHandlerTest;
    @InjectMocks
    private CustomerBalanceHandler customerBalanceHandler;
    @Autowired
    private CurrentProjectAutoPayRedeemTask currentProjectAutoPayRedeemTask;

    List<CurrentProjectInfo> currentProjectInfoList = null;

    List<CurrentProjectRedemptionApply> currentProjectRedemptionApplyList = null;
    List<CurrentProjectRedemptionApply> beforeCurrentProjectRedemptionApplyList = null;

    List<CustomerBalance> customerBalanceList = null;
    List<CustomerBalance> beforeCustomerBalanceList = null;

    List<CurrentAccountSummary> currentAccountSummaryList = null;
    List<CurrentAccountSummary> beforeCurrentAccountSummaryList = null;

    List<CurrentProjectHoldInfo> currentProjectHoldInfoList = null;
    List<CurrentProjectHoldInfo> beforeCurrentProjectHoldInfoList = null;

    List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshotList = null;
    List<CurrentProjectExecuteSnapshot> beforeCurrentProjectExecuteSnapshotList = null;

    List<CurrentAccountPrincipalChangeHis> currentAccountPrincipalChangeHisList = null;
    List<CustomerBalanceHis> customerBalanceHisList = null;

    List<CustomerAccount> customerAccountList = null;

    @Override
    public void setUp() {
        Mockito.reset(currentProjectRedemptionApplyDao);
        Mockito.reset(dateInfoDao);
        Mockito.reset(currentProjectHoldInfoDao);
        Mockito.reset(currentAccountSummaryDao);
        Mockito.reset(currentAccountPrincipalChangeHisDao);
        Mockito.reset(currentProjectExecuteSnapshotDao);
        Mockito.reset(customerBalanceDao);
        Mockito.reset(customerBalanceHisDao);
        Mockito.reset(customerAccountDao);
        setField(currentProjectRedemptionApplyService, currentProjectRedemptionApplyDao, FILED_DAO);
        setField(yeepayCommonHandlerTest, customerBalanceHandler);
        setField(currentProjectRedemptionApplyService, yeepayCommonHandlerTest, "yeepayCommonHandler");
        setField(currentProjectAutoPayRedeemTask, currentProjectRedemptionApplyService);
        currentProjectInfoList = createCurrentProjectInfoList();

        random = new Random(47);
        currentProjectRedemptionApplyList = createCurrentProjectRedemptionApplyList();
        customerBalanceList = createCustomerBalanceListList();
        currentAccountSummaryList = createCurrentAccountSummaryList();
        currentProjectHoldInfoList = createCurrentProjectHoldInfoList();
        currentProjectExecuteSnapshotList = createcurrentProjectExecuteSnapshotList();

        random = new Random(47);
        beforeCurrentProjectRedemptionApplyList = createCurrentProjectRedemptionApplyList();
        beforeCustomerBalanceList = createCustomerBalanceListList();
        beforeCurrentAccountSummaryList = createCurrentAccountSummaryList();
        beforeCurrentProjectHoldInfoList = createCurrentProjectHoldInfoList();
        beforeCurrentProjectExecuteSnapshotList = createcurrentProjectExecuteSnapshotList();

        currentAccountPrincipalChangeHisList = new ArrayList<CurrentAccountPrincipalChangeHis>();
        customerBalanceHisList = new ArrayList<CustomerBalanceHis>();
        customerAccountList = createCustomerAccountList();
    }

    @Test
    public void testJob() {
        Mockito.when(dateInfoDao.isWorkday(Mockito.any(Date.class))).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        return true;//random.nextInt(2) == 0 ? true : false;
                    }
                }
        );
        Mockito.when(currentProjectRedemptionApplyDao.findListByStatus(Mockito.anyString())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        List<CurrentProjectRedemptionApply> applyList = new ArrayList<CurrentProjectRedemptionApply>();
                        for (CurrentProjectRedemptionApply currentProjectRedemptionApply : currentProjectRedemptionApplyList) {
                            if (currentProjectRedemptionApply.getStatus().equals((String) invocation.getArguments()[0])) {
                                applyList.add(currentProjectRedemptionApply);
                            }
                        }
                        return applyList;
                    }
                }
        );
        Mockito.when(currentProjectRedemptionApplyDao.updateByPayRedeem(Mockito.anyString())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        CurrentProjectRedemptionApply currentProjectRedemptionApply = getById(currentProjectRedemptionApplyList, (String) invocation.getArguments()[0]);
                        currentProjectRedemptionApply.setStatus(CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REDEEMED);
                        return null;
                    }
                }
        );
        Mockito.when(currentProjectHoldInfoDao.get(Mockito.anyString())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        return getById((String) invocation.getArguments()[0]);
                    }
                }
        );
        Mockito.when(currentAccountSummaryDao.updateByPayRedeem(Mockito.anyLong(), Mockito.anyDouble())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        CurrentAccountSummary currentAccountSummary = getByAccountId(currentAccountSummaryList, (Long) invocation.getArguments()[0]);
                        currentAccountSummary.setTotalRedeemPrincipal(NumberUtils.add(currentAccountSummary.getTotalRedeemPrincipal(), (Double) invocation.getArguments()[1]));
                        currentAccountSummary.setCurrentPrincipal(NumberUtils.sub(currentAccountSummary.getCurrentPrincipal(), (Double) invocation.getArguments()[1]));
                        return null;
                    }
                }
        );
        Mockito.when(currentProjectHoldInfoDao.updateByPayRedeem(Mockito.anyString(), Mockito.anyDouble())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        CurrentProjectHoldInfo currentProjectHoldInfo = getById((String) invocation.getArguments()[0]);
                        currentProjectHoldInfo.setPrincipal(NumberUtils.sub(currentProjectHoldInfo.getPrincipal(), (Double) invocation.getArguments()[1]));
                        currentProjectHoldInfo.setApplyRedeemPrincipal(NumberUtils.sub(currentProjectHoldInfo.getApplyRedeemPrincipal(), (Double) invocation.getArguments()[1]));
                        return null;
                    }
                }
        );
        Mockito.when(currentAccountPrincipalChangeHisDao.insert(Mockito.any(CurrentAccountPrincipalChangeHis.class))).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        currentAccountPrincipalChangeHisList.add((CurrentAccountPrincipalChangeHis) invocation.getArguments()[0]);
                        return null;
                    }
                }
        );
        Mockito.when(currentProjectExecuteSnapshotDao.updateByPayRedeem(Mockito.anyLong(), Mockito.anyDouble())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = getByProjectId((Long) invocation.getArguments()[0]);
                        currentProjectExecuteSnapshot.setRealPrincipal(NumberUtils.sub(currentProjectExecuteSnapshot.getRealPrincipal(), (Double) invocation.getArguments()[1]));
                        return null;
                    }
                }
        );
        Mockito.when(customerBalanceDao.updateBalance(Mockito.anyLong(), Mockito.anyDouble())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        CustomerBalance customerBalance = getByAccountId((Long) invocation.getArguments()[0]);
                        customerBalance.setGoldBalance(NumberUtils.add(customerBalance.getGoldBalance(), (Double) invocation.getArguments()[1]));
                        return null;
                    }
                }
        );
        Mockito.when(customerBalanceHisDao.insert(Mockito.any(CustomerBalanceHis.class))).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        customerBalanceHisList.add((CustomerBalanceHis) invocation.getArguments()[0]);
                        return null;
                    }
                }
        );
        Mockito.when(customerBalanceDao.get(Mockito.anyString())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        return getByAccountId(Long.parseLong((String) invocation.getArguments()[0]));
                    }
                }
        );
        Mockito.when(customerAccountDao.get(Mockito.anyLong())).then(
                new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        return getCustomerAccountByAccountId((Long) invocation.getArguments()[0]);
                    }
                }
        );
        currentProjectAutoPayRedeemTask.job();
        Assert.assertTrue("活期产品赎回申请状态正确", verifyCurrentProjectRedemptionApplyStatusCorrect());
        Assert.assertTrue("活期账户总览数据正确", verifyCurrentAccountSummaryCorrect());
        Assert.assertTrue("活期产品持有数据正确", verifyCurrentProjectHoldInfoDataCorrect());
        Assert.assertTrue("活期账户本金变更历史数据正确", verifyCurrentAccountPrincipalChangeHisDataCorrect());
        Assert.assertTrue("活期项目执行快照数据正确", verifyCurrentProjectExecuteSnapshotDataCorrect());
        Assert.assertTrue("用户账户余额数据正确", verifyCustomerBalanceDataCorrect());
        Assert.assertTrue("用户账户余额历史数据正确", verifyCustomerBalanceHisDataCorrect());
    }

    private boolean verifyCustomerBalanceHisDataCorrect() {
        int shouldInsertRecord = 0;
        double shouldChangeValue = 0;
        int hasInsertRecord = 0;
        double hasChangeValue = 0;
        for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
            if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())) {
                shouldInsertRecord++;
                shouldChangeValue = NumberUtils.add(shouldChangeValue, beforeCurrentProjectRedemptionApply.getRedeemPrincipal());
            }
        }
        for (CustomerBalanceHis customerBalanceHis : customerBalanceHisList) {
            if (!ProjectConstant.CHANGE_TYPE_BALANCE_CURRENT_PROJECT_REDEEM_PRINCIPAL.equals(customerBalanceHis.getChangeType())) {
                return false;
            }
            hasInsertRecord++;
            hasChangeValue = NumberUtils.add(hasChangeValue, customerBalanceHis.getChangeVal());
        }
        return shouldInsertRecord == hasInsertRecord && shouldChangeValue == hasChangeValue;
    }

    private boolean verifyCustomerBalanceDataCorrect() {
        for (CustomerBalance beforeCustomerBalance : beforeCustomerBalanceList) {
            CustomerBalance customerBalance = getByAccountId(beforeCustomerBalance.getAccountId());
            double deltaTotalRedeemPrincipal = 0;
            for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
                if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())
                        && NumberUtils.compare(beforeCustomerBalance.getAccountId(), getById(String.valueOf(beforeCurrentProjectRedemptionApply.getHoldId())).getAccountId()) == 0) {
                    deltaTotalRedeemPrincipal = NumberUtils.add(beforeCurrentProjectRedemptionApply.getRedeemPrincipal(), deltaTotalRedeemPrincipal);
                }
            }
            if (NumberUtils.compare(beforeCustomerBalance.getGoldBalance(), NumberUtils.sub(customerBalance.getGoldBalance(), deltaTotalRedeemPrincipal)) != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyCurrentProjectExecuteSnapshotDataCorrect() {
        for (CurrentProjectExecuteSnapshot beforeurrentProjectExecuteSnapshot : beforeCurrentProjectExecuteSnapshotList) {
            double deltaTotalRedeemPrincipal = 0;
            CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot = getByProjectId(beforeurrentProjectExecuteSnapshot.getProjectId());
            for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
                if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())
                        && NumberUtils.compare(beforeurrentProjectExecuteSnapshot.getProjectId(), getById(String.valueOf(beforeCurrentProjectRedemptionApply.getHoldId())).getProjectId()) == 0) {
                    deltaTotalRedeemPrincipal = NumberUtils.add(beforeCurrentProjectRedemptionApply.getRedeemPrincipal(), deltaTotalRedeemPrincipal);
                }
            }
            if (NumberUtils.compare(beforeurrentProjectExecuteSnapshot.getRealPrincipal(), NumberUtils.add(currentProjectExecuteSnapshot.getRealPrincipal(), deltaTotalRedeemPrincipal)) != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyCurrentAccountPrincipalChangeHisDataCorrect() {
        int shouldInsertRecord = 0;
        double shouldPayTotalRedeemPrincipal = 0;
        int hasInsertRecord = 0;
        double hasPayTotalRedeemPrincipal = 0;
        for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
            if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())) {
                shouldInsertRecord++;
                shouldPayTotalRedeemPrincipal = NumberUtils.add(shouldPayTotalRedeemPrincipal, beforeCurrentProjectRedemptionApply.getRedeemPrincipal());
            }
        }
        for (CurrentAccountPrincipalChangeHis currentAccountPrincipalChangeHis : currentAccountPrincipalChangeHisList) {
            hasInsertRecord++;
            hasPayTotalRedeemPrincipal = NumberUtils.add(hasPayTotalRedeemPrincipal, currentAccountPrincipalChangeHis.getChangeValue());
        }
        return shouldInsertRecord == hasInsertRecord && shouldPayTotalRedeemPrincipal == hasPayTotalRedeemPrincipal;
    }

    private boolean verifyCurrentAccountSummaryCorrect() {
        for (CurrentAccountSummary beforeCurrentAccountSummary : beforeCurrentAccountSummaryList) {
            CurrentAccountSummary currentAccountSummary = getByAccountId(currentAccountSummaryList, beforeCurrentAccountSummary.getAccountId());
            double deltaTotalRedeemPrincipal = 0;
            double deltaCurrentPrincipal = 0;
            for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
                if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())
                        && NumberUtils.compare(beforeCurrentAccountSummary.getAccountId(), getById(String.valueOf(beforeCurrentProjectRedemptionApply.getHoldId())).getAccountId()) == 0) {
                    deltaTotalRedeemPrincipal = NumberUtils.add(beforeCurrentProjectRedemptionApply.getRedeemPrincipal(), deltaTotalRedeemPrincipal);
                    deltaCurrentPrincipal = NumberUtils.add(beforeCurrentProjectRedemptionApply.getRedeemPrincipal(), deltaCurrentPrincipal);
                }
            }
            if (NumberUtils.compare(NumberUtils.add(beforeCurrentAccountSummary.getTotalRedeemPrincipal(), deltaTotalRedeemPrincipal), currentAccountSummary.getTotalRedeemPrincipal()) != 0
                    || NumberUtils.compare(NumberUtils.sub(beforeCurrentAccountSummary.getCurrentPrincipal(), deltaCurrentPrincipal), currentAccountSummary.getCurrentPrincipal()) != 0) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyCurrentProjectRedemptionApplyStatusCorrect() {
        for (CurrentProjectRedemptionApply currentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
            if ((currentProjectRedemptionApplyService.shouldPayRedeem(currentProjectRedemptionApply.getStatus(), currentProjectRedemptionApply.getApplyDt())
                    && !CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REDEEMED.equals(getById(currentProjectRedemptionApplyList, currentProjectRedemptionApply.getId()).getStatus()))
                    || (!currentProjectRedemptionApplyService.shouldPayRedeem(currentProjectRedemptionApply.getStatus(), currentProjectRedemptionApply.getApplyDt())
                    && !currentProjectRedemptionApply.getStatus().equals(getById(currentProjectRedemptionApplyList, currentProjectRedemptionApply.getId()).getStatus()))) {
                return false;
            }
        }
        return true;
    }

    private boolean verifyCurrentProjectHoldInfoDataCorrect() {
        for (CurrentProjectHoldInfo beforeCurrentProjectHoldInfo : beforeCurrentProjectHoldInfoList) {
            CurrentProjectHoldInfo currentProjectHoldInfo = getById(beforeCurrentProjectHoldInfo.getId());
            double deltaTotalRedeemPrincipal = 0;
            for (CurrentProjectRedemptionApply beforeCurrentProjectRedemptionApply : beforeCurrentProjectRedemptionApplyList) {
                if (currentProjectRedemptionApplyService.shouldPayRedeem(beforeCurrentProjectRedemptionApply.getStatus(), beforeCurrentProjectRedemptionApply.getApplyDt())
                        && String.valueOf(beforeCurrentProjectRedemptionApply.getHoldId()).equals(beforeCurrentProjectHoldInfo.getId())) {
                    deltaTotalRedeemPrincipal = NumberUtils.add(beforeCurrentProjectRedemptionApply.getRedeemPrincipal(), deltaTotalRedeemPrincipal);
                }
            }
            if (NumberUtils.compare(beforeCurrentProjectHoldInfo.getPrincipal(), NumberUtils.add(currentProjectHoldInfo.getPrincipal(), deltaTotalRedeemPrincipal)) != 0
                    || NumberUtils.compare(beforeCurrentProjectHoldInfo.getApplyRedeemPrincipal(), NumberUtils.add(currentProjectHoldInfo.getApplyRedeemPrincipal(), deltaTotalRedeemPrincipal)) != 0) {
                return false;
            }
        }
        return true;
    }

    private CurrentProjectRedemptionApply getById(List<CurrentProjectRedemptionApply> currentProjectRedemptionApplyList, String id) {
        for (CurrentProjectRedemptionApply currentProjectRedemptionApply : currentProjectRedemptionApplyList) {
            if (currentProjectRedemptionApply.getId().equals(id)) {
                return currentProjectRedemptionApply;
            }
        }
        return null;
    }

    protected CurrentProjectHoldInfo getById(String id) {
        for (CurrentProjectHoldInfo currentProjectHoldInfo : currentProjectHoldInfoList) {
            if (currentProjectHoldInfo.getId().equals(id)) {
                return currentProjectHoldInfo;
            }
        }
        return null;
    }

    private CurrentAccountSummary getByAccountId(List<CurrentAccountSummary> currentAccountSummaryList, Long accountId) {
        for (CurrentAccountSummary currentAccountSummary : currentAccountSummaryList) {
            if (NumberUtils.compare(currentAccountSummary.getAccountId(), accountId) == 0) {
                return currentAccountSummary;
            }
        }
        return null;
    }

    private CurrentProjectExecuteSnapshot getByProjectId(long projectId) {
        for (CurrentProjectExecuteSnapshot currentProjectExecuteSnapshot : currentProjectExecuteSnapshotList) {
            if (NumberUtils.compare(currentProjectExecuteSnapshot.getProjectId(), projectId) == 0) {
                return currentProjectExecuteSnapshot;
            }
        }
        return null;
    }

    private CustomerBalance getByAccountId(Long accountId) {
        for (CustomerBalance customerBalance : customerBalanceList) {
            if (NumberUtils.compare(customerBalance.getAccountId(), accountId) == 0) {
                return customerBalance;
            }
        }
        return null;
    }

    protected CustomerAccount getCustomerAccountByAccountId(Long accountId) {
        for (CustomerAccount customerAccount : customerAccountList) {
            if (NumberUtils.compare(customerAccount.getAccountId(), accountId) == 0) {
                return customerAccount;
            }
        }
        return null;
    }

    @Override
    public void clear() {

    }

    private List<CurrentProjectRedemptionApply> createCurrentProjectRedemptionApplyList() {
        List<CurrentProjectRedemptionApply> currentProjectRedemptionApplyList = new ArrayList<CurrentProjectRedemptionApply>();
        for (int i = 1; i < 100; i++) {
            CurrentProjectRedemptionApply currentProjectRedemptionApply = new CurrentProjectRedemptionApply();
            currentProjectRedemptionApply.setId(String.valueOf(i));
            currentProjectRedemptionApply.setHoldId(new Long((i % 30) == 0 ? 1 : (i % 30)));
            currentProjectRedemptionApply.setRedeemPrincipal(randomDoubleWithTwoScale());
            currentProjectRedemptionApply.setRedeemInterest(randomDoubleWithTwoScale());
            currentProjectRedemptionApply.setApplyDt(new Date());
            currentProjectRedemptionApply.setStatus((i % 3 == 0) ?
                    CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_DOING : (i % 2 == 0 ?
                    CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REVIEW_PASS :
                    CurrentProjectConstant.CURRENT_PROJECT_REDEMPTION_APPLY_REDEEMED));
            currentProjectRedemptionApplyList.add(currentProjectRedemptionApply);
        }
        return currentProjectRedemptionApplyList;
    }

    private List<CurrentProjectInfo> createCurrentProjectInfoList() {
        List<CurrentProjectInfo> currentProjectInfoList = new ArrayList<CurrentProjectInfo>();
        for (int i = 1; i < 10; i++) {
            CurrentProjectInfo currentProjectInfo = new CurrentProjectInfo();
            currentProjectInfo.setId(i + "");
            currentProjectInfo.setRate(i % 3 == 0 ? 0.07 : 0.08);
            currentProjectInfoList.add(currentProjectInfo);
        }
        return currentProjectInfoList;
    }

    private List<CustomerBalance> createCustomerBalanceListList() {
        List<CustomerBalance> customerBalanceList = new ArrayList<CustomerBalance>();
        for (int i = 1; i < 10; i++) {
            CustomerBalance customerBalance = new CustomerBalance();
            customerBalance.setAccountId(new Long(i));
            customerBalance.setGoldBalance(randomDoubleWithTwoScale());
            customerBalanceList.add(customerBalance);
        }
        return customerBalanceList;
    }

    private List<CurrentAccountSummary> createCurrentAccountSummaryList() {
        List<CurrentAccountSummary> currentAccountSummaryList = new ArrayList<CurrentAccountSummary>();
        for (CustomerBalance customerBalance : customerBalanceList) {
            CurrentAccountSummary currentAccountSummary = new CurrentAccountSummary();
            currentAccountSummary.setAccountId(customerBalance.getAccountId());
            currentAccountSummary.setCurrentPrincipal(randomDoubleWithTwoScale());
            currentAccountSummary.setTotalRedeemPrincipal(randomDoubleWithTwoScale());
            currentAccountSummary.setCurrentPrincipal(randomDoubleWithTwoScale());
            currentAccountSummaryList.add(currentAccountSummary);
        }
        return currentAccountSummaryList;
    }

    private List<CurrentProjectHoldInfo> createCurrentProjectHoldInfoList() {
        List<CurrentProjectHoldInfo> holdList = new ArrayList<CurrentProjectHoldInfo>();
        long i = 0;
        for (CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
            for (CustomerBalance customerBalance : customerBalanceList) {
                CurrentProjectHoldInfo currentProjectHoldInfo = new CurrentProjectHoldInfo();
                i++;
                currentProjectHoldInfo.setId(i + "");
                currentProjectHoldInfo.setProjectId(Long.parseLong(currentProjectInfo.getId()));
                currentProjectHoldInfo.setAccountId(customerBalance.getAccountId());
                double principal = NumberUtils.add(randomDoubleWithTwoScale(), 20000d);
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

    private List<CurrentProjectExecuteSnapshot> createcurrentProjectExecuteSnapshotList() {
        List<CurrentProjectExecuteSnapshot> currentProjectExecuteSnapshotList = new ArrayList<CurrentProjectExecuteSnapshot>();
        for (CurrentProjectInfo currentProjectInfo : currentProjectInfoList) {
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

    private List<CustomerAccount> createCustomerAccountList() {
        List<CustomerAccount> customerAccountList = new ArrayList<CustomerAccount>();
        for (CustomerBalance customerBalance : customerBalanceList) {
            CustomerAccount customerAccount = new CustomerAccount();
            customerAccount.setAccountId(customerBalance.getAccountId());
            customerAccount.setHasOpenThirdAccount(ProjectConstant.HASOPENED);
            customerAccountList.add(customerAccount);
        }
        return customerAccountList;
    }
}
