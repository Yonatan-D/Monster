package myservlet.student;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import exam.config.ExamTest;
import mybean.data.Test;
import mybean.data.User;

@WebServlet("/EndMockTest")
public class EndMockTest extends DBMServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * �����ύ��������
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		session.removeAttribute("mocktest"); // �Ƴ�mocktest
		request.getRequestDispatcher("student/std_home.jsp").forward(request, response);
		;
	}

	/**
	 * �ύ�Ծ�鿴����
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		if (user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		} else if (user.getUserType() == USERTYPE_STD) {
			String uid = user.getUid(); // �û�ID
			String name = user.getName(); // �û��ǳ�
			Test mocktest = (Test) session.getAttribute("mocktest");
			String startTime = mocktest.getStartTime(); // ���Կ�ʼʱ��

			ExamTest examTest = new ExamTest(); // ���㿼��ɼ�
			int score = examTest.executeExamScore(request, response, mocktest);
			session.removeAttribute("mocktest"); // �Ƴ�mocktest
			String grade = formatScore(score); // �������Խ��
			System.out.println("score:" + score + "grade:" + grade);

			String sql = "INSERT INTO tb_testscore value(null,'" + uid + "'," + score + ",'" + grade + "','" + startTime
					+ "', 2)";
			insertDB(request, response, sql); // ���濼�Լ�¼�����ݿ�
			notify(request, response, name + "�����÷֣�" + score + "  ���Խ����" + grade);
		} else {
			response.sendRedirect("tch_home.jsp"); // ��filter���غ����ȷ����std_home.jsp
													// �� tch_home.jsp
		}
	}

	/**
	 * �����ɼ��ȼ�
	 * 
	 * @param score
	 * @return
	 */
	public String formatScore(int score) {
		String grade = "";
		if (score >= 90)
			grade = "ͨ��";
		else
			grade = "��ͨ��";
		return grade;
	}

	@Override
	public void notify(HttpServletRequest request, HttpServletResponse response, String backNews) {
		response.setContentType("text/html; charset=UTF-8");
		try {
			PrintWriter out = response.getWriter();
			out.println("<html><body>");
			out.println("<h2>" + backNews + "</h2>");
			out.println("<a href='std_home.jsp'>�ص���ҳ</a>");
			out.println("<a href='StartMockTest'>��������</a>");
			out.println("</body></html>");
		} catch (IOException e) {
		}
	}
}
