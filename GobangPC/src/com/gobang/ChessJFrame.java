package com.gobang;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ChessJFrame extends JFrame {
	private ChessBoard chessBoard;// �������
	private JPanel banner;// ��������
	private JPanel bottom;// �׶�����
	private JPanel option;// ��Ϸѡ������
	private JButton startBtn;// ��ʼ��ť
	private JButton backBtn;// ���尴ť
	private JButton exitBtn;// �˳���ť
	private JButton menuBtn;// ���˵���ť
	public static JLabel usergo;// ִ�巽
	public static JLabel showTime;// ��ʱ��ʾ

	public ChessJFrame() {
		/* ������ */
		setTitle("�����嵥����");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/* ������� */
		chessBoard = new ChessBoard();

		/* �����������غ�״̬�� */
		banner = new JPanel();
		banner.setBorder(BorderFactory.createEtchedBorder());
		usergo = new JLabel("���������Ϸ����ʼ");
		usergo.setFont(new Font("����", Font.PLAIN, 18));
		showTime = new JLabel("");
		showTime.setFont(new Font("����", Font.PLAIN, 14));
		banner.add(usergo);
		banner.add(showTime);// ��ӱ�ǩ�����

		/* �׶�������ѡ������� */
		bottom = new JPanel(new BorderLayout());
		bottom.setBorder(BorderFactory.createEtchedBorder());
		option = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		menuBtn = new JButton("���˵�");
		startBtn = new JButton("����Ϸ");
		backBtn = new JButton("����");
		exitBtn = new JButton("����");// ��ť��ʼ��
		menuBtn.setMargin(new Insets(0, 20, 0, 20));
		menuBtn.setFocusPainted(false);
		startBtn.setFocusPainted(false);
		backBtn.setFocusPainted(false);
		exitBtn.setFocusPainted(false);// �����ƽ���״̬
		ButtonListener lis = new ButtonListener(this);// ʵ������ť�¼��������ڲ���
		menuBtn.addActionListener(lis);
		startBtn.addActionListener(lis);
		backBtn.addActionListener(lis);
		exitBtn.addActionListener(lis);// ��Ӱ�ť�����¼�
		option.add(startBtn);
		option.add(backBtn);
		option.add(exitBtn);
		bottom.add(option, BorderLayout.EAST);
		bottom.add(menuBtn, BorderLayout.WEST);// ��Ӱ�ť�����

		// �������������ڸ�����
		add(chessBoard, BorderLayout.CENTER);
		add(banner, BorderLayout.NORTH);
		add(bottom, BorderLayout.SOUTH);
		pack();// ����Ӧ��С
		setLocationRelativeTo(null);// ���ھ���
		setVisible(true);// ���ô��ڿɼ�
	}

	private class ButtonListener implements ActionListener {

		private ChessJFrame chessjframe;

		public ButtonListener(ChessJFrame chessjframe) {// ��ȡ��Ϸ����
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
				int op = JOptionPane.showConfirmDialog(null, "�������˵���������̣��Ƿ񷵻أ�", "��ʾ", JOptionPane.OK_CANCEL_OPTION);
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
