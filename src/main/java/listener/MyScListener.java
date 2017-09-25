package listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by Administrator on 2017/5/25.
 */
public class MyScListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("$$$contextInitialized###");
        servletContextEvent.getServletContext().setAttribute("app",
                servletContextEvent.getServletContext().getContextPath());
    }

    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("$$$contextDestroyed###");
        servletContextEvent.getServletContext().removeAttribute("app");
    }
}
