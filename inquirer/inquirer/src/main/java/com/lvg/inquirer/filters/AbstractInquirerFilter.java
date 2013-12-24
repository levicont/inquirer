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

public abstract class AbstractInquirerFilter implements Filter {

	public void init(FilterConfig filterConfig) throws ServletException {
		
	}

	public final void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp = (HttpServletResponse)response;
		
		doFilter(req,resp,chain);

	}
	
	public abstract void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)throws IOException, ServletException;

	public void destroy() {
		
	}

}
