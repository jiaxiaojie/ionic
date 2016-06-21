package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.modules.JunitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pc on 2016/5/23.
 */
public class AccountManageControllerTest extends JunitTest{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/accountManage";

    @Test
    public void getEmailCode() throws Exception {

    }

    @Test
    public void activateEmail() throws Exception {

    }

    @Test
    public void updateEmail() throws Exception {

    }

    @Test
    public void personalInfor() throws Exception {

    }

    @Test
    public void myAccount() throws Exception {

    }

    @Test
    public void bankInfor() throws Exception {

    }

    /**
     * 账户设置-银行卡解绑结果
     * @throws Exception
     */
    @Test
    public void unbindBankCardResult() throws Exception {
        String methodUrl = "/unbindBankCardResult";
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }
}