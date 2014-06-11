package com.lvg.inquirer.actions.student;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.ResultDataBaseManager;
import com.lvg.inquirer.mocks.TestMistakeDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.models.TestMistake;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.ResultDataService;
import com.lvg.inquirer.services.TestMistakeDataService;

public class TestMistakesHandler extends AbstractInquirerServletHandler {

	
	private static final long serialVersionUID = 8692888773500211895L;
	
	private static final ResultDataService resultManager = new ResultDataBaseManager();
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final TestMistakeDataService mistakeManager = new TestMistakeDataBaseManager();
		
	private static final Logger LOGGER  = Logger.getLogger(ResultTestHandler.class);

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		TestResult testResult;
		String idParameter = request.getParameter("id");
		try{
			checkTestResultId(idParameter);
			testResult = resultManager.getTestResult(Integer.parseInt(idParameter));
			//TODO check Account of current test result
			Account currentAccount  = (Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);
			Role currentRole = (Role)request.getSession().getAttribute(CURRENT_SESSION_ROLE);
			checkTestResultToAccountRole(testResult, currentRole, currentAccount);
						
			
			List<TestMistake> mistakes = mistakeManager.getMistakesByResult(testResult);
			List<Question> questionList = questionManager.getQuestionsByTest(testResult.getTest());
			
			request.setAttribute("CURRENT_TEST_RESULT", testResult);
			request.setAttribute("CURRENT_TEST_MISTAKES", mistakes);
			request.setAttribute("QUESTIONS_COUNT", questionList.size());
			
			gotoToJSP("/student/result.jsp", request, response);
			
		}catch(InvalidDataException ex){
			LOGGER.error("Not possible to load test result "+ex.getMessage());
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ERR_LOAD_TEST_RESULT));
			forwardRequest("/all_results.php", request, response);
		}catch(InquirerDataException ex){
			LOGGER.error("Not possible to load test result "+ex.getMessage());
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ERR_LOAD_TEST_RESULT));
			forwardRequest("/all_results.php", request, response);
		}

	}
	
	private void checkTestResultId(String idParameter)throws InquirerDataException, InvalidDataException{
		try{
			Integer id = Integer.parseInt(idParameter);
			TestResult testResult = resultManager.getTestResult(id);
			
			if(null == testResult){
				throw new InvalidDataException("Not possible to load test result");
			}
		
		}catch(NumberFormatException ex){
			LOGGER.error("Invalid id of test result");
			throw new InvalidDataException("Invalid id of test result");
		}
	}
	
	private void checkTestResultToAccountRole(TestResult testResult, Role currentRole, Account currentAccount)throws InquirerDataException, InvalidDataException{
		if(testResult.getAccount().equals(currentAccount)){
			return;
		}else{
			if(currentRole.getId() == ROLE_ADMIN || currentRole.getId() == ROLE_ADVANCED_TUTOR
					|| currentRole.getId() == ROLE_TUTOR){
				return;
			}else{
				LOGGER.error("Invalid id of test result");
				throw new InvalidDataException("Invalid id of test result");
			}
		}
	}
	
	

}
