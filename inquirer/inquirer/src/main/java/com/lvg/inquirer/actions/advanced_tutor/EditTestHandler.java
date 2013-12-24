package com.lvg.inquirer.actions.advanced_tutor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.TestDataService;

public class EditTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 3383190825502276741L;
	private static final TestDataService testManager = new TestDataBaseManager();

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		checkTest(request, response);
		Test editedTest = (Test)request.getAttribute("EDITED_TEST");
		if (editedTest == null) {			
			redirectRequest("/all_tests.php", request, response);
		} else {
			request.setAttribute("EDITED_TEST_ID", editedTest.getId());
			request.setAttribute("TITLE", editedTest.getTitle());
			request.setAttribute("DESCRIPTION", editedTest.getDescription());
			request.setAttribute("TIME_LIMIT", editedTest.getTimeLimit());
						
			gotoToJSP("/advanced_tutor/edit_test.jsp", request, response);
		}
	}

	private void checkTest(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException, InvalidDataException, InquirerDataException {
		Test editedTest = null;
		try{
			Integer id = Integer.parseInt(request.getParameter("id"));
			editedTest = testManager.getTest(id);
			request.setAttribute("EDITED_TEST", editedTest);
						
		}catch(NumberFormatException ex){
			request.setAttribute(VALIDATION_MESSAGE, "Invalid test id "+ex.getMessage());
			
		}
	}

}
