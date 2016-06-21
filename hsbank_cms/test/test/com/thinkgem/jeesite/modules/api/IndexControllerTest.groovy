package test.com.thinkgem.jeesite.modules.api

import org.junit.Test
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * 首页Controller
 */
class IndexControllerTest {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private String url = "http://localhost:8080/f/api/index"

    void setUp() {
        super.setUp()
    }

    void tearDown() {
    }

    /**
     * 最新公告列表 测试
     */
    @Test
    public void testGetNoticePageList() {
        String methodUrl = "/getNoticePageList";
        Map<String,String> paramsMap = new HashMap<>();
        String result = HttpClientUtils.httpPost(url + methodUrl,paramsMap);
        System.out.println(result);
    }

    /**
     * 最新公告内容 测试
     */
    @Test
    public void testGetNoticeDetails() {
        String methodUrl = "/getNoticeDetails";
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("noticeId","f68022b2d34c412d97f248bad42b1cca");
        String result = HttpClientUtils.httpPost(url + methodUrl,paramsMap);
        System.out.println(result);
    }
    /**
     * 累计募集 测试
     */
    @Test
    public void testCollectTotal() {
        String methodUrl = "/collectTotal";
        Map<String,String> paramsMap = new HashMap<>();
        String result = HttpClientUtils.httpPost(url + methodUrl, paramsMap);
        System.out.println(result);
    }

    /**
     * 媒体报道分页列表 测试
     */
    @Test
    public void testGetNewsPageList() {
        String methodUrl = "/getNewsPageList";
        Map<String,String> paramsMap = new HashMap<>();
        String result = HttpClientUtils.httpPost(url + methodUrl,paramsMap);
        System.out.println(result);
    }

    /**
     * 最新公告内容 测试
     */
    @Test
    public void testGetNewsDetails() {
        String methodUrl = "/getNewsDetails";
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("newsId","3d7e015aeea04dcf915932f2e6bf0909");
        String result = HttpClientUtils.httpPost(url + methodUrl,paramsMap);
        System.out.println(result);
    }

    /**
     * 我的邀请-好友分页列表 测试
     */
    @Test
    public void testFriendsPageList() {
        String methodUrl = "http://localhost:8080/f/api/myInvitation/friendsPageList";
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        String result = HttpClientUtils.httpPost(methodUrl, paramsMap);
        System.out.println(result);
    }
    /**
     * 我的邀请-好友分页列表 测试
     */
    @Test
    public void testPageList() {
        String methodUrl = "http://139.196.5.141:7007/hsbank/f/api/regular/pageList";
        Map<String,String> paramsMap = new HashMap<>();
        paramsMap.put("client","3d7e015aeea04dcf915932f2e6bf0909");
        //paramsMap.put("token","b48bc3ece33a43379dc52737f263cfe5");
        String result = HttpClientUtils.httpPost(methodUrl, paramsMap);
        System.out.println(result);
    }
}
