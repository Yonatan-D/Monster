<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="std_navtag.jsp" %>
<jsp:useBean id="quicktest" class="mybean.data.Test" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
<link rel="stylesheet" href="HTML/css/quicktest.css">	
<title>开始考试</title>
</head>
<body>
	<form action="EndQuickTest" method="post" name="form">
		<div class="testarea">
			<a href="student.jsp">退出考试</a>
			<h4>【五题快测】</h4>
			${quicktest.getTestContent() }
			<input type="button" value="重新出卷" onclick="javascript:window.location.href='ReQuickTest'">
			<input type="submit" value="提交试卷">
		</div>
	</form>
</body>
</html>