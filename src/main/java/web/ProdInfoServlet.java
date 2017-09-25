package web;

import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */
public class ProdInfoServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String id=httpServletRequest.getParameter("pid");
        ProductService productService=new ProductServiceImp();
        Product prod = productService.findProdById(id);
        httpServletRequest.setAttribute("prod",prod);
        httpServletRequest.getRequestDispatcher("/prodInfo.jsp").forward(httpServletRequest,httpServletResponse);
    }
}
