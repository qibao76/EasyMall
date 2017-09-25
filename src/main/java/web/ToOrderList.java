package web;

import entity.OrderInfo;
import entity.User;
import service.OrderService;
import service.impl.OrderServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/11.
 */
public class ToOrderList extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        OrderService orderService=new OrderServiceImp();
        User user = (User) httpServletRequest.getSession().getAttribute("loginUser");
        if (user==null){
            httpServletResponse.sendRedirect("/login.jsp");
            return;
        }
        List<OrderInfo> orderInfo=orderService.showOrderList(user.getId());
        System.out.println(orderInfo);
        httpServletRequest.setAttribute("list",orderInfo);
        httpServletRequest.getRequestDispatcher("/orderList.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
