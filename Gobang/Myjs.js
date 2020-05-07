/**基本参数
 *初始化常量、变量
 *
 */
 var cnv;
 var ctx;
 var ROWS = 15;
 var COLS = 15;
 var width = $(window).width();
 var height = $(window).height();
 var MARGIN_LEFT = width*0.08;
 var MARGIN_TOP = height*0.4;
 var GRID_SPAN = width*0.06;
 var gameover = false;
 var isblack = true;
 var computergo = false;
 var posX, posY;
 var chessCount = 0;

/**定义二维数组
 *存储棋盘棋子分布
 */
 var chess = new Array(15);
 for (var x = 0; x < 15; x++) { //初始化棋子数组
 	chess[x] = new Array(15);
 	for (var y = 0; y < 15; y++) {
 		chess[x][y] = 0;
 	}
 }

/**定义steps类
 *存储棋子步数
 */
 function steps(x, y){
 	this.x = x;
 	this.y = y;
 }
 var step = new Array(15*15);
 for(var k = 0; k < 15*15; k++){
 	step[k] = new steps();
 }

/**定义二维数组
 *存储各个点的权值
 */
 var weightArray = new Array(15);
 for (var x = 0; x < 15; x++) { //初始化棋子数组
 	weightArray[x] = new Array(15);
 	for (var y = 0; y < 15; y++) {
 		chess[x][y] = 0;
 	}
 }

 /**定义图片路径
  *
  */
  var chess_b = new Image();
  chess_b.src="image/chess_b.png";
  var chess_w = new Image();
  chess_w.src="image/chess_w.png";

/**绘制棋盘
 *
 */
 function chessboard(){
 	cnv = document.getElementById('canvas');
 	ctx = cnv.getContext("2d");

	cnv.width = width;//设置棋盘大小
	cnv.height = height;

	// ctx.beginPath();//给棋盘一个透明白色背景
	// ctx.fillStyle="rgba(255, 255, 255, 0.4)";
	// ctx.fillRect(MARGIN_LEFT-10, MARGIN_TOP-10, (ROWS-1)*GRID_SPAN+20, (COLS-1)*GRID_SPAN+20);

	ctx.lineWidth="1";
	ctx.strokeStyle="black";
	for(var i = 0; i < ROWS; i++){
		ctx.beginPath();
		ctx.moveTo(MARGIN_LEFT, MARGIN_TOP+i*GRID_SPAN);//画横线
		ctx.lineTo(MARGIN_LEFT+(COLS-1)*GRID_SPAN, MARGIN_TOP+i*GRID_SPAN);
		ctx.stroke();
		ctx.moveTo(MARGIN_LEFT+i*GRID_SPAN, MARGIN_TOP);//画竖线
		ctx.lineTo(MARGIN_LEFT+i*GRID_SPAN, MARGIN_TOP+(ROWS-1)*GRID_SPAN);
		ctx.stroke();
	}

	ctx.fillStyle="black";
	for(var i = 3; i < 15; i += 4){//4星,1天元
		ctx.beginPath();
		ctx.arc(MARGIN_LEFT+i*GRID_SPAN, MARGIN_TOP+i*GRID_SPAN,3,0,Math.PI*2,true);
		ctx.arc(MARGIN_LEFT+(15-1-i)*GRID_SPAN, MARGIN_TOP+i*GRID_SPAN,3,0,Math.PI*2,true)
		ctx.closePath();
		ctx.fill();
	}
}
/**Mouse事件 按下鼠标触发
 *用于落子操作
 *@param e 事件
 */
 function play(e){
 	posX = parseInt((e.clientX-MARGIN_LEFT+GRID_SPAN/2)/GRID_SPAN);
 	posY = parseInt((e.clientY-MARGIN_TOP+GRID_SPAN/2)/GRID_SPAN);
 	if(posX<0||posX>14||posY<0||posY>14||gameover||checkChess(posX, posY)) return;
 	if(isblack){
 		drawchess(posX, posY, 1);
 	}else{
 		drawchess(posX, posY, 2);
 	}

 	var c = isblack ? 1:2;
 	if(isWin(posY, posX, c)){
 		if(c == 1){
 			msgCSS('blackWin');
 		}else if(c == 2){
 			msgCSS('whiteWin');
 		}
 		gameover = true;
 		return;
 	}

 	if(computergo){
 		isblack = !isblack;
 		action(); 
 		action(); //AI算法执行两次，才能解决黑棋1被白棋2覆盖的问题。
 		drawchess(posX, posY, 2);
 		if(isWin(posY, posX, 2)){
 			msgCSS('whiteWin');
 			gameover = true;
 			return;
 		}
 	}

 	isblack = !isblack;
 }
/**画棋子
 *@param posX 横坐标
 *@param posY 纵坐标
 *@param c 黑棋为1，白棋为2
 */
 function drawchess(x, y, c){
 	console.log(x+","+y+","+c);
 	chess[y][x] = c;
 	step[chessCount++] = new steps(x, y);

 	ctx.font = "14px bold 黑体";
	if(c == 1){
		ctx.drawImage(chess_b, MARGIN_LEFT+x*GRID_SPAN-12, MARGIN_TOP+y*GRID_SPAN-12, 24, 24);//绘制棋子
		
		if(chessCount>1){
			var tw = ctx.measureText(chessCount-1).width/2;//获取序号宽度
			ctx.fillStyle="black";
			ctx.fillText(chessCount-1, MARGIN_LEFT+step[chessCount-2].x*GRID_SPAN-tw, MARGIN_TOP+step[chessCount-2].y*GRID_SPAN+4);
		}
		
		var tw = ctx.measureText(chessCount).width/2;//获取序号宽度
		ctx.fillStyle= "#E3746B";
		ctx.fillText(chessCount, MARGIN_LEFT+x*GRID_SPAN-tw, MARGIN_TOP+y*GRID_SPAN+4);
	}
	else if(c == 2){
		ctx.drawImage(chess_w, MARGIN_LEFT+x*GRID_SPAN-12, MARGIN_TOP+y*GRID_SPAN-12, 24, 24);//绘制棋子

		if(chessCount>1){
			var tw = ctx.measureText(chessCount-1).width/2;//获取序号宽度
			ctx.fillStyle="white";
			ctx.fillText(chessCount-1, MARGIN_LEFT+step[chessCount-2].x*GRID_SPAN-tw, MARGIN_TOP+step[chessCount-2].y*GRID_SPAN+4);
		}

		ctx.fillStyle= "#E3746B";
		var tw = ctx.measureText(chessCount).width/2;//获取序号宽度
		ctx.fillText(chessCount, MARGIN_LEFT+x*GRID_SPAN-tw, MARGIN_TOP+y*GRID_SPAN+4);
	}
		
}
/**判断区域是否落子
 *@param x 横坐标
 *@param y 纵坐标
 *@return bool ture/false
 */
 function checkChess(x, y){
 	if(chess[y][x] != 0)
 		return true;
 	else
 		return false;
 }
/**胜负判定
 *
 */
 function isWin(xIndex, yIndex, c){
 	var continueCount = 1;// 连续棋子数

 	// 判断水平方向是否连续5颗
 	for(var x = xIndex-1; x >= 0; x--){ //横向向左寻找
 		if(chess[x][yIndex] == c){
 			continueCount++;
 		}else{
 			break;
 		}
 	}
 	for(var x = xIndex+1; x < 15; x++){ //横向向右寻找
 		if(chess[x][yIndex] == c){
 			continueCount++;
 		}else{
 			break;
 		}
 	}
	// 判断记录数大于等于五，即表示此方获胜
	if (continueCount >= 5) {
		return true;
	} else{
		continueCount = 1;
	}
	
	// 判断垂直方向是否连续5颗
	for (var y = yIndex-1; y >= 0; y--) { //纵向向上寻找
		if (chess[xIndex][y] == c) {
			continueCount++;
		} else
		break;
	}
	for (var y = yIndex+1; y < 15; y++) { //纵向向下寻找
		if (chess[xIndex][y] == c) {
			continueCount++;
		} else
		break;
	}
	// 判断记录数大于等于五，即表示此方获胜
	if (continueCount >= 5) {
		return true;
	} else{
		continueCount = 1;
	}

	// 判断左下右上方向是否连续5颗
	for (var x = xIndex-1, y = yIndex+1; y < 15 && x >= 0; x--, y++) { //左下寻找
		if (chess[x][y] == c) {
			continueCount++;
		} else
		break;
	}
	for (var x = xIndex+1, y = yIndex-1; y >= 0 && x < 15; x++, y--) { //右上寻找
		if (chess[x][y] == c) {
			continueCount++;
		} else
		break;
	}
	// 判断记录数大于等于五，即表示此方获胜
	if (continueCount >= 5) {
		return true;
	} else{
		continueCount = 1;
	}

	// 判断左上右下方向是否连续5颗
	for (var x = xIndex-1, y = yIndex-1; y >= 0 && x >= 0; x--, y--) { //左上寻找
		if (chess[x][y] == c) {
			continueCount++;
		} else
		break;
	}
	for (var x = xIndex+1, y = yIndex+1; y < 15 && x < 15; x++, y++) { //右下寻找
		if (chess[x][y] == c) {
			continueCount++;
		} else
		break;
	}
	// 判断记录数大于等于五，即表示此方获胜
	if (continueCount >= 5) {
		return true;
	} else{
		continueCount = 1;
	}

	return false;
}
/**JS简单实现HashMap
 *参考：https://thiner.iteye.com/blog/294736
 *     https://www.cnblogs.com/chunyansong/p/5485759.html
 */
 function HashMap() {
 	this.put = function(key, value) {this[key] = value};
 	this.get = function(key) {return this[key]};
 }
/**map存储棋谱
 *
 */
 var map = new HashMap();
// 被堵住
map.put("01", 17);// 眠1连
map.put("02", 12);// 眠1连
map.put("001", 17);// 眠1连
map.put("002", 12);// 眠1连
map.put("0001", 17);// 眠1连
map.put("0002", 12);// 眠1连

map.put("0102", 17);// 眠1连，15
map.put("0201", 12);// 眠1连，10
map.put("0012", 15);// 眠1连，15
map.put("0021", 10);// 眠1连，10
map.put("01002", 19);// 眠1连，15
map.put("02001", 14);// 眠1连，10
map.put("00102", 17);// 眠1连，15
map.put("00201", 12);// 眠1连，10
map.put("00012", 15);// 眠1连，15
map.put("00021", 10);// 眠1连，10

map.put("01000", 21);// 活1连，15
map.put("02000", 16);// 活1连，10
map.put("00100", 19);// 活1连，15
map.put("00200", 14);// 活1连，10
map.put("00010", 17);// 活1连，15
map.put("00020", 12);// 活1连，10
map.put("00001", 15);// 活1连，15
map.put("00002", 10);// 活1连，10

// 被堵住
map.put("0101", 65);// 眠2连，40
map.put("0202", 60);// 眠2连，30
map.put("0110", 65);// 眠2连，40
map.put("0220", 60);// 眠2连，30
map.put("011", 65);// 眠2连，40
map.put("022", 60);// 眠2连，30
map.put("0011", 65);// 眠2连，40
map.put("0022", 60);// 眠2连，30

map.put("01012", 65);// 眠2连，40
map.put("02021", 60);// 眠2连，30
map.put("01102", 65);// 眠2连，40
map.put("02201", 60);// 眠2连，30
map.put("00112", 65);// 眠2连，40
map.put("00221", 60);// 眠2连，30

map.put("01010", 75);// 活2连，40
map.put("02020", 70);// 活2连，30
map.put("01100", 75);// 活2连，40
map.put("02200", 70);// 活2连，30
map.put("00110", 75);// 活2连，40
map.put("00220", 70);// 活2连，30
map.put("00011", 75);// 活2连，40
map.put("00022", 70);// 活2连，30

// 被堵住
map.put("0111", 150);// 眠3连，100
map.put("0222", 140);// 眠3连，80

map.put("01112", 150);// 眠3连，100
map.put("02221", 140);// 眠3连，80

map.put("01101", 1000);// 活3连，130
map.put("02202", 800);// 活3连，110
map.put("01011", 1000);// 活3连，130
map.put("02022", 800);// 活3连，110
map.put("01110", 1000);// 活3连
map.put("02220", 1000);// 活3连  2019/1/27修改800为1000

map.put("01111", 3000);// 4连，300
map.put("02222", 3500);// 4连，280

function unionWeight(a, b) {
	// 必须要先判断a,b两个数值是不是null
	if ((a == null) || (b == null))
		return 0;
	// 一一
	else if ((a >= 10) && (a <= 25) && (b >= 10) && (b <= 25))
		return 60;
	// 一二、二一
	else if (((a >= 10) && (a <= 25) && (b >= 60) && (b <= 80))
		|| ((a >= 60) && (a <= 80) && (b >= 10) && (b <= 25)))
		return 800;
	// 一三、三一、二二
	else if (((a >= 10) && (a <= 25) && (b >= 140) && (b <= 1000))
		|| ((a >= 140) && (a <= 1000) && (b >= 10) && (b <= 25))
		|| ((a >= 60) && (a <= 80) && (b >= 60) && (b <= 80)))
		return 3000;
	// 二三、三二
	else if (((a >= 60) && (a <= 80) && (b >= 140) && (b <= 1000))
		|| ((a >= 140) && (a <= 1000) && (b >= 60) && (b <= 80)))
		return 3000;
	else
		return 0;
}

/**
 * 求最大权值
 */
 function action() {
	// 机器落子
	// 先计算出各个位置的权值
	for (var i = 0; i < chess.length; i++) {
		for (var j = 0; j < chess[i].length; j++) {
			// 首先判断当前位置是否为空
			if (chess[i][j] == 0) {
				// 往左延伸
				var ConnectType = "0";
				var jmin = Math.max(0, j - 4);
				for (var positionj = j - 1; positionj >= jmin; positionj--) {
					// 依次加上前面的棋子
					ConnectType = ConnectType + chess[i][positionj];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置中
				var valueleft = map.get(ConnectType);
				if (valueleft != null)
					weightArray[i][j] += valueleft;

				// 往右延伸
				ConnectType = "0";
				var jmax = Math.min(14, j + 4);
				for (var positionj = j + 1; positionj <= jmax; positionj++) {
					// 依次加上前面的棋子
					ConnectType = ConnectType + chess[i][positionj];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置中
				var valueright = map.get(ConnectType);
				if (valueright != null)
					weightArray[i][j] += valueright;

				// 联合判断，判断行
				weightArray[i][j] += unionWeight(valueleft, valueright);

				// 往上延伸
				ConnectType = "0";
				var imin = Math.max(0, i - 4);
				for (var positioni = i - 1; positioni >= imin; positioni--) {
					// 依次加上前面的棋子
					ConnectType = ConnectType + chess[positioni][j];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置中
				var valueup = map.get(ConnectType);
				if (valueup != null)
					weightArray[i][j] += valueup;

				// 往下延伸
				ConnectType = "0";
				var imax = Math.min(14, i + 4);
				for (var positioni = i + 1; positioni <= imax; positioni++) {
					// 依次加上前面的棋子
					ConnectType = ConnectType + chess[positioni][j];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置中
				var valuedown = map.get(ConnectType);
				if (valuedown != null)
					weightArray[i][j] += valuedown;

				// 联合判断，判断列
				weightArray[i][j] += unionWeight(valueup, valuedown);

				// 往左上方延伸,i,j,都减去相同的数
				ConnectType = "0";
				for (var position = -1; position >= -4; position--) {
					if ((i + position >= 0) && (i + position <= 14) && (j + position >= 0) && (j + position <= 14))
						ConnectType = ConnectType + chess[i + position][j + position];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置
				var valueLeftUp = map.get(ConnectType);
				if (valueLeftUp != null)
					weightArray[i][j] += valueLeftUp;

				// 往右下方延伸,i,j,都加上相同的数
				ConnectType = "0";
				for (var position = 1; position <= 4; position++) {
					if ((i + position >= 0) && (i + position <= 14) && (j + position >= 0) && (j + position <= 14))
						ConnectType = ConnectType + chess[i + position][j + position];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置
				var valueRightDown = map.get(ConnectType);
				if (valueRightDown != null)
					weightArray[i][j] += valueRightDown;

				// 联合判断，判断行
				weightArray[i][j] += unionWeight(valueLeftUp, valueRightDown);

				// 往左下方延伸,i加,j减
				ConnectType = "0";
				for (var position = 1; position <= 4; position++) {
					if ((i + position >= 0) && (i + position <= 14) && (j - position >= 0) && (j - position <= 14))
						ConnectType = ConnectType + chess[i + position][j - position];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置
				var valueLeftDown = map.get(ConnectType);
				if (valueLeftDown != null)
					weightArray[i][j] += valueLeftDown;

				// 往右上方延伸,i减,j加
				ConnectType = "0";
				for (var position = 1; position <= 4; position++) {
					if ((i - position >= 0) && (i - position <= 14) && (j + position >= 0) && (j + position <= 14))
						ConnectType = ConnectType + chess[i - position][j + position];
				}
				// 从数组中取出相应的权值，加到权值数组的当前位置
				var valueRightUp = map.get(ConnectType);
				if (valueRightUp != null)
					weightArray[i][j] += valueRightUp;

				// 联合判断，判断行
				weightArray[i][j] += unionWeight(valueLeftDown, valueRightUp);
			}
		}
	}

	// 取出最大的权值

	var weightmax = 0;
	for (var i = 0; i < 15; i++) {
		for (var j = 0; j < 15; j++) {
			if (weightmax < weightArray[i][j]) {
				weightmax = weightArray[i][j];
				posX = j;
				posY = i;
			}
		}
	}	
	
	// 落子以后重置权值数组weightArray
	for (var i = 0; i < 15; i++)
		for (var j = 0; j < 15; j++)
			weightArray[i][j] = 0;
	}

/**重玩提示框
 *
 */
 function restartDialog(){
 	if(confirm("是否开始新游戏？")){
 		return true;
 	}else{
 		return false;
 	}
 }
/**重玩按钮
 *
 */
 function restart(){
 	if(!restartDialog()) return;
 	gameover = false;
 	isblack = true;
 	chessCount = 0;
 	msgCSS('startCSS');
 	setTimeout(function(){resetMSG()}, 1000);
 	// resetMSG();
 	for(var x=0; x<15; x++){ //清空棋子
 		for(var y=0; y<15; y++){
 			chess[x][y] = 0;
 		}
 	}
 	chessboard(); //重绘棋盘
 }
/**认输提示框
 *
 */
 function giveupDialog(){
 	var winner = isblack ? "白棋" : "黑棋";
 	if(confirm("是否向"+winner+"认输？")){
 		return true;
 	}else{
 		return false;
 	}
 }
/**认输按钮
 *
 */
 function giveup(){
 	if(gameover) return;
 	if(!giveupDialog()) return;
 	gameover = true;
 	if(isblack){
 		msgCSS('whiteWin');
 	}
 	else{
 		msgCSS('blackWin');
 	}
 	
 }
/**撤销上一步棋子
 *(清除棋子并重画格子)
 */
 function huiqi() {
 	chessCount--;
 	chess[step[chessCount].y][step[chessCount].x] = 0;
 	isblack=!isblack;
 	//擦除棋子
 	ctx.clearRect(MARGIN_LEFT+step[chessCount].x*GRID_SPAN-12, MARGIN_TOP+step[chessCount].y*GRID_SPAN-12, 24, 24);
 	
 	//边界值超出判断
 	if(MARGIN_LEFT+step[chessCount].x*GRID_SPAN-12 < MARGIN_LEFT){
 		LEFT = MARGIN_LEFT;
 	}else{
 		LEFT = MARGIN_LEFT+step[chessCount].x*GRID_SPAN-12;
 	}
 	if(MARGIN_LEFT+step[chessCount].x*GRID_SPAN+12 > MARGIN_LEFT+14*GRID_SPAN){
 		RIGHT = MARGIN_LEFT+14*GRID_SPAN
 	}else{
 		RIGHT = MARGIN_LEFT+step[chessCount].x*GRID_SPAN+12;
 	}
 	if(MARGIN_TOP+step[chessCount].y*GRID_SPAN-12 < MARGIN_TOP){
 		TOP = MARGIN_TOP;
 	}else{
 		TOP = MARGIN_TOP+step[chessCount].y*GRID_SPAN-12;
 	}
 	if(MARGIN_TOP+step[chessCount].y*GRID_SPAN+12 > MARGIN_TOP+14*GRID_SPAN){
 		DOWN = MARGIN_TOP+14*GRID_SPAN;
 	}else{
 		DOWN = MARGIN_TOP+step[chessCount].y*GRID_SPAN+12;
 	}

 	//重画周围线条
 	ctx.beginPath();
 	ctx.moveTo(LEFT, MARGIN_TOP+step[chessCount].y*GRID_SPAN);//画横线
 	ctx.lineTo(RIGHT, MARGIN_TOP+step[chessCount].y*GRID_SPAN);

 	ctx.moveTo(MARGIN_LEFT+step[chessCount].x*GRID_SPAN, TOP);//画竖线
 	ctx.lineTo(MARGIN_LEFT+step[chessCount].x*GRID_SPAN, DOWN);

 	ctx.stroke();

 	//特殊位置：4星1天元
 	if(step[chessCount].x==3&&step[chessCount].y==3||step[chessCount].x==7&&step[chessCount].y==7
 		||step[chessCount].x==11&&step[chessCount].y==11||step[chessCount].x==3&&step[chessCount].y==11
 		||step[chessCount].x==11&&step[chessCount].y==3){
 		ctx.fillStyle="black";
 	ctx.beginPath();
 	ctx.arc(MARGIN_LEFT+step[chessCount].x*GRID_SPAN, MARGIN_TOP+step[chessCount].y*GRID_SPAN,3,0,Math.PI*2,true);
 	ctx.closePath();
 	ctx.fill();
 }
}
/**悔棋按钮
 *
 */
 function goback() {
 	if(gameover) return;
 	if(chessCount<1) return;
 	
 	huiqi();

 	if(computergo) {
 		huiqi(); //再回退一步
 	}
 }
/**返回提示框
 *
 */
 function backDialog(){
 	if(confirm("是否清空棋盘？")){
 		return true;
 	}else{
 		return false;
 	}
 }
/**返回按钮
 *
 */
 function backmenu(){
 	if(!backDialog()) return;
 	location.hash="#UI";
 	location.reload();
 	
 }

/**人机对弈
 *
 */
 function robot() {
 	computergo = true;
 }
/**练习模式
 *
 */
 function charg() {
 	computergo = false;
 }

/**初始化
 *
 */
 function load(){
 	chessboard();
 }

/**
 *收集玩家错误，存储当前chess数组
 */
 function saveError(data, filename){

 	if(!data) {
 		console.error('Console.save:No data');
 		return;
 	}

 	if(!filename) filename = 'chess.dat'

 		if(typeof data == "object"){
 			data = JSON.stringify(data, undefined, 4);
 		}

 		var blob = new Blob([data], {type:'text/dat'}),
 		e = document.createEvent('MouseEvents'),
 		a = document.createElement('a');

 		a.download = filename;
 		a.href = window.URL.createObjectURL(blob);
 		a.dataset.downloadurl = ['text/dat', a.download, a.href].join(':');
 		e.initMouseEvent('click', true, false, window, 0, 0, 0, 0, false, false, false, false, 0, null);
 		a.dispatchEvent(e);
 	}

/**玩家胜出效果
 *
 */
 function msgCSS(class_name){
 	var msg = document.getElementById('msg');
 	msg.className = class_name;
 }

 function resetMSG(){
 	var msg = document.getElementById('msg');
 	msg.className = 'none';
 }

/**监听后退事件
 *
 */
// $("#game").ready(function(e) { 
// 	var counter = 0;
// 	if (window.history && window.history.pushState) {
// 		$(window).on('popstate', function () {
// 			window.history.pushState('forward', null, '#');
// 			window.history.forward(1);
// 			backmenu();
// 			// location.reload();//执行操作
// });
// 	}
// window.history.pushState('forward', null, '#'); //在IE中必须得有这两行
// window.history.forward(1);
// });

$(function () {
            //防止页面后退
            history.pushState(null, null, document.URL);
            window.addEventListener('popstate', function () {
                    history.pushState(null, null, document.URL);
            });
        })
