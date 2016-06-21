package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.modules.JunitTest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * 花生乐园接口 测试
 * Created liuguoqing pc on 2016/5/24.
 */
public class IntegralMallControllerTest extends JunitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/integralMall";

    @Test
    public void orderPageList() throws Exception {
        String methodUrl = "/buyOrderList";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("limit", "1");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

}