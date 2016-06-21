package com.thinkgem.jeesite.modules.project.service.loan;

import java.util.Map;

import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;

/**
 * 【融资】服务
 * <p/>
 * 融资成功，指的是项目募集期结束，无论是否满标，按合同约定不流标，则融资成功。
 * 融资失败，指的是项目募集人为终止，或募集期结束，不满标，按合同约定流标。
 * <p/>
 * 本接口服务，提供单次融资操作相关信息的痕迹保留和数据更新等操作
 * <p/>
 * @author Arthur.Xie
 * 2015-07-28
 */
public interface ILoanService {
	/**
	 * 【融资】是否结束：【是否到了募集截止时间】或者【是否满标】
	 * @return
	 */
	public boolean isFinished(ProjectBaseInfo projectInfo);
	
	/**
	 * 是否【满标】
	 * <p/>
	 * 【满标】是指足额募集到所需资金
	 * <p/>
	 * 【满标】的条件：
	 *  <1>.该项目的投资记录没有【冻结中】状态；
	 *  <2>.该项目的总投资金额 >= 募集金额
	 */
	public boolean isFull(ProjectBaseInfo projectInfo);
	
	/**
	 * 融资结束处理器
	 * <p/>
	 * 更新项目状态为【投标结束】
	 * <p/>
	 * @return
	 */
	public Map<String, Object> finishedHandler(ProjectBaseInfo projectInfo);
	
	/**
	 * 融资成功处理器
	 * <1>.投资券处理
	 * <2>.余额处理
	 * <3>.投资记录处理
	 * <4>.还款计划处理
	 * <5>.项目执行快照处理
	 * @return
	 */
	public Map<String, Object> successHandler();
	
	/**
	 * 融资失败处理器
	 * <1>.投资券处理
	 * <2>.余额处理
	 * <3>.投资记录处理
	 * <4>.还款计划处理
	 * <5>.项目执行快照处理
	 * @return
	 */
	public Map<String, Object> failedHandler();
}
