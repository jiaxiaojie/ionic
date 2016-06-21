package com.thinkgem.jeesite.modules.api.web.base;

import com.thinkgem.jeesite.modules.api.frame.spring.validate.login.Token;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by 万端瑞 on 2016/6/5.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:/spring-test*.xml"})
public class BaseControllerTest {
    public Token getToken(){
        Token token = new Token();
        token.setToken("a572d2f953594dfdae070792f05c3518");
        token.setClient("eyJhbmRyb2lkIjp7ImNoYW5uZWwiOiJmZGpmIiwiZGV2aWNlTW9kZWwiOiJVNzA3VCIsImRldmljZU51bWJlciI6IjE4NTczMTE1NDkzIiwibWQ1IjoiODE4QjQ2NkM3MDQ2MzJDNTMyRDI5MTM0NzM2NTA0NkQiLCJzZGtWZXJzaW9uIjoiMTciLCJzeXN0ZW1WZXJzaW9uIjoiNC4yLjEifSwibGFuZ3VhZ2UiOiJ6aCIsInR5cGUiOiJhbmRyb2lkIiwidmVyc2lvbiI6IjEuMi4yLjAifQ\\u003d\\u003d");
        return token;
    }
}
