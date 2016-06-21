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
	/// paymentConfirm 的摘要说明。
	/// </summary>
	public class paymentConfirm : System.Web.UI.Page
	{
		private void Page_Load(object sender, System.EventArgs e)
		{
			// 在此处放置用户代码以初始化页面
			// 从Web.config文件里读取商户密钥
			string keyvalue = ConfigurationSettings.AppSettings["keyValue"];
			// 从Web.config文件里读取商户编号
			string merchantId = ConfigurationSettings.AppSettings["merchantId"];
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
