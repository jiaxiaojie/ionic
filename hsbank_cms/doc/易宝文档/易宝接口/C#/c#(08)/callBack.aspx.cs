using System;
using System.IO;  
using System.Net;  
using System.Text; 
using System.Data;
using System.Configuration;
using System.Collections;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Web.UI.HtmlControls;
using System.Collections.Specialized;
using YeePay_Distribute;
using com.yeepay.utils;


public partial class callBack : System.Web.UI.Page
{
	protected void Page_Load(object sender, EventArgs e)
    {
	//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
    		// 从Web.config文件里读取商户密钥
        string keyvalue = ConfigurationManager.AppSettings["KeyValue"];
        // 从Web.config文件里读取商户编号
        string merchantId = ConfigurationManager.AppSettings["merchantId"];
        // 定义存储提交参数字符串的集合
        NameValueCollection Params = new NameValueCollection();
        log.createLog("Buy", "callBackUrl" + ":" + Request.RawUrl, "");
        // 遍历页面提交的参数，并将参数写入集合
        foreach(string obj in Request.QueryString)
        {
            Params.Add(obj,Request.QueryString[obj]);
        }
        // 实例化接收返回数据类
        doResponseHttp backParams = new doResponseHttp();
        // 
        string localhmac = backParams.getCallBackParams(Params);

        if (localhmac != Request.QueryString["hmac"])
        {
            Response.Write("Hmac不匹配，交易信息被篡改！");
        }
        else
        {
            if (Request.QueryString["r1_Code"] == "1")
            {
                Response.Write("success<br>交易成功！");
                // 处理支付成功时的业务
                Business.doSuccessAfterPay(Params);
            }
            else
            {
                Response.Write("交易失败！");
                // 处理失败时的业务
                Business.doFailAfterPay(Params);
            }
        }
    }
}