package filte1;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import filte1.EncoderFilter.MyRequest;

public class EncoderFilter implements Filter {
	private String encode;

	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		// 解决响应乱码问题
		res.setContentType("text/html;charset=utf-8");
		// 解决请求乱码 只能解决post请求方式
		req.setCharacterEncoding(encode);
		chain.doFilter(new MyRequest((HttpServletRequest) req), (HttpServletResponse) res);
	}

	public void init(FilterConfig cof) throws ServletException {
		// 获取编码参数
		encode = cof.getInitParameter("encode");
		System.out.println(encode);
	}

	/**
	 * 装饰者设计模式 解决请求乱码 通过装饰者模式修改request对象
	 * 中和获取参数的有关三个方法 在方法中添加解决乱码的代码
	 * 虽然request对象内部请求参数的乱码仍然存在,但是之后只要
	 *  通过修改后的三个方法获取请求参数,则会自动进行乱码处理
	 *
	 * @author Administratorss
	 *
	 */
	class MyRequest extends HttpServletRequestWrapper {
		private HttpServletRequest request;

		public MyRequest(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {

			try {
				if ("POST".equals(request.getMethod())) {
					request.setCharacterEncoding(encode);
					return request.getParameter(name);
				}else if("GET".equals(request.getMethod())){
					String parameter = request.getParameter(name);
					return parameter==null?
							null:new String(parameter.getBytes("ISO8859-1"),encode);
				}else{
					//其他提交方式  不处理
					return request.getParameter(name);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}

		}

		@Override
		public String[] getParameterValues(String name) {
			try {
				if ("POST".equals(request.getMethod())) {
					request.setCharacterEncoding(encode);
					return request.getParameterValues(name);
				}else if("GET".equals(request.getMethod())){
					String[] values = request.getParameterValues(name);
					//获取到的结果遍历
					if(values!=null){
						for (int i = 0; i < values.length; i++) {
							values[i]=new String(values[i].getBytes("ISO8859-1"),encode);
						}
					}
					return values;
				}else{
					//其他提交方式  不处理
					return request.getParameterValues(name);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		public Map getParameterMap() {
			try {
				if ("POST".equals(request.getMethod())) {
					request.setCharacterEncoding(encode);
					return request.getParameterMap();
				}else if("GET".equals(request.getMethod())){
					Map<String, String[]> map = request.getParameterMap();
					//获取到的结果遍历
					for (Map.Entry<String, String[]> entry:map.entrySet()) {
						String values[]=entry.getValue();
						if(values!=null){
							for (int i = 0; i < values.length; i++) {
								values[i]=new String(values[i].getBytes("ISO8859-1"),encode);
							}
						}
					}
					return map;
				}else{
					//其他提交方式  不处理
					return request.getParameterMap();
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}

	}

}
