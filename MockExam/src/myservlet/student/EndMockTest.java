package myservlet.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import exam.config.ExamTest;
import mybean.data.Test;
import mybean.data.User;

@WebServlet("/EndMockTest")
public class EndMockTest extends DBMServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 放弃提交结束考试
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.removeAttribute("mocktest"); // 移除mocktest
		request.getRequestDispatcher("student/std_home.jsp").forward(request, response);
		;
	}

	/**
	 * 提交试卷查看分数
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			String uid = user.getUid(); // 用户ID
			String name = user.getName(); // 用户昵称
			Test mocktest = (Test) session.getAttribute("mocktest");
			String startTime = mocktest.getStartTime(); // 考试开始时间

			ExamTest examTest = new ExamTest(); // 计算考卷成绩
			int score = examTest.executeExamScore(request, response, mocktest);
			session.removeAttribute("mocktest"); // 移除mocktest
			String grade = formatScore(score); // 评定考试结果
			System.out.println("score:" + score + "grade:" + grade);

			String sql = "INSERT INTO tb_testscore value(null,'" + uid + "'," + score + ",'" + grade + "','" + startTime
					+ "', 2)";
			insertDB(request, response, sql); // 保存考试记录进数据库
			notify(request, response, name + "，最后得分：" + score + "  考试结果：" + grade);
		} else {
			response.sendRedirect("tch_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}

	/**
	 * 评定成绩等级
	 * 
	 * @param score
	 * @return
	 */
	public String formatScore(int score) {
		String grade = "";
		if (score >= 90)
			grade = "通过";
		else
			grade = "不通过";
		return grade;
	}

	@Override
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("<a href='std_home.jsp'>回到首页</a>");
			out.println("<a href='StartMockTest'>继续考试</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
