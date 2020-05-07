<%@page import="java.net.InetAddress"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="tch_navtag.jsp" %>
<%  String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+InetAddress.getLocalHost().getHostAddress()+":"+request.getServerPort()+path+"/"; 
	request.setAttribute("path", basePath);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<link rel="stylesheet" type="text/css" href="HTML/css/navbar.css">
	<link rel="stylesheet" type="text/css" href="HTML/css/inputQuestions.css">
	<script type="text/javascript" src="HTML/js/jquery-1.9.1.min.js"></script>
<title>添加题目</title>
</head>
<body onload="getCorrectAnswer()">
	<div class="editArea">
		<form class="editForm" action="RecordQuestions" method="post" enctype="multipart/form-data" onsubmit="return alertNoChooseAnswer()">
			<a href="javascript:history.go(-1)">返回上一级</a><br>
			<div class="updatetext">
				<input type="hidden" name="id" value="${id}">
				问题详情：<br><textarea rows="10" cols="60" name="quest" required="required">${quest}</textarea><br>
				选项A：<br><input type="text" name="a" size="60" value="${a}" required="required" autocomplete="off"><br>
				选项B：<br><input type="text" name="b" size="60" value="${b}" required="required" autocomplete="off"><br>
				选项C：<br><input type="text" name="c" size="60" value="${c}" required="required" autocomplete="off"><br>
				选项D：<br><input type="text" name="d" size="60" value="${d}" required="required" autocomplete="off"><br>
				正确选项：
				A<input type="radio" name="R" value="A">
				B<input type="radio" name="R" value="B">
				C<input type="radio" name="R" value="C">
				D<input type="radio" name="R" value="D">
			</div>
			<div class="updateimg">
				上传图片：<div id="addImgBtn" onclick="openFile()">本地选择</div>
				<input type="file" id="cover" name="cover">
				<font size="2" color="#888888">（仅支持jpg，jpeg，png格式）</font>
				<div class="cover">
					<br><img alt="" src="${path}${pic}" width="400px" height="250px" id="cover-img"><br>
				</div>
				<input type="submit" value="保存题目">
			</div>
		</form>
	</div>
	
	<script type="text/javascript" src="HTML/js/inputQuestions.js"></script>
	<script type="text/javascript">
		//作为修改页面时，获取答案并选中单选框
		function getCorrectAnswer() {
			$("input[type='radio'][value='${answer}']").attr("checked", true);
		}
		function openFile() {
			$('#cover').click();
		}
	</script>
</body>
</html>