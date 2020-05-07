<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/loginForm.css">
<title>欢迎使用科目一模拟考试系统</title>
</head>
<body>
	<form action="HandleLogin" method="post" class="loginForm1">
		<h1>欢迎使用科目一模拟考试系统</h1>
		<div class="loginText">
			账号：<input type="text" name="uid" required="required" autocomplete="off"><br>
			密码：<input type="password" name="pwd" required="required">
		</div>
		<input type="radio" name="R" value="student" checked="checked" id="std">
		<label for="std">考生</label>
		<input type="radio" name="R" value="teacher" id="tch">
		<label for="tch">管理员</label>
		<p>没有账号？<a href="UserRegist">立即注册</a></p>
		<input type="button" value="重置" onclick="clearLoginTextValue()">
		<input type="submit" value="登录">
	</form>
	
	<script type="text/javascript" src="HTML/js/index.js"></script>
</body>
</html>