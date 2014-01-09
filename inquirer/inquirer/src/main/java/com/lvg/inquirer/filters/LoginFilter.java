package com.lvg.inquirer.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;

public class LoginFilter extends AbstractInquirerFilter implements Filter, InquirerConstants {
	private static final Logger LOGGER = Logger.getLogger(LoginFilter.class);

	public void destroy() {

	}

	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		LOGGER.info("<<== Login filter starts. Requested URL is: "+request.getRequestURL());
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("/login.php");
		String uri = request.getRequestURI();
		request.setCharacterEncoding("UTF8");
		if("POST".equals(request.getMethod())){
			chain.doFilter(request, response);
			return;
		}
		if(uri.indexOf("/resources")>0){
			chain.doFilter(request, response);
			return;
		}else{
			if(uri.indexOf("/register.php")>0){
				chain.doFilter(request, response);
				return;
			}else{
				if(uri.indexOf("/recover.php")>0){
					chain.doFilter(request, response);
					return;
				}else{
					if (session.getAttribute(CURRENT_SESSION_ACCOUNT) == null){
						rd.forward(request, response);
						return;
					}else{
						chain.doFilter(request, response);
						return;
					}
				}
			}
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
