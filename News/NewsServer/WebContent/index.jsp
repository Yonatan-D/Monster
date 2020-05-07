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
<body onload="load()">
<h1>新闻管理后台</h1>
	<div class="navbar">
		<a>新建图文</a>
		<a href="SelectArticle">文章管理</a>
		<a href="SelectBanner">轮播图管理</a>
	</div>
	<div class="editArea">
		<form class="editForm" action="InsertArticle" method="post" enctype="multipart/form-data">
			标题：<input type="text" name="title" size="80"><br>
			作者：<input type="text" name="author" size="80"><br>
			发布日期：<input type="datetime-local" name="publicdate" id="datetime"><br>
			封面：<input type="file" id="cover" name="cover">
			<font size="2" color="#888888">（仅支持jpg，jpeg，png格式）</font>
			<div class="cover">
				<br><img alt="" src="" width="400px" height="250px" id="cover-img"><br>
			</div>
			<br>正文：<br>
			<textarea rows="20" cols="90" name="content"></textarea>
			<input type="submit" value="提交">
			<p>(支持html格式文本，推荐段落文本p标签，小标题使用span标签。)</p>
		</form>
	</div>
	
	<script type="text/javascript">
		function load() {
			currentDatetime();
			loadCoverIMG();
		}
		
		function currentDatetime() {
			var nowDate = new Date();
			var Y = nowDate.getFullYear();
			var M = nowDate.getMonth()+1;
			var D = nowDate.getDate();
			var hh = nowDate.getHours();
			var mm = nowDate.getMinutes();
			
			if((M + '').length == 1) {
				M = '0' + (M + '')
			}
			if((D + '').length == 1) {
				D = '0' + (D + '')
			}
			if((hh + '').length == 1) {
				hh = '0' + (hh + '')
			}
			if((mm + '').length == 1) {
				mm = '0' + (mm + '')
			}
			
			var curDate = Y+"-"+M+"-"+D+"T"+hh+":"+mm;
			document.getElementById('datetime').value = curDate;
			
		}
		
		function loadCoverIMG() {
			var coverobj = document.getElementById('cover');
			coverobj.addEventListener('change', function() {
				var IMG = coverobj.files[0];
				//在前端限定文件类型
				var index = IMG.name.lastIndexOf(".");
				var type = IMG.name.substring(index+1);
				if(type!="jpg" && type!="jpeg" && type!="png"){
					alert("不支持 "+ type +" 文件格式！");
					coverobj.value="";
					return;
				}
				
				var URL = window.URL.createObjectURL(IMG);
				document.getElementById('cover-img').src = URL;
			})
		}
	</script>
</body>
</html>