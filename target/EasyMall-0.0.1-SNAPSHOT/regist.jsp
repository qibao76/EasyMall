<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML>
<html>
<head>
	<title>欢迎注册EasyMall</title>
	<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="<%= request.getContextPath() %>/css/regist.css"/>
	<script type="text/javascript" src="<%= request.getContextPath() %>/js/ajax.js"></script>
	<script type="text/javascript">
		function checkForm(){
			var flag = true;
			flag = checkNull("username", "用户名不能为空") && flag;
			flag = checkNull("password", "密码不能为空") && flag;
			flag = checkNull("password2", "确认密码不能为空") && flag;
			flag = checkNull("nickname", "昵称不能为空") && flag;
			flag = checkNull("email", "邮箱不能为空") && flag;
			flag = checkNull("valistr", "验证码不能为空") && flag;
			flag = checkPassword("password", "两次密码不一致") && flag;
			flag = checkEmail("email", "邮箱格式不正确") && flag;
			return flag;
		}
		
		function checkPassword(name, msg){
			var psw1 = document.getElementsByName(name)[0].value;
			var psw2 = document.getElementsByName(name+"2")[0].value;
			setMsg(name+"2", "");
			if(psw2 == ""){
				setMsg(name+"2", "确认密码不能为空");
				return false;
			}
			if(psw1 != "" && psw2 != ""){
				if(psw1 != psw2){
					setMsg(name+"2", msg);
					return false;
				}
			}
			return true;
		}
		
		function checkEmail(name, msg){
			var email = document.getElementsByName(name)[0].value;
			var reg = /^\w+@\w+(\.\w+)+$/;
			setMsg(name, "");
			
			if(email == ""){
				setMsg(name, "邮箱不能为空");
				return false;
			}
			
			if(email != "" && !reg.test(email)){
				setMsg(name, msg);
				return false;
			}
			return true;
		}
		
		function checkNull(name, msg){
			var value = document.getElementsByName(name)[0].value;
			setMsg(name, "");
			if(value == ""){
				setMsg(name, msg);
				return false;
			}
			return true;
		}
		
		function setMsg(name, msg){
			document.getElementById(name+"_msg").innerHTML = msg;
		}
		
		/*
		 * 换一张验证码图片
		 */
		function changeImage(thisobj){
			thisobj.src = "<%= request.getContextPath() %>/ValiImageServlet?time="+new Date().getTime();
		}
		
		
		/*
		 * 利用ajax实现用户名是否存在校验
		 */
		 function ajaxCheckUserName(thisobj){
		 	//获取用户名
		 	var username = thisobj.value;
		 	
		 	//检查用户名是否为空
		 	if(username == ""){
		 		setMsg("username", "用户名不能为空");
		 		return;
		 	}
		 	
		 	//利用ajax检查用户名是否存在
		 	//1.创建XMLHttpRequest对象
		 	var xmlHttp = ajaxFunction();
		 	
		 	//2.打开与服务器的连接
		 	xmlHttp.open("POST", "<%= request.getContextPath() %>/AjaxCheckUserNameServlet", true);
		 	
		 	//3.发送请求
		 	//通知服务器发送的数据是请求参数
		 	xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		 	xmlHttp.send("username="+username);
		 	
		 	//4.注册监听
		 	xmlHttp.onreadystatechange = function(){
		 		//alert(1);
		 		//只关注readyState为4的状态(4表示服务器响应完成)
		 		if(xmlHttp.readyState == 4){
		 			//只关注处理成功的情况
		 			if(xmlHttp.status == 200){
		 				var result = xmlHttp.responseText;
		 				if(result == "true"){
		 					setMsg("username", "用户名已存在");
		 				}else{
		 					setMsg("username", "恭喜用户名可以使用!");
		 				}
		 			}
		 		}
		 	}
		 }
	</script>
</head>
<body>
	<h1>欢迎注册EasyMall</h1>
	<form action="<%= request.getContextPath() %>/RegistServlet" method="POST" onsubmit="return checkForm()">
		<table>
			<tr>
				<td class="tds" colspan="2" style="color: red; font-size: 16px; text-align:center;">
					${msg }
				</td>
			</tr>
			<tr>
				<td class="tds">用户名：</td>
				<td><input type="text" name="username"
						value="<%= request.getParameter("username") == null ? "" : request.getParameter("username") %>"
						onblur="ajaxCheckUserName(this)"
					>
					<span id="username_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">密码：</td>
				<td><input type="password" name="password" onblur="checkNull('password', '密码不能为空')">
					<span id="password_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">确认密码：</td>
				<td><input type="password" name="password2" onblur="checkPassword('password', '两次密码不一致')">
					<span id="password2_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">昵称：</td>
				<td><input type="text" name="nickname"  
						value="<%= request.getParameter("nickname") == null ? "" : request.getParameter("nickname") %>"
						onblur="checkNull('nickname', '昵称不能为空')"
					>
					<span id="nickname_msg"></span>
				</td>
			</tr>
			<tr>
				<td class="tds">邮箱：</td>
				<td><input type="text" name="email"  
						value="<%= request.getParameter("email") == null ? "" : request.getParameter("email") %>"
						onblur="checkEmail('email', '邮箱格式不正确')"
					>
					<span id="email_msg"></span>
				</td>
			</tr>
			
			<tr>
				<td class="tds">验证码：</td>
				<td><input type="text" name="valistr" onblur="checkNull('valistr', '验证码不能为空')">
					<img id="yzm_img" onclick="changeImage(this)" src="<%= request.getContextPath() %>/ValiImageServlet" style="cursor: pointer"/>
					<span id="valistr_msg"></span>
				</td>
			</tr>
			<tr>
				<td colspan="2">
					<input type="submit" value="注册用户"/>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
