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
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) { // 考生状态

			String basePath = null; // 项目路径
			// 获取项目路径
			String path = request.getContextPath();
			try {
				basePath = request.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":"
						+ request.getServerPort() + path + "/";
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			String sql = "SELECT * FROM tb_test";
			CachedRowSetImpl rowSet = QueryDBToRowSet(sql);

			try {// 随机抽取题目
				ExamTest examTest = new ExamTest();
				examTest.executeMockExamTest(request, response, rowSet);
				Test mocktest = (Test) session.getAttribute("mocktest");
				StringBuffer testContent = new StringBuffer();

				int num = 1;
				for (Integer qid : mocktest.getTestID()) {
					testContent.append(examTest.showMock(num, qid, rowSet, basePath));// 增加每次生成的题目
					num++;
				}
				mocktest.setTestContent(testContent);
				/* 设置计时器剩余时间 */
				Date startTime = (Date) session.getAttribute("startTime");
				long endTime = startTime.getTime() + MOCKTEST_TIME;
				long nowTime = new Date().getTime();
				long spareTime = endTime - nowTime;
				mocktest.setSpareTime(spareTime / 1000);
				/* 调试时输出数据 */
				System.out.println(mocktest.getStartTime());
				System.out.println(mocktest.getTestContent());
				System.out.println(mocktest.getTestAnswer() + "\n");

			} catch (SQLException e) {
				e.printStackTrace();
			}
			request.getRequestDispatcher("student/mocktest.jsp").forward(request, response);
		} else {
			response.sendRedirect("tch_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}
}
