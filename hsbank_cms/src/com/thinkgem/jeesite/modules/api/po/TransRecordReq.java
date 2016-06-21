package com.thinkgem.jeesite.modules.api.po;

/**
 * 交易记录入参
 * @author lzb
 * @version 2016-05-18
 */
public class TransRecordReq {
    private String client;
    private String token;
    private String beginDate;   //交易开始日期
    private String endDate;     //交易结束日期
    private Integer type;       //交易类型(0:全部,1:充值[],2:提现,3:投标,4:回款,5:奖励,6:其他)

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(String beginDate) {
        this.beginDate = beginDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


}
