package exam.output.excel;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.DBMServlet;

@WebServlet("/DownloadQuestions")
public class DownloadQuestions extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "SELECT * FROM tb_test";
		CachedRowSetImpl rowSet = QueryDBToRowSet(sql);
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.exportExcel(response, rowSet, "test");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
