package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.UserService;
import service.impl.UserServiceImp;

public class AjaxCheckUserNameServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//解决参数乱码(POST)
		request.setCharacterEncoding("utf-8");
		//获取请求参数
		String username = request.getParameter("username");


		//检查用户名是否存在(查询数据库)
		//3.注册用户 -- 把数据保存到数据库
		UserService userService=new UserServiceImp();
		Boolean b=userService.chick(username);
		response.getWriter().write(b+"");

//		Connection conn = null;
//		PreparedStatement ps = null;
//		ResultSet rs = null;
//		ComboPooledDataSource pool = new ComboPooledDataSource();
//		try {
//			conn = pool.getConnection();
//
//			//>用户名是否存在校验
//			ps = conn.prepareStatement("select * from user where username=?");
//			ps.setString(1, username);
//			rs = ps.executeQuery();
//
//			response.getWriter().write(rs.next()+"");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new RuntimeException(e);
//		}finally{
//			if(rs != null){
//				try {
//					rs.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					rs = null;
//				}
//			}
//			if(ps != null){
//				try {
//					ps.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}finally{
//					ps = null;
//				}
//			}
//			if(conn != null){
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
