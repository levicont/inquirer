package com.lvg.inquirer.actions.advanced_tutor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AnswerDataBaseManager;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.models.Answer;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.AnswerDataService;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.TestDataService;

public class SaveQuestionHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 713675544351635258L;
	private final Logger LOGGER = Logger.getLogger(SaveQuestionHandler.class);
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final TestDataService testManager = new TestDataBaseManager();
	private static final AnswerDataService answerManager = new AnswerDataBaseManager();

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");

		if ("cancel".equals(action)) {
			String testId = request.getParameter("test");
			redirectRequest("/all_questions.php?test_id=" + testId, request, response);
		} else {
			if ("edit_question".equals(action)) {
				initParameters(request, response);
				Integer questionId = Integer.parseInt(request.getParameter("question"));
				Question newQuestion = (Question)request.getAttribute("NEW_QUESTION");
				Question editedQuestion = questionManager.getQuestion(questionId);
				editedQuestion.setText(newQuestion.getText());
				request.setAttribute("TEST_ID", newQuestion.getTest().getId());
				request.setAttribute("DEFAULT_ANSWERS_COUNT",DEFAULT_ANSWERS_COUNT);
				updateQuestion(editedQuestion, request, response);
			} else {
				initParameters(request, response);
				Question newQuestion = (Question) request.getAttribute("NEW_QUESTION");
				request.setAttribute("TEST_ID", newQuestion.getTest().getId());
				request.setAttribute("DEFAULT_ANSWERS_COUNT",DEFAULT_ANSWERS_COUNT);
				saveQuestion(newQuestion, request, response);
			}
		}

	}

	private void initParameters(HttpServletRequest request, HttpServletResponse response) throws InvalidDataException,
			InquirerDataException {
		Question newQuestion;
		Integer testId;

		String text = request.getParameter("text");
		List<Answer> answerList = new ArrayList<Answer>();
		try {
			if(null!=request.getParameter("question")){
				Integer questionId = Integer.parseInt(request.getParameter("question"));
				request.setAttribute("EDITED_QUESTION", questionManager.getQuestion(questionId));
			}
				
			testId = Integer.parseInt(request.getParameter("test"));
			Test test = testManager.getTest(testId);			

			for (int i = 1; i <= DEFAULT_ANSWERS_COUNT; i++) {
				String answerText = request.getParameter("answer_" + i);

				if (StringUtils.isNotBlank(answerText)) {
					Answer answer = new Answer();
					answer.setText(answerText.trim());
					if (request.getParameter("isCorrect_" + i) != null) {
						answer.setIsCorrect(1);
					} else {
						answer.setIsCorrect(0);
					}
					answerList.add(answer);
				}
			}
			newQuestion = new Question();
			newQuestion.setTest(test);
			newQuestion.setText(text);
			request.setAttribute("NEW_QUESTION", newQuestion);
			request.setAttribute("ANSWERS_LIST", answerList);
			request.setAttribute("SIZE_OF_ANSWER_LIST", answerList.size());
		} catch (NumberFormatException ex) {
			LOGGER.warn("Not possible to cast test id to number id=" + request.getParameter("test"), ex);
		}

	}

	@SuppressWarnings("unchecked")
	private void saveQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
			throws InvalidDataException, InquirerDataException, IOException, ServletException {

		List<Answer> answerList = (List<Answer>) request.getAttribute("ANSWERS_LIST");		
		try {
			checkQuestion(question);
			request.setAttribute("QUESTION_TEXT", question.getText());
			checkAnswers(answerList);			
			questionManager.addQuestion(question);
			answerManager.deleteAnswerByQuestion(questionManager.getLastInsertedQuestion());
			for (Answer answer : answerList) {
				answer.setQuestion(questionManager.getLastInsertedQuestion());
				answerManager.insertAnswer(answer);
			}
			redirectRequest("/all_questions.php?test_id=" + question.getTest().getId(), request, response);
		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to save question with answer.", ex);			
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/add_question.jsp", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to save question with answers.", ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/add_question.jsp", request, response);
		}
	}
	
	
	@SuppressWarnings("unchecked")
	private void updateQuestion(Question question, HttpServletRequest request, HttpServletResponse response)
			throws InvalidDataException, InquirerDataException, IOException, ServletException {

		List<Answer> answerList = (List<Answer>)request.getAttribute("ANSWERS_LIST");		
		try {					
			checkQuestion(question);
			request.setAttribute("QUESTION_TEXT", question.getText());			
			checkAnswers(answerList);			
			answerManager.deleteAnswerByQuestion(question);
			questionManager.updateQuestion(question);			
			for (Answer answer : answerList) {
				answer.setQuestion(question);
				answerManager.insertAnswer(answer);
			}
			redirectRequest("/all_questions.php?test_id=" + question.getTest().getId(), request, response);
		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to update question with answer.", ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/edit_question.jsp", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to update question with answers.", ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/advanced_tutor/edit_question.jsp", request, response);
		}
	}
	
	private void checkQuestion(Question question) throws InvalidDataException {

		if (!StringUtils.isNotBlank(question.getText()))
			throw new InvalidDataException("Title must not be empty");
		if (question.getTest() == null)
			throw new InvalidDataException("Test is not recognized");
	}
	
	private void checkAnswers(List<Answer> answerList)throws InvalidDataException{
		if(null == answerList || answerList.isEmpty())
			throw new InvalidDataException("Question must have one or more answers.");	
	}

}
