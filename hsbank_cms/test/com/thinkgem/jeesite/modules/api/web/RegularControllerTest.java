package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.modules.JunitTest;
import com.thinkgem.jeesite.modules.api.web.base.BaseControllerTest;
import com.thinkgem.jeesite.modules.api.web.param.AboutFilesParams;
import org.junit.Assert;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by pc on 2016/5/24.
 */
public class RegularControllerTest  extends BaseControllerTest {
    @Resource
    RegularController regularController;
    @Test
    public void aboutFiles() throws Exception {
        AboutFilesParams params = new AboutFilesParams();
        params.setProjectId(521);
        Map<String,Object> result = regularController.aboutFiles(params);
        Assert.assertEquals(result.get("code"),0);
    }

    @Test
    public void projectPaymentPlan() throws Exception {

    }

    @Test
    public void investRepaymentPlan() throws Exception {

    }

    @Test
    public void investmentRecords() throws Exception {

    }


}