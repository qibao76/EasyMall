package web.back;

import entity.Product;
import factory.BasicFactory;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/6/4.
 */
public class BackProdListServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        ProductService productService=new ProductServiceImp();
        List<Product> allProd = productService.findAllProd();
        httpServletRequest.setAttribute("prods",allProd);
        httpServletRequest.getRequestDispatcher("/back/prod_list.jsp").forward(httpServletRequest,httpServletResponse);
    }
}