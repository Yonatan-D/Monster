package myservlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ReadCloud
 */
@WebServlet("/ReadCloud")
public class ReadCloud extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReadCloud() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String path = getServletContext().getRealPath("/uploadFile/");
		getFile(path);//读取文件
	}

	/**
	 * 读取服务器文件
	 * 
	 * @param path
	 */
	private static void getFile(String path) {

		File dir = new File(path);
		File[] files = dir.listFiles();
		String filenames = "";
		// 读取服务器文件夹下所有内容
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String filename = files[i].getName();
				if (files[i].isFile()) {
					String strpath = files[i].getAbsolutePath();
					System.out.println(filename);//
					System.out.println(strpath);//
					filenames = filenames + filename + ", ";

				} else if (files[i].isDirectory()) {
					getFile(files[i].getPath());
				}
			}
		}
		// 将文件信息写入list.dat
		OutputStreamWriter writer;
		try {
			writer = new OutputStreamWriter(new FileOutputStream(path + "list.dat"), "UTF-8");
			writer.write(filenames);
			writer.flush();
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
