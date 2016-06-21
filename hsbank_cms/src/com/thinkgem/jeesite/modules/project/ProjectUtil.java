package com.thinkgem.jeesite.modules.project;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.thinkgem.jeesite.common.loan.repayment_plan.RepaymentPlanFactory;
import com.thinkgem.jeesite.common.loan.util.LoanConstant;
import com.thinkgem.jeesite.common.loan.util.LoanUtil;
import com.thinkgem.jeesite.common.loan.util.bean.IncreaseInterestItem;
import com.thinkgem.jeesite.common.utils.DateUtils;
import com.thinkgem.jeesite.modules.entity.CustomerBalance;
import com.thinkgem.jeesite.modules.entity.ProjectBaseInfo;
import com.thinkgem.jeesite.modules.project.bean.InvestAmountBean;
import com.hsbank.util.type.StringUtil;

/**
 * P2P公共方法
 * <p>
 * 单例类
 * @author Arthur.Xie
 * 2015-04-23
 */
public class ProjectUtil {
	/**
	 * 计算某次投资的相关金额
	 * @param investmentType				投资类型（直投、债权转让）
	 * @param amount						某次投资的投资金额
	 * @param sumTicketAmount				某次投资的选中的优惠券金额（多张则相加）
	 * @param userPlatformAmount			是否使用平台垫付金额
	 * @param customerBalance				当前用户账户余额汇总
	 * @return
	 */
	public static InvestAmountBean calculateInvestAmout(String investmentType, Double amount, Double sumTicketAmount, boolean userPlatformAmount, CustomerBalance customerBalance) {
		InvestAmountBean resultValue = new InvestAmountBean();
		if (amount == null) {
			return resultValue;
		}
		//当前用户余额
		Double goldBalance = customerBalance.getGoldBalance() != null ? customerBalance.getGoldBalance() : 0;
		//当前账户冻结金额
		Double congealVal = customerBalance.getCongealVal() != null ? customerBalance.getCongealVal() : 0;
		//用户可用余额  = 账户余额 - 账户冻结金额
		Double balance = LoanUtil.formatAmount(goldBalance - congealVal);
		if (balance == null) {
			return resultValue;
		}
		resultValue.setAmount(LoanUtil.formatAmount(amount));
		resultValue.setSumTicketAmount(LoanUtil.formatAmount(sumTicketAmount));
		resultValue.setBalance(balance);
		//当前用户的平台垫付金额
		Double maxPlatformAmount = customerBalance.getPlatformAmount();
		maxPlatformAmount = maxPlatformAmount == null ? 0.00 : maxPlatformAmount;
		sumTicketAmount = sumTicketAmount == null ? 0.00 : sumTicketAmount;
		//本次可用的平台垫付金额
		Double tempPlatformAmount = LoanUtil.formatAmount(amount - sumTicketAmount) * ProjectConfig.getInstance().getPlatformAmountRate();
		tempPlatformAmount = tempPlatformAmount > maxPlatformAmount ? maxPlatformAmount : tempPlatformAmount;
		tempPlatformAmount = LoanUtil.formatAmount(tempPlatformAmount);
		resultValue.setPlatformAmount(tempPlatformAmount);
		//应付金额
		Double payableAmount = 0.00;
		if (userPlatformAmount) {
			if (ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(investmentType)) {
				//直投
				payableAmount = LoanUtil.formatAmount(amount - sumTicketAmount - tempPlatformAmount);
			} else if (ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(investmentType)) {
				//债权转让
				//下家手续费
				Double downServiceCharge = getDownServiceCharge(amount);
				payableAmount = LoanUtil.formatAmount(amount - sumTicketAmount - tempPlatformAmount + downServiceCharge);
				resultValue.setDownServiceCharge(downServiceCharge);
			}
		} else {
			if (ProjectConstant.PROJECT_INVESTMENT_TYPE_DIRECT.equals(investmentType)) {
				//直投
				payableAmount = LoanUtil.formatAmount(amount - sumTicketAmount);
			} else if (ProjectConstant.PROJECT_INVESTMENT_TYPE_ASSIGNMENT.equals(investmentType)) {
				//债权转让
				//下家手续费
				Double downServiceCharge = getDownServiceCharge(amount);
				payableAmount = LoanUtil.formatAmount(amount - sumTicketAmount + downServiceCharge);
				resultValue.setDownServiceCharge(downServiceCharge);
			}
		}
		resultValue.setPayableAmount(payableAmount);
		//差额
		resultValue.setDifferenceAmount(LoanUtil.formatAmount(resultValue.getPayableAmount() - balance));
		return resultValue;
	}
	
	/**
	 * 得到当前投资应提的服务费
	 * <p/>
	 * 投资服务费收取策略
	 * <1>.优先收取
	 * <2>.按比例收取
	 * @param amount							本次投资金额
	 * @param totalServiceCharge				本次投资应收服务费
	 * @param serviceChargeTypeCode				服务费收取方式
	 * @param sumServiceCharge					已冻结服务费金额
	 * @param 
	 * @return
	 */
	public static Double getServiceCharge(Double amount, Double totalServiceCharge, String serviceChargeTypeCode, Double sumServiceCharge){
		Double resultValue = 0.0;
		if (amount == null || totalServiceCharge == null) {
			return resultValue;
		}
		sumServiceCharge = sumServiceCharge == null ? 0 : sumServiceCharge;
		if (ProjectConstant.PROJECT_SERVICE_CHARGE_TYPE_SERVICE_CHARGE_FIRST.equals(serviceChargeTypeCode)) {
			//优先收取
			resultValue = LoanUtil.formatAmount(totalServiceCharge - sumServiceCharge);
			resultValue = resultValue > amount ? amount : resultValue;
		} else if (ProjectConstant.PROJECT_SERVICE_CHARGE_TYPE_BY_RATE.equals(serviceChargeTypeCode)) {
			//按比例收取
			resultValue = amount * ProjectConfig.getInstance().getServiceChargeRate();
			resultValue = new BigDecimal(resultValue).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		}else {
			resultValue=new Double("0");
		}
		return LoanUtil.formatAmount(resultValue);
	}
	
	/**
	 * 债权转让时，平台应收上家（转出方）的服务费
	 * <p/>
	 * @param amount		本次投资金额
	 * @return
	 */
	public static Double getUpServiceCharge(Double amount){
		amount = amount == null ? 0.00 : amount;
		Double resultValue = amount * ProjectConfig.getInstance().getAssignmentFeeRateUp();
		resultValue = LoanUtil.formatAmount(resultValue);
		return resultValue;
	}
	
	/**
	 * 债权转让时，平台应收下家（转入方）的服务费
	 * <p/>
	 * @param amount		本次投资金额
	 * @return
	 */
	public static Double getDownServiceCharge(Double amount){
		amount = amount == null ? 0.00 : amount;
		Double resultValue = amount * ProjectConfig.getInstance().getAssignmentFeeRateDown();
		resultValue = LoanUtil.formatAmount(resultValue);
		return resultValue;
	}
	
	/**
	 * 计算预计收益: 预计收益 = 投资金额 * (年化利率 / 12) * 投资周期
	 * @param totalLoan
	 * @param annualizedRate
	 * @param projectDuration
	 * @return
	 */
	public static Double calculateWillProfit(List<IncreaseInterestItem> interestItems, ProjectBaseInfo projectInfo, Double totalLoan, String rateTicketIds, Date beginInterestDate){
		//还款方式
		String repaymentMethod = ProjectUtil.getRepaymentMethod(projectInfo.getRepaymentMode());
		//年利率
		double annualInterestRate = projectInfo.getAnnualizedRate();
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalDuration = projectInfo.getProjectDuration().intValue();
		//项目期限类型
		String durationType = StringUtil.dealString(projectInfo.getDurationType());
		//投标截止日期
		Date beginDate = DateUtils.dateFormate(projectInfo.getBiddingDeadline());
		return RepaymentPlanFactory.getDailyInstance(repaymentMethod).calculate(interestItems,
				durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
	}
	
	/**
	 * 还款方式：常量转换
	 * @param repaymentMethod
	 * @return
	 */
	public static String getRepaymentMethod(String repaymentMethod) {
		if (ProjectConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST.equals(repaymentMethod)) {
			return LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL_PLUS_INTEREST;
		} else if (ProjectConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL.equals(repaymentMethod)) {
			return LoanConstant.REPAYMNET_METHOD_AVERAGE_CAPITAL;
		} else if (ProjectConstant.REPAYMNET_METHOD_ONE_TIME.equals(repaymentMethod)) {
			return LoanConstant.REPAYMNET_METHOD_ONE_TIME;
		} else if (ProjectConstant.REPAYMNET_METHOD_INTEREST_FIRST.equals(repaymentMethod)) {
			return LoanConstant.REPAYMNET_METHOD_INTEREST_FIRST;
		}
		return "";
	}
	
	/**
	 * 对比计算其他第三方平台收益
	 * @param projectInfo
	 * @param repaymentMethod
	 * @param annualInterestRate
	 * @param totalLoan
	 * @param beginInterestDate
	 * @return
	 */
	public static Double calThirdWillProfit(ProjectBaseInfo projectInfo, String repaymentMethod, double annualInterestRate, Double totalLoan, Date beginInterestDate){
		List<IncreaseInterestItem> interestItems = new ArrayList<IncreaseInterestItem>();
		//年利率折扣
		double discount = 1.0;
		//贷款期数
		int totalDuration = projectInfo.getProjectDuration().intValue();
		//项目期限类型
		String durationType = StringUtil.dealString(projectInfo.getDurationType());
		//投标截止日期
		Date beginDate = DateUtils.dateFormate(projectInfo.getBiddingDeadline());
		return RepaymentPlanFactory.getDailyInstance(repaymentMethod).calculate(interestItems,
				durationType, totalLoan, totalDuration, annualInterestRate, discount, beginDate, beginInterestDate);
	}
}
