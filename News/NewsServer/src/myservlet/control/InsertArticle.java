package myservlet.control;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.mysql.jdbc.PreparedStatement;

import mybean.data.Articles;

@WebServlet("/InsertArticle")
public class InsertArticle extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		//���� MySQL�� JDBC���ݿ�����
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("��������ʧ��");
		}
		
		//��ȡ���ύ����Ϣ
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		String title=null; //����
		String author=null; //����
		String subtime=null; //��������
		String content=null; //����
		String cover=null; //����ͼ��ַ
		
		try{
			List<FileItem> list = upload.parseRequest(request);
			//��ȡ�������ı�����
			title = list.get(0).getString("UTF-8");
			author = list.get(1).getString("UTF-8");
			subtime = list.get(2).getString("UTF-8");
			content = list.get(4).getString("UTF-8");
			if(title==null || title.length()==0) {
				notify(request, response, "�ύʧ�ܣ�������д���⣡");
				return;
			}
			
			//�ϴ�����ͼƬ��������
			FileItem coverIMG = list.get(3);			
			String path = getServletContext().getRealPath("/COVER_IMG/");//ָ���ϴ��ļ��е�·��
			String fileName = coverIMG.getName();
			
			if(fileName==null || fileName.length()==0){
				cover = "NoCover";
			}else {
				//�������ٴ�����ͼƬ���ͣ���ֹ�����ƹ�ǰ̨����
				if(!fileName.toLowerCase().endsWith("jpg") 
						&& !fileName.toLowerCase().endsWith("jpeg") 
						&& !fileName.toLowerCase().endsWith("png")) {
					notify(request, response, "����ͼƬ��ʽ�޷�ʶ��");
					return;
				}
				cover = "/NewsServer/COVER_IMG/" + fileName; //���÷���ͼ���·��
				File file = new File(path, fileName);
				coverIMG.write(file); // �ϴ���ָ���ļ���
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//�������ݿ�
		Connection con;
		PreparedStatement pstme;
		ResultSet rs;
		try {
			String url = "jdbc:mysql://localhost/newsdb?user=root&password=mysql&characterEncoding=UTF-8&useSSL=false";
			con = DriverManager.getConnection(url);
			String sql = "insert into tb_article values(null, ?, ?, ?, ?, ?)";
			pstme = (PreparedStatement) con.prepareStatement(sql);
			pstme.setString(1, title);
			pstme.setString(2, author);
			pstme.setString(3, subtime);
			pstme.setString(4, cover);
			pstme.setString(5, content);
			pstme.executeUpdate();
			notify(request, response, "�ѱ��������ݿ�");
			con.close();
			
		} catch (SQLException e) {
			System.out.println(e);
			notify(request, response, "�ύʧ�ܣ�"+e.toString());
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
			out.println("����");
			out.println("<a href='javascript:history.go(-1)'>�����༭</a>");
			out.println("&nbsp;&nbsp;<a href='index.jsp'>�ص���ҳ</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}

}
