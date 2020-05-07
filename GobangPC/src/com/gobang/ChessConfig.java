package com.gobang;

import java.awt.*;
import javax.swing.*;

public interface ChessConfig {
	int MARGIN = 50;// ���̱߾�
	int GRID_SPAN = 40;// ������
	int ROWS = 15;// ��������
	int COLS = 15;// ��������
	int DIAMETER = 30;// ����ֱ��
	int MAX = 30;//��������30��
	int WIN_COUNT = 5;// Ӯ����������5ö����
	int LGWIDTH = 811;
	int LGHIGHTH = 574;//������ֵΪģʽѡ�����Ĵ�С
	ChessMan[] CHESSLIST = new ChessMan[ROWS * COLS];// ��ʼ��ÿ������Ԫ��Ϊnull
	int[][] BOARD = new int[15][15];
	Image LOGINBG = new ImageIcon("./img/login_background.png").getImage();
	Image BACKGROUND = new ImageIcon("./img/background.jpg").getImage();// ���̱���
	Image BIMG = new ImageIcon("./img/chess_b.png").getImage();// ����
	Image WIMG = new ImageIcon("./img/chess_w.png").getImage();// ����
	ImageIcon BICO = new ImageIcon("./img/chess_b.png");// ����
	ImageIcon WICO = new ImageIcon("./img/chess_w.png");// ����
	ImageIcon GAMEOVER = new ImageIcon("./img/gameover.png");
	ImageIcon ONEPERSON = new ImageIcon("./img/onegame.png");
	ImageIcon TWOPERSON = new ImageIcon("./img/twogame.png");
	Dimension DIM = new Dimension(170, 80);// ���õ�¼��ť����Ĵ�С
	Dimension CHESSBOARD = new Dimension(MARGIN * 2 + GRID_SPAN * (COLS - 1), MARGIN * 2 + GRID_SPAN * (ROWS - 1));
}
