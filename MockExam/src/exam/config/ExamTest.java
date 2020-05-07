package exam.config;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.sun.rowset.CachedRowSetImpl;

import mybean.data.Test;

public class ExamTest implements ExamConfig {

	Test quicktest = null; // ��ȡtest���󣬴洢���������������
	Test mocktest = null; // ��ȡtest���󣬴洢���濼�Ե���������
	int testAmount; // �����ȡ������
	ArrayList<Integer> testID; // ���(��ȡ��ĿʱServlet������)
	ArrayList<String> testAnswer= new ArrayList<String>(); // ��ȷ��
	ArrayList<String> answerList; // ѧ����EndTest

	/**
	 * ������������������
	 * @param request
	 * @param response
	 * @param rowSet
	 * @throws SQLException
	 */
	public void executeQuickExamTest(HttpServletRequest request, HttpServletResponse response, CachedRowSetImpl rowSet)
			throws SQLException {
		HttpSession session = request.getSession(true);
		try {
			quicktest = (Test) session.getAttribute("quicktest");
			if (quicktest == null) {
				quicktest = new Test();
				session.setAttribute("quicktest", quicktest);
			}
		} catch (Exception e) {
			quicktest = new Test();
			session.setAttribute("quicktest", quicktest);
		}
		/* �ϴε��Ծ�δ�ύ���������� */
		testID = quicktest.getTestID(); 
		if (testID == null || testID.size() == 0) {
			
			testID = new ArrayList<Integer>();
			rowSet.last();// ȷ����ȡ������
			int recordAmount = rowSet.getRow();
			testAmount = TESTAMOUNT_QUICK; // �����ȡ������
			testAmount = Math.min(recordAmount, testAmount);
			System.out.println("#���³���: " + testAmount);//

			LinkedList<Integer> list = new LinkedList<Integer>();// �ݴ������������
			for (int i = 1; i <= recordAmount; i++) {
				list.add(i);
			}

			int randomNum; // �����
			int qid; // ����ȡ�����
			for (int number = 1; number <= testAmount; number++) {// �������
				
				randomNum = (int) (Math.random() * list.size());
				qid = list.get(randomNum);
				list.remove(randomNum);
				testID.add(qid);// ��¼��ȡ�����
			}	
			quicktest.setTestID(testID);
			/* ����ʱ������� */
			System.out.println(testID);
		} else {
			/* ����ʱ������� */
			System.out.println(quicktest.getTestID()); //
		}
	}
	/**
	 * ������ɷ��濼�Ե�����
	 * @param request
	 * @param response
	 * @param rowSet
	 * @throws SQLException
	 */
	public void executeMockExamTest(HttpServletRequest request, HttpServletResponse response, CachedRowSetImpl rowSet)
			throws SQLException {
		HttpSession session = request.getSession(true);
		try {
			mocktest = (Test) session.getAttribute("mocktest");
			if (mocktest == null) {
				mocktest = new Test();
				session.setAttribute("mocktest", mocktest);
			}
		} catch (Exception e) {
			mocktest = new Test();
			session.setAttribute("mocktest", mocktest);
		}
		/* �ϴε��Ծ�δ�ύ���������� */
		testID = mocktest.getTestID(); 
		if (testID == null || testID.size() == 0) {
			
			testID = new ArrayList<Integer>();
			rowSet.last();// ȷ����ȡ������
			int recordAmount = rowSet.getRow();
			testAmount = TESTAMOUNT_MOCK; // �����ȡ������
			testAmount = Math.min(recordAmount, testAmount);
			System.out.println("#���³���: " + testAmount);//

			LinkedList<Integer> list = new LinkedList<Integer>();// �ݴ������������
			for (int i = 1; i <= recordAmount; i++) {
				list.add(i);
			}

			int randomNum; // �������
			int qid; // ����ȡ�����
			for (int number = 1; number <= testAmount; number++) {// �������
				
				randomNum = (int) (Math.random() * list.size());
				qid = list.get(randomNum);
				list.remove(randomNum);
				testID.add(qid);// ��¼��ȡ�����
			}	
			mocktest.setTestID(testID);
			/* ��¼��ʼʱ��,���ڷ��濼�Եļ�ʱ�� */
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = new Date();
			mocktest.setStartTime(df.format(startTime));
			session.setAttribute("startTime", startTime);
			/* ����ʱ������� */
			System.out.println(testID);
		} else {
			/* ����ʱ������� */
			System.out.println(mocktest.getTestID()); //
		}
	}
	/**
	 * ��������������
	 * @param number
	 * @param testID
	 * @param rowSet
	 * @return
	 */
	public StringBuffer show(int number, int testID, CachedRowSetImpl rowSet, String basePath) {
		StringBuffer str = new StringBuffer();
		try {
			rowSet.absolute(testID);
			str.append("<div class='quest'>");
			str.append("<p>"+ number +". "+ rowSet.getString(2) + "</p>");
			str.append("<input type='radio' name=qid" + testID + " value='A'>A." + rowSet.getString(3) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='B'>B." + rowSet.getString(4) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='C'>C." + rowSet.getString(5) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='D'>D." + rowSet.getString(6) + "<br>");
			if(!rowSet.getString(8).equals(" ")) { // ��Ϊ�ո���ζ����ͼƬ������img��ǩ
				str.append("<img src='" + basePath + rowSet.getString(8) +"'><br>");
			}
			str.append("</div>");
			if (testAnswer.size() < testAmount) { // ��¼��ȷ��
				testAnswer.add(rowSet.getString(7)); 
				quicktest.setTestAnswer(testAnswer);
			}
		} catch (SQLException e) {
		}
		return str;
	}
	/**
	 * ������濼�Ե�����
	 * @param number
	 * @param testID
	 * @param rowSet
	 * @return
	 */
	public StringBuffer showMock(int number, int testID, CachedRowSetImpl rowSet, String basePath) {
		StringBuffer str = new StringBuffer();
		try {
			rowSet.absolute(testID);
			str.append("<div class='quest'>");
			str.append("<p>"+ number +". "+ rowSet.getString(2) + "</p>");
			str.append("<input type='radio' name=qid" + testID + " value='A'>A." + rowSet.getString(3) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='B'>B." + rowSet.getString(4) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='C'>C." + rowSet.getString(5) + "<br>");
			str.append("<input type='radio' name=qid" + testID + " value='D'>D." + rowSet.getString(6) + "<br>");
			if(!rowSet.getString(8).equals(" ")) { // ��Ϊ�ո���ζ����ͼƬ������img��ǩ
				str.append("<img src='" + basePath + rowSet.getString(8) +"'><br>");
			}
			str.append("</div>");
			if (testAnswer.size() < testAmount) { // ��¼��ȷ��
				testAnswer.add(rowSet.getString(7)); 
				mocktest.setTestAnswer(testAnswer);
			}
		} catch (SQLException e) {
		}
		return str;
	}
	/**
	 * �����Ծ�÷�
	 * @param request
	 * @param response
	 * @param test
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public int executeExamScore (HttpServletRequest request, HttpServletResponse response, Test test)
			throws ServletException, IOException {
		int score = 0; // ��������
		testID = test.getTestID();
		testAnswer = test.getTestAnswer();// ��ȷ��
		answerList = new ArrayList<String>(); // ������
		for(Integer i: testID) { // ��ȡ�����ύ�Ĵ�			
			String answer = request.getParameter("qid"+i);
			answerList.add(answer);
		}	
		System.out.println("answer:"+answerList.size());
		for(int i=0; i<testAnswer.size(); i++) { // �Աȿ����𰸺���ȷ�𰸣������ֵ
			if(testAnswer.get(i).equals(answerList.get(i))) {
				score += (100/testAnswer.size());
			}
		}
		return score;
	}
	/**
	 * �����ɼ��ȼ�������
	 * @param score
	 * @return
	 */
	public String formatMockScore(int score) {
		String grade = "";
		if(score >= 90) grade="ͨ��";
		else grade="��ͨ��";
		return grade;
	}
	/**
	 * �����ɼ��ȼ������
	 * @param score
	 * @return
	 */
	public String formatQuickScore(float score) {
		String grade = "";
		if(score == 100) grade="A+";
		else if (score >= 90 ) grade="A";
		else if (score >= 70) grade="B";
		else if (score >= 60) grade="C";
		else if (score < 60) grade="D";
		return grade;
	}
	/**
	 * ����ˮƽ������ƽ����
	 * @param list
	 * @return
	 */
	public float scoreAvg(ArrayList<String> list) {
		float avg = 0;
		float sum = list.size();
		for(String score: list) {
			int s = Integer.parseInt(score);
			avg += Math.round(s/sum);
			System.out.println(avg);
		}
		System.out.println(avg);
		return avg;
	}
}
