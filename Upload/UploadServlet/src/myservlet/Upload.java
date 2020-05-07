package myservlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class Upload
 */
@WebServlet("/Upload")
@MultipartConfig
public class Upload extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Upload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding("utf-8");
//		PrintWriter out = response.getWriter();

		String path = getServletContext().getRealPath("/uploadFile/");

		Part part = request.getPart("myfile");// ��ȡ�ļ�����
		String fileName = part.getSubmittedFileName(); // tomcat8.0����֧�֣���ȡ�ļ���

		part.write(path + fileName); // �ϴ���ָ���ļ���
		System.out.println(path + fileName);//

//		out.print("�ϴ��ļ�����ɹ�!");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
