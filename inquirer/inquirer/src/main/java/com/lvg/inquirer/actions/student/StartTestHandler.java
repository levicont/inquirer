package com.lvg.inquirer.actions.student;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AnswerDataBaseManager;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Answer;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.AnswerDataService;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.TestDataService;

public class StartTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 5284531398902849690L;
	private static final Logger LOGGER = Logger.getLogger(StartTestHandler.class);
	private static final TestDataService testManager = new TestDataBaseManager();
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final AnswerDataService answerManager = new AnswerDataBaseManager();
	
	private static final String FAIL_ANSWERS_LIST_ATTR = "FAIL_ANSWERS_LIST";

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer testId;
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		try {			
			testId = Integer.parseInt(request.getParameter("id"));
			Test test = testManager.getTest(testId);
			checkTest(test);
			checkTimeStamp(request);
			List<Question> questionsList = questionManager.getQuestionsByTest(test);
			Integer questionsCount = questionsList.size();

			List<Answer> answersList = answerManager.getAnswerListByQuestion(questionsList.get(0));
			TestResult testResult = new TestResult();
						
			
			testResult.setAccount((Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT));
			testResult.setTest(test);
			
			request.getSession().setAttribute("CURRENT_TEST_RESULT", testResult);
			request.getSession().removeAttribute("CURRENT_TEST_MISTAKES");
			request.getSession().removeAttribute(FAIL_ANSWERS_LIST_ATTR);
			request.setAttribute("TEST", test);
			request.setAttribute("QUESTIONS_COUNT", questionsCount);
			request.setAttribute("QUESTION", questionsList.get(0));
			request.setAttribute("ANSWERS_LIST", answersList);

			gotoToJSP("/student/question.jsp", request, response);
		}catch(InvalidDataException ex){
			LOGGER.warn("Not possible to start test", ex);
			request.setAttribute(VALIDATION_MESSAGE,errMessage.getString(ex.getMessage()));
			forwardRequest("/all_tests.php", request, response);
		}catch (InquirerDataException ex) {
			LOGGER.warn("Not possible to start test", ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			forwardRequest("/all_tests.php", request, response);
			
		}catch(NumberFormatException ex){
			LOGGER.warn("Not possible to load test", ex);
			request.setAttribute(VALIDATION_MESSAGE,errMessage.getString(ERR_NOT_SELECTED_TEST));
			forwardRequest("/all_tests.php", request, response);
		}

	}
	private void checkTimeStamp(HttpServletRequest request)throws InquirerDataException{
			ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
			if(null == request.getSession().getAttribute("TIME_STAMP")){
				request.getSession().setAttribute("TIME_STAMP", System.currentTimeMillis());
			}else{
				request.getSession().removeAttribute("TIME_STAMP");
				throw new InquirerDataException(errMessage.getString(ERR_TIME_STAMP_KEY));
			}
				
	}

	private void checkTest(Test test) throws InquirerDataException, InvalidDataException {
		if (questionManager.getQuestionsByTest(test).size() <= 0)
			throw new InvalidDataException(ERR_NO_MORE_QUESTIONS);
	}

}
