package com.thinkgem.jeesite.modules.api.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.JunitTest;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 我的花生 junit test
 * Created by liuguoqing on 2016/5/24.
 */
public class PeanutControllerTest extends JunitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/myPeanut";

    @Test
    public void myPeanutPageList() throws Exception {

    }

    /**
     * 我的花生-兑换记录(订单)分页列表
     * @throws Exception
     */
    @Test
    public void orderPageList() throws Exception {
        String methodUrl = "/orderPageList";
        Map<String, String> paramsMap = new HashMap<>();
        //paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");

        logger.info("===1、 验证token，token不填情况, 提示“请重新登录” ===");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        JSONObject json = super.checkCodeText(result);
        Assert.assertSame(json.get("code"),1);//code=1,token无效,重新登录

        logger.info("===2、返回数据正常 ===");
        paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        paramsMap.put("searchFlag","0");
        result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     * 我的花生-兑换记录(订单)详情
     * @throws Exception
     */
    @Test
    public void orderDetails() throws Exception {
        String methodUrl = "/orderDetails";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("orderCode", "1463561427833667");
        logger.info("===1、 验证token，token不填情况, 提示“请重新登录” ===");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        JSONObject json = super.checkCodeText(result);
        Assert.assertSame(json.get("code"),1);//code=1,token无效,重新登录

        logger.info("===2、 验证orderCode，orderCode不填情况, 提示“缺少订单编号” ===");
        paramsMap.put("token", "b48bc3ece33a43379dc52737f263cfe5");
        paramsMap.put("orderCode","");
        result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        json = super.checkCodeText(result);
        Assert.assertSame(json.get("code"),-1);//code=-1,缺少订单编号

        logger.info("===3、返回数据正常 ===");
        paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        paramsMap.put("orderCode","1463561427833667");
        result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

}