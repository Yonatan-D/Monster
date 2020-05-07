package myservlet.control;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mybean.data.Articles;

@WebServlet("/SelectBanner")
public class SelectBanner extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 创建存储查询结果的模型
		Articles setCover = null; //封面信息
		try {
			setCover = (Articles) request.getAttribute("setCover");
			if(setCover == null) {
				setCover = new Articles();
				request.setAttribute("setCover", setCover);
			}
		} catch (Exception e) {
			setCover = new Articles();
			request.setAttribute("setCover", setCover);
		}
		
		//加载 MySQL的 JDBC数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("加载驱动失败");
		}
		
		// 连接数据库
		Connection con;
		Statement stme;
		ResultSet rs;
		try {
			String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
			con = DriverManager.getConnection(url);
			stme = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
			String sql = "select id, title, cover from tb_article a where exists (select id from tb_banner b where a.id=b.id)";
			rs = stme.executeQuery(sql);

			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			String []columnName = new String[columnCount];
			for(int i=0; i<columnName.length; i++) {
				columnName[i] = metaData.getColumnName(i+1);
			}
			setCover.setColumnName(columnName);
			
			rs.last();
			int rowNumber = rs.getRow();
			String [][]tableRecord = setCover.getTableRecord();
			tableRecord = new String[rowNumber][columnCount];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()) {
				for(int k=0; k<columnCount; k++) {
					tableRecord[i][k] = rs.getString(k+1);
				}
				i++;
			}
			setCover.setTableRecord(tableRecord);
			con.close();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("setSlideCover.jsp");
			dispatcher.forward(request, response);
			
		} catch (SQLException e) {
			System.out.println(e);
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
			out.println("<a href=''>继续操作</a>");
			out.println("&nbsp;&nbsp;<a href='index.jsp'>回到首页</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}

}
