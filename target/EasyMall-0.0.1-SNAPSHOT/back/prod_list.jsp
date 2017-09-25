<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<script src="/js/ajax.js"></script>
<script>
	function changePnum(id,input,oldNum) {
	    console.log(id);
	//	var oldNum=document.getElementById(id+"_pum").value;
		var num=input.value;
        var reg = /^0*[1-9][0-9]*$/;
        console.log(num);
        if(!reg.test(input.value)){
            alert("请输入正整数");
            input.value = oldPnum;
            return;
        }
        if(num!=oldNum){
            var xmlHttp=ajaxFunction();
            //2.打开与服务器的连接
            xmlHttp.open("POST", "${app}/servlet/AjaxChangePnumServlet", true);
            //通知服务器发送的数据是请求参数
            xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
            xmlHttp.send("id="+id+"&num="+num);
            xmlHttp.onreadystatechange=function () {
                if(xmlhttp.readyState==4&&xmlHttp.status==200){
                    var result=xmlHttp.responseText;
                    if(result=="true"){
                        document.getElementById(id).value=num;
                        alert("添加成功");
                    }else{
                        alert("修改失败");
                    }
                }
            }
        }
    }

</script>
</head>
<body>
	<h1>商品管理</h1>
	<a href="${app}/back/addProd.jsp">添加商品</a>
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
			<th>操作</th>
		</tr>
		<c:forEach items="${prods }" var="prod">
			<tr>
				<td><img width="120px" height="120px" src="${app }/img.do?imgUrl=${prod.imgurl }"/></td>
				<td>${prod.id }</td>
				<td>${prod.name }</td>
				<td>${prod.category }</td>
				<td>${prod.price }</td>
				<td><input type="text" id="${prod.id }" value="${prod.pnum }" style="width: 50px" onblur="changePnum('${prod.id}',this,${prod.pnum})"/>

				</td>
				<td>${prod.description }</td>
				<td><a href="${app}/deltePro?id=${prod.id}">删除</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
