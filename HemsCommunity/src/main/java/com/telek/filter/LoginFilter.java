package com.telek.filter;

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

import net.sf.json.JSONObject;

import com.telek.model.UserEntity;
import com.telek.util.Util;

/**
 * Servlet Filter implementation class LoginFilter
 */
public class LoginFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public LoginFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}
	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 获得在下面代码中要用的request,response,session对象
		HttpServletRequest servletRequest = (HttpServletRequest) request;
		HttpServletResponse servletResponse = (HttpServletResponse) response;
		HttpSession session = servletRequest.getSession();
		
		// 设置返回参数的中文编码格式
		response.setCharacterEncoding("utf-8");
		
		// 防止跨域访问所产生的问题
		servletResponse.setHeader("Access-Control-Allow-Origin",
		servletRequest.getHeader("Origin"));
		servletResponse.setHeader("Access-Control-Allow-Credentials", "true");// 支持cookie跨域

		// 输出进入过滤器的标识
		System.out.println("=========================进过滤器了==========================");
		
		// 获得用户请求的URI，判断用户进入的页面
		String path = servletRequest.getRequestURI();
		String pageName = "";
		if ("".equals(path.replace("/MicroGridDemo/", ""))) {
			pageName = "login";
		} else {
			// 该if条件是为了判断http://localhost:8080/BuildingMS/build/jsp/logi的类似情况
			if (path.lastIndexOf(".") > path.lastIndexOf("/")) {
				pageName = path.substring(path.lastIndexOf("/"), path.lastIndexOf("."));
			}
		}
		
		// 分两种情况，后台用户登录和前台用户登录
		// 后台用户登录
				// 当前用户登录
				UserEntity admin = (UserEntity) session.getAttribute("loginUser");
				// 用户可以访问的页面
				String prvlgMenuBtn = (String) session.getAttribute("prvlgMenuBtnJsp");
//				// 登陆无需过滤
//				if (path.indexOf("/login.jsp") > -1 || path.indexOf("/transition.html") > -1) {
//					chain.doFilter(servletRequest, servletResponse);
//					return;
//				}
//				// 判断如果没有取到员工信息,就跳转到登陆页面
//				if (admin == null) {
//					// 跳转到登陆页面
//					servletResponse.sendRedirect("/MicroGridDemo/jsp/transition.html");
//				} else if (!prvlgMenuBtn.contains(pageName)) {
//					servletResponse.sendRedirect("/MicroGridDemo/jsp/prvlgerror.html");
//				} else {
//					// 已经登陆,继续此次请求
//					chain.doFilter(request, response);
//				}
		UserEntity loginUser = (UserEntity)session.getAttribute("loginUser");
		System.out.println("本次登录的用户是"+session.getAttribute("loginUser"));
		System.out.println("==========这是第一次修改===============出过滤器了==========================");
//		
		System.out.println(servletRequest.getRequestURL().toString());
		chain.doFilter(request, response);
//		if (loginUser!=null) {
//			chain.doFilter(request, response);
//		} else {
//			
//			if (!servletRequest.getRequestURL().toString().contains("frontstageUserLogin")
//					&&!servletRequest.getRequestURL().toString().contains("index")
//					&&!servletRequest.getRequestURL().toString().contains("backstageUserLogin")
//					&&!servletRequest.getRequestURL().toString().contains("util")
//					&&!servletRequest.getRequestURL().toString().contains("static")
//					&&!servletRequest.getRequestURL().toString().contains("Manager")
//					&&!servletRequest.getRequestURL().toString().contains("InformationImage_test")
//					&&!servletRequest.getRequestURL().toString().contains("queryinformationByType")){
//				System.out.println(servletRequest.getRequestURL().toString());
//				JSONObject backJSON = new JSONObject();
//				backJSON.put("sysError", "session_timeout");
//				servletResponse.getWriter().write(backJSON.toString());
//				servletResponse.getWriter().flush();
//				return;
//			}
//			chain.doFilter(request, response);
//		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
