package com.gobang;

import java.awt.Color;

public class ChessMan {

	private int posX, posY; // ����
	private Color chessColor; // ������ɫ
	private int number; // ���

	public ChessMan() {
	}

	public ChessMan(int posX, int posY, Color chessColor, int number) {
		this.posX = posX;
		this.posY = posY;
		this.chessColor = chessColor;
		this.number = number;
	}

	// ��ȡ������ɫ
	public Color getChessColor() {
		return chessColor;
	}

	// ��ȡ�����������е�x����ֵ
	public int getPosX() {
		return posX;
	}

	// ��ȡ�����������е�y����ֵ
	public int getPosY() {
		return posY;
	}

	// ��ȡ�������
	public int getnum() {
		return number;
	}

}
