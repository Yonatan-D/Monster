package myservlet.teacher;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/RecordQuestions")
public class RecordQuestions extends DBMServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			// ��ȡ���ύ����Ϣ
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");

			try {
				List<FileItem> list = upload.parseRequest(request);
				String id = list.get(0).getString("UTF-8");
				id = list.get(0).getString("UTF-8"); // ��ȡ"id"��ֵ
				// �ж�id�Ƿ�Ϊ��ѡ��insert��update
				if (id.length() == 0) { // ����Questions
					InsertQuestionToDB(request, response, list);
				} else { // �޸�Questions
					updateQuestionToDB(request, response, list);
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("std_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
			// �� tch_home.jsp
		}

	}

	public void InsertQuestionToDB(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		String path = getServletContext().getRealPath("/TEST_IMG/");// ָ���ϴ��ļ��е�·��
		FileItem testIMG = list.get(7);
		String imgName = testIMG.getName();
		String pic = null;

		if (imgName.length() == 0) {
			pic = " "; // ����ո�
		} else {
			if (!imgName.toLowerCase().endsWith("jpg") && !imgName.toLowerCase().endsWith("jpeg")
					&& !imgName.toLowerCase().endsWith("png")) {
				return;
			} // �������ٴ�����ͼƬ���ͣ���ֹ�����ƹ�ǰ̨����
			pic = IMAGE_DIR + imgName; // ���÷���ͼ���·��
			try {
				File file = new File(path, imgName);
				testIMG.write(file);// �ϴ���ָ���ļ���
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		String columns = "";
		for (int i = 1; i < list.size() - 1; i++) {
			columns += (",'" + list.get(i).getString("UTF-8")) + "'";
		}
		String sql = "INSERT INTO tb_test value(null" + columns + ",'" + pic + "')";
		boolean boo = insertDB(request, response, sql);
		if (boo) {
			notify(request, response, "�ѱ��棡");
		} else {
			notify(request, response, "����ʧ�ܣ���");
		}
	}

	public void updateQuestionToDB(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		String path = getServletContext().getRealPath("/TEST_IMG/");// ָ���ϴ��ļ��е�·��
		FileItem testIMG = list.get(7);
		String imgName = testIMG.getName();
		String pic = null;
		String sql = null;

		if (imgName.length() == 0) { // �ر༭��input[file]Ϊ�գ��ύ��ʱ������ԭ��ͼƬ
			sql = "UPDATE tb_test SET quest='" + list.get(1).getString("UTF-8") + "', a='"
					+ list.get(2).getString("UTF-8") + "', b='" + list.get(3).getString("UTF-8") + "', c='"
					+ list.get(4).getString("UTF-8") + "', d='" + list.get(5).getString("UTF-8") + "', answer='"
					+ list.get(6).getString("UTF-8") + "' WHERE id='" + list.get(0).getString("UTF-8") + "'";
			boolean boo = updateDB(request, response, sql);
			if (boo) {
				notify(request, response, "�ѱ��棡");
			} else {
				notify(request, response, "����ʧ�ܣ���");
			}
		} else {
			if (!imgName.toLowerCase().endsWith("jpg") && !imgName.toLowerCase().endsWith("jpeg")
					&& !imgName.toLowerCase().endsWith("png")) {
				return;
			} // �������ٴ�����ͼƬ���ͣ���ֹ�����ƹ�ǰ̨����
			pic = IMAGE_DIR + imgName; // ���÷���ͼ���·��
			try {
				File file = new File(path, imgName);
				testIMG.write(file);// �ϴ���ָ���ļ���
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			sql = "UPDATE tb_test SET quest='" + list.get(1).getString("UTF-8") + "', a='"
					+ list.get(2).getString("UTF-8") + "', b='" + list.get(3).getString("UTF-8") + "', c='"
					+ list.get(4).getString("UTF-8") + "', d='" + list.get(5).getString("UTF-8") + "', answer='"
					+ list.get(6).getString("UTF-8") + "', pic='" + pic + "' WHERE id='"
					+ list.get(0).getString("UTF-8") + "'";
			boolean boo = updateDB(request, response, sql);
			if (boo) {
				notify(request, response, "�ѱ��棡");
			} else {
				notify(request, response, "����ʧ�ܣ���");
			}
		}
	}

	@Override
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("<a href='QueryQuestions'>�������</a>");
			out.println("<a href='LoadInputQuestions'>�������</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
