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

@WebServlet("/EndQuickTest")
public class EndQuickTest extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			String uid = user.getUid(); // 用户ID
			String name = user.getName(); // 用户昵称

			ExamTest examTest = new ExamTest(); // 计算考卷成绩
			Test quicktest = (Test) session.getAttribute("quicktest");
			int score = examTest.executeExamScore(request, response, quicktest);
			session.removeAttribute("quicktest"); // 移除quicktest
			String grade = formatScore(score); // 评定成绩等级
			System.out.println("score:" + score + "grade:" + grade);

			String sql = "INSERT INTO tb_testscore value(null,'" + uid + "'," + score + ",'" + grade + "',now(),1)";
			insertDB(request, response, sql); // 保存考试记录进数据库
			notify(request, response, name + "，最后得分：" + score + "  等级：" + grade);
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
		if (score == 100)
			grade = "A+";
		else if (score >= 90)
			grade = "A";
		else if (score >= 70)
			grade = "B";
		else if (score >= 60)
			grade = "C";
		else if (score < 60)
			grade = "D";
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
			out.println("<a href='StartQuickTest'>继续考试</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
