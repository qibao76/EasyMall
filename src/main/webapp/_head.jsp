<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="entity.User"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>

<!DOCTYPE HTML>
<link rel="stylesheet" href="<%= request.getContextPath() %>/css/head.css"/>
<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />

<div id="common_head">
	<div id="line1">
		<div id="content">
			<c:if test="${sessionScope.loginUser==null}" var="flag" scope="page">
				<!-- 提示登陆或注册 -->
				<a href="<%= request.getContextPath() %>/login.jsp">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="<%= request.getContextPath() %>/regist.jsp">注册</a>
			</c:if>
			<c:if test="${!flag }">
				<!-- 欢迎, xxx回来 -->
				欢迎, ${sessionScope.loginUser.username} 回来 &nbsp;|&nbsp;
				<a href="<%= request.getContextPath() %>/LogoutServlet">注销</a>
				<a href="${app}/back/manage.jsp" >后台管理</a>
			</c:if>
		</div>
	</div>

	<%--<%= request.getContextPath() %>--%>


	<div id="line2">
		<img id="logo" src="${pageContext.request.contextPath}/img/head/logo.jpg"/>
		<input type="text" name=""/>
		<input type="button" value="搜索"/>
		<span id="goto">
			<a id="goto_order" href="${app}/toOrderList">我的订单</a>
			<a id="goto_cart" href="${app}/cart.jsp">我的购物车</a>
		</span>
		<img id="erwm" src="${pageContext.request.contextPath}/img/head/qr.jpg"/>
	</div>
	<div id="line3">
		<div id="content">
			<ul>
				<li><a href="#">首页</a></li>
				<li><a href="${app}/proPage.jsp">全部商品</a></li>
				<li><a href="#">手机数码</a></li>
				<li><a href="#">电脑平板</a></li>
				<li><a href="#">家用电器</a></li>
				<li><a href="#">汽车用品</a></li>
				<li><a href="#">食品饮料</a></li>
				<li><a href="#">图书杂志</a></li>
				<li><a href="#">服装服饰</a></li>
				<li><a href="#">理财产品</a></li>
			</ul>
		</div>
	</div>
</div>