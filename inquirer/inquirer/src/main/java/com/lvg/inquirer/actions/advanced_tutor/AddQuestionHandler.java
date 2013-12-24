package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class AddQuestionHandler extends AbstractInquirerServletHandler {
	
	private static final long serialVersionUID = -3027824626283519130L;
	
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		request.setAttribute("TEST_ID", request.getParameter("test_id"));
		request.setAttribute("DEFAULT_ANSWERS_COUNT", DEFAULT_ANSWERS_COUNT);
		gotoToJSP("/advanced_tutor/add_question.jsp", request, response);

	}

}
