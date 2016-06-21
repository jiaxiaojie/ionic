using System;
using System.Collections.Specialized;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Web;
using System.Web.SessionState;
using System.Web.UI;
using System.Web.UI.WebControls;
using System.Web.UI.HtmlControls;
using System.Configuration;
using com.yeepay.utils;
using com.yeepay.fengzhangbiz;

namespace YeePay_Distrubute2._0
{
	/// <summary>
	/// callBack 的摘要说明。
	/// </summary>
	public class callBack : System.Web.UI.Page
	{
		private void Page_Load(object sender, System.EventArgs e)
		{
			//在接收到支付结果通知后，判断是否进行过业务逻辑处理，不要重复进行业务逻辑处理
			// 在此处放置用户代码以初始化页面
			// 从Web.config文件里读取商户密钥
			string keyvalue = ConfigurationSettings.AppSettings["KeyValue"];
			// 从Web.config文件里读取商户编号
			string merchantId = ConfigurationSettings.AppSettings["merchantId"];
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

		#region Web 窗体设计器生成的代码
		override protected void OnInit(EventArgs e)
		{
			//
			// CODEGEN: 该调用是 ASP.NET Web 窗体设计器所必需的。
			//
			InitializeComponent();
			base.OnInit(e);
		}
		
		/// <summary>
		/// 设计器支持所需的方法 - 不要使用代码编辑器修改
		/// 此方法的内容。
		/// </summary>
		private void InitializeComponent()
		{    
			this.Load += new System.EventHandler(this.Page_Load);

		}
		#endregion
	}
}
