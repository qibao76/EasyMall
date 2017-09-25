package web;

import service.OrderService;
import service.impl.OrderServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/12.
 */
public class OrderDeleteServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String id = httpServletRequest.getParameter("id");
        OrderService orderService=new OrderServiceImp();
        try {
            orderService.deleteOrderById(id);
            httpServletResponse.getWriter().write("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            httpServletResponse.getWriter().write("删除失败");
        }
        httpServletResponse.setHeader("Refresh","1;url="+httpServletRequest.getContextPath()+"/toOrderList");

    }
}
