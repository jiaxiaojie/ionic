using System;
using System.Data;
using System.Configuration;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.IO;

namespace com.yeepay.utils
{
    public class log
    {
        // 创建日志文件      
        public static void createLog(string actionType, string paramStr, string hmac)
        {

            StreamWriter sw = new StreamWriter(System.Web.HttpContext.Current.Server.MapPath("yeepay_distribute.log"), true);
            sw.WriteLine(("[" + DateTime.Now.ToString("yyyy-MM-dd HH:mm:ss") + ":" + DateTime.Now.Millisecond + "]" + "[" + actionType + "]" + "[" + paramStr + "]" + "[" + hmac + "]"));
            sw.Flush();
            sw.Close();
        }
    }
}
