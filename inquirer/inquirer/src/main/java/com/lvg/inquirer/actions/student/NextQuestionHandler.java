package com.lvg.inquirer.actions.student;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AnswerDataBaseManager;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.models.Answer;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.AnswerDataService;
import com.lvg.inquirer.services.QuestionDataService;

public class NextQuestionHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -6024070595855617782L;
	private static final Logger LOGGER = Logger.getLogger(NextQuestionHandler.class);
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final AnswerDataService answerManager = new AnswerDataBaseManager();

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if(null != request.getSession().getAttribute("TIME_STAMP"))
			request.getSession().removeAttribute("TIME_STAMP");
		String action = request.getParameter("action");
		if ("cancel".equals(action)) {
			
			redirectRequest("/all_tests.php", request, response);
		} else {
			Integer questionId = Integer.parseInt(request.getParameter("question"));
			Question question = questionManager.getQuestion(questionId);
			Test test = question.getTest();
			List<Question> questionList = questionManager.getQuestionsByTest(test);
			Integer indexQuestion = questionList.indexOf(question);
			request.setAttribute("CHECKED_QUESTION", question);
			if (isLastQuestionOfTest(question)) {				
				updateResult(request, response);
				checkQuestionCount(request, response);				
			} else {
				Question nextQuestion = questionList.get(indexQuestion + 1);
				List<Answer> answersList = answerManager.getAnswerListByQuestion(nextQuestion);
				updateResult(request, response);
				request.setAttribute("TEST", test);
				request.setAttribute("QUESTION", nextQuestion);
				request.setAttribute("ANSWERS_LIST", answersList);
				gotoToJSP("/student/question.jsp", request, response);
			}
		}
	}

	private Boolean isLastQuestionOfTest(Question question) throws InvalidDataException {
		try {
			List<Question> questionList = questionManager.getQuestionsByTest(question.getTest());
			return questionList.indexOf(question) + 1 == questionList.size();
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to show next question page", ex);
			return false;
		}
	}

	private void updateResult(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException, InquirerDataException, InvalidDataException {
		TestResult testResult = (TestResult) request.getSession().getAttribute("CURRENT_TEST_RESULT");
		if (validAnswer(request, response)) {
			testResult.setCorrectAnswers(testResult.getCorrectAnswers() + 1);
		} else {
			testResult.setFailAnswers(testResult.getFailAnswers() + 1);
		}
		updateSessionQuestionCount(request.getSession());
		request.getSession().setAttribute("CURRENT_TEST_RESULT", testResult);

	}

	private int getQuestionCount(Test test){		
		try{			
			return (questionManager.getQuestionsByTest(test)).size();
		}catch(InquirerDataException ex){
			LOGGER.warn("Not possible to calculate questions count",ex);
			return -1;
		}catch(InvalidDataException ex){
			LOGGER.warn("Not possible to calculate questions count",ex);
			return -1;
		}
	}
	
	private void checkQuestionCount(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException{
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		Integer currentQuestionCount = (Integer)request.getSession().getAttribute("QUESTION_COUNT");
		Test currentTest = ((Question)request.getAttribute("CHECKED_QUESTION")).getTest();
		LOGGER.debug("Current question count: "+currentQuestionCount);
		LOGGER.debug("Questions count in test is: "+getQuestionCount(currentTest));
		if(currentQuestionCount==getQuestionCount(currentTest)){
			redirectRequest("/result_test.php", request, response);
		}else{
			LOGGER.warn("Error! Question checksum not valid.");
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ERR_QUESTION_BAD_CHECKSUM));
			forwardRequest("/all_tests.php", request, response);
		}
		request.getSession().removeAttribute("QUESTION_COUNT");		
	}
	
	private void updateSessionQuestionCount(HttpSession session){
		
		if(null == session.getAttribute("QUESTION_COUNT")){
			Integer count = 1;
			session.setAttribute("QUESTION_COUNT", count);
		}else{
			Integer count = (Integer)session.getAttribute("QUESTION_COUNT");
			session.setAttribute("QUESTION_COUNT", count+1);
		}
	}

	private Boolean validAnswer(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException, InquirerDataException, InvalidDataException {

		Question question = (Question) request.getAttribute("CHECKED_QUESTION");
		List<Answer> answerCorrectList = answerManager.getCorrectAnswerListByQuestion(question);
		List<Answer> answerList = answerManager.getAnswerListByQuestion(question);
		List<Answer> answersTest = new ArrayList<Answer>();

		if (null != request.getParameter("unknow"))
			return false;
		for (Answer answer : answerList) {
			if (null != request.getParameter("isCorrect_" + answer.getId()))
				answersTest.add(answer);
		}
		
		if (answersTest.isEmpty())
			return false;

		if (answersTest.size() == answerCorrectList.size()) {
			for (Answer answer : answersTest) {
				if (!answerCorrectList.contains(answer))
					return false;
			}
			return true;
		} else {
			return false;
		}

	}

}
