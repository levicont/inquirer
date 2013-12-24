package com.lvg.inquirer.actions.tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class TutorHomeHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 5828723362767246577L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		gotoToJSP("/tutor/home.jsp", request, response);
		
	}

}
