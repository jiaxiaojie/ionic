select n1.* from
(select recommend_account_id as recommend_account_id,count(1) as  cc from customer_account where recommender_type='2' and status_code='0' group by recommend_account_id ) n1
where n1.recommend_account_id not in (select DISTINCT(account_id) account_id from customer_investment_ticket aaa where aaa.get_remark='邀请好友满5位赠送')
and n1.cc>=5
order by n1.cc desc