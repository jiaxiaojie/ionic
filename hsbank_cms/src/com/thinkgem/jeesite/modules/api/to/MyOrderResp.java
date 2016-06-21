package com.thinkgem.jeesite.modules.api.to;

public class MyOrderResp {

    private String orderCode;    //订单编号
    private Long productId;    //商品Id
    private String productName;    //商品名称
    private String typeId;		// 产品类别
    private String typeName;		//产品类别名称
    private Integer productCount;    //商品数量
    private String logoMin;    //商品图片
    private Integer price;    //花生豆总价
    private String createDt;    //交易时间
    private Long status;    //状态
    private String statusName;    //状态名称
    private Integer productPrice; //产品单价

    //地址信息
    private String showName;		// 收件人名称
    private String mobile;		// 收件人手机号
    private String address;		// 收件人地址
    private String postCode;		// 收件人邮编

    private String provinceId;   //省份ID
    private String provinceName; //省份名称
    private String cityId;  //城市ID
    private String cityName; //城市名称
    private String districtId; //区县ID
    private String districtName; //区县名称

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getProductCount() {
        return productCount;
    }

    public void setProductCount(Integer productCount) {
        this.productCount = productCount;
    }

    public String getLogoMin() {
        return logoMin;
    }

    public void setLogoMin(String logoMin) {
        this.logoMin = logoMin;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public Long getStatus() {
        return status;
    }

    public void setStatus(Long status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getDistrictName() {
        return districtName;
    }

    public void setDistrictName(String districtName) {
        this.districtName = districtName;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("MyOrderResp{");
        sb.append("orderCode='").append(orderCode).append('\'');
        sb.append(", productId=").append(productId);
        sb.append(", productName='").append(productName).append('\'');
        sb.append(", typeId=").append(typeId);
        sb.append(", typeName='").append(typeName).append('\'');
        sb.append(", productCount=").append(productCount);
        sb.append(", logoMin='").append(logoMin).append('\'');
        sb.append(", price=").append(price);
        sb.append(", createDt='").append(createDt).append('\'');
        sb.append(", status=").append(status);
        sb.append(", statusName='").append(statusName).append('\'');
        sb.append(", productPrice=").append(productPrice);
        sb.append(", showName='").append(showName).append('\'');
        sb.append(", mobile='").append(mobile).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", postCode='").append(postCode).append('\'');
        sb.append(", provinceId='").append(provinceId).append('\'');
        sb.append(", provinceName='").append(provinceName).append('\'');
        sb.append(", cityId='").append(cityId).append('\'');
        sb.append(", cityName='").append(cityName).append('\'');
        sb.append(", districtId='").append(districtId).append('\'');
        sb.append(", districtName='").append(districtName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
