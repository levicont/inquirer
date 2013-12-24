package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.QuestionDataBaseManager;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.services.QuestionDataService;

public class DeleteQuestionHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -3775633980091429558L;
	private static final QuestionDataService questionManager = new QuestionDataBaseManager();
	private final Logger LOGGER = Logger.getLogger(DeleteQuestionHandler.class);
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Integer questionId=null;
		questionId = Integer.parseInt(request.getParameter("id"));
		Question question = questionManager.getQuestion(questionId);
		questionManager.deleteQuestion(question);
		LOGGER.debug("Question with id: "+question.getId()+" has been deleted.");
		redirectRequest("/all_questions.php?test_id="+question.getTest().getId(), request, response);
		
	}

}
