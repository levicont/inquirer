package com.lvg.inquirer.actions.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class StudentHomeHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 4055528413446097972L;

	@Override
	protected void handleRequest(HttpServletRequest request,HttpServletResponse response) throws Exception {
		gotoToJSP("/student/home.jsp", request, response);

	}

}
