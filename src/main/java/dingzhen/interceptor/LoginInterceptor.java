package dingzhen.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import dingzhen.entity.User;

public class LoginInterceptor extends HandlerInterceptorAdapter{
	
	
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		// 请求的路径
		String url = request.getServletPath().toString();
		HttpSession session = request.getSession();
		User currentUser = ((User) session.getAttribute("currentUser"));
		if (currentUser == null) {
			if (url.contains("login") || url.contains("Login")) {
				return true;
			}
			request.getRequestDispatcher("login.jsp").forward(request, response);
	        return true;
		}
		return true;
	}
}
