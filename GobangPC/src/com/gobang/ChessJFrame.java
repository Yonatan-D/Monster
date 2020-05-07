package com.gobang;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessJFrame extends JFrame {
	private ChessBoard chessBoard;// 棋盘面板
	private JPanel banner;// 顶端容器
	private JPanel bottom;// 底端容器
	private JPanel option;// 游戏选项容器
	private JButton startBtn;// 开始按钮
	private JButton backBtn;// 悔棋按钮
	private JButton exitBtn;// 退出按钮
	private JButton menuBtn;// 主菜单按钮
	public static JLabel usergo;// 执棋方
	public static JLabel showTime;// 计时显示

	public ChessJFrame() {
		/* 主窗口 */
		setTitle("五子棋单机版");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* 棋盘面板 */
		chessBoard = new ChessBoard();

		/* 顶端容器（回合状态） */
		banner = new JPanel();
		banner.setBorder(BorderFactory.createEtchedBorder());
		usergo = new JLabel("点击“新游戏”开始");
		usergo.setFont(new Font("宋体", Font.PLAIN, 18));
		showTime = new JLabel("");
		showTime.setFont(new Font("宋体", Font.PLAIN, 14));
		banner.add(usergo);
		banner.add(showTime);// 添加标签至面板

		/* 底端容器（选项工具栏） */
		bottom = new JPanel(new BorderLayout());
		bottom.setBorder(BorderFactory.createEtchedBorder());
		option = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		menuBtn = new JButton("主菜单");
		startBtn = new JButton("新游戏");
		backBtn = new JButton("悔棋");
		exitBtn = new JButton("认输");// 按钮初始化
		menuBtn.setMargin(new Insets(0, 20, 0, 20));
		menuBtn.setFocusPainted(false);
		startBtn.setFocusPainted(false);
		backBtn.setFocusPainted(false);
		exitBtn.setFocusPainted(false);// 不绘制焦点状态
		ButtonListener lis = new ButtonListener(this);// 实例化按钮事件监听器内部类
		menuBtn.addActionListener(lis);
		startBtn.addActionListener(lis);
		backBtn.addActionListener(lis);
		exitBtn.addActionListener(lis);// 添加按钮监听事件
		option.add(startBtn);
		option.add(backBtn);
		option.add(exitBtn);
		bottom.add(option, BorderLayout.EAST);
		bottom.add(menuBtn, BorderLayout.WEST);// 添加按钮至面板

		// 添加面板至主窗口各区域
		add(chessBoard, BorderLayout.CENTER);
		add(banner, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		pack();// 自适应大小
		setLocationRelativeTo(null);// 窗口居中
		setVisible(true);// 设置窗口可见
	}

	private class ButtonListener implements ActionListener {

		private ChessJFrame chessjframe;

		public ButtonListener(ChessJFrame chessjframe) {// 获取游戏窗口
			this.chessjframe = chessjframe;
		}

		public void actionPerformed(ActionEvent e) {
			Object obj = e.getSource();
			if (obj == startBtn) {
				chessBoard.restartGame();
			} else if (obj == backBtn) {
				chessBoard.goback();
			} else if (obj == exitBtn) {
				chessBoard.giveup();
			} else if (obj == menuBtn) {
				ChessBoard.time.stop();
				int op = JOptionPane.showConfirmDialog(null, "返回主菜单将清除棋盘，是否返回？", "提示", JOptionPane.OK_CANCEL_OPTION);
				if (op != JOptionPane.OK_CANCEL_OPTION) {
					new LoginJFrame().Login();
					chessjframe.dispose();
				}else{
					ChessBoard.time.start();
				}
			}
		}
	}
}
