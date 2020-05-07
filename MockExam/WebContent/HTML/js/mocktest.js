var timer;
function countTime() {
	var time = document.getElementById('spareTime').value;
	timer = setInterval(function() {
		time--;
		if (time < 0) {
			clearInterval(timer);
			alert("考试结束，点击确定提交试卷！");
			document.getElementsByName('form')[0].submit();
			return;
		}
		formatTime(time);
	}, 1000);
}

function stopTime() {
	clearInterval(timer);
}

function formatTime(time) {
	var minute = parseInt(time / 60);
	if (minute < 10) {
		minute = "0" + minute;
	}
	var second = parseInt(time % 60);
	if (second < 10) {
		second = "0" + second;
	}
	document.getElementById('testTime').innerHTML = "剩余时间：" + minute + " : "
			+ second;
}
var distance = 0;
function pre_question() {
	distance += 100;
	if (distance >= 100)
		distance = 0;
	document.getElementById('testarea').style = "transform: translate("
			+ distance + "vw);";
}
function next_question() {
	distance -= 100;
	if (distance <= -1000) {
		distance += 100;
		return;
	}
	document.getElementById('testarea').style = "transform: translate("
			+ distance + "vw);";
}