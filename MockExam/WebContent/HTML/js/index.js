/**
 * 重置登录框的账户密码
 */
function clearLoginTextValue() {
	document.getElementsByName('uid')[0].value = "";
	document.getElementsByName('pwd')[0].value = "";
}
/**
 * 注册框检查密码
 */
function checkPassword() {
	var pwd = document.getElementsByName('pwd')[0].value;
	var repwd = document.getElementsByName('repwd')[0].value;
	if(pwd != repwd) {
		alert("两次输入密码不一致！");
		return false;
	}else {
		return true;
	}
}