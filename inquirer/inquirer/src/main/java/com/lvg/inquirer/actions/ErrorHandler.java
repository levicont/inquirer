package com.lvg.inquirer.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ErrorHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 3988564369293612700L;

	@Override
	protected void handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		gotoToJSP("/error.jsp", request, response);
	}

}
