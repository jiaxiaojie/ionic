package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.api.frame.format.annotation.Formater;
import com.thinkgem.jeesite.modules.api.frame.generator.APIGenerator;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.API;
import com.thinkgem.jeesite.modules.api.frame.generator.obj.APINode;
import com.thinkgem.jeesite.modules.api.frame.spring.controller.base.NAPIBaseController;
import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import com.thinkgem.jeesite.modules.api.web.param.DeleteAddressParams;
import com.thinkgem.jeesite.modules.api.web.param.MergeAddressParams;
import com.thinkgem.jeesite.modules.api.web.param.base.PageParams;
import com.thinkgem.jeesite.modules.entity.CustomerAddress;
import com.thinkgem.jeesite.modules.integral.service.CustomerAddressService;
import com.thinkgem.jeesite.modules.project.ProjectConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by 万端瑞 on 2016/5/24.
 */

@Controller("apiAddressController")
@RequestMapping(value="${frontPath}/api/myAddress")
public class AddressController extends NAPIBaseController {

    @Autowired
    private CustomerAddressService customerAddressService;

    /**
     * 我的收货地址-新增/编辑地址 （有addressId则为编辑，没有则为新增）
     * @param mergeAddressParams
     * @author 万端瑞
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "mergeAddress")
    public API mergeAddress(Token token, MergeAddressParams mergeAddressParams){

        //参数转实体
        CustomerAddress customerAddress = mergeAddressParams.toEntity();
        customerAddress.setAccountId(token.getAccountId());
        customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL);

        //执行更新操作
        customerAddressService.updateAccountAddress(customerAddress);

        return APIGenerator.createResultAPI(true);
    }


    /**
     * 我的收货地址-删除地址
     * @param deleteAddressParams
     * @author 万端瑞
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "deleteAddress")
    public API deleteAddress(Token token, DeleteAddressParams deleteAddressParams){

        //操作成功标识
        Boolean success = false;

        CustomerAddress customerAddress = customerAddressService.get(deleteAddressParams.getAddressId());
        if(customerAddress != null && customerAddress.getAccountId() != null
                && customerAddress.getAccountId().equals(token.getAccountId())){
            //修改address状态为删除
            customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_DELETED);
            customerAddressService.update(customerAddress);
            success = true;
        }

        return APIGenerator.createResultAPI(success);
    }


    /**
     * 我的收货地址-分页列表
     * @param pageParams
     * @author 万端瑞
     * @return
     */
    @Formater(path = "${frontPath}/api/address/pageList")
    @ResponseBody
    @RequestMapping(value = "pageList")
    public Map<String,Object> pageList(Token token,PageParams pageParams){

        //查询数据
        CustomerAddress customerAddress = new CustomerAddress();
        customerAddress.setAccountId(token.getAccountId());
        customerAddress.setStatus(ProjectConstant.CUSTOMER_ADDRESS_STATUS_NORMAL);
        Page<CustomerAddress> page = customerAddressService.findDetailAddressPage(new Page<CustomerAddress>(pageParams.getPageNumber(),pageParams.getPageSize(),true),customerAddress);
        //构造resultList节点
        APINode resultList = APIGenerator.toAPINodeWithCollection(page.getList());

        //构造API
        API api = APIGenerator.createAPIBuilder().putDataChildNode("count",page.getCount())
                .putDataChildNode("resultList",resultList).build();

        return api;
    }

}
