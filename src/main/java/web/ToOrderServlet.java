package web;

import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/10.
 */
public class ToOrderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ProductService productService=new ProductServiceImp();
        //获取选中的商品信息id
        String[] pids = httpServletRequest.getParameterValues("prodC");
        HashMap<String, Integer> map=new HashMap<String, Integer>();
        for (String pid:pids){
            String value = httpServletRequest.getParameter(pid);
            map.put(pid,Integer.parseInt(value));
        }
        Map<Product,Integer> prodList=productService.addProdList(map);
        httpServletRequest.setAttribute("prodList",prodList);
        httpServletRequest.getRequestDispatcher("/addOrder.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
