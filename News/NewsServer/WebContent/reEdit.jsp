<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
			box-shadow: 0px 2px 2px #bbbbbb;
		}
		
		.editForm {
			margin-top: 20px;
			color: #4c4c4c;
			font-size: 18px;
			font-weight: bold;
		}
		.editForm p {
			color: #888888;
			font-size: 14px;
			font-weight: normal;
			margin-bottom: 40px;
		}
		
		.cover {
			width: 420px;
			height: 270px;
			border: 1px solid buttonface;
			margin: 15px 0 15px 0;
			display: flex;
			align-items: center;
			justify-content: center;
		}
		
		.cover img {
			object-fit: cover;
		}
		
		input {
			margin: 15px;
			line-height: 25px;
		}
		
		.editArea {
			width: 50%;
			margin: 0 auto;
		}
		
		h3 {
			margin-top: 20px;
		}
	</style>

<title>新闻管理后台</title>
</head>
<body>
<h1>新闻管理后台</h1>
	<div class="editArea">
		<form class="editForm" action="UpdateArticle" method="post" accept-charset="UTF-8">
			<a href="javascript:history.go(-1)">返回上一级</a>
			<h3>提示：发布时间和封面不能修改。</h3>
			<input type="hidden" name="tid" value="${id}">
			标题：<input type="text" name="title" size="80" value="${title}"><br>
			作者：<input type="text" name="author" size="80" value="${author}"><br>
			<br>正文：<br>
			<textarea rows="35" cols="90" name="content">${content}</textarea>
			<input type="submit" value="提交">
			<p>(支持html格式文本，推荐段落文本p标签，小标题使用span标签。)</p>
		</form>
	</div>
</body>
</html>