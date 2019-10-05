<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="js/jquery-3.3.1.min.js"></script>
<script type="text/javascript">
$(function(){
	$("#reset_btn").click(function(){
	//function fName(){
		test();
		var test = function(){
			console.log(1);
		}
		test();
		function test(){
			console.log(2);
		}
		test();
	//}
	})
})
</script>
</head>
<body>
	<h2>登陆成功</h2>
	<input id="reset_btn"  type="button" name="btn2" value='运行' />
</body>
</html>