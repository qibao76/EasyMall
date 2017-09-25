package web;

import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
public class CartAddServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String id = httpServletRequest.getParameter("id");
        int num = Integer.parseInt(httpServletRequest.getParameter("num"));
        ProductService productService=new ProductServiceImp();
        Product product = productService.findProdById(id);
        //3、从session中获取购物车的信息
        Object obj = httpServletRequest.getSession().getAttribute("cart");
        Map<Product,Integer> cart = null;
        if(obj!=null){//存在
            cart = (Map<Product,Integer>)obj;
        }else{//不存在
            cart = new HashMap<Product,Integer>();
            httpServletRequest.getSession().setAttribute("cart", cart);
        }
        if (cart.containsKey(product)){
            cart.put(product,cart.get(product)+num);
        }else{
            cart.put(product,num);
        }
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/cart.jsp");

    }
}
