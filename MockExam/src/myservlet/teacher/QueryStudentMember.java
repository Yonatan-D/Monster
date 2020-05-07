package myservlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.TableController;
import mybean.data.User;

@WebServlet("/QueryStudentMember")
public class QueryStudentMember extends TableController {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String sql = "SELECT uid, name, sex, email, phone, registdate FROM tb_user WHERE usertype=1";
			QueryRowsetToTable(request, response, sql);
			request.getRequestDispatcher("teacher/showStudentMember.jsp").forward(request, response);
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String searchBox = "";
			searchBox = request.getParameter("searchBox");
			String sql = "SELECT uid, name, sex, email, phone, registdate FROM tb_user WHERE uid LIKE '" + searchBox
					+ "%' AND usertype=1";
			QueryRowsetToTable(request, response, sql);
			request.getRequestDispatcher("teacher/showStudentMember.jsp").forward(request, response);
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}
}
