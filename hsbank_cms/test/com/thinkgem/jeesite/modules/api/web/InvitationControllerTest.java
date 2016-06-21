package com.thinkgem.jeesite.modules.api.web;

import com.alibaba.fastjson.JSONObject;
import com.thinkgem.jeesite.modules.JunitTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuguoqing on 2016/5/23.
 */
public class InvitationControllerTest extends JunitTest{

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/myInvitation";

    @Before
    public void setUp(){
        //该方法在 test 方法运行前执行。
        //可以做些对象、数据初始化话操作
    }

    @After
    public void tearDown() throws Exception {
        //该方法在 test 方法运行后执行。
        //主要做些资源的释放
    }

    /**
     * 我的邀请-好友分页列表 测试
     */
    @Test
    public void friendsPageList() throws Exception {
        String methodUrl = "/friendsPageList";
        Map<String,String> paramsMap = new HashMap<>();
        logger.info("===1、 验证token，token不填情况, 提示“请重新登录” ===");
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        JSONObject json = super.checkCodeText(result);
        Assert.assertSame(json.get("code"),1);//code=1,token无效,重新登录

        logger.info("===2、返回数据正常 ===");
        paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);

    }

}