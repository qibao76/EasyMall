package web;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import entity.User;
import exception.MsgException;
import factory.BasicFactory;
import service.UserService;
import utils.MD5Utils;

public class LoginServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决请求参数乱码
		request.setCharacterEncoding("utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String remname = request.getParameter("remname");

		//>>记住用户名
		if("true".equals(remname)){//记住用户名
			//手动将中文数据进行url编码
			Cookie cookie = new Cookie("remname", URLEncoder.encode(username));
			cookie.setMaxAge(3600*24*30);
			cookie.setPath(request.getContextPath()+"/");
			response.addCookie(cookie);
		}else{//取消记住用户名
			Cookie cookie = new Cookie("remname", "");
			cookie.setMaxAge(0);
			cookie.setPath(request.getContextPath()+"/");
			response.addCookie(cookie);
		}

		UserService userService= BasicFactory.getBasicFactory().getInstance(UserService.class);
		try{
			//根据查询返回对应的用户对象
			User user=userService.log(username, MD5Utils.md5(password));

			//将用户信息保存在session中作为登陆的标识
			request.getSession().setAttribute("loginUser", user);
			//30天自动登录
			if("true".equals(request.getParameter("autologin"))){//选中30天自动登录
				Cookie autologincookie=new Cookie("autologin",
						URLEncoder.encode(user.getUsername()+":"+user.getPassword(),"utf-8"));
				autologincookie.setPath(request.getContextPath()+"/");
				autologincookie.setMaxAge(10*24*3600);
				response.addCookie(autologincookie);
			}

				//跳转到首页
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}catch (MsgException e){
			e.printStackTrace();
			//登陆失败跳转回登陆界面
			request.setAttribute("msg", "用户名或密码不正确");
			request.getRequestDispatcher("/login.jsp").forward(request, response);

		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException("登录失败",e);
		}


//		//根据用户名和密码查询数据
//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ComboPooledDataSource pool = new ComboPooledDataSource();
//		try {
//			//获取连接
//			conn = pool.getConnection();
//			ps = conn.prepareStatement("select * from user where username=? and password=?");
//			ps.setString(1, username);
//			ps.setString(2, password);
//			rs = ps.executeQuery();
//
//			if(rs.next()){//允许用户登陆
//				int id = rs.getInt("id");
//				String nickname = rs.getString("nickname");
//				String email = rs.getString("email");
//				User user = new User();
//				user.setId(id);
//				user.setUsername(username);
//				user.setPassword(password);
//				user.setNickname(nickname);
//				user.setEmail(email);
//
//				//将用户信息保存在session中作为登陆的标识
//				request.getSession().setAttribute("user", user);
//				//跳转到首页
//				response.sendRedirect(request.getContextPath()+"/index.jsp");
//			}else{
//				//登陆失败跳转回登陆界面
//				request.setAttribute("msg", "用户名或密码不正确");
//				request.getRequestDispatcher("/login.jsp").forward(request, response);
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			if(rs !=null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					rs = null;
//				}
//			}
//			if(ps !=null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					ps = null;
//				}
//			}
//			if(conn !=null){
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					conn = null;
//				}
//			}
//		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
