<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="sendArt" class="mybean.data.Articles" scope="request"></jsp:useBean>
<jsp:useBean id="noSendArt" class="mybean.data.Articles" scope="request"></jsp:useBean>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

	<style type="text/css">
		* {
			margin: 0;
			padding: 0;
			font-family: Microsoft YaHei;
		}
	
		body {
			margin: 0;
			padding: 0px;
		}
		
		h1 {
			padding: 25px 10px 10px 10px;
			color: #ffffff;
			text-shadow: 2px 2px 5px #000000;
			background-color: #3498db;
			box-shadow: 0px 2px 5px #888888;
		}	
		table {
			color: #4c4c4c;
			margin: 20px 40px 40px 40px;
		}
		h2 {
			color: #3498db;
			margin-left: 40px;
		}
		
		.navbar {
			height: 40px;
			background: #bdc3c7;
			display: flex;
			align-items: center;
			justify-content: center;
			margin-bottom: 40px;
		}
		.navbar a {
			margin: 0 100px;
			padding: 10px 30px;
			font-size: 16px;
			text-decoration: none;
			color: white;
			font-weight: bold;
		}
		.navbar a:hover {
			color: #bdc3c7;
			background: #fff;
		}
	</style>

<title>新闻管理后台</title>
</head>
<body>
<h1>新闻管理后台</h1>
	<div class="navbar">
		<a href="index.jsp">新建图文</a>
		<a>文章管理</a>
		<a href="SelectBanner">轮播图管理</a>
	</div>
	
	<h2>-> 待发布</h2>
	<table border="1">
		<% String[]columnName = noSendArt.getColumnName(); %>
		<tr>
		<% for(String s:columnName) { %>
			<th><%=s %></th>
		<% } %>
			<th colspan="3">ArticleManagement</th>
		</tr>
		<% String[][]record = noSendArt.getTableRecord();
			for(int i=0; i<record.length; i++) {
		%>
		<tr>
			<% for(int j=0; j<record[i].length; j++) { %>
				<td align="center"><%=record[i][j] %></td>
			<% } %>
			<td align="center"><a href="EditArticle?tid=<%=record[i][0]%>">编辑</a></td>
			<td align="center"><a href="DeleteArticle?tid=<%=record[i][0]%>">删除</a></td>
		</tr>
		<% } %>
	</table>

	<h2>-> 已发布</h2>
	<table border="1">
		<% String[]columnName1 = sendArt.getColumnName(); %>
		<tr>
		<% for(String s:columnName1) { %>
			<th><%=s %></th>
		<% } %>
			<th colspan="3">ArticleManagement</th>
		</tr>
		<% String[][]record1 = sendArt.getTableRecord();
			for(int i=0; i<record1.length; i++) {
		%>
		<tr>
			<% for(int j=0; j<record1[i].length; j++) { %>
				<td align="center"><%=record1[i][j] %></td>
			<% } %>	
			<td align="center"><a href="EditArticle?tid=<%=record1[i][0]%>">编辑</a></td>
			<td align="center"><a href="DeleteArticle?tid=<%=record1[i][0]%>">删除</a></td>
				<td align="center"><a href="InsertBanner?tid=<%=record1[i][0]%>">设为轮播图</a></td>			
		</tr>
		<% } %>
	</table>
</body>
</html>