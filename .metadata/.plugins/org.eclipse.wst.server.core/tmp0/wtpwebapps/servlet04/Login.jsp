<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>登陆界面</title>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
	$(function(){

		//登录
		$("#login_btn").click(function(){
		//表单校验
			var username = $("#user_name").val();
			if(username == "" || username == undefined || username == null){
				alert("用户姓名不能空！");
				return;
			}
			var password = $("#pass_word").val();
			if(password == "" || password == undefined || password == null){
				alert("用户密码不能空！");
				return;
			}
		//提交表单
			$("#login_form").submit();
		});
		//重置
		$("#reset_btn").click(function(){
			$("#login_form")[0].reset();//因为$("#id/.class")得到的是一个数组
		});
	})
</script>
</head>
<body>
 	<br/><br/>
        <center><h1>用户登录</h1></center><br/>
    <br/><br/>
    <% 
    if(request.getAttribute("reg") != null){
    	String reg = request.getAttribute("reg").toString();
    		if(reg.equals("reg")){
    			out.append("注册成功！");
    		}
    		}
    %>
	<form action="login.do" method="post" id="login_form">
		用户姓名：<input type="text" name="username" id="user_name"  /><br/>
		用户密码：<input type="password" name="password" id="pass_word"  /><br/>
		<input type="button" name="btn1" value="登录" id="login_btn">
		<input type="button" name="btn2" value="重置" id="reset_btn"><span id="warn">${requestScope.warn}   ${warn}</span>
		<br/>还没有账号? <a href="register.jsp">立即注册</a> <br/>
	</form>
</body>
</html>