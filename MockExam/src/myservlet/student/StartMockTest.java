package myservlet.student;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.DBMServlet;
import exam.config.ExamTest;
import mybean.data.Test;
import mybean.data.User;

@WebServlet("/StartMockTest")
public class StartMockTest extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) { // ����״̬

			String basePath = null; // ��Ŀ·��
			// ��ȡ��Ŀ·��
			String path = request.getContextPath();
			try {
				basePath = request.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":"
						+ request.getServerPort() + path + "/";
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			String sql = "SELECT * FROM tb_test";
			CachedRowSetImpl rowSet = QueryDBToRowSet(sql);

			try {// �����ȡ��Ŀ
				ExamTest examTest = new ExamTest();
				examTest.executeMockExamTest(request, response, rowSet);
				Test mocktest = (Test) session.getAttribute("mocktest");
				StringBuffer testContent = new StringBuffer();

				int num = 1;
				for (Integer qid : mocktest.getTestID()) {
					testContent.append(examTest.showMock(num, qid, rowSet, basePath));// ����ÿ�����ɵ���Ŀ
					num++;
				}
				mocktest.setTestContent(testContent);
				/* ���ü�ʱ��ʣ��ʱ�� */
				Date startTime = (Date) session.getAttribute("startTime");
				long endTime = startTime.getTime() + MOCKTEST_TIME;
				long nowTime = new Date().getTime();
				long spareTime = endTime - nowTime;
				mocktest.setSpareTime(spareTime / 1000);
				/* ����ʱ������� */
				System.out.println(mocktest.getStartTime());
				System.out.println(mocktest.getTestContent());
				System.out.println(mocktest.getTestAnswer() + "\n");

			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("student/mocktest.jsp").forward(request, response);
		} else {
			response.sendRedirect("tch_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
													// �� tch_home.jsp
		}
	}
}
