<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/loginForm.css">
<title>注册新账户</title>
</head>
<body>
	<form action="UserRegist" method="post" class="registForm" onsubmit="return checkPassword()">
		<h4><a class="backBtn" href="javascript:history.go(-1)">返回</a></h4>
		<h1>注册新账户</h1>
		<p>昵称：<input type="text" name="name" size="25" required="required" placeholder="必填" autocomplete="off"></p>
		<p>性别：<input type="text" name="sex" size="25" list="cList" autocomplete="off"></p>
		<datalist id="cList">
			<option value="男"></option>
			<option value="女"></option>
		</datalist>
		<p>邮箱：<input type="email" name="email" size="25" required="required" placeholder="必填" autocomplete="off"></p>
		<p>联系号码：<input type="text" name="phone" size="22" autocomplete="off"></p>
		<p>您的密码：<input type="password" name="pwd" size="22" required="required"></p>
		<p>确认密码：<input type="password" name="repwd" size="22" required="required"></p>
		<input type="submit" value="立即注册" style="font-size: 14px">
	</form>
	
	<script type="text/javascript" src="HTML/js/index.js"></script>
</body>
</html>