package com.thinkgem.jeesite.modules.project.service.task;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thinkgem.jeesite.modules.operation.service.OperationDataService;

/**
 * @author ydt 维护运营数据表中的数据
 */
@Component
public class FixOperationDataTask {
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private OperationDataService operationDataService;

	/**
	 * 每日1:25分执行一次
	 * 维护运营数据表中的数据，将需要插入的数据插入到运营数据表中
	 * 若当前一日数据不存在则不插入
	 * 例如今天是2015-10-10，表中无2015-10-08、2015-10-09的数据而有2015-10-07及之前的数据，则将2015-10-08、2015-10-09的数据插入到表中
	 */
	public void job() {
		operationDataService.insertNeedInsertData();
	}
}
