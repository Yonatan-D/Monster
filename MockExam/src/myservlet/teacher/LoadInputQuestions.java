package myservlet.teacher;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/LoadInputQuestions")
public class LoadInputQuestions extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String tid = request.getParameter("tid");// ��ȡ"?tid="��ֵ
			if (tid == null || tid.length() == 0) {
				request.getRequestDispatcher("teacher/inputQuestions.jsp").forward(request, response);
			} else {
				int id = Integer.parseInt(tid);
				String sql = "SELECT * FROM tb_test WHERE id='" + id + "'";
				CachedRowSetImpl rowSet = QueryDBToRowSet(sql);
				try {
					java.sql.ResultSetMetaData metaData = rowSet.getMetaData();
					if (rowSet.next()) {
						for (int i = 1; i <= metaData.getColumnCount(); i++) { // ����Ӧ���������ݴ洢��request
							request.setAttribute(metaData.getColumnName(i), rowSet.getString(i));
						}
					}
				} catch (SQLException e) {
					notify(request, response, "�������ݿ�ʧ�ܣ�");
				}
				request.getRequestDispatcher("teacher/inputQuestions.jsp").forward(request, response);
			}
		} else {
			response.sendRedirect("std_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
													// �� tch_home.jsp
		}
	}
}
