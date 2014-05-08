package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.TestMistake;
import com.lvg.inquirer.models.TestResult;

public interface TestMistakeDataService extends DataClosable {
	 
		List<TestMistake> getMistakesByResult(TestResult result)throws InquirerDataException, InvalidDataException;
		
		void addTestMistake(TestMistake testMistake)throws InquirerDataException, InvalidDataException;
		
		void updateTestMistake(TestMistake testMistake)throws InquirerDataException, InvalidDataException;
			
		void deleteTestMistake(TestMistake testMistake)throws InquirerDataException, InvalidDataException;
			
		TestMistake getTestMistake(Integer id)throws InquirerDataException, InvalidDataException;
}
