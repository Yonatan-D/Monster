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

@WebServlet("/QueryTestReport")
public class QueryTestReport extends TableController {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			String uid = user.getUid();
			String sql = "SELECT time, typename, score, grade FROM tb_testscore t1 "
					+ "join tb_scoretype t2 on t1.scoretype = t2.typeid " + "WHERE uid='" + uid
					+ "' ORDER BY time DESC";
			// ��״ͼ����
			ArrayList<String> qck = QueryDB(
					"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=1");
			ArrayList<String> mck = QueryDB(
					"SELECT count(*) FROM tb_testscore WHERE uid='" + uid + "' AND scoretype=2");
			float qckNum = Float.parseFloat(qck.get(0));
			float mckNum = Float.parseFloat(mck.get(0));
			float data = mckNum / (qckNum + mckNum) * 100;
			System.out.println(data);
			session.setAttribute("Evaluation", data);
			session.setAttribute("green", "���濼��");
			session.setAttribute("red", "������");
			session.setAttribute("selector", 0);

			try { // ѡ��鿴����
				int selector = (int) session.getAttribute("selector");
				if (selector == 1) {
					sql = "SELECT time, typename, score, grade FROM tb_testscore t1 "
							+ "join tb_scoretype t2 on t1.scoretype = t2.typeid " + "WHERE uid='" + uid
							+ "' AND scoretype=1 " + "ORDER BY time DESC";
				} else if (selector == 2) {
					sql = "SELECT time, typename, score, grade FROM tb_testscore t1 "
							+ "join tb_scoretype t2 on t1.scoretype = t2.typeid " + "WHERE uid='" + uid
							+ "' AND scoretype=2 " + "ORDER BY time DESC";
				}
			} catch (Exception e) {
			}
			QueryRowsetToTable(request, response, sql);
			request.getRequestDispatcher("student/showTestReport.jsp").forward(request, response);
		} else {
			response.sendRedirect("tch_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
													// �� tch_home.jsp
		}
	}
}
