<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>注册界面</title>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<!-- jqeury 表单提交  -->
<script type="text/javascript">
	$(function(){
		$("#register").click(function(){
			var username = $("#user_name").val();
			if(username == "" || username == undefined || username == null){
				alert("用户姓名不能空！");
				return;
			}
			var password_One = $("#password_One").val();
			var password_Two = $("#password_Two").val();
			if(password_One == "" || password_One == undefined || password_One == null){
				alert("用户密码不能空！");
				return;
			}
			if(password_One != password_Two){
				alert("两次密码不一致！");
				return;
			}
			
			$("#register").click(function(){
				$("#register_form").submit();
			});
		});
		
		$("#reset_btn").click(function(){
			$("#register_form")[0].reset();//因为$("#id/.class")得到的是一个数组
		});

	})
</script>

<!-- ajax提交 -->
<script type="text/javascript">
	$(document).ready(function(){
		//var psd1 = $("#password_One").val();
		//var psd2 = $("#password_Two").val();
		$("#user_name").blur(function(){
			if($("#user_name").val() == ""){
				$("#warn_u").html("用户名不能为空");
				$("#warn_u").css("color","red");
			}else{
				 var user = {username:$("#user_name").val()};
			  $.ajax(
			   {url:"ajax.do",
			   data:user,
			   async:true,
			   type:"POST",
			   dataType:"html",
			   success:function(result){
					 $("#warn_u").html(result);
					 if(result == "您的用户名已经注册"){
						 $("#warn_u").css("color","red");
						
					 }else{
						 $("#warn_u").css("color","blue");
					 }}  
		        }); 
			}//else
        }); 
	});
</script>
</head>
<body>
	<div>
        <br/>
        <br/>
        <center><h1>用户信息注册</h1></center><br/>
    <br/><br/>
    <form action="register.do" method="POST" id="register_form">
        	用户账号:<input  id="user_name" type="text" name="username" /><div id="warn_u"></div><br/>
    		用户密码:<input  id="password_One" type="password" name="password" /><div id="warn_p1"></div><br/>
   	 		确认密码:<input  id="password_Two" type="password" name="passwordTwo" /><div id="warn_p2"></div><br>
 				  <input  id="register"  style="width: 200px;color: #fff;background: #999;" type="button" value="立即注册"/>
 				  <input id="reset_btn"  type="button" name="btn2" value='重置' />
    </form>
    </div>
</body>
</html>