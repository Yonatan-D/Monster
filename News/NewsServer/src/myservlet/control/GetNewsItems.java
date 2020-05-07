package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@WebServlet("/GetNewsItems")
public class GetNewsItems extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("application/json;charset=UTF-8");
		PrintWriter out = response.getWriter();

		String time = request.getParameter("time");// 获取"?time="的值

		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
		}

		try {
			JSONArray jsonArr = new JSONArray();
			JSONObject jsonObj = new JSONObject();

			// 从数据库读取10条新闻
			Connection con;
			Statement stme;
			ResultSet rs;
			try {
				String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
				String sql = "select id, title, author, subtime, cover from tb_article where subtime < '" + time
						+ "' order by subtime desc limit 10";
				con = DriverManager.getConnection(url);
				stme = con.createStatement();
				rs = stme.executeQuery(sql);

				while (rs.next()) {
					jsonObj.put("id", rs.getInt("id"));
					jsonObj.put("title", rs.getString("title"));
					jsonObj.put("author", rs.getString("author"));
					jsonObj.put("subtime", rs.getString("subtime"));
					jsonObj.put("cover", rs.getString("cover"));
					jsonArr.add(jsonObj);
				}

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

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
