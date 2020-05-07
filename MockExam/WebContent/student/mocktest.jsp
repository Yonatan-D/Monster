<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="std_navtag.jsp" %>
<jsp:useBean id="mocktest" class="mybean.data.Test" scope="session"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
<link rel="stylesheet" href="HTML/css/mocktest.css">	
<title>开始考试</title>
</head>
<body onload="countTime()">
	<input id="spareTime" type="hidden" value="${mocktest.getSpareTime() }">
	<div class="test">
	<div class="toolbar">
		<a href="javascript:window.location.href='EndMockTest'" class="btn" id="exitTest" onclick="stopTime()">退出考试</a>
		<h4>【仿真考试】</h4>
		<h5 id="testTime">剩余时间</h5>
		<a href="#" class="btn" id="prebtn" onclick="pre_question()">上一题</a>
		<a href="#" class="btn" id="nextbtn" onclick="next_question()">下一题</a>
	</div>
	<form action="EndMockTest" method="post" name="form" id="testform">
		<div class="testarea" id="testarea">${mocktest.getTestContent() }</div> 
		<input id="submitBtn" type="submit" value="提交试卷">
	</form>
	</div>
	<script type="text/javascript" src="HTML/js/mocktest.js"></script>
</body>
</html>