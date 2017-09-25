<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.net.URLDecoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>


<html>
	<head>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="<%= request.getContextPath() %>/css/login.css"/>
		<title>EasyMall欢迎您登陆</title>
		<script type="text/javascript">
            window.onload = function(){
                var oUsername = document.getElementsByName("username")[0];
                oUsername.value = decodeURI("${ cookie.remname.value }");
            }
		</script>
	</head>
	<body>
		<h1>欢迎登陆EasyMall</h1>
		<form action="<%= request.getContextPath() %>/LoginServlet" method="POST">
			<table>
				<tr>
					<td class="tdx" colspan="2" style="color: red; font-size: 16px; text-align: center;">
						<%= request.getAttribute("msg")==null? "" : request.getAttribute("msg") %>
					</td>
				</tr>
				<tr>
					<%--<%
						//取出用户名
						Cookie[] cs = request.getCookies();
						Cookie remnameCookie = null;
						if(cs != null){
							for(Cookie c: cs){
								if("remname".equals(c.getName())){
									remnameCookie = c;
								}
							}
						}
						
						String username = "";
						if(remnameCookie != null){
							username = URLDecoder.decode(remnameCookie.getValue());
						}
					 %>--%>
					<td class="tdx">用户名:</td>
					<td><input type="text" name="username" value="${cookie.remname.value}"/></td>
				</tr>
				<tr>
					<td class="tdx">密码:</td>
					<td><input type="password" name="password"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="checkbox" name="remname" value="true"
							   <c:if test="${cookie.remname.value!=null}">
								   checked="checked"
							   </c:if>
							<%--<%= remnameCookie==null ? "" : "checked='checked'" %>--%>
						/>记住用户名
						<input type="checkbox" name="autologin" value="true"/>30天内自动登陆
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<input type="submit" value="登陆"/>
					</td>
				</tr>
			</table>
		</form>		
	</body>
</html>
