package myservlet.student;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

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

@WebServlet("/StartQuickTest")
public class StartQuickTest extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {

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
				examTest.executeQuickExamTest(request, response, rowSet);
				Test quicktest = (Test) session.getAttribute("quicktest");
				StringBuffer testContent = new StringBuffer();
				int num = 1;
				for (Integer qid : quicktest.getTestID()) {
					testContent.append(examTest.show(num, qid, rowSet, basePath));// 增加每次生成的题目
					num++;
				}
				quicktest.setTestContent(testContent);

				/* 调试时输出数据 */
				System.out.println(quicktest.getTestContent());
				System.out.println(quicktest.getTestAnswer() + "\n");
			} catch (SQLException e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("student/quicktest.jsp").forward(request, response);

		} else {
			response.sendRedirect("tch_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}
}
