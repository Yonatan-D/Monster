<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="std_navtag.jsp" %>
<jsp:useBean id="user" class="mybean.data.User" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
<title>考生模拟测试</title>
</head>
<body>
	<h3>
		请核对个人信息：<br>
		账号：${user.getUid()}<br>
		昵称：${user.getName()}<br>
		性别：${user.getSex()}<br>
		邮箱：${user.getEmail()}<br>
		联系方式：${user.getPhone()}
	</h3>
</body>
</html>