<%@page import="java.net.InetAddress"%>
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
		}
	</style>
	<script type="text/javascript" src="HTML/js/jquery-1.9.1.min.js"></script>
<title>题库管理</title>
</head>
<body>
	<div class="tableArea">
	<h3>题库管理</h3>
	<a class="addBtn" href="LoadInputQuestions">＋添加一行</a>
	<a class="addBtn" href="DownloadQuestions" target="_blank">导出题库</a>
	<form action="QueryQuestions" method="post" class="toolbar">
		<input type="text" name="searchBox" placeholder="输入问题关键字" autocomplete="off">
		<input type="submit" value="查询">
	</form>
	<div class="table-wrapper">
		<table class="fl-table" id="questTable">
			<thead>
			<tr>
				<th>题号</th>
				<th>问题详情</th>
				<th>A选项</th>
				<th>B选项</th>
				<th>C选项</th>
				<th>D选项</th>
				<th>正确选项</th>
				<th>图片路径</th>
				<th>图片预览</th>
				<th colspan="2">题目操作</th>
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
			<td><form action="QueryQuestions" method="get">
				<input type="hidden" name="presentPage" value="<%=show.getPresentPage()-1 %>">
				<input type="submit" value="上一页">
			</form></td>
			<td><form action="QueryQuestions" method="get">
				到 <select name="presentPage">
					<% for(int i=1; i<=pageAllCount; i++) { %>
						<option><%=i %></option>
					<% } %>
				</select> 页
				<input type="submit" value="确定">
			</form></td>
			<td><form action="QueryQuestions" method="get">
				<input type="hidden" name="presentPage" value="<%=show.getPresentPage()+1 %>">
				<input type="submit" value="下一页">
			</form></td>
			<td><form action="QueryQuestions" method="get" id="PageSizeForm">
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
		$(document).ready(function() {
			alertIsDeleteRow();
		})
		function submitPageSizeSelector() {
			document.getElementById('PageSizeForm').submit();
		}
		function alertIsDeleteRow() {
			$('.nodoBtn').click(function() {
				if(confirm("真的要删除吗?")) {
					window.location.href = "RemoveQuestions?tid="+$(this).attr("id");
				}else {
					return false;
				}
			})
		}
	</script>
</body>
</html>