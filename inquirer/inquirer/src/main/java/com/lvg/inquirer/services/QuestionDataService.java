package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;

public interface QuestionDataService extends DataClosable {
	
	List<Question> getQuestionList() throws InquirerDataException, InvalidDataException;

	void addQuestion(Question question) throws InquirerDataException, InvalidDataException ;
	
	void updateQuestion(Question question) throws InquirerDataException, InvalidDataException;
	
	void deleteQuestion(Question question) throws InquirerDataException, InvalidDataException;
	
	List<Question> getQuestionsByTest(Test test) throws InquirerDataException, InvalidDataException;
	
	Question getQuestion(Integer id) throws InquirerDataException, InvalidDataException;
	
	Question getLastInsertedQuestion()throws InquirerDataException, InvalidDataException;

	
}
