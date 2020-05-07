package myservlet.control;

import java.io.IOException;
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

@WebServlet("/SelectArticle")
public class SelectArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// 创建存储查询结果的模型
		Articles sendArt = null; //已发布文章
		try {
			sendArt = (Articles) request.getAttribute("sendArt");
			if(sendArt == null) {
				sendArt = new Articles();
				request.setAttribute("sendArt", sendArt);
			}
		} catch (Exception e) {
			sendArt = new Articles();
			request.setAttribute("sendArt", sendArt);
		}
		
		Articles noSendArt = null; //未发布文章
		try {
			noSendArt = (Articles) request.getAttribute("noSendArt");
			if(noSendArt == null) {
				noSendArt = new Articles();
				request.setAttribute("noSendArt", noSendArt);
			}
		} catch (Exception e) {
			noSendArt = new Articles();
			request.setAttribute("noSendArt", noSendArt);
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
			
			// 已发布文章
			String condition = "select * from tb_article where subtime<now() order by subtime desc";
			rs = stme.executeQuery(condition);
			
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			String []columnName = new String[columnCount];
			for(int i=0; i<columnName.length; i++) {
				columnName[i] = metaData.getColumnName(i+1);
			}
			sendArt.setColumnName(columnName);
			
			rs.last();
			int rowNumber = rs.getRow();
			String [][]tableRecord = sendArt.getTableRecord();
			tableRecord = new String[rowNumber][columnCount];
			rs.beforeFirst();
			int i = 0;
			while(rs.next()) {
				for(int k=0; k<columnCount; k++) {
					tableRecord[i][k] = rs.getString(k+1);
				}
				i++;
			}
			sendArt.setTableRecord(tableRecord);
			
			
			//未发布文章
			String condition1 = "select * from tb_article where subtime>now() order by subtime desc";
			rs = stme.executeQuery(condition1);
			
			ResultSetMetaData metaData1 = rs.getMetaData();
			int columnCount1 = metaData1.getColumnCount();
			String []columnName1 = new String[columnCount1];
			for(i=0; i<columnName1.length; i++) {
				columnName1[i] = metaData1.getColumnName(i+1);
			}
			noSendArt.setColumnName(columnName1);
			
			rs.last();
			int rowNumber1 = rs.getRow();
			String [][]tableRecord1 = noSendArt.getTableRecord();
			tableRecord1 = new String[rowNumber1][columnCount1];
			rs.beforeFirst();
			i = 0;
			while(rs.next()) {
				for(int k=0; k<columnCount1; k++) {
					tableRecord1[i][k] = rs.getString(k+1);
				}
				i++;
			}
			noSendArt.setTableRecord(tableRecord1);
			
			
			con.close();
			
			RequestDispatcher dispatcher = request.getRequestDispatcher("showArticles.jsp");
			dispatcher.forward(request, response);
			
		} catch (SQLException e) {
			System.out.println(e);
		}
		
	}

}
