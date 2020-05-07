package myservlet.student;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.TableController;
import mybean.data.User;

@WebServlet("/showTRselector")
public class showTRselector extends TableController {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String selector = request.getParameter("selector");

		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // 无用户状态
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			String uid = user.getUid();
			if (selector.equals("0")) {
				response.sendRedirect("QueryTestReport");
			} else if (selector.equals("1")) {
				// 饼状图数据
				ArrayList<String> qck = QueryDB(
						"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=1");
				float qckNum = Float.parseFloat(qck.get(0));
				ArrayList<String> qckHeight = QueryDB(
						"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=1 AND score>90");
				float qckHeightNum = Float.parseFloat(qckHeight.get(0));
				float data = qckHeightNum / qckNum * 100;
				System.out.println(data);
				session.setAttribute("Evaluation", data);
				session.setAttribute("green", "90分以上");
				session.setAttribute("red", "90分以下");
				// 查表
				String sql = "SELECT time, typename, score, grade FROM tb_testscore t1 "
						+ "join tb_scoretype t2 on t1.scoretype = t2.typeid " + "WHERE uid='" + uid
						+ "' AND scoretype=1 " + "ORDER BY time DESC";
				QueryRowsetToTable(request, response, sql);
				request.getRequestDispatcher("student/showTestReport.jsp").forward(request, response);
				session.setAttribute("selector", 1);

			} else if (selector.equals("2")) {
				// 饼状图数据
				ArrayList<String> mck = QueryDB(
						"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=2");
				float mckNum = Float.parseFloat(mck.get(0));
				ArrayList<String> mckHeight = QueryDB(
						"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=1 AND score>90");
				float mckHeightNum = Float.parseFloat(mckHeight.get(0));
				float data = mckHeightNum / mckNum * 100;
				System.out.println(data);
				session.setAttribute("Evaluation", data);
				session.setAttribute("green", "通过");
				session.setAttribute("red", "不通过");
				// 查表
				String sql = "SELECT time, typename, score, grade FROM tb_testscore t1 "
						+ "join tb_scoretype t2 on t1.scoretype = t2.typeid " + "WHERE uid='" + uid
						+ "' AND scoretype=2 " + "ORDER BY time DESC";
				QueryRowsetToTable(request, response, sql);
				request.getRequestDispatcher("student/showTestReport.jsp").forward(request, response);
				session.setAttribute("selector", 2);
			}

		} else {
			response.sendRedirect("tch_home.jsp"); // 被filter拦截后会正确导向std_home.jsp
													// 或 tch_home.jsp
		}
	}
}
