package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class UpdateArticle
 */
@WebServlet("/UpdateArticle")
public class UpdateArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String tid = request.getParameter("tid");
		int id = Integer.parseInt(tid);
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String content = request.getParameter("content");
		
		//加载 MySQL的 JDBC数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("加载驱动失败");
		}
		
		//连接数据库
		Connection con;
		PreparedStatement pstme;
		ResultSet rs;
		try {
			String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
			con = DriverManager.getConnection(url);
			String sql = "update tb_article set title=?, author=?, content=? where id=?";
			pstme = (PreparedStatement) con.prepareStatement(sql);
			pstme.setString(1, title);
			pstme.setString(2, author);
			pstme.setString(3, content);
			pstme.setInt(4, id);
			pstme.executeUpdate();
			notify(request, response, "已保存至数据库");
			con.close();
			
			request.getRequestDispatcher("SelectArticle").forward(request, response);
			
		} catch (SQLException e) {
			System.out.println(e);
			notify(request, response, "提交失败："+e.toString());
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {

		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("返回");
			out.println("<a href='javascript:history.go(-1)'>继续编辑</a>");
			out.println("&nbsp;&nbsp;<a href='index.jsp'>回到首页</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}

}
