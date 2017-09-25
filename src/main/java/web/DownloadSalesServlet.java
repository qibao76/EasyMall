package web;

import entity.SaleInfo;
import service.OrderService;
import service.impl.OrderServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class DownloadSalesServlet extends HttpServlet {

    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        OrderService orderService=new OrderServiceImp();
        List<SaleInfo> list=orderService.findAllSaf();
        //2、将结果集拼接
        StringBuffer sbf = new StringBuffer("商品id,商品名称,销售总量\r\n");
        for(SaleInfo si:list){
            sbf.append(si.getProd_id()).append(",");
            sbf.append(si.getProd_name()).append(",");
            sbf.append(si.getSale_num()).append("\r\n");
        }
        String name = "EasyMall销售榜单.csv";
        //3告知浏览器以附件下载的方式打开
        httpServletResponse.setHeader("Content-Disposition",
                "attachment;filename="+ URLEncoder.encode(name,"utf-8"));
        //4将内容输出
        httpServletResponse.setContentType("text/html;charset=gbk");
        httpServletResponse.getWriter().write(sbf.toString());
    }
}
