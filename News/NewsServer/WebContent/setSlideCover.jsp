<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:useBean id="setCover" class="mybean.data.Articles" scope="request"></jsp:useBean>
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
			margin: 0 auto;
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
			color: #fff;
			text-decoration: none;
			font-weight: bold;
		}
	</style>

<title>新闻管理后台</title>
</head>
<body>
<h1>新闻管理后台</h1>
	<div class="navbar">
		<a href="index.jsp">新建图文</a>
		<a href="SelectArticle">文章管理</a>
		<a>轮播图管理</a>
	</div>
	
	<table border="1">
		<% String[]columnName = setCover.getColumnName(); %>
		<tr>
		<% for(String s:columnName) { %>
			<th><%=s %></th>
		<% } %>
			<th>Preview</th>
			<th>CoverManagement</th>
		</tr>
		<% String[][]record = setCover.getTableRecord();
			for(int i=0; i<record.length; i++) {
		%>
		<tr>
			<% for(int j=0; j<record[i].length; j++) { %>
				<td align="center"><%=record[i][j] %></td>
			<% } %>
			<td><img width="200" src="http://<%=InetAddress.getLocalHost().getHostAddress() %>:<%=request.getServerPort() %><%=record[i][2] %>"></td>
			<td align="center"><a href="DeleteBanner?tid=<%=record[i][0]%>">移除封面图</a></td>
		</tr>
		<% } %>
	</table>
</body>
</html>