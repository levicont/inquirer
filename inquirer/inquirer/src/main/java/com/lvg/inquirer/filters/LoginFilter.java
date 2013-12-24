package com.lvg.inquirer.filters;

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

public class LoginFilter implements Filter {
	private static final String USER_PARAMETER = "user";
    HttpServletRequest request = null;
    HttpServletResponse response = null;
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest)request).getSession();
		this.request = (HttpServletRequest)request;
		this.response = (HttpServletResponse)response;
		String user;
		session.setAttribute("error", "");
		if(session.getAttribute(USER_PARAMETER)==null){
			this.response.sendRedirect("./WEB-INF/JSP/login.jsp");
		}else{
			user=session.getAttribute(USER_PARAMETER)+"";
			if(user.isEmpty())
				
				this.response.sendRedirect("/login.php");				
		}
		chain.doFilter(request, response);
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
