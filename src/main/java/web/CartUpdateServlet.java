package web;

import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/8.
 */
public class CartUpdateServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        int buynum = Integer.parseInt(httpServletRequest.getParameter("buynum"));
        String id = httpServletRequest.getParameter("id");
        ProductService productService=new ProductServiceImp();
        Product product = productService.findProdById(id);
        Object obj = httpServletRequest.getSession().getAttribute("cart");
        if (obj!=null){
            Map<Product, Integer> car = (Map<Product, Integer>) obj;
            if (buynum==-1) {
                car.remove(product);
            }else{
                car.put(product,buynum);
            }
        }else{
            System.out.println("没有找到对应的购物车");
        }
        //4重定向
        httpServletResponse.sendRedirect(httpServletRequest.getContextPath()+"/cart.jsp");
    }


}
