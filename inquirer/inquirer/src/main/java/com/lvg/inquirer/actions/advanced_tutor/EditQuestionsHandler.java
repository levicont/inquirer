package com.lvg.inquirer.actions.advanced_tutor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

public class EditQuestionsHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -2371805812241305507L;
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private static final TestDataService testManager = new TestDataBaseManager();
	private static final AnswerDataService answerManager = new AnswerDataBaseManager();
	

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		prepareRequest(request, response);
		gotoToJSP("/advanced_tutor/edit_question.jsp", request, response);

	}
	
	private void prepareRequest(HttpServletRequest request, HttpServletResponse response)throws InquirerDataException, InvalidDataException{
		Integer questId = Integer.parseInt(request.getParameter("id"));
		Question question = questionManager.getQuestion(questId);
		Integer testId = Integer.parseInt(request.getParameter("test_id"));
		Test test = testManager.getTest(testId);
		List<Answer> answerList = answerManager.getAnswerListByQuestion(question);
		Integer countOfEmptyAnswers = DEFAULT_ANSWERS_COUNT-answerList.size();
		Integer sizeAnswerList = answerList.size();
		
		if(answerList.size()<DEFAULT_ANSWERS_COUNT )
			countOfEmptyAnswers = 0;
		
		request.setAttribute("EDITED_QUESTION", question);
		request.setAttribute("test_id", test.getId());
		request.setAttribute("QUESTION_TEXT", question.getText());
		request.setAttribute("DEFAULT_ANSWERS_COUNT", DEFAULT_ANSWERS_COUNT);
		request.setAttribute("ANSWERS_LIST", answerList);
		request.setAttribute("SIZE_OF_ANSWER_LIST", sizeAnswerList);
		request.setAttribute("COUNT_OF_EMPTY_ANSWERS", countOfEmptyAnswers);
	}
}
