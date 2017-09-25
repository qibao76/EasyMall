package utils;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

//在jsp运行的过程中遇到自定义标签 首先创建自定义标签对应的类的对象 执行构造方法
public class ShowIpTag extends SimpleTagSupport{
    @Override
    public void doTag() throws JspException, IOException {

    }
    //    PageContext pc;
//    //最后调用doTag方法执行自定义标签的具体逻辑
//    public void doTag() throws JspException, IOException {
//        String addr=pc.getRequest().getRemoteAddr();
//        pc.getOut().write(addr);
//    }
//    //如果当前标签还有父标签将调用该方法  没有则不执行
//    public void setParent(JspTag jspTag) {
//
//    }
//    public JspTag getParent() {
//        return null;
//    }
//    //调用该方法  将当前jsp页面的pageConext对象传入
//    public void setJspContext(JspContext jspContext) {
//        this.pc= (PageContext) jspContext;
//    }
//    //如果当前标签有标签体 则调用该方法 将封装了标签的信息jspFragment类对象传入
//    //如果没有标签体 则不执行该方法
//    public void setJspBody(JspFragment jspFragment) {
//
//    }
}
