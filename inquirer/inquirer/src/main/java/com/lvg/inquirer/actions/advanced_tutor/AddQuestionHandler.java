package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.TestDataService;

public class AddQuestionHandler extends AbstractInquirerServletHandler {
	
	private static final long serialVersionUID = -3027824626283519130L;
	private static final TestDataService testManager = new TestDataBaseManager();
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer test_id = Integer.parseInt(request.getParameter("test_id"));
		request.setAttribute("QUESTION_NUMBER", questionManager.getNextQuestionNumberByTest(testManager.getTest(test_id)));
		request.setAttribute("TEST_ID", request.getParameter("test_id"));
		request.setAttribute("DEFAULT_ANSWERS_COUNT", DEFAULT_ANSWERS_COUNT);
		gotoToJSP("/advanced_tutor/add_question.jsp", request, response);

	}

}
