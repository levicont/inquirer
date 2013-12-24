package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class AdvancedTutorHomeHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 720568055903709655L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		gotoToJSP("/advanced_tutor/home.jsp", request, response);
	}

}
