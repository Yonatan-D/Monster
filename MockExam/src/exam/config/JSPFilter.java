package exam.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mybean.data.User;

@WebFilter("*.jsp")
public class JSPFilter extends HttpServlet implements Filter, ExamConfig{
	private static final long serialVersionUID = 1L;

	public void init(FilterConfig arg0) throws ServletException {}

	public void destroy() {}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(true);
		User user = (User) session.getAttribute("user");
		if(user == null) { // ���û�״̬
			request.getRequestDispatcher("index.jsp").forward(request, response);
		}
		else if(user.getUserType() == USERTYPE_STD) { // ����״̬
			request.getRequestDispatcher("student/std_home.jsp").forward(request, response);
		}
		else if(user.getUserType() == USERTYPE_TCH) { // ����Ա״̬
			request.getRequestDispatcher("teacher/tch_home.jsp").forward(request, response);
		}
	}
}
