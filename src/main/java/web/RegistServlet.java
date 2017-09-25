package web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entity.User;
import exception.MsgException;
import factory.BasicFactory;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;
import utils.MD5Utils;

public class RegistServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//0.解决乱码
		//>>请求参数乱码
		request.setCharacterEncoding("utf-8");
		//>>响应中文乱码
		response.setContentType("text/html;charset=utf-8");


		//>验证码是否正确校验
		String valistr1 = request.getParameter("valistr");
		String valistr2 = (String) request.getSession().getAttribute("valistr");
		if (valistr1 == null || "".equals(valistr1)) {
			request.setAttribute("msg", "验证码不能为空");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		if (!valistr1.equalsIgnoreCase(valistr2)) {
			request.setAttribute("msg", "验证码不正确");
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
			return;
		}
		//自动获取参数
		try {
			User user = new User();
			//获取表单参数
			BeanUtils.populate(user, request.getParameterMap());
			//判断非空
            System.out.print(user);
			user.check();
			//实现注册
			UserService userService= BasicFactory.getBasicFactory().getInstance(UserService.class);
			//给密码进行加密
			user.setPassword(MD5Utils.md5(user.getPassword()));
			userService.save(user);


			//4.给出提示
            response.getWriter().write("<h1 style='color:red; text-align:center;'>恭喜您注册成功, 3秒之后跳转到首页....</h1>");
            response.setHeader("refresh", "3;url="+ request.getContextPath() +"/index.jsp");
		} catch (MsgException e) {
			e.printStackTrace();
			request.setAttribute("msg", e.getMessage());
			request.getRequestDispatcher("/regist.jsp").forward(request, response);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		//1.接受表单数据
//		String username = request.getParameter("username");
//		String password = request.getParameter("password");
//		String password2 = request.getParameter("password2");
//		String nickname = request.getParameter("nickname");
//		String email = request.getParameter("email");

		//2.校验数据
		//>非空校验


	}




	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
