package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.Statement;

/**
 * Servlet implementation class DeleteBanner
 */
@WebServlet("/DeleteBanner")
public class DeleteBanner extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String tid = request.getParameter("tid");// 获取"?tid="的值
		int id = Integer.parseInt(tid);
		
		//加载 MySQL的 JDBC数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("加载驱动失败");
		}
		
		//连接数据库
		Connection con;
		Statement stme;
		try {
			String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
			con = DriverManager.getConnection(url);
			stme = (Statement) con.createStatement();
			String sql = "delete from tb_banner where id="+id;
			stme.executeUpdate(sql);
			notify(request, response, "已移除该封面图");
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			notify(request, response, "删除失败："+e.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {

		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("返回");
			out.println("<a href='SelectBanner'>继续操作</a>");
			out.println("&nbsp;&nbsp;<a href='index.jsp'>回到首页</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}

}
