package web;

import entity.Product;
import entity.User;
import factory.BasicFactory;
import service.OrderService;
import service.ProductService;
import service.impl.OrderServiceImp;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
public class OrderAddServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //没有登录
        if(httpServletRequest.getSession(false)==null||httpServletRequest.getSession().getAttribute("loginUser")==null){
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/login.jsp");
            return;
        }

        //获取购买的商品信息id
        String[] pids = httpServletRequest.getParameterValues("prodC");
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        for (String pid:pids){
            String value = httpServletRequest.getParameter(pid);
            map.put(pid,Integer.parseInt(value));
        }
        //获取地址
        String receiverinfo = httpServletRequest.getParameter("receiverinfo");
        //获取总金额
        double totlMonkey = Double.parseDouble(httpServletRequest.getParameter("totlMonkey"));
        User user = (User) httpServletRequest.getSession().getAttribute("loginUser");
        OrderService orderService= BasicFactory.getBasicFactory().getInstance(OrderService.class);
        orderService.addOrder(map,receiverinfo,user.getId(),totlMonkey);

        //获取session中的cart 把添加到订单列表的商品删除
        Map<Product,Integer> cart = (Map<Product, Integer>) httpServletRequest.getSession().getAttribute("cart");
        ProductService productService=new ProductServiceImp();
        Map<Product, Integer> productIntegerMap = productService.addProdList(map);
        for (Map.Entry<Product,Integer> entry: productIntegerMap.entrySet()){
            cart.remove(entry.getKey());
        }
        httpServletResponse.sendRedirect("/toOrderList");
    }
}
