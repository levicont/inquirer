package com.lvg.inquirer.actions.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.ResultDataBaseManager;
import com.lvg.inquirer.mocks.TestMistakeDataBaseManager;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.TestMistake;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.ResultDataService;
import com.lvg.inquirer.services.TestMistakeDataService;

public class ResultTestHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -4224304914494370209L;
	private static final ResultDataService resultManager = new ResultDataBaseManager();
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final TestMistakeDataService mistakeManager = new TestMistakeDataBaseManager();
	
	private static final Logger LOGGER  = Logger.getLogger(ResultTestHandler.class);
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		TestResult testResult = (TestResult)request.getSession().getAttribute("CURRENT_TEST_RESULT");
		List<Question> questionList = questionManager.getQuestionsByTest(testResult.getTest());
		@SuppressWarnings("unchecked")
		List<TestMistake> testMistakes = (List<TestMistake>)request.getSession().getAttribute("CURRENT_TEST_MISTAKES");
		
		request.setAttribute("QUESTIONS_COUNT", questionList.size());
		saveTestResult(testResult);
		saveMistakes(testMistakes);
		gotoToJSP("/student/result.jsp", request, response);

	}
	
	private void saveTestResult(TestResult testResult){
		try{
			resultManager.insertTestResult(testResult);
			
		}catch(InquirerDataException ex){
			LOGGER.error("Not possible to save test result whith id: "+testResult.getId(), ex);
			
		}catch(InvalidDataException ex){
			LOGGER.error("Not possible to save test result whith id: "+testResult.getId(), ex);
			
		}
	}
	
	private void saveMistakes(List<TestMistake> testMistakes){
		try{
			if(testMistakes.size()>7){
				for(TestMistake mistake : testMistakes){
					mistakeManager.addTestMistake(mistake);
				}
			}
		}catch(InquirerDataException ex){
			LOGGER.error("Not possible to save test mistakes");
		}catch(InvalidDataException ex){
			LOGGER.error("Not possible to save test mistakes");
		}
	}

}
