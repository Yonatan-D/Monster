package myservlet.login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/UserRegist")
public class UserRegist extends DBMServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("registForm.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("name");
		String sex = request.getParameter("sex");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String pwd = request.getParameter("pwd");

		// 显示生成账号
		User user = null;
		HttpSession session = request.getSession(true);
		try {
			user = (User) session.getAttribute("user");
			if (user == null) {
				user = new User();
				session.setAttribute("user", user);
			}
		} catch (Exception e) {
			user = new User();
			session.setAttribute("user", user);
		}
		String uid = getRandomUID();// 获得随机账号
		user.setUid(uid);
		String sql = "INSERT INTO tb_user value('" + uid + "','" + pwd + "','" + name + "','" + sex + "','" + email
				+ "','" + phone + "', now(), 1)";
		if (insertDB(request, response, sql)) {
			request.getRequestDispatcher("registSuccess.jsp").forward(request, response);
		} else {
			notify(request, response, "注册失败！");
		}
	}

	public String getRandomUID() throws ServletException, IOException {

		String uid = "";
		for (int k = 0; k < 10; k++) {
			for (int i = 0; i < USERIDNUM; i++) {
				int num = (int) (Math.random() * 9);
				uid += String.valueOf(num);
			}
			System.out.println(uid);//
			if (!isFirstNumZero(uid)) { // 去除首位为0的账号
				if (isNoRepeatUid(uid))
					return uid;// 数据库查重
			}
			uid = "";
		}
		return uid;
	}

	public boolean isNoRepeatUid(String uid) throws ServletException, IOException {
		boolean result = false;
		String sql = "SELECT count(uid) ct FROM tb_user WHERE uid='" + uid + "'";
		ArrayList<String> list = QueryDB(sql);
		if (list.size()!=0) {
			if(list.get(0).equals("0")) result = true;
		}
		return result;
	}

	public boolean isFirstNumZero(String uid) {
		boolean result = false;
		String[] numarr = uid.split("");
		if (numarr[0].equals("0"))
			result = true;
		return result;
	}
}
