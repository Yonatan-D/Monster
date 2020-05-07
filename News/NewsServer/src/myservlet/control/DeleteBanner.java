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

		String tid = request.getParameter("tid");// ��ȡ"?tid="��ֵ
		int id = Integer.parseInt(tid);
		
		//���� MySQL�� JDBC���ݿ�����
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("��������ʧ��");
		}
		
		//�������ݿ�
		Connection con;
		Statement stme;
		try {
			String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
			con = DriverManager.getConnection(url);
			stme = (Statement) con.createStatement();
			String sql = "delete from tb_banner where id="+id;
			stme.executeUpdate(sql);
			notify(request, response, "���Ƴ��÷���ͼ");
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			notify(request, response, "ɾ��ʧ�ܣ�"+e.toString());
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
			out.println("����");
			out.println("<a href='SelectBanner'>��������</a>");
			out.println("&nbsp;&nbsp;<a href='index.jsp'>�ص���ҳ</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}

}
