<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="std_navtag.jsp" %>
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
<link rel="stylesheet" href="HTML/css/navbar.css">
<link rel="stylesheet" href="HTML/css/showTestReport.css">
<link rel="stylesheet" type="text/css" href="HTML/css/tableUI.css">
<script type="text/javascript" src="HTML/js/jquery-1.9.1.min.js"></script>
<script type="text/javascript" src="HTML/js/Chart.min.js"></script>
<title>查看成绩</title>
</head>
<body>
	<div class="pieChart">
		<canvas id="doughnut" height="150" width="150"></canvas>
		<div class="pieChart-colorInfo"><div class="green"></div><%=session.getAttribute("green")%></div>
		<br><div class="pieChart-colorInfo"><div class="red"></div><%=session.getAttribute("red")%></div>
	</div>
	<div class="tableArea">
		<h3>历史成绩</h3>
		<form action="showTRselector" method="post" id="showTRform"
			class="toolbar">
			考试类型：<select name="selector" onchange="submitTestReportSelector()">
				<option>-请选择-</option>
				<option value="0">全部</option>
				<option value="1">仅看五题快测</option>
				<option value="2">仅看仿真考试</option>
			</select>
		</form>
		<div class="table-wrapper">
			<table class="fl-table">
				<thead>
					<tr>
						<th>考试时间</th>
						<th>考试类型</th>
						<th>试卷分数</th>
						<th>成绩评级</th>
					</tr>
				</thead>
				<tbody>
					<%=presentPageResult%>
				</tbody>
			</table>
			<font size="2" color="#4c4c4c">当前<%=pageSize%>条/页, 总条数：<%=pageAllSize%></font>
		</div>
		
		<%=presentPage%>/<%=pageAllCount%>
		<table class="pageController">
			<tr>
				<td><form action="QueryTestReport" method="get">
						<input type="hidden" name="presentPage"
							value="<%=show.getPresentPage() - 1%>"> <input
							type="submit" value="上一页">
					</form></td>
				<td><form action="QueryTestReport" method="get">
						到 <select name="presentPage">
							<%
								for (int i = 1; i <= pageAllCount; i++) {
							%>
							<option><%=i%></option>
							<%
								}
							%>
						</select> 页 <input type="submit" value="确定">
					</form></td>
				<td><form action="QueryTestReport" method="get">
						<input type="hidden" name="presentPage"
							value="<%=show.getPresentPage() + 1%>"> <input
							type="submit" value="下一页">
					</form></td>
				<td><form action="QueryTestReport" method="get"
						id="PageSizeForm">
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
		$(document).ready(
				function() {
					var Evaluation =
	<%=session.getAttribute("Evaluation")%>
		;
					var doughnutData = [ {
						value : 100-Evaluation,
						color : "#F7464A"
					}, {
						value : Evaluation,
						color : "#46BFBD"
					} ];

					var myDoughnut = new Chart(document.getElementById(
							"doughnut").getContext("2d"))
							.Doughnut(doughnutData);
				})
		function submitTestReportSelector() {
			document.getElementById('showTRform').submit();
		}
		function submitPageSizeSelector() {
			document.getElementById('PageSizeForm').submit();
		}
	</script>
</body>
</html>