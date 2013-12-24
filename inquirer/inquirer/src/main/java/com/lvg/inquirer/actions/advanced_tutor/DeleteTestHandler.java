package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.actions.student.ResultTestHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.TestDataService;

public class DeleteTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 2007771812975096471L;
	private static final TestDataService testManager = new TestDataBaseManager();


	private static final Logger LOGGER = Logger.getLogger(ResultTestHandler.class);

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Integer idTest;
		try {
			idTest = Integer.parseInt(request.getParameter("id"));
			Test test = testManager.getTest(idTest);
			testManager.deleteTest(test);
			redirectRequest("/all_tests.php", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to delete test", ex);
			redirectRequest("/all_tests.php", request, response);
		} catch(NumberFormatException ex){
			LOGGER.error("Not possible to delete test - invalid id", ex);
			redirectRequest("/all_tests.php", request, response);
		}
	}

}
