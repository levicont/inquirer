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

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;

public class LoginFilter implements Filter, InquirerConstants {
	private static final String USER_PARAMETER = "user";
	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);
    
	
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//TODO make valid filter
		HttpServletRequest httpRequest = null;
	    HttpServletResponse httpResponse = null;
		LOGGER.info("<<== Login filter starts ==>>");		
		httpRequest = (HttpServletRequest)request;
		httpResponse = (HttpServletResponse)response;
		HttpSession session = httpRequest.getSession();

		if(session.getAttribute(CURRENT_SESSION_ACCOUNT)==null){
			httpResponse.sendRedirect("/login.php");
			
		}else{
			httpResponse.sendRedirect("/login.php");
			chain.doFilter(httpRequest, httpResponse);
		}
		
		
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
