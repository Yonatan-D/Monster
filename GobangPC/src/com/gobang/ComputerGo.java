package com.gobang;

import java.util.HashMap;

/* �˻���ս�㷨 */
public class ComputerGo implements ChessConfig{
	private int[][] weightArray = new int [ROWS][COLS];// ����һ����ά���飬����������Ȩֵ
	public int posX , posY;

	public static HashMap<String, Integer> map = new HashMap<String, Integer>();// ���ò�ͬ�����������ӦȨֵ������
	static {

		// ����ס
		map.put("01", 17);// ��1��
		map.put("02", 12);// ��1��
		map.put("001", 17);// ��1��
		map.put("002", 12);// ��1��
		map.put("0001", 17);// ��1��
		map.put("0002", 12);// ��1��

		map.put("0102", 17);// ��1����15
		map.put("0201", 12);// ��1����10
		map.put("0012", 15);// ��1����15
		map.put("0021", 10);// ��1����10
		map.put("01002", 19);// ��1����15
		map.put("02001", 14);// ��1����10
		map.put("00102", 17);// ��1����15
		map.put("00201", 12);// ��1����10
		map.put("00012", 15);// ��1����15
		map.put("00021", 10);// ��1����10

		map.put("01000", 21);// ��1����15
		map.put("02000", 16);// ��1����10
		map.put("00100", 19);// ��1����15
		map.put("00200", 14);// ��1����10
		map.put("00010", 17);// ��1����15
		map.put("00020", 12);// ��1����10
		map.put("00001", 15);// ��1����15
		map.put("00002", 10);// ��1����10

		// ����ס
		map.put("0101", 65);// ��2����40
		map.put("0202", 60);// ��2����30
		map.put("0110", 65);// ��2����40
		map.put("0220", 60);// ��2����30
		map.put("011", 65);// ��2����40
		map.put("022", 60);// ��2����30
		map.put("0011", 65);// ��2����40
		map.put("0022", 60);// ��2����30

		map.put("01012", 65);// ��2����40
		map.put("02021", 60);// ��2����30
		map.put("01102", 65);// ��2����40
		map.put("02201", 60);// ��2����30
		map.put("00112", 65);// ��2����40
		map.put("00221", 60);// ��2����30

		map.put("01010", 75);// ��2����40
		map.put("02020", 70);// ��2����30
		map.put("01100", 75);// ��2����40
		map.put("02200", 70);// ��2����30
		map.put("00110", 75);// ��2����40
		map.put("00220", 70);// ��2����30
		map.put("00011", 75);// ��2����40
		map.put("00022", 70);// ��2����30

		// ����ס
		map.put("0111", 150);// ��3����100
		map.put("0222", 140);// ��3����80

		map.put("01112", 150);// ��3����100
		map.put("02221", 140);// ��3����80

		map.put("01101", 1000);// ��3����130
		map.put("02202", 800);// ��3����110
		map.put("01011", 1000);// ��3����130
		map.put("02022", 800);// ��3����110
		map.put("01110", 1000);// ��3��
		map.put("02220", 800);// ��3��

		map.put("01111", 3000);// 4����300
		map.put("02222", 3500);// 4����280
	}

	public Integer unionWeight(Integer a, Integer b) {
		// ����Ҫ���ж�a,b������ֵ�ǲ���null
		if ((a == null) || (b == null))
			return 0;
		// һһ
		else if ((a >= 10) && (a <= 25) && (b >= 10) && (b <= 25))
			return 60;
		// һ������һ
		else if (((a >= 10) && (a <= 25) && (b >= 60) && (b <= 80))
				|| ((a >= 60) && (a <= 80) && (b >= 10) && (b <= 25)))
			return 800;
		// һ������һ������
		else if (((a >= 10) && (a <= 25) && (b >= 140) && (b <= 1000))
				|| ((a >= 140) && (a <= 1000) && (b >= 10) && (b <= 25))
				|| ((a >= 60) && (a <= 80) && (b >= 60) && (b <= 80)))
			return 3000;
		// ����������
		else if (((a >= 60) && (a <= 80) && (b >= 140) && (b <= 1000))
				|| ((a >= 140) && (a <= 1000) && (b >= 60) && (b <= 80)))
			return 3000;
		else
			return 0;
	}

	/**
	 * �������
	 */
	public void clearBoard(){
		for(int i = 0; i < COLS; i++){
			for(int j = 0; j < ROWS; j++)
				BOARD[i][j]=0;
		}
	}
	
	/**
	 * �������ӵ�λ��
	 * @param c ���ӣ��������壩
	 * @param chess ��1��2��
	 */
	public void setChess(ChessMan c, int chess) {
		posX = c.getPosX();
		posY = c.getPosY();
		BOARD[posY][posX] = chess;
	}
	
	/**
	 * �����Ȩֵ
	 */
	public void action() {
		
		// ��������
		// �ȼ��������λ�õ�Ȩֵ
		for (int i = 0; i < BOARD.length; i++) {
			for (int j = 0; j < BOARD[i].length; j++) {
				// �����жϵ�ǰλ���Ƿ�Ϊ��
				if (BOARD[i][j] == 0) {
					// ��������
					String ConnectType = "0";
					int jmin = Math.max(0, j - 4);
					for (int positionj = j - 1; positionj >= jmin; positionj--) {
						// ���μ���ǰ�������
						ConnectType = ConnectType + BOARD[i][positionj];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
					Integer valueleft = map.get(ConnectType);
					if (valueleft != null)
						weightArray[i][j] += valueleft;

					// ��������
					ConnectType = "0";
					int jmax = Math.min(14, j + 4);
					for (int positionj = j + 1; positionj <= jmax; positionj++) {
						// ���μ���ǰ�������
						ConnectType = ConnectType + BOARD[i][positionj];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
					Integer valueright = map.get(ConnectType);
					if (valueright != null)
						weightArray[i][j] += valueright;

					// �����жϣ��ж���
					weightArray[i][j] += unionWeight(valueleft, valueright);

					// ��������
					ConnectType = "0";
					int imin = Math.max(0, i - 4);
					for (int positioni = i - 1; positioni >= imin; positioni--) {
						// ���μ���ǰ�������
						ConnectType = ConnectType + BOARD[positioni][j];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
					Integer valueup = map.get(ConnectType);
					if (valueup != null)
						weightArray[i][j] += valueup;

					// ��������
					ConnectType = "0";
					int imax = Math.min(14, i + 4);
					for (int positioni = i + 1; positioni <= imax; positioni++) {
						// ���μ���ǰ�������
						ConnectType = ConnectType + BOARD[positioni][j];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ����
					Integer valuedown = map.get(ConnectType);
					if (valuedown != null)
						weightArray[i][j] += valuedown;

					// �����жϣ��ж���
					weightArray[i][j] += unionWeight(valueup, valuedown);

					// �����Ϸ�����,i,j,����ȥ��ͬ����
					ConnectType = "0";
					for (int position = -1; position >= -4; position--) {
						if ((i + position >= 0) && (i + position <= 14) && (j + position >= 0) && (j + position <= 14))
							ConnectType = ConnectType + BOARD[i + position][j + position];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
					Integer valueLeftUp = map.get(ConnectType);
					if (valueLeftUp != null)
						weightArray[i][j] += valueLeftUp;

					// �����·�����,i,j,��������ͬ����
					ConnectType = "0";
					for (int position = 1; position <= 4; position++) {
						if ((i + position >= 0) && (i + position <= 14) && (j + position >= 0) && (j + position <= 14))
							ConnectType = ConnectType + BOARD[i + position][j + position];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
					Integer valueRightDown = map.get(ConnectType);
					if (valueRightDown != null)
						weightArray[i][j] += valueRightDown;

					// �����жϣ��ж���
					weightArray[i][j] += unionWeight(valueLeftUp, valueRightDown);

					// �����·�����,i��,j��
					ConnectType = "0";
					for (int position = 1; position <= 4; position++) {
						if ((i + position >= 0) && (i + position <= 14) && (j - position >= 0) && (j - position <= 14))
							ConnectType = ConnectType + BOARD[i + position][j - position];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
					Integer valueLeftDown = map.get(ConnectType);
					if (valueLeftDown != null)
						weightArray[i][j] += valueLeftDown;

					// �����Ϸ�����,i��,j��
					ConnectType = "0";
					for (int position = 1; position <= 4; position++) {
						if ((i - position >= 0) && (i - position <= 14) && (j + position >= 0) && (j + position <= 14))
							ConnectType = ConnectType + BOARD[i - position][j + position];
					}
					// ��������ȡ����Ӧ��Ȩֵ���ӵ�Ȩֵ����ĵ�ǰλ��
					Integer valueRightUp = map.get(ConnectType);
					if (valueRightUp != null)
						weightArray[i][j] += valueRightUp;

					// �����жϣ��ж���
					weightArray[i][j] += unionWeight(valueLeftDown, valueRightUp);
				}
			}
		}

		// ȡ������Ȩֵ

		int weightmax = 0;
		for (int i = 0; i < COLS; i++) {
			for (int j = 0; j < ROWS; j++) {
				if (weightmax < weightArray[i][j]) {
					weightmax = weightArray[i][j];
					posX = j;
					posY = i;
				}
			}
		}	
		
		// �����Ժ�����Ȩֵ����weightArray
		for (int i = 0; i < COLS; i++)
			for (int j = 0; j < ROWS; j++)
				weightArray[i][j] = 0;
	}
}
