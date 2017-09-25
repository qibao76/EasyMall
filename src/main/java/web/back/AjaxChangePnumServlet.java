package web.back;

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
public class AjaxChangePnumServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String id = httpServletRequest.getParameter("id");
        int num = Integer.parseInt(httpServletRequest.getParameter("num"));
        System.out.println(id+num);
        ProductService service=new ProductServiceImp();
        boolean update = service.update(id, num);
        httpServletResponse.getWriter().write(update+"");
    }
}
