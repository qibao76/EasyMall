package web;

import entity.SaleInfo;
import service.OrderService;
import service.impl.OrderServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/14.
 */
public class SaleInfoListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        OrderService orderService=new OrderServiceImp();
        List<SaleInfo> list=orderService.findAllSaf();
        httpServletRequest.getRequestDispatcher("/back/saleList.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
