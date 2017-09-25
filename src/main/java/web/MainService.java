package web;

import utils.ImageUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/5/15.
 */
public class MainService extends HttpServlet{
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.setCharacterEncoding("utf-8");
        String servletPath = httpServletRequest.getServletPath();
      //  System.out.println(servletPath);
        if ("/code.do".equals(servletPath)){
            createCode(httpServletRequest,httpServletResponse);
        }else if("/img.do".equals(servletPath)){
            loadImg(httpServletRequest,httpServletResponse);
        }else{
            System.out.println("输入路径有问题");
        }



    }

    private void loadImg(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        String imgUrl = httpServletRequest.getParameter("imgUrl");
      //  System.out.println(imgUrl);
        httpServletRequest.getRequestDispatcher(imgUrl).forward(httpServletRequest,httpServletResponse);
    }

    private void createCode(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {

        //通知浏览器不要缓存图片
//        httpServletResponse.setDateHeader("Expires",-1);
//        httpServletResponse.setHeader("Cache-control","no-cache");
//        httpServletResponse.setHeader("Pragma","no=cache");
//        VerifyCode verifyCode=new VerifyCode();
//        verifyCode.drawImage(httpServletResponse.getOutputStream());
        //生成随机的验证码和图片
        Object[] objs = ImageUtil.createImage();
        //将验证码存入session
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute("imgCode", objs[0]);
        //将图片输出给浏览器
        BufferedImage img =
                (BufferedImage) objs[1];
        //格式见/tomcat/conf/web.xml
        httpServletResponse.setContentType("image/png");
        //该输出流的目标就是浏览器
        OutputStream os = httpServletResponse.getOutputStream();
        ImageIO.write(img, "png", os);
        os.close();
    }
}
