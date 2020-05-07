package com.gobang;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessBoard extends JPanel implements ChessConfig {
	boolean isBlack = true;// 默认开始是黑棋先下
	boolean gameOver = true;// 游戏是否结束
	int xIndex, yIndex;// 当前刚下棋子的索引
	int chessCount = 0;// 当前棋盘的棋子个数
	int number = 1;// 棋子序号
	int maxTime = MAX;// 下棋期限
	static javax.swing.Timer time;// 计时器
	public static int MODEL;// 切换单人模式及双人模式

	ComputerGo com = new ComputerGo();

	public ChessBoard() {
		ChessListener cl = new ChessListener();
		addMouseListener(cl);
		addMouseMotionListener(cl);

		time = new Timer(1000, cl);
	}

	/* 绘制 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);// 画棋盘
		/* 画棋盘 */
		g.drawImage(BACKGROUND, 0, 0, this.getWidth(), this.getHeight(), this);

		for (int i = 0; i < ROWS; i++) {// 画横线
			g.drawString("" + (i + 1), MARGIN - 25, MARGIN + i * GRID_SPAN);// 棋盘列号-数字
			g.drawLine(MARGIN, MARGIN + i * GRID_SPAN, MARGIN + (COLS - 1) * GRID_SPAN, MARGIN + i * GRID_SPAN);
		}
		for (int i = 0; i < COLS; i++) {// 画直线
			g.drawString("" + (char) (i + 65), MARGIN + i * GRID_SPAN, MARGIN - 20);// 棋盘行号-英文字母
			g.drawLine(MARGIN + i * GRID_SPAN, MARGIN, MARGIN + i * GRID_SPAN, MARGIN + (ROWS - 1) * GRID_SPAN);
		}
		g.fillOval(MARGIN + 3 * GRID_SPAN - 3, MARGIN + 3 * GRID_SPAN - 3, 6, 6);// 左上星
		g.fillOval(MARGIN + 11 * GRID_SPAN - 3, MARGIN + 3 * GRID_SPAN - 3, 6, 6);// 右上星
		g.fillOval(MARGIN + 3 * GRID_SPAN - 3, MARGIN + 11 * GRID_SPAN - 3, 6, 6);// 左下星
		g.fillOval(MARGIN + 11 * GRID_SPAN - 3, MARGIN + 11 * GRID_SPAN - 3, 6, 6);// 右下星
		g.fillOval(MARGIN + 7 * GRID_SPAN - 3, MARGIN + 7 * GRID_SPAN - 3, 6, 6);// 天元

		/* 画棋子 */
		for (int i = 0; i < chessCount; i++) {
			int xPos = (int) (CHESSLIST[i].getPosX() * GRID_SPAN + MARGIN);// 网格交叉的x坐标
			int yPos = (int) (CHESSLIST[i].getPosY() * GRID_SPAN + MARGIN);// 网格交叉的y坐标
			// 在棋子上标记序号
			String num = "" + CHESSLIST[i].getnum();// 将数字转化为字符串
			g.setColor(new Color(236, 117, 0));
			Font f = new Font("Times New Roman", Font.PLAIN, 12);
			FontMetrics fm = getFontMetrics(f);// 获取当前字体的字体规格
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
				g.setColor(Color.red);// 标记最后一个棋子为红色
				g.drawRect(xPos - DIAMETER / 2, yPos - DIAMETER / 2, DIAMETER, DIAMETER);
			}
		}

	}

	// Dimension:矩形
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

	/* 判断哪方赢棋 */
	private boolean isWin(int xIndex, int yIndex, Color c) {

		// 判断水平方向是否连续5颗
		int continueCount = 1;// 连续棋子的个数
		for (int x = xIndex - 1; x >= 0; x--) {// 横向向左寻找
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1; x <= ROWS; x++) {// 横向向右寻找
			if (getChess(x, yIndex, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 判断记录数大于等于五，即表示此方获胜
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// 判断垂直方向是否连续5颗
		for (int y = yIndex - 1; y >= 0; y--) {// 纵向向上寻找
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int y = yIndex + 1; y <= ROWS; y++) {// 纵向向下寻找
			if (getChess(xIndex, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 判断记录数大于等于五，即表示此方获胜
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// 判断左上右下方向是否连续5颗
		for (int x = xIndex - 1, y = yIndex + 1; y <= ROWS && x >= 0; x--, y++) {// 左上寻找
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1, y = yIndex - 1; y >= 0 && x <= COLS; x++, y--) {// 右下寻找
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 判断记录数大于等于五，即表示此方获胜
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		// 判断左下右上方向是否连续5颗
		for (int x = xIndex - 1, y = yIndex - 1; y >= 0 && x >= 0; x--, y--) {// 左下寻找
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		for (int x = xIndex + 1, y = yIndex + 1; y <= ROWS && x <= COLS; x++, y++) {// 右上寻找
			if (getChess(x, y, c) != null) {
				continueCount++;
			} else
				break;
		}
		// 判断记录数大于等于五，即表示此方获胜
		if (continueCount >= 5) {
			return true;
		} else
			continueCount = 1;

		return false;
	}

	/* 新游戏 */
	public void restartGame() {// 清除棋子
		int op = JOptionPane.showConfirmDialog(this, "是否开始新游戏？", "提示", JOptionPane.OK_CANCEL_OPTION);
		if (op != JOptionPane.OK_CANCEL_OPTION) {
			maxTime = MAX;
			time.stop();
			for (int i = 0; i < CHESSLIST.length; i++)
				CHESSLIST[i] = null;
			/* 恢复游戏相关的变量值 */
			isBlack = true;
			ChessJFrame.usergo.setText("黑方执棋");
			ChessJFrame.usergo.setIcon(BICO);
			gameOver = false;// 游戏是否结束
			chessCount = 0;// 当前棋盘的棋子个数
			number = 1;// 棋子序号刷新
			time.start();

			if (MODEL == 1) {// 数字棋盘归零
				com.clearBoard();
			}

			repaint();
		}
	}

	/* 悔棋 */
	public void goback() {
		if (gameOver == true) {
			return;
		} else if (chessCount == 0) {
			return;
		}

		/* 人机模式 */
		if (MODEL == 1) {
			BOARD[CHESSLIST[chessCount - 1].getPosY()][CHESSLIST[chessCount - 1].getPosX()] = 0;
			BOARD[CHESSLIST[chessCount - 2].getPosY()][CHESSLIST[chessCount - 2].getPosX()] = 0;
			CHESSLIST[chessCount - 1] = null;
			CHESSLIST[chessCount - 2] = null;
			chessCount -= 2;
			number -= 2;
		}

		/* 人人模式 */
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

	/* 认输 */
	public void giveup() {
		if (gameOver == true) {
			return;
		} else if (chessCount == 0) {
			JOptionPane.showMessageDialog(this, "棋盘未落子，不能认输!");
			return;
		}
		int op = JOptionPane.showConfirmDialog(this, "是否认输？", "提示", JOptionPane.OK_CANCEL_OPTION);
		if (op != JOptionPane.OK_CANCEL_OPTION) {
			gameOver = true;
			time.stop();
			String colorName = isBlack ? "黑棋" : "白棋";
			String msg = String.format("%s认输，游戏结束。", colorName);
			JOptionPane.showMessageDialog(this, msg);
		}
	}

	/*
	 * ChessListener类
	 */
	private class ChessListener implements MouseListener, MouseMotionListener, ActionListener {

		ComputerGo computer = new ComputerGo();

		/* MouseListener事件 */
		@Override
		public void mousePressed(MouseEvent e) {// 鼠标按键在组件上按下时调用
			if (gameOver)// 游戏已经结束，不能下
				return;

			xIndex = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			yIndex = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;// 将鼠标单击的坐标位置转化为网格索引

			if (xIndex < 0 || xIndex > ROWS || yIndex < 0 || yIndex > COLS)// 棋子落在棋盘外，不能下
				return;
			if (findChess(xIndex, yIndex))// x,y位置已经有棋子存在，不能下
				return;

			Color color = isBlack ? Color.black : Color.white;
			ChessMan ch = new ChessMan(xIndex, yIndex, color, number++);
			CHESSLIST[chessCount++] = ch;// 添加棋子

			time.stop();
			maxTime = MAX;
			time.start();// 计时器重置

			repaint();// 通知系统重新绘制

			if (isWin(xIndex, yIndex, color)) {// 判断是否赢棋
				time.stop();
				gameOver = true;
				String colorName = isBlack ? "黑棋" : "白棋";
				String msg = String.format("恭喜，%s胜出！", colorName);
				JOptionPane.showMessageDialog(null, msg);
				return;

			} else if (chessCount == (COLS * ROWS)) {
				time.stop();
				gameOver = true;
				JOptionPane.showMessageDialog(null, "和棋，棋鼓相当。");
			}

			if (isBlack) {// 轮到哪方执棋
				ChessJFrame.usergo.setText("白方执棋");
				ChessJFrame.usergo.setIcon(WICO);
			} else {
				ChessJFrame.usergo.setText("黑方执棋");
				ChessJFrame.usergo.setIcon(BICO);
			}

			/* 人机模式 */
			if (MODEL == 1) {
				if (gameOver)// 游戏已经结束，不能下
					return;
				isBlack = !isBlack;// 电脑为白方

				computer.setChess(ch, 1);
				computer.action();

				ch = new ChessMan(computer.posX, computer.posY, Color.white, number++);
				CHESSLIST[chessCount++] = ch;// 添加棋子
				computer.setChess(ch, 2);
				repaint();

				if (isWin(computer.posX, computer.posY, Color.white)) {// 判断是否赢棋
					time.stop();
					gameOver = true;
					String msg = String.format("恭喜，白棋胜出！");
					JOptionPane.showMessageDialog(null, msg);

				} else if (chessCount == (COLS * ROWS)) {
					time.stop();
					gameOver = true;
					JOptionPane.showMessageDialog(null, "和棋，棋鼓相当。");
				}

				if (isBlack) {// 轮到哪方执棋
					ChessJFrame.usergo.setText("白方执棋");
					ChessJFrame.usergo.setIcon(WICO);
				} else {
					ChessJFrame.usergo.setText("黑方执棋");
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

		/* MouseMotionListener事件 */
		@Override
		public void mouseMoved(MouseEvent e) {
			int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;
			int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;// 将鼠标单击的坐标位置转化为网格索引
			if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameOver || findChess(x1, y1)) {// 游戏已经结束，不能下；落在棋盘外，不能下；x，y位置已经有棋子存在，不能下
				setCursor(new Cursor(Cursor.DEFAULT_CURSOR));// 设置成默认形状
			} else {
				setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置成手型
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {
		}

		/* ActionListener事件 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == time) {// 处理计时器事件
				ChessJFrame.showTime.setText("    剩：" + maxTime + "秒");
				maxTime--;
				if (maxTime <= 0) {
					maxTime = MAX;
					if (isBlack) {// 轮到哪方执棋
						time.stop();
						gameOver = true;
						JOptionPane.showMessageDialog(null, "时间结束，白棋胜出!");
					} else {
						time.stop();
						gameOver = true;
						JOptionPane.showMessageDialog(null, "时间结束，黑棋胜出!");
					}
				}
			}
		}
	}
}
