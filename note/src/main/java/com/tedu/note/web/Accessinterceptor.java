package com.tedu.note.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tedu.note.entity.User;
import com.tedu.note.util.JsonResult;

@Component
public class Accessinterceptor implements HandlerInterceptor {

	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String path = request.getRequestURI();
//		System.out.println("interceptor:"+path);
		//如果没有登陆，返回错误的json
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("loginUser");
		if(user==null) {
			JsonResult result = new JsonResult("需要重新登陆");
			//利用response返回结果
			response.setContentType("application/json;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			//将javabean转换成json
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(result);
			//通过response流传送json
			response.getWriter().println(json);
			response.flushBuffer();
			return false;
		}
		//如果登陆 请求放行
		return true;//请求放行
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {

	}

	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}

}
