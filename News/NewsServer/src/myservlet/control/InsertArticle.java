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

		//加载 MySQL的 JDBC数据库驱动
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			System.out.println("加载驱动失败");
		}
		
		//获取表单提交的信息
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		
		String title=null; //标题
		String author=null; //作者
		String subtime=null; //发布日期
		String content=null; //正文
		String cover=null; //封面图地址
		
		try{
			List<FileItem> list = upload.parseRequest(request);
			//获取输入框的文本内容
			title = list.get(0).getString("UTF-8");
			author = list.get(1).getString("UTF-8");
			subtime = list.get(2).getString("UTF-8");
			content = list.get(4).getString("UTF-8");
			if(title==null || title.length()==0) {
				notify(request, response, "提交失败，必须填写标题！");
				return;
			}
			
			//上传封面图片到服务器
			FileItem coverIMG = list.get(3);			
			String path = getServletContext().getRealPath("/COVER_IMG/");//指定上传文件夹的路径
			String fileName = coverIMG.getName();
			
			if(fileName==null || fileName.length()==0){
				cover = "NoCover";
			}else {
				//服务器再次拦截图片类型，防止恶意绕过前台发送
				if(!fileName.toLowerCase().endsWith("jpg") 
						&& !fileName.toLowerCase().endsWith("jpeg") 
						&& !fileName.toLowerCase().endsWith("png")) {
					notify(request, response, "封面图片格式无法识别！");
					return;
				}
				cover = "/NewsServer/COVER_IMG/" + fileName; //设置封面图相对路径
				File file = new File(path, fileName);
				coverIMG.write(file); // 上传至指定文件夹
			}			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		//连接数据库
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
			notify(request, response, "已保存至数据库");
			con.close();
			
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
