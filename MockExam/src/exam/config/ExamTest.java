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

	Test quicktest = null; // 获取test对象，存储五题快测的试题内容
	Test mocktest = null; // 获取test对象，存储仿真考试的试题内容
	int testAmount; // 随机抽取的题数
	ArrayList<Integer> testID; // 题号(抽取题目时Servlet内排序)
	ArrayList<String> testAnswer= new ArrayList<String>(); // 正确答案
	ArrayList<String> answerList; // 学生答案EndTest

	/**
	 * 随机生成五题快测的试题
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
		/* 上次的试卷未提交则不重新生成 */
		testID = quicktest.getTestID(); 
		if (testID == null || testID.size() == 0) {
			
			testID = new ArrayList<Integer>();
			rowSet.last();// 确定抽取的题数
			int recordAmount = rowSet.getRow();
			testAmount = TESTAMOUNT_QUICK; // 随机抽取的题数
			testAmount = Math.min(recordAmount, testAmount);
			System.out.println("#重新抽题: " + testAmount);//

			LinkedList<Integer> list = new LinkedList<Integer>();// 暂存题库所有题数
			for (int i = 1; i <= recordAmount; i++) {
				list.add(i);
			}

			int randomNum; // 随机数
			int qid; // 被抽取的题号
			for (int number = 1; number <= testAmount; number++) {// 随机抽题
				
				randomNum = (int) (Math.random() * list.size());
				qid = list.get(randomNum);
				list.remove(randomNum);
				testID.add(qid);// 记录抽取的题号
			}	
			quicktest.setTestID(testID);
			/* 调试时输出数据 */
			System.out.println(testID);
		} else {
			/* 调试时输出数据 */
			System.out.println(quicktest.getTestID()); //
		}
	}
	/**
	 * 随机生成仿真考试的试题
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
		/* 上次的试卷未提交则不重新生成 */
		testID = mocktest.getTestID(); 
		if (testID == null || testID.size() == 0) {
			
			testID = new ArrayList<Integer>();
			rowSet.last();// 确定抽取的题数
			int recordAmount = rowSet.getRow();
			testAmount = TESTAMOUNT_MOCK; // 随机抽取的题数
			testAmount = Math.min(recordAmount, testAmount);
			System.out.println("#重新抽题: " + testAmount);//

			LinkedList<Integer> list = new LinkedList<Integer>();// 暂存题库所有题数
			for (int i = 1; i <= recordAmount; i++) {
				list.add(i);
			}

			int randomNum; // 随机号码
			int qid; // 被抽取的题号
			for (int number = 1; number <= testAmount; number++) {// 随机抽题
				
				randomNum = (int) (Math.random() * list.size());
				qid = list.get(randomNum);
				list.remove(randomNum);
				testID.add(qid);// 记录抽取的题号
			}	
			mocktest.setTestID(testID);
			/* 记录开始时间,用于仿真考试的计时器 */
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startTime = new Date();
			mocktest.setStartTime(df.format(startTime));
			session.setAttribute("startTime", startTime);
			/* 调试时输出数据 */
			System.out.println(testID);
		} else {
			/* 调试时输出数据 */
			System.out.println(mocktest.getTestID()); //
		}
	}
	/**
	 * 输出五题快测的试题
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
			if(!rowSet.getString(8).equals(" ")) { // 不为空格：意味着有图片，插入img标签
				str.append("<img src='" + basePath + rowSet.getString(8) +"'><br>");
			}
			str.append("</div>");
			if (testAnswer.size() < testAmount) { // 记录正确答案
				testAnswer.add(rowSet.getString(7)); 
				quicktest.setTestAnswer(testAnswer);
			}
		} catch (SQLException e) {
		}
		return str;
	}
	/**
	 * 输出仿真考试的试题
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
			if(!rowSet.getString(8).equals(" ")) { // 不为空格：意味着有图片，插入img标签
				str.append("<img src='" + basePath + rowSet.getString(8) +"'><br>");
			}
			str.append("</div>");
			if (testAnswer.size() < testAmount) { // 记录正确答案
				testAnswer.add(rowSet.getString(7)); 
				mocktest.setTestAnswer(testAnswer);
			}
		} catch (SQLException e) {
		}
		return str;
	}
	/**
	 * 计算试卷得分
	 * @param request
	 * @param response
	 * @param test
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public int executeExamScore (HttpServletRequest request, HttpServletResponse response, Test test)
			throws ServletException, IOException {
		int score = 0; // 考生分数
		testID = test.getTestID();
		testAnswer = test.getTestAnswer();// 正确答案
		answerList = new ArrayList<String>(); // 考生答案
		for(Integer i: testID) { // 获取考生提交的答案			
			String answer = request.getParameter("qid"+i);
			answerList.add(answer);
		}	
		System.out.println("answer:"+answerList.size());
		for(int i=0; i<testAnswer.size(); i++) { // 对比考生答案和正确答案，计算分值
			if(testAnswer.get(i).equals(answerList.get(i))) {
				score += (100/testAnswer.size());
			}
		}
		return score;
	}
	/**
	 * 评定成绩等级：仿真
	 * @param score
	 * @return
	 */
	public String formatMockScore(int score) {
		String grade = "";
		if(score >= 90) grade="通过";
		else grade="不通过";
		return grade;
	}
	/**
	 * 评定成绩等级：快测
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
	 * 考生水平评估：平均分
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
