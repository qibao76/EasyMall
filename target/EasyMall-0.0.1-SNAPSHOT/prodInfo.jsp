<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>商品详情</title>
  	<link href="${app }/css/prodInfo.css" rel="stylesheet" type="text/css">
  	<script type="text/javascript">
  	function addCart(){
  	   var num= document.getElementById("num").vale;
  		window.location.href = "${app}/servlet/CartAddServlet?id=${prod.id}&num="+num;
  	}
  	</script>
</head>
<body>
<%@include file="/_head.jsp" %>
	<div id="warp">
		<div id="left">
			<div id="left_top">
				<img src="${app }/img.do?imgUrl=${prod.imgurl}"/>
			</div>
			<div id="left_bottom">
				<img id="lf_img" src="img/prodInfo/lf.jpg"/>
				<img id="mid_img" src="${app }/img.do?imgUrl=${prod.imgurl}" width="60px" height="60px"/>
				<img id="rt_img" src="img/prodInfo/rt.jpg"/>
			</div>
		</div>
		<div id="right">
			<div id="right_top">
				<span id="prod_name">${prod.name } <br/></span>
				<br>
				<span id="prod_desc">${prod.description }<br/></span>
			</div>
			<div id="right_middle">
				<span id="right_middle_span">
						EasyMall 价：<span class="price_red">￥${prod.price }<br/>
			            运     费：满 100 免运费<br />
			            服     务：由EasyMall负责发货，并提供售后服务<br />
			            库     存：${prod.pnum }<br />
			            购买数量：
	            <a href="#" id="delNum" onclick="">-</a>
	            <input id="num" name="" type="text" value="1" onblur="">
		        <a href="#" id="addNum" onclick="">+</a>
			</div>
			<div id="right_bottom">
				<input class="add_cart_but" type="button" onclick="addCart()"/>	
			</div>
		</div>
	</div>
<%@include file="/_foot.jsp" %>
</body>
</html>