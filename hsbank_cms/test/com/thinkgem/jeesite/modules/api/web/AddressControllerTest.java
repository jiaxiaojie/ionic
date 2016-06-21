package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.modules.api.frame.util.ValueObjectUtils;
import com.thinkgem.jeesite.modules.api.web.base.BaseControllerTest;
import com.thinkgem.jeesite.modules.api.web.param.DeleteAddressParams;
import com.thinkgem.jeesite.modules.api.web.param.MergeAddressParams;
import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.Map;

/**
 * Created by 万端瑞 on 2016/6/3.
 */

public class AddressControllerTest extends BaseControllerTest {

    @Resource
    private AddressController apiAddressController;




    @Test
    public void mergeAddress() throws Exception {


       MergeAddressParams mergeAddressParams = new MergeAddressParams();
        mergeAddressParams.setAddress("我的地址");
        mergeAddressParams.setDistrictId("1765");
        mergeAddressParams.setIsDefault("1");
        mergeAddressParams.setMobile("133208330118");
        mergeAddressParams.setPostCode("432100");
        mergeAddressParams.setShowName("万端瑞");

        Map<String,Object> result = apiAddressController.mergeAddress(this.getToken(),mergeAddressParams);
        Assert.assertEquals(result.get("code"),0);
    }

    @Test
    public void deleteAddress() throws Exception {

        //1.查询
        Map<String,Object> result = apiAddressController.pageList(this.getToken(),new PageParams());

        //2.删除
        DeleteAddressParams deleteAddressParams = new DeleteAddressParams();
        deleteAddressParams.setAddressId((String)ValueObjectUtils.getValueByPath(result,"data.resultList[0].addressId"));
        apiAddressController.deleteAddress(this.getToken(),deleteAddressParams);

        //2.查询
        //Map<String,Object> result2 = apiAddressController.pageList(this.getToken(),new PageParams());

        //System.out.println();
    }

    @Test
    public void pageList() throws Exception {
       // MvcResult result = null;

        Map<String,Object> result = apiAddressController.pageList(this.getToken(),new PageParams());
        System.out.println();
    }

}