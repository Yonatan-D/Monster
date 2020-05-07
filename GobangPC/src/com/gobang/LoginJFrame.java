package com.gobang;

import java.awt.*;
import java.awt.event.*;
import java.util.Enumeration;

import javax.swing.*;
import javax.swing.plaf.FontUIResource;

public class LoginJFrame extends JFrame implements ChessConfig {

	private LoginBackground login;
	JLabel onegame, twogame, gameover;

	public void Login() {
		setTitle("五子棋单机版");
		setSize(LGWIDTH, LGHIGHTH);
		setLocationRelativeTo(null);
		setLayout(null);
		setCursor(new Cursor(Cursor.HAND_CURSOR));// 设置成手型
		setUndecorated(true);// 取消标题栏
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		login = new LoginBackground();

		onegame = new JLabel("单人模式", ONEPERSON, JLabel.CENTER);
		twogame = new JLabel("双人模式", TWOPERSON, JLabel.CENTER);
		gameover = new JLabel("退出游戏", GAMEOVER, JLabel.CENTER);
		onegame.setBounds(220, 210, DIM.width, DIM.height);
		twogame.setBounds(220, 280, DIM.width, DIM.height);
		gameover.setBounds(220, 350, DIM.width, DIM.height);
		onegame.addMouseListener(new ModelListener(this, "单人模式"));
		twogame.addMouseListener(new ModelListener(this, "双人模式"));
		gameover.addMouseListener(new ModelListener(this, "退出游戏"));

		add(onegame);
		add(twogame);
		add(gameover);

		login.setSize(this.getWidth(), this.getHeight());
		add(login);

		setVisible(true);
	}

	private class LoginBackground extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(LOGINBG, 0, 0, this.getWidth(), this.getHeight(), this);
		}
	}

	private class ModelListener implements MouseListener {
		private LoginJFrame loginjframe;
		String option;

		public ModelListener(LoginJFrame loginjframe, String option) {// 获取登陆窗口
			this.loginjframe = loginjframe;
			this.option = option;
		}

		@Override
		public void mouseClicked(MouseEvent e) {

			if ("退出游戏".equals(option)) {
				System.exit(0);
			} else if ("单人模式".equals(option)) {
				ChessBoard.MODEL = 1;
				new ChessJFrame();
				loginjframe.dispose();
			} else if ("双人模式".equals(option)) {
				ChessBoard.MODEL = 2;
				new ChessJFrame();
				loginjframe.dispose();
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {

		}

		@Override
		public void mouseExited(MouseEvent arg0) {

		}

		@Override
		public void mousePressed(MouseEvent arg0) {

		}

		@Override
		public void mouseReleased(MouseEvent arg0) {

		}

	}

	public static void main(String[] args) {
		//设置全局字体
		Font font = new Font("宋体", Font.PLAIN, 15);
		Enumeration keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements())
		{
			Object key = keys.nextElement();
			Object value = UIManager.get(key);
			if(value instanceof FontUIResource){
				UIManager.put(key, font);
			}
		}
		new LoginJFrame().Login();
	}
}
