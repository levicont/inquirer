package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Answer;
import com.lvg.inquirer.models.Question;

public interface AnswerDataService extends DataClosable {
	
	List<Answer> getAnswerList() throws InquirerDataException, InvalidDataException;
	
	List<Answer> getAnswerListByQuestion(Question question) throws InquirerDataException, InvalidDataException;
	
	List<Answer> getCorrectAnswerListByQuestion(Question question) throws InquirerDataException, InvalidDataException;
	
	Answer getAnswer(Integer id) throws InquirerDataException, InvalidDataException;
		
	void insertAnswer(Answer answer)throws InquirerDataException, InvalidDataException;
		
	void updateAnswer(Answer answer)throws InquirerDataException, InvalidDataException;
	
	void deleteAnswer(Answer answer)throws InquirerDataException, InvalidDataException;
	
	void deleteAnswerByQuestion(Question question)throws InquirerDataException, InvalidDataException;	
}
