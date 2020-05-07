package exam.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.rowset.CachedRowSetImpl;

@WebServlet("/DBMServlet")
public class DBMServlet extends HttpServlet implements ExamConfig {
	private static final long serialVersionUID = 1L;

	String url = "jdbc:mysql://localhost/examdb?user=root&password=mysql&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8&useSSL=false";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (Exception e) {
		}
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doGet(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		super.doPost(request, response);
	}
	/**
	 * 插入数据到数据库表
	 * @param request
	 * @param response
	 * @param list
	 * @throws UnsupportedEncodingException
	 */
	public boolean insertDB(HttpServletRequest request, HttpServletResponse response, String sql) 
			throws ServletException, IOException {
		boolean boo = true;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stme = con.createStatement();
			stme.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			boo  = false;
		}
		return boo;
	}
	/**
	 * 删除数据库表的数据
	 * @param request
	 * @param response
	 * @param sql
	 * @throws UnsupportedEncodingException
	 */
	public boolean deleteDB(HttpServletRequest request, HttpServletResponse response, String sql) 
			throws ServletException, IOException {
		boolean boo = true;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stme = con.createStatement();
			stme.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			boo  = false;
		}
		return boo;
	}
	/**
	 * 更新数据到数据库表
	 * @param request
	 * @param response
	 * @param list
	 * @throws UnsupportedEncodingException
	 */
	public boolean updateDB(HttpServletRequest request, HttpServletResponse response, String sql) 
			throws ServletException, IOException {
		boolean boo = true;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stme = con.createStatement();
			stme.executeUpdate(sql);
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
			boo  = false;
		}
		return boo;
	}
	/**
	 * 查询数据库，将结果存储进 ArrayList
	 * @param sql
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public ArrayList<String> QueryDB(String sql) 
			throws ServletException, IOException {
		ArrayList<String> list = new ArrayList<String>();
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stme = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stme.executeQuery(sql);
			java.sql.ResultSetMetaData metaData = rs.getMetaData();
			while(rs.next()) {
				for(int i=1; i<=metaData.getColumnCount(); i++) {
					list.add(rs.getString(i));
				}
			}
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	/**
	 * 查询数据库，将结果存储进 CachedRowSetImpl
	 * @param sql
	 * @return
	 */
	public CachedRowSetImpl QueryDBToRowSet(String sql) {
		CachedRowSetImpl rowSet = null;
		Connection con = null;
		try {
			con = DriverManager.getConnection(url);
			Statement stme = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stme.executeQuery(sql);
			rowSet = new CachedRowSetImpl();
			rowSet.populate(rs);
			con.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return rowSet;
	}
	/**
	 * 执行结果反馈
	 * @param request
	 * @param response
	 * @param backNews
	 */
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {

		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("<a href='javascript:history.go(-1)'>返回上一级</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
