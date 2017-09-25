package web;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.OrderService;
import service.impl.OrderServiceImp;
import utils.PaymentUtil;
import utils.PropUtil;

public class CallBackServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
		PropUtil prop=new PropUtil("merchantInfo.properties");
		//1、接收参数
		// 获得回调所有数据
		String p1_MerId = httpServletRequest.getParameter("p1_MerId");
		String r0_Cmd = httpServletRequest.getParameter("r0_Cmd");
		String r1_Code = httpServletRequest.getParameter("r1_Code");
		String r2_TrxId = httpServletRequest.getParameter("r2_TrxId");
		String r3_Amt = httpServletRequest.getParameter("r3_Amt");
		String r4_Cur = httpServletRequest.getParameter("r4_Cur");
		String r5_Pid = httpServletRequest.getParameter("r5_Pid");
		String r6_Order = httpServletRequest.getParameter("r6_Order");
		String r7_Uid = httpServletRequest.getParameter("r7_Uid");
		String r8_MP = httpServletRequest.getParameter("r8_MP");
		String r9_BType = httpServletRequest.getParameter("r9_BType");
		String rb_BankId = httpServletRequest.getParameter("rb_BankId");
		String ro_BankOrderId = httpServletRequest.getParameter("ro_BankOrderId");
		String rp_PayDate = httpServletRequest.getParameter("rp_PayDate");
		String rq_CardNo = httpServletRequest.getParameter("rq_CardNo");
		String ru_Trxtime = httpServletRequest.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = httpServletRequest.getParameter("hmac");
		String keyValue= prop.getKey("keyValue");
		boolean isVer = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order,
				r7_Uid, r8_MP, r9_BType, keyValue);
		if(isVer){//数据没有被篡改
			OrderService orderService = new OrderServiceImp();
			if("1".equals(r9_BType)){//重定向过来
				httpServletResponse.getWriter().write("支付操作已经执行，等待进一步确认的结果");
				//正式使用时，将以下测试代码删掉
				orderService.doPay(r6_Order);
			}else if("2".equals(r9_BType)){//点对点通信
				orderService.doPay(r6_Order);
				httpServletResponse.getWriter().write("success");
			}
		}else{
			System.out.println("数据被篡改了....");
		}
	}
}
