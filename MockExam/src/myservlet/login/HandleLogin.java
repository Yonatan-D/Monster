package myservlet.login;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import exam.config.DBMServlet;
import mybean.data.User;

@WebServlet("/HandleLogin")
public class HandleLogin extends DBMServlet {
	private static final long serialVersionUID = 1L;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(true);
		User user = null; // 创建user对象，存储用户数据
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

		String uid = request.getParameter("uid");
		String pwd = request.getParameter("pwd");
		String userType = request.getParameter("R");
		String sql = null;
		if(userType.equals("student")) { //考生登录
			sql = "SELECT pwd FROM tb_user WHERE uid = '"+uid+"' AND usertype=1";
			ArrayList<String> list = QueryDB(sql);
			if(list.size()!=0) {
				if(list.get(0).equals(pwd)) { //密码正确将用户信息读入session
					sql = "SELECT uid, name, sex, email, phone FROM tb_user WHERE uid = '"+uid+"' AND usertype=1";
					list = QueryDB(sql);
					user.setUid(list.get(0));
					user.setName(list.get(1));
					user.setSex(list.get(2));
					user.setEmail(list.get(3));
					user.setPhone(list.get(4));
					user.setUserType(USERTYPE_STD);
					request.getRequestDispatcher("student/std_home.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}	
			}else {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}else if(userType.equals("teacher")) { //管理员登录
			sql = "SELECT pwd FROM tb_user WHERE uid = '"+uid+"' AND usertype=2";
			ArrayList<String> list = QueryDB(sql);
			if(list.size()!=0) {
				if(list.get(0).equals(pwd)) {
					sql = "SELECT uid, name, registdate FROM tb_user WHERE uid = '"+uid+"' AND usertype=2";
					list = QueryDB(sql);
					user.setUid(list.get(0));
					user.setName(list.get(1));
					user.setRegistdate(list.get(2));
					user.setUserType(USERTYPE_TCH);
					sql = "UPDATE tb_user SET registdate=now() WHERE uid = '"+uid+"' AND usertype=2";
					updateDB(request, response, sql);
					request.getRequestDispatcher("teacher/tch_home.jsp").forward(request, response);
				}else {
					request.getRequestDispatcher("index.jsp").forward(request, response);
				}	
			}else {
				request.getRequestDispatcher("index.jsp").forward(request, response);
			}
		}	
	}
}
