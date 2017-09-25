<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
<style type="text/css">
body {
	background: #F5F5F5;
	text-align: center;
}

table {
	text-align: center;
	margin: 0px auto;
}

th {
	background-color: silver;
}
</style>
</head>
<body>
	<h1>商品管理</h1>
	<a href="${pageContext.request.contextPath}/back/addProd.jsp">添加商品</a>
	<hr>
	<table bordercolor="black" border="1" width="95%" cellspacing="0px" cellpadding="5px">
		<tr>
			<th>商品图片</th>
			<th>商品id</th>
			<th>商品名称</th>
			<th>商品种类</th>
			<th>商品单价</th>
			<th>库存数量</th>
			<th>描述信息</th>
		</tr>
			<tr>
				<td><img width="120px" height="120px" src=""/></td>
				<td>1ab4b5bd-c48c-4adc-925f-1ff1962db850</td>
				<td>手机IPhone7</td>
				<td>手机数码</td>
				<td>5000</td>
				<td><input type="text" value="100" style="width: 50px" onblur=""/></td>
				<td>街机。。。。</td>
			</tr>
	</table>
</body>
</html>
