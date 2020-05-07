<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tch_navtag.jsp" %>
<jsp:useBean id="show" type="mybean.data.ShowByPage" scope="session"></jsp:useBean>
<%
	StringBuffer presentPageResult = show.getPresentPageResult();
	int presentPage = show.getPresentPage();
	int pageAllCount = show.getPageAllCount();
	int pageAllSize = show.getPageAllSize();
	int pageSize = show.getPageSize();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
<link rel="stylesheet" type="text/css" href="HTML/css/tableUI.css">
	<style type="text/css">
		body {
			display: flex;
			justify-content: center;
			overflow-x: hidden;
		}
		.tableArea {
			width: 60vw;
		}
	</style>
<title>考生信息管理</title>
</head>
<body>
	<div class="tableArea">
	<h3>考生信息管理</h3>
	<a class="addBtn" href="DownloadUser" target="_blank">导出用户数据</a>
	<form action="QueryStudentMember" method="post" class="toolbar">
		<input type="text" name="searchBox" placeholder="输入账号" autocomplete="off">
		<input type="submit" value="查询">
	</form>
	<div class="table-wrapper">
		<table class="fl-table">
			<thead>
			<tr>
				<th>账号</th>
				<th>昵称</th>
				<th>性别</th>
				<th>邮箱</th>
				<th>联系电话</th>
				<th>注册时间</th>
			</tr>
			</thead>
			<tbody>
				<%=presentPageResult %>
			</tbody>
		</table>
		<font size="2" color="#4c4c4c">当前<%=pageSize%>条/页, 总条数：<%=pageAllSize%></font>
	</div>
	<%=presentPage %>/<%=pageAllCount %>
	<table class="pageController">
		<tr>
			<td><form action="QueryStudentMember" method="get">
				<input type="hidden" name="presentPage" value="<%=show.getPresentPage()-1 %>">
				<input type="submit" value="上一页">
			</form></td>
			<td><form action="QueryStudentMember" method="get">
				到 <select name="presentPage">
					<% for(int i=1; i<=pageAllCount; i++) { %>
						<option><%=i %></option>
					<% } %>
				</select> 页
				<input type="submit" value="确定">
			</form></td>
			<td><form action="QueryStudentMember" method="get">
				<input type="hidden" name="presentPage" value="<%=show.getPresentPage()+1 %>">
				<input type="submit" value="下一页">
			</form></td>
			<td><form action="QueryStudentMember" method="get" id="PageSizeForm">
				<select name="pageSize" onchange="submitPageSizeSelector()">
					<option>-请选择-</option>
					<option value="10">10条/页</option>
					<option value="15">15条/页</option>
					<option value="20">20条/页</option>
				</select>
			</form></td>
		</tr>
	</table>
	</div>
	
	<script type="text/javascript">
		function submitPageSizeSelector() {
			document.getElementById('PageSizeForm').submit();
		}
	</script>
</body>
</html>