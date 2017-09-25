<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE HTML>
<html>
  <head>
    <title>我的购物车</title>
    <link href="${app }/css/cart.css" rel="stylesheet" type="text/css">
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<script type="text/javascript">
		function changeBuyNum(obj,oldBuyNum,id){
			if(!/^[1-9][0-9]*$/.test(obj.value)){
				alert("请输入正整数");
				obj.value = oldBuyNum;
				return;
			}
			if(obj.value!=oldBuyNum){
				window.location.href = "${app}/servlet/CartUpdateServlet?id="+id+"&buynum="+obj.value;
			}
		}
		function delBuyNum(id){
			var obj = document.getElementById(id);
			var newNum = parseInt(obj.value)-1;
			if(newNum>0){
				window.location.href="${app}/servlet/CartUpdateServlet?id="+id+"&buynum="+newNum;
			}else{
				if(window.confirm("您确定删除吗？")){
					window.location.href="${app}/servlet/CartUpdateServlet?id="+id+"&buynum=-1";
				}
			}
		}
		function addBuyNum(id){
		    console.log(id);
			var obj = document.getElementById(id);
			var newNum = parseInt(obj.value)+1;
			window.location.href="${app}/servlet/CartUpdateServlet?id="+id+"&buynum="+newNum;
		}
		function deleteProd(id){
			if(window.confirm("您确定删除吗？")){
					window.location.href="${app}/servlet/CartUpdateServlet?id="+id+"&buynum=-1";
			}
		}
	</script>
</head>
<body>
<%@include file="/_head.jsp" %>
  ${msg }
	<div class="warp">
		<!-- 标题信息 -->
		<div id="title">
			<input name="allC" type="checkbox" value="" onclick=""/>
			<span id="title_checkall_text">全选</span>
			<span id="title_name">商品</span>
			<span id="title_price">单价（元）</span>
			<span id="title_buynum">数量</span>
			<span id="title_money">小计（元）</span>
			<span id="title_del">操作</span>
		</div>
		<!-- 购物信息 -->
	<form method="post" action="${app}/toOrder">
	<c:set var="money" value="0"/>
	<c:forEach items="${sessionScope.cart}" var="entry">
		<div id="prods">
			<input name="prodC" type="checkbox" value="${entry.key.id}" onclick=""/>
			<img src="${app }/img.do?imgUrl=${entry.key.imgurl}" width="90" height="90" />
			<span id="prods_name">${entry.key.name }</span>
			<span id="prods_price">${entry.key.price }</span>
			<span id="prods_buynum"> 
				<a href="javascript:void(0)" id="delNum" onclick="delBuyNum('${entry.key.id}')">-</a>
				<input id="${entry.key.id }" NAME="${entry.key.id}" type="text" value="${entry.value }" onblur="changeBuyNum(this,${entry.value},'${entry.key.id }')">
				<a href="javascript:void(0)" id="addNum" onclick="addBuyNum('${entry.key.id}')">+</a>
			</span>
			<span id="prods_money">${entry.key.price*entry.value }</span>
			<c:set var="money" value="${money+entry.key.price*entry.value }"/>
			<span id="prods_del"><a href="javascript:void(0)" onclick="deleteProd('${entry.key.id}')">删除</a></span>
		</div>
	</c:forEach>
		<!-- 总计条 -->
		<div id="total">
			<div id="total_1">
				<input name="allC" type="checkbox" value=""/> 
				<span>全选</span>
				<a id="del_a" href="#">删除选中的商品</a>
				<span id="span_1">总价：</span>
				<span id="span_2">￥${money }</span>
			</div>
			<div id="total_2">	
				<a id="goto_order" href="javascript:document.forms[0].submit()">去结算</a>
			</div>
		</div>
		</form>
	</div>
	<%@include file="/_foot.jsp" %>
</body>
</html>