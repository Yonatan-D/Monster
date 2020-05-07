<%@ page contentType="text/html; charset=UTF-8" %>
<div class="navbar">
	<h1>${user.getName()}(管理员账号：${user.getUid()})</h1>
	<a href="tch_home.jsp">首页</a>
	<a href="QueryQuestions">题库管理</a>
	<a href="QueryStudentMember">考生信息管理</a>
	<a href="LoadInputQuestions">题库录入</a>
	<a class="exit" href="HandleExit">退出登录</a>
</div>