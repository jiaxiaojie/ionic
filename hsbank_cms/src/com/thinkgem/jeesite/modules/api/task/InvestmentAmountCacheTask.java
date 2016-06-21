package com.thinkgem.jeesite.modules.api.task;

import com.thinkgem.jeesite.modules.api.service.InvestmentAmountService;
import com.thinkgem.jeesite.modules.project.service.ProjectInvestmentRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 平台投资总额(累计募集)任务调度
 *
 * @Author liuguoqing
 */
@Component
public class InvestmentAmountCacheTask {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private InvestmentAmountService investmentAmountService;

    public void sumAmountJob() {
        investmentAmountService.saveInvestmentAmountToCache();
        logger.debug("=== 平台投资总额(累计募集)任务调度执行完成 ===");
    }
}
