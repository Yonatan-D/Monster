package com.gobang;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessBoard extends JPanel implements ChessConfig {
	boolean isBlack = true;// Ĭ�Ͽ�ʼ�Ǻ�������
	boolean gameOver = true;// ��Ϸ�Ƿ����
	int xIndex, yIndex;// ��ǰ�������ӵ�����
	int chessCount = 0;// ��ǰ���̵����Ӹ���
	int number = 1;// �������
	int maxTime = MAX;// ��������
	static javax.swing.Timer time;// ��ʱ��
	public static int MODEL;// �л�����ģʽ��˫��ģʽ

	ComputerGo com = new ComputerGo();

	public ChessBoard() {
		ChessListener cl = new ChessListener();
		addMouseListener(cl);
		addMouseMotionListener(cl);

		time = new Timer(1000, cl);
	}

	/* ���� */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// ������
		/* ������ */
		g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), this);

		for (int i = 0; i < ROWS; i++) {// ������
			g.drawString("" + (i + 1), MARGIN - 25, MARGIN + i * GRID_SPAN);// �����к�-����
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + (COLS - 1) * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i < COLS; i++) {// ��ֱ��
			g.drawString("" + (char) (i + 65), MARGIN + i * GRID_SPAN, MARGIN - 20);// �����к�-Ӣ����ĸ
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + (ROWS - 1) * GRID_SPAN);
		}
		g.fillOval(MARGIN + 3 * GRID_SPAN - 3, MARGIN + 3 * GRID_SPAN - 3, 6, 6);// ������
		g.fillOval(MARGIN + 11 * GRID_SPAN - 3, MARGIN + 3 * GRID_SPAN - 3, 6, 6);// ������
		g.fillOval(MARGIN + 3 * GRID_SPAN - 3, MARGIN + 11 * GRID_SPAN - 3, 6, 6);// ������
		g.fillOval(MARGIN + 11 * GRID_SPAN - 3, MARGIN + 11 * GRID_SPAN - 3, 6, 6);// ������
		g.fillOval(MARGIN + 7 * GRID_SPAN - 3, MARGIN + 7 * GRID_SPAN - 3, 6, 6);// ��Ԫ

		/* ������ */
		for (int i = 0; i < chessCount; i++) {
			int xPos = (int) (CHESSLIST[i].getPosX() * GRID_SPAN + MARGIN);// ���񽻲��x����
			int yPos = (int) (CHESSLIST[i].getPosY() * GRID_SPAN + MARGIN);// ���񽻲��y����
			// �������ϱ�����
			String num = "" + CHESSLIST[i].getnum();// ������ת��Ϊ�ַ���
			g.setColor(new Color(236, 117, 0));
			Font f = new Font("Times New Roman", Font.PLAIN, 12);
			FontMetrics fm = getFontMetrics(f);// ��ȡ��ǰ�����������
			g.setFont(f);

			if (Color.black == CHESSLIST[i].getChessColor()) {
				g.drawImage(BIMG, xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER, null);
				g.setColor(Color.white);
				g.drawString(num, xPos - fm.stringWidth(num) / 2, yPos + 5);
			} else {
				g.drawImage(WIMG, xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER, null);
				g.setColor(Color.black);
				g.drawString(num, xPos - fm.stringWidth(num) / 2, yPos + 5);
			}

			if (i == chessCount - 1) {
				g.setColor(Color.red);// ������һ������Ϊ��ɫ
				g.drawRect(xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER);
			}
		}

	}

	// Dimension:����
	public Dimension getPreferredSize() {
		return CHESSBOARD;
	}

	private boolean findChess(int x, int y) {
		for (ChessMan c : CHESSLIST) {
			if (c != null && c.getPosX() == x && c.getPosY() == y)
				return true;
		}
		return false;
	}

	private ChessMan getChess(int xIndex, int yIndex, Color color) {
		for (ChessMan c : CHESSLIST) {
			if (c != null && c.getPosX() == xIndex && c.getPosY() == yIndex && c.getChessColor() == color)
				return c;
		}
		return null;
	}

	/* �ж��ķ�Ӯ�� */
	private boolean isWin(int xIndex, int yIndex, Color c) {

		// �ж�ˮƽ�����Ƿ�����5��
		int continueCount = 1;// �������ӵĸ���
		for (int x = xIndex - 1; x >= 0; x--) {// ��������Ѱ��
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1; x <= ROWS; x++) {// ��������Ѱ��
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// �жϼ�¼�����ڵ����壬����ʾ�˷���ʤ
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// �жϴ�ֱ�����Ƿ�����5��
		for (int y = yIndex - 1; y >= 0; y--) {// ��������Ѱ��
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int y = yIndex + 1; y <= ROWS; y++) {// ��������Ѱ��
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// �жϼ�¼�����ڵ����壬����ʾ�˷���ʤ
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// �ж��������·����Ƿ�����5��
		for (int x = xIndex - 1, y = yIndex + 1; y <= ROWS && x >= 0; x--, y++) {// ����Ѱ��
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {// ����Ѱ��
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// �жϼ�¼�����ڵ����壬����ʾ�˷���ʤ
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// �ж��������Ϸ����Ƿ�����5��
		for (int x = xIndex - 1, y = yIndex - 1; y >= 0 && x >= 0; x--, y--) {// ����Ѱ��
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1, y = yIndex + 1; y <= ROWS && x <= COLS; x++, y++) {// ����Ѱ��
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// �жϼ�¼�����ڵ����壬����ʾ�˷���ʤ
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		return false;
	}

	/* ����Ϸ */
	public void restartGame() {// �������
		int op = JOptionPane.showConfirmDialog(this, "�Ƿ�ʼ����Ϸ��", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
		if (op != JOptionPane.OK_CANCEL_OPTION) {
			maxTime = MAX;
			time.stop();
			for (int i = 0; i < CHESSLIST.length; i++)
				CHESSLIST[i] = null;
			/* �ָ���Ϸ��صı���ֵ */
			isBlack = true;
			ChessJFrame.usergo.setText("�ڷ�ִ��");
			ChessJFrame.usergo.setIcon(BICO);
			gameOver = false;// ��Ϸ�Ƿ����
			chessCount = 0;// ��ǰ���̵����Ӹ���
			number = 1;// �������ˢ��
			time.start();

			if (MODEL == 1) {// �������̹���
				com.clearBoard();
			}

			repaint();
		}
	}

	/* ���� */
	public void goback() {
		if (gameOver == true) {
			return;
		} else if (chessCount == 0) {
			return;
		}

		/* �˻�ģʽ */
		if (MODEL == 1) {
			BOARD[CHESSLIST[chessCount - 1].getPosY()][CHESSLIST[chessCount - 1].getPosX()] = 0;
			BOARD[CHESSLIST[chessCount - 2].getPosY()][CHESSLIST[chessCount - 2].getPosX()] = 0;
			CHESSLIST[chessCount - 1] = null;
			CHESSLIST[chessCount - 2] = null;
			chessCount -= 2;
			number -= 2;
		}

		/* ����ģʽ */
		if (MODEL == 2) {
			CHESSLIST[chessCount - 1] = null;
			chessCount--;
			number--;

			if (chessCount > 0) {
				xIndex = CHESSLIST[chessCount - 1].getPosX();
				yIndex = CHESSLIST[chessCount - 1].getPosY();
			}
			isBlack = !isBlack;
		}

		repaint();
	}

	/* ���� */
	public void giveup() {
		if (gameOver == true) {
			return;
		} else if (chessCount == 0) {
			JOptionPane.showMessageDialog(this, "����δ���ӣ���������!");
			return;
		}
		int op = JOptionPane.showConfirmDialog(this, "�Ƿ����䣿", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
		if (op != JOptionPane.OK_CANCEL_OPTION) {
			gameOver = true;
			time.stop();
			String colorName = isBlack ? "����" : "����";
			String msg = String.format("%s���䣬��Ϸ������", colorName);
			JOptionPane.showMessageDialog(this, msg);
		}
	}

	/*
	 * ChessListener��
	 */
	private class ChessListener implements MouseListener, MouseMotionListener, ActionListener {

		ComputerGo computer = new ComputerGo();

		/* MouseListener�¼� */
		@Override
		public void mousePressed(MouseEvent e) {// ��갴��������ϰ���ʱ����
			if (gameOver)// ��Ϸ�Ѿ�������������
				return;

			xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;// ����굥��������λ��ת��Ϊ��������

			if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)// �������������⣬������
				return;
			if (findChess(xIndex, yIndex))// x,yλ���Ѿ������Ӵ��ڣ�������
				return;

			Color color = isBlack ? Color.black : Color.white;
			ChessMan ch = new ChessMan(xIndex, yIndex, color, number++);
			CHESSLIST[chessCount++] = ch;// �������

			time.stop();
			maxTime = MAX;
			time.start();// ��ʱ������

			repaint();// ֪ͨϵͳ���»���

			if (isWin(xIndex, yIndex, color)) {// �ж��Ƿ�Ӯ��
				time.stop();
				gameOver = true;
				String colorName = isBlack ? "����" : "����";
				String msg = String.format("��ϲ��%sʤ����", colorName);
				JOptionPane.showMessageDialog(null, msg);
				return;

			} else if (chessCount == (COLS * ROWS)) {
				time.stop();
				gameOver = true;
				JOptionPane.showMessageDialog(null, "���壬����൱��");
			}

			if (isBlack) {// �ֵ��ķ�ִ��
				ChessJFrame.usergo.setText("�׷�ִ��");
				ChessJFrame.usergo.setIcon(WICO);
			} else {
				ChessJFrame.usergo.setText("�ڷ�ִ��");
				ChessJFrame.usergo.setIcon(BICO);
			}

			/* �˻�ģʽ */
			if (MODEL == 1) {
				if (gameOver)// ��Ϸ�Ѿ�������������
					return;
				isBlack = !isBlack;// ����Ϊ�׷�

				computer.setChess(ch, 1);
				computer.action();

				ch = new ChessMan(computer.posX, computer.posY, Color.white, number++);
				CHESSLIST[chessCount++] = ch;// �������
				computer.setChess(ch, 2);
				repaint();

				if (isWin(computer.posX, computer.posY, Color.white)) {// �ж��Ƿ�Ӯ��
					time.stop();
					gameOver = true;
					String msg = String.format("��ϲ������ʤ����");
					JOptionPane.showMessageDialog(null, msg);

				} else if (chessCount == (COLS * ROWS)) {
					time.stop();
					gameOver = true;
					JOptionPane.showMessageDialog(null, "���壬����൱��");
				}

				if (isBlack) {// �ֵ��ķ�ִ��
					ChessJFrame.usergo.setText("�׷�ִ��");
					ChessJFrame.usergo.setIcon(WICO);
				} else {
					ChessJFrame.usergo.setText("�ڷ�ִ��");
					ChessJFrame.usergo.setIcon(BICO);
				}
			}

			isBlack = !isBlack;

		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}

		/* MouseMotionListener�¼� */
		@Override
		public void mouseMoved(MouseEvent e) {
			int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;// ����굥��������λ��ת��Ϊ��������
			if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver || findChess(x1, y1)) {// ��Ϸ�Ѿ������������£����������⣬�����£�x��yλ���Ѿ������Ӵ��ڣ�������
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// ���ó�Ĭ����״
			} else {
				setCursor(new Cursor(Cursor.HAND_CURSOR));// ���ó�����
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		/* ActionListener�¼� */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == time) {// �����ʱ���¼�
				ChessJFrame.showTime.setText("    ʣ��" + maxTime + "��");
				maxTime--;
				if (maxTime <= 0) {
					maxTime = MAX;
					if (isBlack) {// �ֵ��ķ�ִ��
						time.stop();
						gameOver = true;
						JOptionPane.showMessageDialog(null, "ʱ�����������ʤ��!");
					} else {
						time.stop();
						gameOver = true;
						JOptionPane.showMessageDialog(null, "ʱ�����������ʤ��!");
					}
				}
			}
		}
	}
}
