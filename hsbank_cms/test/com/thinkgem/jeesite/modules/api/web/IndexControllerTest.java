package com.thinkgem.jeesite.modules.api.web;

import com.thinkgem.jeesite.common.utils.ZxingHandler;
import com.thinkgem.jeesite.modules.JunitTest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuguoqing on 2016/5/23.
 */
public class IndexControllerTest extends JunitTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/index";

    @Before
    public void setUp(){
        //该方法在 test 方法运行前执行。可以做些对象、数据初始化话操作
    }

    @After
    public void tearDown() throws Exception {
        //该方法在 test 方法运行后执行。可以做些资源的释放
    }

    @Test
    public void getLatestInvestPageList() throws Exception {

    }

    @Test
    public void todayRanking() throws Exception {

    }

    @Test
    public void getVerifyCode() throws Exception {

    }

    @Test
    public void login() throws Exception {

    }

    @Test
    public void register() throws Exception {

    }

    @Test
    public void getAdPositionInfo() throws Exception {

    }

    @Test
    public void setAdPositionInfo() throws Exception {

    }

    /**
     * 最新公告列表 测试
     */
    @Test
    public void getNoticePageList() {
        String methodUrl = "/getNoticePageList";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("flag","1");
        paramsMap.put("pageSize","100");
        paramsMap.put("pageNumber","1");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     * 最新公告内容 测试
     */
    @Test
    public void getNoticeDetails() {
        String methodUrl = "/getNoticeDetails";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("noticeId", "f68022b2d34c412d97f248bad42b1cca");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     * 累计募集 测试
     */
    @Test
    public void collectTotal() {
        String methodUrl = "/collectTotal";
        Map<String, String> paramsMap = new HashMap<>();
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     * 媒体报道分页列表 测试
     */
    @Test
    public void getNewsPageList() {
        String methodUrl = "/getNewsPageList";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("newsType","2");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     *  媒体报道内容 测试
     */
    @Test
    public void getNewsDetails() {
        String methodUrl = "/getNewsDetails";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("newsId", "070b047e12b744aba4d70996f081d91d");
        String result = super.httpPost(url + methodUrl, paramsMap);
        //check code、text
        super.checkCodeText(result);
    }

    /**
     *  生成二维码 测试
     */
    @Test
    public void generateQrCode() {
        String methodUrl = "/generateQrCode";
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("client", "3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("content", "http%3A%2F%2Fm.hsbank360.com%2Fsign%2Fregister%3Fm%3DMTM4MTg0MzU2MTI%3D");
        String result = super.httpPost(url + methodUrl, paramsMap);
    }

    @Test
    public void getQrCode(){
    // 二维码
        String fileType = "png";
        String contents = "http%3A%2F%2Fm.hsbank360.com%2Fsign%2Fregister%3Fm%3DMTM4MTg0MzU2MTI%3D";

//        Integer[] size = {110,120,125,130,135,140};
        Integer[] size = {120};
        for (int i = 0; i < size.length; i++) {
            String imgPath = "d:\\zxing_"+size[i]+"."+fileType;
            try {
                // zxing
                ZxingHandler.createQRCode(contents, size[i], size[i], fileType, imgPath);
                ZxingHandler.addLogo_QRCode(new File(imgPath),new File("D:/logo/hs-34x34.png"), fileType, null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        logger.info("finished zxing encode.");
    }

}