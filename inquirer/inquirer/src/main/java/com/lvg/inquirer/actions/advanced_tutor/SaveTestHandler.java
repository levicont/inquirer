package com.lvg.inquirer.actions.advanced_tutor;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.TestDataService;

public class SaveTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 7901956274459674765L;
	private final Logger LOGGER = Logger.getLogger(SaveTestHandler.class);
	private static final TestDataService testManager = new TestDataBaseManager();
	
	

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if ("all_questions".equals(action)) {
			updateTest(request, response);
			String testId = request.getParameter("test");
			redirectRequest("/all_questions.php?test_id=" + testId, request, response);
		} else {

			if ("cancel".equals(request.getParameter("action"))) {
				redirectRequest("/all_tests.php", request, response);
			} else {
				if ("new_test".equals(action)) {
					saveNewTest(request, response);
				} else {
					if ("edit".equals(action)) {
						updateTest(request, response);
						redirectRequest("/all_tests.php", request, response);
					} else {
						LOGGER.warn("Action is not determinated.");
						redirectRequest("/all_tests.php", request, response);
					}

				}
			}
		}
	}

	private void saveNewTest(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		Test newTest = null;
		
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		try {
			checkFields(request);
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Integer timeLimit= Integer.parseInt(request.getParameter("timeLimit"));
			newTest = new Test();
			newTest.setTitle(title);
			newTest.setDescription(description);
			newTest.setTimeLimit(timeLimit);
			newTest.setAuthor((Account) request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT));
			testManager.addTest(newTest);
			redirectRequest("/all_tests.php", request, response);
		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to save test.", ex);
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ex.getMessage()));
			gotoToJSP("/advanced_tutor/add_test.jsp", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to save test.", ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/add_test.jsp", request, response);
		}
	}

	private void updateTest(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		Test updatedTest = null;
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		try {
			updatedTest = testManager.getTest(Integer.parseInt(request.getParameter("test")));
			checkFields(request);
			String title = request.getParameter("title");
			String description = request.getParameter("description");
			Integer timeLimit= Integer.parseInt(request.getParameter("timeLimit"));	
			
			updatedTest.setTitle(title);
			updatedTest.setDescription(description);
			updatedTest.setTimeLimit(timeLimit);
			testManager.updateTest(updatedTest);

		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to update test.", ex);
			fillRequestByOldData(request, updatedTest);
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ex.getMessage()));
			gotoToJSP("/advanced_tutor/edit_test.jsp", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to update test.", ex);
			fillRequestByOldData(request, updatedTest);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/edit_test.jsp", request, response);
		}
	}
	
	private void fillRequestByOldData(HttpServletRequest request, Test updatedTest){
		request.setAttribute("TITLE", updatedTest.getTitle());
		request.setAttribute("DESCRIPTION", updatedTest.getDescription());
		request.setAttribute("TIME_LIMIT", updatedTest.getTimeLimit());
		request.setAttribute("EDITED_TEST_ID", updatedTest.getId());
	}

	private void checkFields(HttpServletRequest request) throws InvalidDataException {
		String title = request.getParameter("title");
		if (!StringUtils.isNotBlank(title))
			throw new InvalidDataException(ERR_EMPTY_TITLE);
		try {
			Integer timeLimit = Integer.parseInt(request.getParameter("timeLimit"));
			if (timeLimit <= 0)
				throw new InvalidDataException(ERR_TEST_TIME_LIMIT);
		} catch (NumberFormatException ex) {
			throw new InvalidDataException(ERR_TEST_TIME_LIMIT);
		}
		
	}

}
