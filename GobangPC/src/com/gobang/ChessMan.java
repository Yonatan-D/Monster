package com.gobang;

import java.awt.Color;

public class ChessMan {

	private int posX, posY; // 坐标
	private Color chessColor; // 棋子颜色
	private int number; // 序号

	public ChessMan() {
	}

	public ChessMan(int posX, int posY, Color chessColor, int number) {
		this.posX = posX;
		this.posY = posY;
		this.chessColor = chessColor;
		this.number = number;
	}

	// 获取棋子颜色
	public Color getChessColor() {
		return chessColor;
	}

	// 获取棋子在棋盘中的x索引值
	public int getPosX() {
		return posX;
	}

	// 获取棋子在棋盘中的y索引值
	public int getPosY() {
		return posY;
	}

	// 获取棋子序号
	public int getnum() {
		return number;
	}

}
