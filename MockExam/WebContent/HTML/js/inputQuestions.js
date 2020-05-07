/**
 * 页面打开时加载
 */
$(document).ready(function() {
	loadCoverIMG();
	getCorrectAnswer();
})
/**
 * 预览上传的图片
 */
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
			document.getElementById('cover-img').src = "";
			return;
		}
		
		var URL = window.URL.createObjectURL(IMG);
		document.getElementById('cover-img').src = URL;
	})
}
/**
 * 未选择答案时阻止表单提交
 * @returns
 */
function alertNoChooseAnswer() {
	if($("input[name='R']:checked").val() == null) {
		alert("必须选择一个正确答案！");
		return false;
	}
}