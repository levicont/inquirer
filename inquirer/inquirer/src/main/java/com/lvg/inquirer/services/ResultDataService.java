package com.lvg.inquirer.services;

import java.sql.Timestamp;
import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.models.TestResult;

public interface ResultDataService extends DataClosable {
	
	List<TestResult> getTestResultList()throws InquirerDataException, InvalidDataException ;
	

	List<TestResult> getTestResultList(Test test)throws InquirerDataException, InvalidDataException;
		
	List<TestResult> getTestResultList(Account account)throws InquirerDataException, InvalidDataException;
		
	List<TestResult> getTestResultList(Account account, Test test)throws InquirerDataException, InvalidDataException;
		
	TestResult getTestResult(Integer id)throws InquirerDataException, InvalidDataException ;
	
	TestResult getTestResultByAccountAndDate(Account account, Timestamp date)throws InquirerDataException, InvalidDataException;
		
	void insertTestResult(TestResult testResult)throws InquirerDataException, InvalidDataException;
		
	void deleteTestResult(TestResult testResult)throws InquirerDataException, InvalidDataException;
		
	void deleteTestResult(Account account)throws InquirerDataException, InvalidDataException;
		
	void deleteTestResult(Test test)throws InquirerDataException, InvalidDataException;
	
}
