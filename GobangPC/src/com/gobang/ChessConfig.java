package com.gobang;

import java.awt.*;
import javax.swing.*;

public interface ChessConfig {
	int MARGIN = 50;// 棋盘边距
	int GRID_SPAN = 40;// 网格间距
	int ROWS = 15;// 棋盘行数
	int COLS = 15;// 棋盘列数
	int DIAMETER = 30;// 棋子直径
	int MAX = 30;//下棋期限30秒
	int WIN_COUNT = 5;// 赢棋条件连续5枚棋子
	int LGWIDTH = 811;
	int LGHIGHTH = 574;//两个数值为模式选择界面的大小
	ChessMan[] CHESSLIST = new ChessMan[ROWS * COLS];// 初始化每个数组元素为null
	int[][] BOARD = new int[15][15];
	Image LOGINBG = new ImageIcon("./img/login_background.png").getImage();
	Image BACKGROUND = new ImageIcon("./img/background.jpg").getImage();// 棋盘背景
	Image BIMG = new ImageIcon("./img/chess_b.png").getImage();// 黑棋
	Image WIMG = new ImageIcon("./img/chess_w.png").getImage();// 白棋
	ImageIcon BICO = new ImageIcon("./img/chess_b.png");// 黑棋
	ImageIcon WICO = new ImageIcon("./img/chess_w.png");// 白棋
	ImageIcon GAMEOVER = new ImageIcon("./img/gameover.png");
	ImageIcon ONEPERSON = new ImageIcon("./img/onegame.png");
	ImageIcon TWOPERSON = new ImageIcon("./img/twogame.png");
	Dimension DIM = new Dimension(170, 80);// 设置登录按钮组件的大小
	Dimension CHESSBOARD = new Dimension(MARGIN * 2 + GRID_SPAN * (COLS - 1), MARGIN * 2 + GRID_SPAN * (ROWS - 1));
}
