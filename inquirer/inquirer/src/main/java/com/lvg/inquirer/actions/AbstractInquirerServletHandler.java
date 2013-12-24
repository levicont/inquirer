package com.lvg.inquirer.actions;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.services.DataService;
import com.lvg.inquirer.services.InquirerServiceManager;

public abstract class AbstractInquirerServletHandler extends HttpServlet implements InquirerConstants{

	private static final long serialVersionUID = 4882419922289159448L;
	private final Logger LOGGER = Logger.getLogger(getClass());
	private final String PAGE_TEMPLATE_JSP_PATH ="/WEB-INF/templates/page-template.jsp";
	private final String ERROR_JSP = "error.jsp";
	private String contextName;
	private DataService dataService;
	
	
	@Override
	public final void init(ServletConfig config){
		contextName = config.getServletContext().getContextPath();
		dataService = InquirerServiceManager.getInstance(config.getServletContext()).getDataService();
	}
	
	
	
	@Override
	protected final void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		super.service(req, resp);
	}
	
	@Override
	public final void service(ServletRequest req, ServletResponse res)throws ServletException, IOException {
		super.service(req, res);
	}
	
	
	
	@Override
	protected final void doGet(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
		handleRequest0(req,resp);
	}


	@Override
	protected final void doHead(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		handleRequest0(req,resp);
	}

	@Override
	protected final void doPost(HttpServletRequest req, HttpServletResponse resp)	throws ServletException, IOException {
		handleRequest0(req,resp);
	}

	@Override
	protected final void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest0(req,resp);
	}

	@Override
	protected final void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest0(req,resp);
	}

	@Override
	protected final void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest0(req,resp);
	}

	@Override
	protected final void doTrace(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		handleRequest0(req,resp);
	}
	
	private void handleRequest0(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		try{
			preHandleRequest(request, response);
			handleRequest(request, response);
		}catch(Exception ex){
			LOGGER.error("Can not fulfill this request",ex);
			handleError(ex, request, response);
		}
	}
	
	/**
	 * Common request handler for all types of requests
	 */
	
	protected abstract void handleRequest (HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	protected void preHandleRequest (HttpServletRequest request, HttpServletResponse response) throws Exception {
		
	}
	
	protected final DataService getDataService() {
		return dataService;
	}
	
	protected final String getContextName() {
		return contextName;
	}
	
	/**
	 * Some useful methods
	 */
	
	protected final void sendTextResponse (String message, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		response.setContentLength(message.length());
		response.getWriter().write(message);
	}
	
	protected final void handleError (Exception ex, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("javax.servlet.error.exception", ex);
		gotoToJSP(ERROR_JSP, request, response);
	}

	protected final void gotoToJSP (String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("currentPage", "../JSP/"+page);
		request.getRequestDispatcher(PAGE_TEMPLATE_JSP_PATH).forward(request, response);
	}

	protected final void forwardRequest (String url, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(url).forward(request, response);
	}

	protected final void redirectRequest (String path, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.sendRedirect(this.getContextName() + path);
	}
	
	
	
	

	
	

}
