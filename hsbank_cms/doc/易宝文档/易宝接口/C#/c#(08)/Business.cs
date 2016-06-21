using System;
using System.Data;
using System.Configuration;
using System.Linq;
using System.Web;
using System.Web.Security;
using System.Web.UI;
using System.Web.UI.HtmlControls;
using System.Web.UI.WebControls;
using System.Web.UI.WebControls.WebParts;
using System.Xml.Linq;
using System.Collections.Specialized;

namespace YeePay_Distribute
{
    // 商户可在此处做业务
    public class Business
    {
        // 发起支付请求时需要做的业务
        public static void doBeforPay()
        {
        
        }
        // 支付失败后需要做的业务
        public static void doFailAfterPay(NameValueCollection reParams)
        {
            string p1_MerId = reParams["p1_MerId"];
            string r0_Cmd = reParams["r0_Cmd"];
            string r1_Code = reParams["r1_Code"];
            string r2_TrxId = reParams["r2_TrxId"];
            string r3_Amt = reParams["r3_Amt"];
            string r4_Cur = reParams["r4_Cur"];
            string r5_Pid = reParams["r5_Pid"];
            string r6_Order = reParams["r6_Order"];
            string r8_MP = reParams["r8_MP"];
            string r9_BType = reParams["r9_BType"];
            string ra_Details = reParams["ra_Details"];
            string rb_SplitStatus = reParams["rb_SplitStatus"];

            // 可在此处编写SQL语句
            //#-----提示-----#//
            //请在更新数据之前先判断是否存在重复订单
        }
        // 定义商户业务处理函数
         public static void doSuccessAfterPay(NameValueCollection reParams)
        {

            string p1_MerId = reParams["p1_MerId"];
            string r0_Cmd = reParams["r0_Cmd"];
            string r1_Code = reParams["r1_Code"];
            string r2_TrxId = reParams["r2_TrxId"];
            string r3_Amt = reParams["r3_Amt"];
            string r4_Cur = reParams["r4_Cur"];
            string r5_Pid = reParams["r5_Pid"];
            string r6_Order = reParams["r6_Order"];
            string r8_MP = reParams["r8_MP"];
            string r9_BType = reParams["r9_BType"];
            string ra_Details = reParams["ra_Details"];
            string rb_SplitStatus = reParams["rb_SplitStatus"];

            // 可在此处编写SQL语句
            //#-----提示-----#//
            //请在更新数据之前先判断是否存在重复订单

        }
 
    }
}
