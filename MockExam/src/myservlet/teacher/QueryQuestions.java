package myservlet.teacher;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.TableController;
import mybean.data.User;

@WebServlet("/QueryQuestions")
public class QueryQuestions extends TableController {
	private static final long serialVersionUID = 1L;

	String basePath;
	/**
	 * 查看所有题目
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String path = request.getContextPath();
			basePath = request.getScheme() + "://" + InetAddress.getLocalHost().getHostAddress() + ":"
					+ request.getServerPort() + path + "/";

			String sql = "SELECT * FROM tb_test";
			QueryRowsetToTable(request, response, sql);
			request.getRequestDispatcher("teacher/showQuestions.jsp").forward(request, response);
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}
	/**
	 * 模糊搜索题目
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			String searchBox = "";
			searchBox = request.getParameter("searchBox");
			System.out.println(searchBox);
			String sql = "SELECT * FROM tb_test WHERE quest LIKE '%" + searchBox + "%';";
			QueryRowsetToTable(request, response, sql);
			request.getRequestDispatcher("teacher/showQuestions.jsp").forward(request, response);
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}

	/*
	 * 重写 TableController 的 show 方法 （增加图片预览栏和操作栏）
	 * 
	 * @see exam.config.DBMServlet#show(int, int,
	 * com.sun.rowset.CachedRowSetImpl)
	 */
	public StringBuffer show(int page, int pageSize, CachedRowSetImpl rowSet) {
		StringBuffer str = new StringBuffer();
		try {
			rowSet.absolute((page - 1) * pageSize + 1);
			for (int i = 1; i <= pageSize; i++) {
				str.append("<tr>");
				str.append("<input type='hidden' value='" + rowSet.getString(1) + "'>");
				for (int j = 1; j <= rowSet.getMetaData().getColumnCount(); j++) {
					str.append("<td>" + rowSet.getString(j) + "</td>");
				}
				if (!rowSet.getString(8).equals(" ")) { // 不为空格：意味着有图片，插入img标签
					str.append(
							"<td align='center'><img width='200' src='" + basePath + rowSet.getString(8) + "'></td>");
				} else {
					str.append("<td align='center'></td>");
				}
				str.append("<td align='center'><a class='doBtn' href='LoadInputQuestions?tid=" + rowSet.getString(1)
						+ "'>编辑</a></td>");
				str.append(
						"<td align='center'><a class='nodoBtn' href='#' id='" + rowSet.getString(1) + "'>删除</a></td>");
				str.append("</tr>");
				rowSet.next();
			}
		} catch (SQLException e) {
		}
		return str;
	}
}
