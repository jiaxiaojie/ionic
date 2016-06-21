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
using com.yeepay.utils;


public partial class paymentConfirm : System.Web.UI.Page
{
    protected void Page_Load(object sender, EventArgs e)
    {
        // 从Web.config文件里读取商户密钥
        string keyvalue = ConfigurationManager.AppSettings["keyValue"];
        // 从Web.config文件里读取商户编号
        string merchantId = ConfigurationManager.AppSettings["merchantId"];
        // 定义存储提交参数字符串的集合
        NameValueCollection Params = new NameValueCollection();
        // 将商户编号加入到集合中
        Params.Add("p1_MerId",merchantId);
        // 遍历页面提交的参数，并将参数写入集合
        foreach(string obj in Request.Form)
		{
            Params.Add(obj,Request.Form[obj]);
        }
        // 实例化分账业务处理类
        doRequestHttp req = new doRequestHttp();
        // 调用提交函数并获得返回结果字符串  参数一：PaymentConfirm为提交业务类型,参数二：Params为参数集合,参数参数三：1为服务器重定向;2为服务器点对点通讯
        string resultStr = req.returnResult("PaymentConfirm",Params,"2");
        // 输出结果
        Response.Write(resultStr);
    }
}