<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tch_navtag.jsp" %>
<jsp:useBean id="user" class="mybean.data.User" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
<link rel="stylesheet" type="text/css" href="HTML/css/resetPwdBox.css">
<script type="text/javascript" src="HTML/js/jquery-1.9.1.min.js"></script>
<title>教师管理系统</title>
</head>
<body>
	<form action="ResetPassword" method="post">
		<div class="pretime">上次登录时间： ${user.getRegistdate()}</div>
		<div class="pwd-box">
			<input type="password" class="newPassword" name="newPassword" placeholder="输入新密码" required="required">
			<input type="submit" class="submit-btn" value="修改密码"></a>		
		</div>
	</form>
	
	<script type="text/javascript">
		$('.newPassword').focus(function() {
			$('body').css("background", "#f1f2f6");
		})
		$('.newPassword').blur(function() {
			$('body').css("background", "#FFF");
		})
	</script>
</body>
</html>