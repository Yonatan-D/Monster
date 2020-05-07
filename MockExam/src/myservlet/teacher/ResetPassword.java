package myservlet.teacher;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/ResetPassword")
public class ResetPassword extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String uid = user.getUid();
			String newPassword = request.getParameter("newPassword");
			String sql = "UPDATE tb_user SET pwd='" + newPassword + "' WHERE uid='" + uid + "'";
			boolean boo = insertDB(request, response, sql);
			if (boo) {
				request.getRequestDispatcher("teacher/tch_home.jsp").forward(request, response);
			} else {
				notify(request, response, "�޸�ʧ��!");
			}
		} else {
			response.sendRedirect("std_home.jsp"); // ��filter���غ����ȷ����std_home.jsp��tch_home.jsp
		}

	}
}
