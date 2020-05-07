<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="user" class="mybean.data.User" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/loginForm.css">
<title>注册成功</title>
</head>
<body>
	<form action="HandleLogin" method="post" class="loginForm2">
		<h4><a class="backBtn" href="HandleExit">返回首页</a></h4>
		<h1>注册成功</h1>
		<h3>您的账号是：${user.getUid()}</h3>
		<div class="loginText">
			账号：<input type="text" name="uid" required="required" autocomplete="off"><br>
			密码：<input type="password" name="pwd" required="required">
		</div>
		<input type="hidden" name="R" value="student">
		<input type="submit" value="立即登录">
	</form>
</body>
</html>