package myservlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.ExamConfig;
import mybean.data.User;

@WebServlet("/ReQuickTest")
public class ReQuickTest extends HttpServlet implements ExamConfig {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			session.removeAttribute("quicktest");
			response.sendRedirect("StartQuickTest");
		} else {
			response.sendRedirect("tch_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
													// �� tch_home.jsp
		}
	}
}
