package web;

import entity.Page;
import entity.Product;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/7.
 */
public class ProdPageServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        //1、接收参数
        int cpn = Integer.parseInt(httpServletRequest.getParameter("cpn"));
        int rpp = Integer.parseInt(httpServletRequest.getParameter("rpp"));
        String nameStr = httpServletRequest.getParameter("name");
        String categoryStr = httpServletRequest.getParameter("category");
        String minStr = httpServletRequest.getParameter("minprice");
        String maxStr = httpServletRequest.getParameter("maxprice");
        //2、处理参数
        String name = "";
        String category="";
        double min = Double.MIN_VALUE;
        double max = Double.MAX_VALUE;
        if(nameStr!=null&&!"".equals(nameStr.trim())){
            name = nameStr.trim();
        }
        if(categoryStr!=null&&!"".equals(categoryStr.trim())){
            category = categoryStr.trim();
        }
        if(minStr!=null&&!"".equals(minStr.trim())){
            min = Double.parseDouble(minStr);
        }
        if(maxStr!=null&&!"".equals(maxStr.trim())){
            max = Double.parseDouble(maxStr.trim());
        }
        //
        ProductService productService=new ProductServiceImp();
        Page<Product> page= productService.pageList(cpn,rpp,name,category,min,max);

        httpServletRequest.setAttribute("page",page);
        httpServletRequest.getRequestDispatcher("/proPage.jsp").forward(httpServletRequest,httpServletResponse);


    }
}
