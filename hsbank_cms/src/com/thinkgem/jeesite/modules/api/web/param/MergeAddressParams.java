package com.thinkgem.jeesite.modules.api.web.param;

import com.thinkgem.jeesite.modules.api.web.param.base.BaseParams;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by 万端瑞 on 2016/5/26.1
 */

public class MergeAddressParams extends BaseParams {
    private String showName; //收件人名称
    private String mobile; //收件人手机号码
    private String districtId = "0"; //区、县Id
    private String address; //收件人详细地址
    private String postCode; //收件人邮编
    private String isDefault; //是否缺省收件地址(0是，1不是)
    private String addressId; //记录Id(空值为新增,否则为编辑)

    @NotBlank(message="收件人名称不能为空")
    @Length(min=0, max=100, message="收件人名称长度必须介于 0 和 100 之间")
    public String getShowName() {
        return showName;
    }

    public void setShowName(String showName) {
        this.showName = showName;
    }

    @NotBlank(message="收件人手机号不能为空")
    @Length(min=0, max=20, message="收件人手机号长度必须介于 0 和 20 之间")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    @NotBlank(message = "区、县Id不能为空")
    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    @NotBlank(message="收件人地址不能为空")
    @Length(min=0, max=500, message="收件人地址长度必须介于 0 和 500 之间")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Length(min=0, max=6, message="收件人邮编长度必须介于 0 和 6 之间")
    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    @Length(min=0, max=1, message="是否缺省收件地址长度必须介于 0 和 1 之间")
    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }



    public CustomerAddress toEntity(){
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setShowName(this.showName);
        customerAddress.setMobile(this.mobile);
        customerAddress.setDistrictId(this.districtId);
        customerAddress.setAddress(this.address);
        customerAddress.setPostCode(this.postCode);
        customerAddress.setIsDefault(this.isDefault);
        customerAddress.setId(this.addressId);

        return customerAddress;
    }
}
