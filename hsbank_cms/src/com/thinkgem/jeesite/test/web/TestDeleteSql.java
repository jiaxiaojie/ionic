package com.thinkgem.jeesite.test.web;

public class TestDeleteSql {

	public static void deleteProjectRelative(String projectIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from project_base where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_date_node where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_car_flow where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_car_mortgage where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_enterprise_management where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_house_mortgage where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_person_consumption where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_factor_person_credit where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_review_record where project_id not in (").append(projectIds).append(");").append("\n");

        sb.append("delete from project_execute_snapshot where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_investment_record where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_repayment_plan where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_repayment_record where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_repayment_split_record where project_id not in (").append(projectIds).append(");").append("\n");
        sb.append("delete from project_transfer_info where project_id not in (").append(projectIds).append(");");
        System.out.println(sb);
	}
	
	public static void deleteCustomerRelative(String accountIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from customer_contacts where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_cosurety where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_credit_auth where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_finance where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_housing where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_org_extend_info where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_org_finance_year_record where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_work where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_work_unit where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_car where customer_id in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_account where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_balance where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_balance_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_bank_card where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_bank_card_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_base where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_base_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_free_withdraw_count_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_gold_coin_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_gold_coin_snapshot where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_integral_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_integral_snapshot where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_investment_money_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_investment_ticket where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_login_log where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_recharge_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_rest_pwd_log where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_deposi_his where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_deposit_snapshot where account_id in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_process where account_id in (").append(accountIds).append(");").append("\n");
		System.out.println(sb);
	}
	
	public static void deleteCustomerRelativeNotInAccountIds(String accountIds) {
		StringBuffer sb = new StringBuffer();
		sb.append("delete from customer_contacts where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_cosurety where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_credit_auth where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_finance where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_housing where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_org_extend_info where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_org_finance_year_record where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_work where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_work_unit where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_car where customer_id not in (select customer_id from customer_base where account_id in (" ).append(accountIds ).append("));").append("\n");
		sb.append("delete from customer_account where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_balance where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_balance_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_bank_card where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_bank_card_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_base where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_base_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_free_withdraw_count_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_gold_coin_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_gold_coin_snapshot where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_integral_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_integral_snapshot where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_investment_money_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_investment_ticket where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_login_log where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_recharge_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_rest_pwd_log where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_deposi_his where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_deposit_snapshot where account_id not in (").append(accountIds).append(");").append("\n");
		sb.append("delete from customer_withdraw_process where account_id not in (").append(accountIds).append(");").append("\n");
		System.out.println(sb);
	}
	
	public static void main(String[] args) {
		deleteCustomerRelative("92, 94, 96");
	}
}
