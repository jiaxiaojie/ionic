package com.thinkgem.jeesite.modules.api.service;

import com.thinkgem.jeesite.common.utils.CacheUtils;
import com.thinkgem.jeesite.modules.current.CurrentProjectConstant;
import com.thinkgem.jeesite.modules.current.dao.CurrentAccountPrincipalChangeHisDao;
import com.thinkgem.jeesite.modules.project.dao.ProjectInvestmentRecordDao;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * 平台投资总额 service
 * Created by liuguoqing on 2016/5/25.
 */
@Service
public class InvestmentAmountService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String PROJECT_INVESTMENT_CACHE = "projectInvestmentCache";
    private final String PROJECT_INVESTMENT_AMOUNR_CACHE = "investmentAmountCache";

    @Autowired
    private ProjectInvestmentRecordDao projectInvestmentRecordDao;
    @Autowired
    private CurrentAccountPrincipalChangeHisDao currentAccountPrincipalChangeHisDao;

    /**
     * 获取平台投资总额(累计募集)
     * @return
     */
    public String getInvestmentAmountByCache(){
        String result =(String) CacheUtils.get(PROJECT_INVESTMENT_CACHE, PROJECT_INVESTMENT_AMOUNR_CACHE);
        return result;
    }

    /**
     * 保存平台投资总额(累计募集)到缓存中
     * @return
     */
    public void saveInvestmentAmountToCache(){
        try {
            //定期投资总额
            String regularAmount =  projectInvestmentRecordDao.getInvestmentAmount();
            //活期投资总额
            String currentAmount =  currentAccountPrincipalChangeHisDao.getCurrentAmount(CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_TYPE_INVESTMENT, CurrentProjectConstant.CURRENT_ACCOUNT_PRINCIPAL_CHANGE_STATUS_NORMAL);
            String result = (new BigDecimal(regularAmount).add(new BigDecimal(currentAmount))).toBigInteger().toString();
            CacheUtils.put(PROJECT_INVESTMENT_CACHE, PROJECT_INVESTMENT_AMOUNR_CACHE, result);
            logger.debug("=== 平台投资总额(累计募集):{}", result);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

}
