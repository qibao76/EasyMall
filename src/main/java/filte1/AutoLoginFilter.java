package filte1;


import entity.User;
import factory.BasicFactory;
import service.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/5/24.
 */
public class AutoLoginFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //1.只有未登录的用户才会需要自动登录
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        if (httpServletRequest.getSession()==null||httpServletRequest.getSession().getAttribute("loginUser")==null){
            //2.只有带了自动登录的cookie才自动登录
            Cookie[] cookies = httpServletRequest.getCookies();
            Cookie cookie=null;
            if (cookies!=null){
               for (int i=0;i<cookies.length;i++){
                   if ("autologin".equals(cookies[i].getName())){
                       cookie=cookies[i];
                       break;
                   }
               }
            }
            if (cookie!=null){
                //判断自动登录的cookie中帐号密码是否正确
                String decode = URLDecoder.decode(cookie.getValue(), "utf-8");
                String username = decode.split(":")[0];
                String password = decode.split(":")[1];

                UserService userService= BasicFactory.getBasicFactory().getInstance(UserService.class);
                User user=userService.log(username,password);
                if (user!=null){
                    httpServletRequest.getSession().setAttribute("loginUser",user);
                }
            }
        }
        //无论是否自动登录,都要放行资源
        filterChain.doFilter(httpServletRequest,servletResponse);
    }

    public void destroy() {

    }
}
