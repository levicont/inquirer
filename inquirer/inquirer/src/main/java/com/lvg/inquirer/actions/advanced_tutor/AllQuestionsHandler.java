package com.lvg.inquirer.actions.advanced_tutor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.TestDataService;

public class AllQuestionsHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -2115529728366397544L;
	private final Logger LOGGER = Logger.getLogger(AllQuestionsHandler.class);
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final TestDataService testManager = new TestDataBaseManager();
	private final String ITEMS_PAGE = "page";
	private final Integer ITEMS_ON_PAGE = 5;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		checkParameters(request, response);

		gotoToJSP("/advanced_tutor/questions_table.jsp", request, response);

	}

	private void checkParameters(HttpServletRequest request, HttpServletResponse response)
			throws InquirerDataException, InvalidDataException {
		List<Question> questionList = null;
		Integer testId = null;
		Integer page = 1;
		try {

			questionList = null;
			String id = request.getParameter("test_id");
			LOGGER.info("Test id = "+id);
			testId = Integer.parseInt(id);

			if (testId == null) {
				questionList = questionManager.getQuestionList();
			} else {
				Test test = testManager.getTest(testId);
				
				LOGGER.info("Test id = "+test.getId());
				questionList = questionManager.getQuestionsByTest(test);
				request.setAttribute("CURRENT_TEST", test);

			}
			request.setAttribute("test_id", testId);
			request.getServletContext().setAttribute("QUESTIONS_LIST", questionList);
			request.setAttribute("QUESTIONS_ITEMS", testManager.getTestList().size());
			request.setAttribute("ITEMS_ON_PAGE", ITEMS_ON_PAGE);
			request.setAttribute(ITEMS_PAGE, page);
			if (request.getParameter(ITEMS_PAGE) != null) {
				page = Integer.parseInt(request.getParameter(ITEMS_PAGE));
				if (page < 1)
					page = 1;
			}

		} catch (NumberFormatException ex) {
			LOGGER.warn("Not possible to determinate the id of test. ID=" + testId, ex);
			questionList = questionManager.getQuestionList();
		}
	}

}
