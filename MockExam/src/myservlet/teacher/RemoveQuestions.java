package myservlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/RemoveQuestions")
public class RemoveQuestions extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String tid = request.getParameter("tid");// 获取"?tid="的值
			int id = Integer.parseInt(tid);
			String sql = "DELETE FROM tb_test WHERE id='" + id + "'";
			deleteDB(request, response, sql);
			request.getRequestDispatcher("QueryQuestions").forward(request, response);
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp或
													// tch_home.jsp
		}
	}
}
