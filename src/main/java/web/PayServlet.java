package web;

import utils.PaymentUtil;
import utils.PropUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/12.
 */
public class PayServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        PropUtil prop=new PropUtil("merchantInfo.properties");
        //1、准备参数
        String p0_Cmd="Buy";
        String p1_MerId=prop.getKey("p1_MerId");
        String p2_Order = httpServletRequest.getParameter("orderid");
        //String p3_Amt = os.getMoneyByOid(p2_Order)+"";//正式环境用
        String p3_Amt = "0.01";//开发测试时使用
        String p4_Cur="CNY";
        String p5_Pid = "";
        String p6_Pcat="";
        String p7_Pdesc="";
        //回掉时调用的地址
        String p8_Url=prop.getKey("responseURL");
        String p9_SAF="";
        String pa_MP="";
        //使用什么类型的银行卡
        String pd_FrpId = httpServletRequest.getParameter("pd_FrpId");
        String pr_NeedResponse = "1";
        String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt, p4_Cur,
                p5_Pid, p6_Pcat,p7_Pdesc, p8_Url, p9_SAF, pa_MP,
                pd_FrpId, pr_NeedResponse,prop.getKey("keyValue") );
        //2、将以上保存到request
        httpServletRequest.setAttribute("pd_FrpId", pd_FrpId);
        httpServletRequest.setAttribute("p0_Cmd", p0_Cmd);
        httpServletRequest.setAttribute("p1_MerId", p1_MerId);
        httpServletRequest.setAttribute("p2_Order", p2_Order);
        httpServletRequest.setAttribute("p3_Amt", p3_Amt);
        httpServletRequest.setAttribute("p4_Cur", p4_Cur);
        httpServletRequest.setAttribute("p5_Pid", p5_Pid);
        httpServletRequest.setAttribute("p6_Pcat", p6_Pcat);
        httpServletRequest.setAttribute("p7_Pdesc", p7_Pdesc);
        httpServletRequest.setAttribute("p8_Url", p8_Url);
        httpServletRequest.setAttribute("p9_SAF", p9_SAF);
        httpServletRequest.setAttribute("pa_MP", pa_MP);
        httpServletRequest.setAttribute("pr_NeedResponse", pr_NeedResponse);
        httpServletRequest.setAttribute("hmac", hmac);
        //3、转发
        httpServletRequest.getRequestDispatcher("/confirm.jsp").forward(httpServletRequest,httpServletResponse);


    }
}
