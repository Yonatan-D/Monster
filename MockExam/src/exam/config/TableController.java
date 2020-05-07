package exam.config;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.rowset.CachedRowSetImpl;

import mybean.data.ShowByPage;

public class TableController extends DBMServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 查询多条记录输出到表格
	 * 
	 * @param request
	 * @param response
	 * @param sql
	 * @param jsp
	 * @throws ServletException
	 * @throws IOException
	 */
	public void QueryRowsetToTable(HttpServletRequest request, HttpServletResponse response, String sql)
			throws ServletException, IOException {
		CachedRowSetImpl rowSet = QueryDBToRowSet(sql); // 查询数据库将结果集存储进rowSet

		HttpSession session = request.getSession(true);
		StringBuffer presentPageResult = new StringBuffer();
		ShowByPage showBean = null;
		try { // 创建ShowByPage模型，存储表页面信息
			showBean = (ShowByPage) session.getAttribute("show");
			if (showBean == null) {
				showBean = new ShowByPage();
				session.setAttribute("show", showBean);
			}
		} catch (Exception e) {
			showBean = new ShowByPage();
			session.setAttribute("show", showBean);
		}

		showBean.setPageAllSize(rowSet.size()); // 存储总记录数

		String presentPageString = request.getParameter("presentPage");
		if (presentPageString == null || presentPageString.length() == 0)
			presentPageString = "1";
		int presentPage = Integer.parseInt(presentPageString);
		if (presentPage > showBean.getPageAllCount())
			presentPage = 1;
		if (presentPage <= 0)
			presentPage = showBean.getPageAllCount();
		showBean.setPresentPage(presentPage); // 设置当前页

		int pageSize; // 每页显示条数
		String pagesizeString = request.getParameter("pageSize");
		if (pagesizeString == null || pagesizeString.length() == 0) {
			pageSize = showBean.getPageSize();
		} else {
			pageSize = Integer.parseInt(pagesizeString);
			showBean.setPageSize(pageSize);
		}

		try {
			showBean.setRowSet(rowSet);
			rowSet.last();
			int m = rowSet.getRow();
			int n = pageSize;
			int pageAllCount = ((m % n) == 0) ? (m / n) : (m / n + 1);
			presentPageResult = show(presentPage, pageSize, rowSet);
			showBean.setPageAllCount(pageAllCount);
			showBean.setPresentPageResult(presentPageResult);
		} catch (Exception e) { // 查询结果不存在时返回空结果
			showBean.setPageAllCount(1);
			showBean.setPresentPageResult(new StringBuffer());
		}
	}

	/**
	 * 拼接结果集为html表格
	 * 
	 * @param page
	 * @param pageSize
	 * @param rowSet
	 * @return
	 */
	public StringBuffer show(int page, int pageSize, CachedRowSetImpl rowSet) {
		StringBuffer str = new StringBuffer();
		try {
			rowSet.absolute((page - 1) * pageSize + 1);
			for (int i = 1; i <= pageSize; i++) {
				str.append("<tr>");
				for (int j = 1; j <= rowSet.getMetaData().getColumnCount(); j++) {
					str.append("<td>" + rowSet.getString(j) + "</td>");
				}
				str.append("</tr>");
				rowSet.next();
			}
		} catch (SQLException e) {
		}
		return str;
	}
}
