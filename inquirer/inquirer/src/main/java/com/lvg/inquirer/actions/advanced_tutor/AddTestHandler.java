package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class AddTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -4944720384553940899L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		gotoToJSP("/advanced_tutor/add_test.jsp", request, response);
	}

}
