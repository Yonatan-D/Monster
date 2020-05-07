package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetNewsArticle
 */
@WebServlet("/GetNewsArticle")
public class GetNewsArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String tid = request.getParameter("tid");// 获取"?tid="的值
		int id = Integer.parseInt(tid);

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
		}

		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();

			// 从数据库读取指定id的新闻
			Connection con;
			Statement stme;
			ResultSet rs;
			try {
				String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
				String sql = "select * from tb_article where id=" + id;
				con = DriverManager.getConnection(url);
				stme = con.createStatement();
				rs = stme.executeQuery(sql);

				rs.next();
				jsonObj.put("id", rs.getInt("id"));
				jsonObj.put("title", rs.getString("title"));
				jsonObj.put("author", rs.getString("author"));
				jsonObj.put("subtime", rs.getString("subtime"));
				jsonObj.put("content", rs.getString("content"));
				jsonObj.put("cover", rs.getString("cover"));
				jsonArr.add(jsonObj);

				con.close();

			} catch (SQLException e) {
				out.println(e);
			}
			// 回调函数
			String jsonpCallback = request.getParameter("jsonpCallback");
			out.println(jsonpCallback + "(" + jsonArr.toString() + ")");
			out.flush();
			out.close();

		} catch (Exception e) {
			out.println(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
