package com.thinkgem.jeesite.test.web;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.thinkgem.jeesite.common.utils.DateUtils;

/**
 * 临时的简单测试类
 * @author ydt
 *
 */
public class TestInsertSql {
	public static void main(String[] args) {
		printInsertProjectExecuteSnapshotSql();
	}
	
	public static void printInsertIntoCustomerAccountSql() {
		//insert into customer_account (account_id,account_name,account_pwd,account_type)
		//values(35,'customer2','7f475eaee2c385469f95414ad466a8724fe34cde3c9b1e67b0cb1241','0');
		for(int i=0; i<10; i++) {
			String sql = "insert into customer_account (account_id,account_name,account_pwd,account_type) values("
					+ (i + 35) + ",'customer" + (i + 2) + "','7f475eaee2c385469f95414ad466a8724fe34cde3c9b1e67b0cb1241','0');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertIntoCustomerBaseSql() {
		//insert into customer_base (customer_id,account_id)
		//values(35,35);
		for(int i=0; i<10; i++) {
			String sql = "insert into customer_base (customer_id,account_id) values("
					+ (i + 35) + "," + (i + 35) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectBaseSql() {
		//insert into project_base (project_id,project_code,project_type_code,project_name,
		//project_introduce,repayment_mode,borrowers_user,project_duration,annualized_rate,finance_money,
		//planned_repayment_date,starting_amount,project_status)
		//values (34,'project1','1','project1','项目1','1',34,3,8,50,2015...,1000,1)
		for(int i=0; i<150; i++) {
			int projectId = (i + 34);
			String sql = "insert into project_base (project_id,project_code,project_type_code,project_name,"
					+ "project_introduce,repayment_mode,borrowers_user,project_duration,annualized_rate,finance_money,"
					+ "planned_repayment_date,starting_amount,project_status) values("
					+ projectId + ",'project" + projectId + "','" + randomInt(1,5) + "','project" + (i + 1)
					+ "','项目" + (i + 1) + "','" + randomInt(1,4) + "'," + randomInt(34,43) + "," + randomInt(3,24) + "," + randomInt(5,16)
					+ "," + randomInt(10,100) + ",'" + randomDateString()
					+ "'," + randomInt(1,10) * 100 + ",'" + randomInt(3,7) + "');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectInvestmentRecordSql() {
		//insert into project_investment_record (id,project_id,investment_user_id,money,real_money,
		//ticket_money,will_profit,investment_type,op_dt,real_profit,status) values (
		for(int i=0; i<400; i++) {
			int money = randomInt(1,100) * 100;
			int ticketMoney = randomInt(1,10) * 10;
			int realMoney = money - ticketMoney;
			int willProfit = randomInt(1,20) * 100;
			int realProfit = willProfit - randomInt(1,5);
			String sql = "insert into project_investment_record (id,project_id,investment_user_id,amount,actual_amount,"
					+ "ticket_amount,will_profit,investment_type,op_dt,real_profit,status) values ("
					+ (i + 1) + "," + randomInt(34,183) + "," + randomInt(34,43) + "," + money + "," + realMoney + "," + ticketMoney
					+ "," + willProfit + ",'" + randomInt(1,2) + "','" + randomDateTimeString() + "'," + realProfit + "," + "'" + randomInt(-1,2)
					+"');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertCustomerBalanceSql() {
		//insert into customer_balance (account_id,gold_balance,congeal_val,will_loan,sum_loan,repayment_money,repayment_principal,
		//repayment_late_money,repayment_pre_money,repayment_30d_will,will_profit,sum_profit,sum_investment,receive_money,
		//receive_principal,receive_late_money,receive_pre_money,receive_transfer_money,net_assets,financial_assets,sum_recharge,
		//sum_withdraw,recharge_count,withdraw_count,investment_count,cancel_count,transfer_count,accept_count) values (
		for(int i=0; i<10; i++) {
			int goldBalance = randomInt(500,300000);
			int financialAssets = randomInt(300,30000);
			int willLoan = randomInt(1000,30000);
			int netAssets = financialAssets - willLoan + goldBalance;
			String sql = "insert into customer_balance (account_id,gold_balance,congeal_val,will_loan,sum_loan,repayment_money,repayment_principal,"
					+ "repayment_late_money,repayment_pre_money,repayment_30d_will,will_profit,sum_profit,sum_investment,receive_money,"
					+ "receive_principal,receive_late_money,receive_pre_money,receive_transfer_money,net_assets,financial_assets,sum_recharge,"
					+ "sum_withdraw,recharge_count,withdraw_count,investment_count,cancel_count,transfer_count,accept_count) values ("
					+ (i + 34) + "," + goldBalance + "," + randomInt(10,100) + "," + willLoan + ","
					+ randomInt(500,300000) + "," + randomInt(500,300000) + "," + randomInt(500,300000) + "," + randomInt(0,10000) + ","
					+ randomInt(0,10000) + "," + randomInt(500,30000) + "," + randomInt(100,5000) + "," + randomInt(500,30000) + ","
					+ randomInt(500,300000) + "," + randomInt(500,300000) + "," + randomInt(500,300000) + "," + randomInt(0,1000) + ","
					+ randomInt(0,1000) + "," + randomInt(500,30000) + ","
					+ netAssets + "," + financialAssets + "," + randomInt(0,300000) + "," + randomInt(0,30000) + ","
					+ randomInt(0,30) + "," + randomInt(0,30) + "," + randomInt(0,30) + "," + randomInt(0,30) + "," + randomInt(0,30) + "," + randomInt(0,30) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertCustomerBalanceHisSql() {
		//insert into customer_balance_his (id,account_id,change_val,balance,change_type,change_reason,op_dt) values (
		for(int i=0; i<150; i++) {
			int changeVal = randomInt(-100,100) * 100;
			String sql = "insert into customer_balance_his (id,account_id,change_val,balance,change_type,change_reason,op_dt) values ("
					+ (i + 1) + "," + randomInt(34,43) + "," + changeVal + "," + randomInt(500,300000) + ",'" + randomInt(0,7) + "','资金变化额为："
					+ changeVal + "','" + randomDateTimeString() + "');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectRepaymentPlanSql() {
		//insert into project_repayment_plan (plan_id,project_id,plan_date,plan_money,principal,interest,remaining_principal) values (
		for(int i=0; i<150; i++) {
			int principal = randomInt(100,1000);
			int interest = randomInt(10,150);
			int planMoney = principal + interest;
			String sql = "insert into project_repayment_plan (plan_id,project_id,plan_date,plan_money,principal,interest,remaining_principal) values ("
					+ (i + 11) + "," + randomInt(34,43) + ",'" + randomMonthString() + "'," + planMoney + "," + principal + "," + interest + "," + randomInt(100,10000) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectRepaymentSplitRecordSql() {
		//insert into project_repayment_split_record (split_record_id,record_id,project_id,
		for(int i=0; i<500; i++) {
			int repaymentUserId = randomInt(34,43);
			String repaymentAccount = "customer" + (repaymentUserId - 33);
			int payeeUserId = randomInt(34,43);
			String payeeAccount = "customer" + (repaymentUserId - 33);
			String sql = "insert into project_repayment_split_record (split_record_id,record_id,project_id,"
					+ "investment_record_id,repayment_user_id,repayment_account,payee_user_id,payee_account,"
					+ "money,repay_type,pre_penalty_money,late_penalty_money,principal,interest,remained_principal,"
					+ "repayment_dt,actual_repayment_dt,status) values ("
					+ (i + 1) + "," + randomInt(1,100) + "," + randomInt(34,93) + ","
					+ randomInt(1,150) + "," + repaymentUserId + ",'" + repaymentAccount + "'," + payeeUserId + ",'" + payeeAccount + "',"
					+ randomInt(10,1000) + "," + randomInt(0,4) + "," + randomInt(0,100) + "," + randomInt(0,100) + ","
					+ randomInt(100,1000) + "," + randomInt(1,300) + "," + randomInt(10,10000) + ",'" + randomDateTimeString() + "','"
					+ randomDateTimeString() + "','" + randomInt(-1,0) + "');";
			System.out.println(sql);
				
		}
	}
	
	public static void printInsertProjectWillLoanSql() {
		//insert into project_will_loan (id,title,duration,annualized_rate,money,status) values (
		for(int i=0; i<100; i++) {
			int id = i + 8;
			String sql = "insert into project_will_loan (id,title,duration,annualized_rate,money,status,create_user_id,create_dt) values ("
					+ id + ",'我要借款啊" + id + "'," + randomInt(6,20) + "," + randomInt(3,24) + ","
					+ randomInt(500,1000000) + ",'" + randomInt(0,4) + "'," + randomInt(34,43) + ",'" + randomDateTimeString() + "');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectExecuteSnapshotSql() {
		//insert into project_execute_snapshot (id,project_id,transfer_project_id,finance_money,end_finance_money,
		//end_repay_money,sum_service_charge,status,remaining_time
		for(int i=0; i<600; i++) {
			String sql = "insert into project_execute_snapshot (id,project_id,transfer_project_id,finance_money,end_finance_money,"
					+ "end_repay_money,sum_service_charge,status,remaining_time) values ("
					+ (i + 2) + "," + randomInt(34,183) + "," + randomInt(1,300) + "," + randomInt(1000,500000) + "," + randomInt(100,500000) + ","
					+ randomInt(100,500000) + "," + randomInt(100,10000) + ",'" + randomInt(0,3) + "'," + randomInt(1,30) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertCustomerWithdrawDepositSnapshotSql() {
		//insert into customer_withdraw_deposit_snapshot (account_id,deposit_balance) values (
		for(int i=0; i<10; i++) {
			int accountId = i + 34;
			String sql = "insert into customer_withdraw_deposit_snapshot (account_id,deposit_balance) values ("
					+ accountId + "," + randomInt(0,300) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertCustomerWithdrawDeposiHisSql() {
		//insert into customer_withdraw_deposi_his (id,account_id,change_val,change_reason,op_dt) values (
		for(int i=0; i<300; i++) {
			int changeVal = randomInt(-100,100);
			String sql = "insert into customer_withdraw_deposi_his (id,account_id,change_val,change_reason,op_dt) values ("
					+ (i + 1) + "," + randomInt(34,43) + "," + changeVal + ",'钱变啦：" + changeVal + "','" + randomDateTimeString() + "');";
			System.out.println(sql);
		}
		
	}
	
	public static void printInsertIntegralSnapshotSql() {
		//insert into customer_integral_snapshot (account_id,deposit_balance) values (
		for(int i=0; i<10; i++) {
			int accountId = i + 34;
			String sql = "insert into customer_integral_snapshot (account_id,integral_balance) values ("
					+ accountId + "," + randomInt(0,3000) + ");";
			System.out.println(sql);
		}
	}
	
	public static void printInsertCustomerIntegralHisSql() {
		//insert into customer_integral_his (id,account_id,change_val,change_reason,op_dt) values (
		for(int i=0; i<300; i++) {
			int changeVal = randomInt(-100,100);
			String sql = "insert into customer_integral_his (id,account_id,change_val,change_reason,op_dt) values ("
					+ (i + 2) + "," + randomInt(34,43) + "," + changeVal + ",'花生豆变啦：" + changeVal + "','" + randomDateTimeString() + "');";
			System.out.println(sql);
		}
		
	}
	
	public static void printInsertCustomerInvestmentTicketSql() {
		//insert into customer_investmetn_ticket (id,account_id,ticket_type_id,get_dt,invalid_dt,status) values (
		for(int i=0; i<100; i++) {
			String sql = "insert into customer_investment_ticket (id,account_id,ticket_type_id,get_dt,invalid_dt,status) values ("
					+ (i + 1) + "," + randomInt(34,43) + "," + randomInt(1,4) + ",'" + randomDateTimeString() + "','" + randomDateTimeFatureString() + "','"
					+ randomInt(0,2) + "');";
			System.out.println(sql);
		}
	}
	
	public static void printInsertProjectTransferInfoSql() {
		//insert into project_transfer_info (transfer_project_id,project_id,parent_transfer_project_id,is_recommend,
		//investment_record_id,project_end_date,next_repayment_date,transferor,transfer_price,fair_price,
		//discount_date,service_charge_type,create_date,remainder_creditor,status
		for(int i=0; i<300; i++) {
			String sql = "insert into project_transfer_info (transfer_project_id,project_id,parent_transfer_project_id,is_recommend,"
					+ "investment_record_id,project_end_date,next_repayment_date,transferor,transfer_price,fair_price,"
					+ "discount_date,service_charge_type,create_date,remainder_creditor,status) values("
					+ (i + 1) + "," + randomInt(34,183) + ",null,'" + randomInt(0,1) + "',"
					+ (i + 1) + ",'" + randomMonthString() + "','" + randomDateTimeString() + "'," + randomInt(34,43) + "," + randomInt(10,1000) * 100 + "," + randomInt(100,1000) * 100 + ",'"
					+ randomDateTimeFatureString() + "'," + randomInt(10,1000) + ",'" + randomDateTimeString() + "'," + randomInt(10,1000) * 50 + ",'" + randomInt(0,4) + "');";
			System.out.println(sql);
		}
	}
	
	/**
	 * 生成随机整数，范围在min-max之间，包括min、max
	 * @param min
	 * @param max
	 * @return
	 */
	public static int randomInt(int min, int max) {
		Random random = new Random();
		return random.nextInt(max - min + 1) + min;
	}
	
	public static String randomDateString() {
		Date date = DateUtils.addDays(new Date(), randomInt(10,60));
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
	
	public static String randomDateTimeString() {
		Date date = DateUtils.addDays(new Date(), randomInt(-30,-1));
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String randomDateTimeFatureString() {
		Date date = DateUtils.addDays(new Date(), randomInt(1,90));
		return DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
	}
	
	public static String randomMonthString() {
		Date date = DateUtils.addMonths(new Date(), randomInt(1,18));
		return DateFormatUtils.format(date, "yyyy-MM-dd");
	}
}
