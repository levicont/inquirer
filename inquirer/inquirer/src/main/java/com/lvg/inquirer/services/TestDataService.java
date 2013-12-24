package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Test;

public interface TestDataService extends DataClosable {
	
	 List<Test> getTestList()throws InquirerDataException, InvalidDataException;	
	
	void addTest(Test test)throws InquirerDataException, InvalidDataException;
	
	void updateTest(Test test)throws InquirerDataException, InvalidDataException;
		
	void deleteTest(Test test)throws InquirerDataException, InvalidDataException;
		
	Test getTest(Integer id)throws InquirerDataException, InvalidDataException;
}
