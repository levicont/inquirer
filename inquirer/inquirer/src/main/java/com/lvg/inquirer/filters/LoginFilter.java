package com.lvg.inquirer.filters;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

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
				
		ResourceBundle bundle = ResourceBundle.getBundle(RESOURCE_BUNDLE_PROPERTY_FILE, getCurrentLocale(request));
		
		
		HttpSession session = request.getSession();
		RequestDispatcher rd = request.getRequestDispatcher("/login.php");
		String uri = request.getRequestURI();
		request.setCharacterEncoding("UTF8");
		
		
		//Locale check
		checkLocale(request, bundle);
		if(null == session.getAttribute(RESOURCE_BUNDLE)){
			request.getSession().setAttribute(RESOURCE_BUNDLE, bundle);
			request.getSession().removeAttribute(CURRENT_SESSION_ACCOUNT);
			request.getRequestDispatcher("/login.php").forward(request, response);			
			return;
		}
		
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
	
	private void checkLocale(HttpServletRequest request, ResourceBundle bundle){
		 
		if(null==request.getSession().getAttribute(RESOURCE_BUNDLE)){
			bundle.getLocale();
			request.getSession().setAttribute(RESOURCE_BUNDLE, bundle);
		}else{
			String oldLang = ((ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE)).getLocale().getLanguage();
			if(bundle.getLocale().getLanguage().equals(oldLang))
				return;
			else{
				request.getSession().removeAttribute(RESOURCE_BUNDLE);				
			}
		}
	}
	
	private Locale getCurrentLocale(HttpServletRequest request){
		String defaultLocale = request.getHeader("Accept-Language").substring(0, 2); 
		LOGGER.debug("<-- defaultLocale = "+defaultLocale+" -->");
		if(null != request.getParameter("lang")){
			String newLang = request.getParameter("lang");
			LOGGER.debug("<-- newLang = "+newLang+" -->");
			if(newLang.equals("en")){
				request.getSession().setAttribute("LANG", newLang);
				return  new Locale(newLang);
			}else{
				if(newLang.equals("ru")){
					request.getSession().setAttribute("LANG", newLang);
					return new Locale(newLang);
				}else{
					if(null != request.getSession().getAttribute("LANG"))
						request.getSession().removeAttribute("LANG");
					return new Locale(defaultLocale);
				}
			}
		}else{
			if(null != request.getSession().getAttribute("LANG")){
				LOGGER.debug("<-- LANG = "+request.getSession().getAttribute("LANG")+" -->");
				return new Locale(""+request.getSession().getAttribute("LANG"));
			}else{
				return new Locale(defaultLocale);
			}
		}		
	}

}
