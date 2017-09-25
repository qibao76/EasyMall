package filte1;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/5/24.
 */
public class NoCacheFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        //设置响应头
        httpServletResponse.setDateHeader("Expires",-1);
        httpServletResponse.setHeader("Cache-Control","no-cache");
        httpServletResponse.setHeader("Pragma","no-cache");
        //放行资源
        filterChain.doFilter(servletRequest,httpServletResponse);
    }

    public void destroy() {

    }
}
