package exam.output.excel;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.rowset.CachedRowSetImpl;

import exam.config.DBMServlet;

@WebServlet("/DownloadUser")
public class DownloadUser extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String sql = "SELECT uid, name, sex, email, phone, registdate FROM tb_user WHERE usertype=1";
		CachedRowSetImpl rowSet = QueryDBToRowSet(sql);
		ExportExcel exportExcel = new ExportExcel();
		exportExcel.exportExcel(response, rowSet, "user");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
