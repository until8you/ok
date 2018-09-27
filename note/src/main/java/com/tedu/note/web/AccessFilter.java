package com.tedu.note.web;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tedu.note.entity.User;

public class AccessFilter implements Filter {

	public void destroy() {
	}

	private String login = "log_in.html";
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//放过login.html
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		
		String path = req.getRequestURI();
//		System.out.println("access"+path);
//		System.out.println(req.getContextPath());
		if(path.endsWith(login)) {
			
			chain.doFilter(request, response);
			return;
		}
		//放过错误显示页面
		if(path.endsWith("alert_error.html")) {
			chain.doFilter(request, response);
			return;
		}
		if(path.endsWith("demoImg.html")) {
			chain.doFilter(request, response);
			return;
		}
		//校验登陆状态，没登陆，重定向到登陆页面
		User user = (User) session.getAttribute("loginUser");
		if(user==null) {
			//没有登陆 成定向到到登陆页 最好成定向到绝对路径中 避免错误
			res.sendRedirect(req.getContextPath()+"/"+login);
			return;
		}
		//登陆成功,放行
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
