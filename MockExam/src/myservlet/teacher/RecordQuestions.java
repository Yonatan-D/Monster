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
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_TCH) {
			// 获取表单提交的信息
			DiskFileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setHeaderEncoding("UTF-8");

			try {
				List<FileItem> list = upload.parseRequest(request);
				String id = list.get(0).getString("UTF-8");
				id = list.get(0).getString("UTF-8"); // 获取"id"的值
				// 判断id是否为空选择insert或update
				if (id.length() == 0) { // 新增Questions
					InsertQuestionToDB(request, response, list);
				} else { // 修改Questions
					updateQuestionToDB(request, response, list);
				}
			} catch (FileUploadException e) {
				e.printStackTrace();
			}
		} else {
			response.sendRedirect("std_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
			// 或 tch_home.jsp
		}

	}

	public void InsertQuestionToDB(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		String path = getServletContext().getRealPath("/TEST_IMG/");// 指定上传文件夹的路径
		FileItem testIMG = list.get(7);
		String imgName = testIMG.getName();
		String pic = null;

		if (imgName.length() == 0) {
			pic = " "; // 插入空格
		} else {
			if (!imgName.toLowerCase().endsWith("jpg") && !imgName.toLowerCase().endsWith("jpeg")
					&& !imgName.toLowerCase().endsWith("png")) {
				return;
			} // 服务器再次拦截图片类型，防止恶意绕过前台发送
			pic = IMAGE_DIR + imgName; // 设置封面图相对路径
			try {
				File file = new File(path, imgName);
				testIMG.write(file);// 上传至指定文件夹
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
			notify(request, response, "已保存！");
		} else {
			notify(request, response, "保存失败！！");
		}
	}

	public void updateQuestionToDB(HttpServletRequest request, HttpServletResponse response, List<FileItem> list)
			throws ServletException, IOException {
		String path = getServletContext().getRealPath("/TEST_IMG/");// 指定上传文件夹的路径
		FileItem testIMG = list.get(7);
		String imgName = testIMG.getName();
		String pic = null;
		String sql = null;

		if (imgName.length() == 0) { // 重编辑，input[file]为空，提交表单时不覆盖原来图片
			sql = "UPDATE tb_test SET quest='" + list.get(1).getString("UTF-8") + "', a='"
					+ list.get(2).getString("UTF-8") + "', b='" + list.get(3).getString("UTF-8") + "', c='"
					+ list.get(4).getString("UTF-8") + "', d='" + list.get(5).getString("UTF-8") + "', answer='"
					+ list.get(6).getString("UTF-8") + "' WHERE id='" + list.get(0).getString("UTF-8") + "'";
			boolean boo = updateDB(request, response, sql);
			if (boo) {
				notify(request, response, "已保存！");
			} else {
				notify(request, response, "保存失败！！");
			}
		} else {
			if (!imgName.toLowerCase().endsWith("jpg") && !imgName.toLowerCase().endsWith("jpeg")
					&& !imgName.toLowerCase().endsWith("png")) {
				return;
			} // 服务器再次拦截图片类型，防止恶意绕过前台发送
			pic = IMAGE_DIR + imgName; // 设置封面图相对路径
			try {
				File file = new File(path, imgName);
				testIMG.write(file);// 上传至指定文件夹
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
				notify(request, response, "已保存！");
			} else {
				notify(request, response, "保存失败！！");
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
			out.println("<a href='QueryQuestions'>返回题库</a>");
			out.println("<a href='LoadInputQuestions'>继续添加</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
