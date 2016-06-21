package com.thinkgem.jeesite.modules.project;


/**
 * P2P公共配置参数
 * <p>
 * 单例类
 * @author Arthur.Xie
 * 2015-04-23
 */
public class ProjectConfig {
	/**单例*/
	private static ProjectConfig instance = null;
	/**短信通道apiUrl*/
    private String smsApiUrl = "http://www.shfuding.com/sms/listener";
	/**短信通道apiKey(api账户)*/
    private String smsApiKey = "fd_sms";
	/**短信通道secretKey(api密码)*/
    private String smsSecretKey = "9851232de99ac56999655a35bf0e018b";
	/**逾期代偿天数*/
    private int maxOverdueDayCount = 90;
	/**距下一个收款日N天内，不允许发起债权转让*/
    private int dayCountToNextRepay = 5;
	/**债权转让时：向转让方收取的手续费比例*/
    private double assignmentFeeRateUp = 0.002;
    /**债权转让时：向受让方收取的手续费比例*/
    private double assignmentFeeRateDown = 0.002;
    /**平台垫付金额比例*/
    private double platformAmountRate = 0.0018;
    /**投资时，收取服务费的比例*/
    private double serviceChargeRate = 0.0036;
    /**投资时，可使用优惠券的比例*/
    private double ticketAmountRate = 0.005;
    /**债权转让周期：默认24小时*/
    private int maxAssignmentHours  = 24;
    /**最大投资金额*/
    private int maxAmountDefault = 10000;
    /**每日最多取款次数*/
    private int maxWithdrawCount =1;
    
	/**私有构造函数*/
	private ProjectConfig() {
	}
	
	/**
     * 得到单例
     * @return  
     */
	public static synchronized ProjectConfig getInstance() {
		return instance == null ? instance = new ProjectConfig() : instance;
	}
	
	public String getSmsApiUrl() {
		return smsApiUrl;
	}

	public void setSmsApiUrl(String smsApiUrl) {
		this.smsApiUrl = smsApiUrl;
	}

	public String getSmsApiKey() {
		return smsApiKey;
	}

	public void setSmsApiKey(String smsApiKey) {
		this.smsApiKey = smsApiKey;
	}

	public String getSmsSecretKey() {
		return smsSecretKey;
	}

	public void setSmsSecretKey(String smsSecretKey) {
		this.smsSecretKey = smsSecretKey;
	}
	
	public int getMaxOverdueDayCount() {
		return maxOverdueDayCount;
	}

	public void setMaxOverdueDayCount(int maxOverdueDayCount) {
		this.maxOverdueDayCount = maxOverdueDayCount;
	}

	public int getDayCountToNextRepay() {
		return dayCountToNextRepay;
	}

	public void setDayCountToNextRepay(int dayCountToNextRepay) {
		this.dayCountToNextRepay = dayCountToNextRepay;
	}

	public double getAssignmentFeeRateUp() {
		return assignmentFeeRateUp;
	}

	public void setAssignmentFeeRateUp(double assignmentFeeUp) {
		this.assignmentFeeRateUp = assignmentFeeUp;
	}

	public double getAssignmentFeeRateDown() {
		return assignmentFeeRateDown;
	}

	public void setAssignmentFeeRateDown(double assignmentFeeDown) {
		this.assignmentFeeRateDown = assignmentFeeDown;
	}

	public double getPlatformAmountRate() {
		return platformAmountRate;
	}

	public void setPlatformAmountRate(double platformAmountRate) {
		this.platformAmountRate = platformAmountRate;
	}

	public double getServiceChargeRate() {
		return serviceChargeRate;
	}

	public void setServiceChargeRate(double serviceChargeRate) {
		this.serviceChargeRate = serviceChargeRate;
	}

	public double getTicketAmountRate() {
		return ticketAmountRate;
	}

	public void setTicketAmountRate(double ticketAmountRate) {
		this.ticketAmountRate = ticketAmountRate;
	}

	public int getMaxAssignmentHours() {
		return maxAssignmentHours;
	}

	public void setMaxAssignmentHours(int maxAssignmentHours) {
		this.maxAssignmentHours = maxAssignmentHours;
	}
	
	public int getMaxAmountDefault() {
		return maxAmountDefault;
	}

	public int getMaxWithdrawCount() {
		return maxWithdrawCount;
	}

	public void setMaxWithdrawCount(int maxWithdrawCount) {
		this.maxWithdrawCount = maxWithdrawCount;
	}

	public void setMaxAmountDefault(int maxAmountDefault) {
		this.maxAmountDefault = maxAmountDefault;
	}

	
}
