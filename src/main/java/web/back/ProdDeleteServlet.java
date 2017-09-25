package web.back;

import exception.MsgException;
import service.ProductService;
import service.impl.ProductServiceImp;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/6/6.
 */
public class ProdDeleteServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String id = httpServletRequest.getParameter("id");
        ProductService prodService = new ProductServiceImp();
        //3、调用业务层的方法
        try {
            prodService.deleteProdById(id);
            //4、删除成功
            httpServletResponse.getWriter().write("删除成功！");
        } catch (MsgException e) {
            e.printStackTrace();
            //4、删除失败
            httpServletResponse.getWriter().write(e.getMessage());
        }
        httpServletResponse.setHeader("Refresh", "2;url="+
                httpServletRequest.getContextPath()+"/servlet/BackProdListServlet");
    }
}
