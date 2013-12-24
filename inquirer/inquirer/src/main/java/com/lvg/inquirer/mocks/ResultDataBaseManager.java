package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.AccountDataService;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.ResultDataService;
import com.lvg.inquirer.services.TestDataService;

public class ResultDataBaseManager implements ResultDataService, InquirerConstants{
	
	private static final Logger LOGGER = Logger.getLogger(ResultDataBaseManager.class);
	
	private DBConnectionService connectionManager = new DBConnectionManager();
	private AccountDataService accountManager = new AccountDataBaseManager();
	private TestDataService testManager = new TestDataBaseManager();
	
	private final String COLUMN_NAME_ID = "id_test_results";
	private final String COLUMN_NAME_ACCOUNT = "id_accounts";
	private final String COLUMN_NAME_TEST = "id_tests";
	private final String COLUMN_NAME_CORRECT_ANSWERS = "correct_answers";
	private final String COLUMN_NAME_FAIL_ANSWERS = "fail_answers";
	private final String COLUMN_NAME_DATE = "date";
	
	private final String SQL_ALL_RESULTS = "SELECT * FROM test_results";
	private final String SQL_GET_RESULTS_BY_ID = "SELECT * FROM test_results WHERE id_test_results=?";
	private final String SQL_GET_RESULTS_BY_TEST_ID = "SELECT * FROM test_results WHERE id_tests=?";
	private final String SQL_GET_RESULTS_BY_ACCOUNT_ID = "SELECT * FROM test_results WHERE id_accounts=?";
	private final String SQL_GET_RESULTS_BY_ACCOUNT_AND_TEST = "SELECT * FROM test_results WHERE id_accounts=? AND id_tests=?";
	private final String SQL_ADD_NEW_RESULT = "INSERT INTO test_results VALUE (null,?,?,?,?,?)";
	private final String SQL_DELETE_RESULT = "DELETE FROM test_results WHERE id_test_results=?";
	private final String SQL_DELETE_RESULT_BY_TEST = "DELETE FROM test_results WHERE id_tests=?";
	private final String SQL_DELETE_RESULT_BY_ACCOUNT = "DELETE FROM test_results WHERE id_accounts=?";
	
	
	
	
	public List<TestResult> getTestResultList()throws InquirerDataException, InvalidDataException {
		List<TestResult> result = new ArrayList<TestResult>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_ALL_RESULTS);
			
			while (rs.next()) {
				TestResult testResult = new TestResult();
				testResult.setId(rs.getInt(COLUMN_NAME_ID));
				testResult.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				testResult.setAccount(accountManager.getAccount(rs.getInt(COLUMN_NAME_ACCOUNT)));
				testResult.setCorrectAnswers(rs.getInt(COLUMN_NAME_CORRECT_ANSWERS));
				testResult.setFailAnswers(rs.getInt(COLUMN_NAME_FAIL_ANSWERS));
				testResult.setDate(rs.getDate(COLUMN_NAME_DATE));
				result.add(testResult);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load results list from DB ",ex);
			throw new InquirerDataException("Not possible to load results list from DB ",ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
		
	public List<TestResult> getTestResultList(Test test)throws InquirerDataException, InvalidDataException{
		List<TestResult> result = new ArrayList<TestResult>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_RESULTS_BY_TEST_ID);
			pstmt.setInt(1, test.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TestResult testResult = new TestResult();
				testResult.setId(rs.getInt(COLUMN_NAME_ID));
				testResult.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				testResult.setAccount(accountManager.getAccount(rs.getInt(COLUMN_NAME_ACCOUNT)));
				testResult.setCorrectAnswers(rs.getInt(COLUMN_NAME_CORRECT_ANSWERS));
				testResult.setFailAnswers(rs.getInt(COLUMN_NAME_FAIL_ANSWERS));
				testResult.setDate(rs.getDate(COLUMN_NAME_DATE));
				result.add(testResult);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load results by test with id: "+test.getId()+" list from DB ",ex);
			throw new InquirerDataException("Not possible to load results by test with id: "+test.getId()+" list from DB ",ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	
	public List<TestResult> getTestResultList(Account account)throws InquirerDataException, InvalidDataException{
		List<TestResult> result = new ArrayList<TestResult>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_RESULTS_BY_ACCOUNT_ID);
			pstmt.setInt(1, account.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TestResult testResult = new TestResult();
				testResult.setId(rs.getInt(COLUMN_NAME_ID));
				testResult.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				testResult.setAccount(accountManager.getAccount(rs.getInt(COLUMN_NAME_ACCOUNT)));
				testResult.setCorrectAnswers(rs.getInt(COLUMN_NAME_CORRECT_ANSWERS));
				testResult.setFailAnswers(rs.getInt(COLUMN_NAME_FAIL_ANSWERS));
				testResult.setDate(rs.getDate(COLUMN_NAME_DATE));
				result.add(testResult);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load results by account with id: "+account.getId()+" list from DB ",ex);
			throw new InquirerDataException("Not possible to load results by account with id: "+account.getId()+" list from DB ",ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
		
	public List<TestResult> getTestResultList(Account account, Test test)throws InquirerDataException, InvalidDataException{
		List<TestResult> result = new ArrayList<TestResult>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_RESULTS_BY_ACCOUNT_AND_TEST);
			pstmt.setInt(1, account.getId());
			pstmt.setInt(2, test.getId());
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				TestResult testResult = new TestResult();
				testResult.setId(rs.getInt(COLUMN_NAME_ID));
				testResult.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				testResult.setAccount(accountManager.getAccount(rs.getInt(COLUMN_NAME_ACCOUNT)));
				testResult.setCorrectAnswers(rs.getInt(COLUMN_NAME_CORRECT_ANSWERS));
				testResult.setFailAnswers(rs.getInt(COLUMN_NAME_FAIL_ANSWERS));
				testResult.setDate(rs.getDate(COLUMN_NAME_DATE));
				result.add(testResult);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load results by account and test from DB ",ex);
			throw new InquirerDataException("Not possible to load results by account and test from DB ",ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
		
	public TestResult getTestResult(Integer id)throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		TestResult result = new TestResult();
		
		try {
			
			PreparedStatement pstmt =  connection.prepareStatement(SQL_GET_RESULTS_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				if (id.equals(rs.getInt(COLUMN_NAME_ID))) {
					result = new TestResult();
					result.setId(rs.getInt(COLUMN_NAME_ID));
					result.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
					result.setAccount(accountManager.getAccount(rs.getInt(COLUMN_NAME_ACCOUNT)));
					result.setCorrectAnswers(rs.getInt(COLUMN_NAME_CORRECT_ANSWERS));
					result.setFailAnswers(rs.getInt(COLUMN_NAME_FAIL_ANSWERS));
					result.setDate(rs.getDate(COLUMN_NAME_DATE));
					return result;
				}
			}
			throw new InvalidDataException("Not possible to find test result with id:"+id);

		} catch (SQLException ex) {
			LOGGER.error("Not possible to load test result by id",ex);
			throw new InquirerDataException("Not possible to load test result by id",ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	
	
	public void insertTestResult(TestResult testResult)throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try {
			
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_NEW_RESULT);
			pstmt.setInt(1, testResult.getAccount().getId());
			pstmt.setInt(2, testResult.getTest().getId());
			pstmt.setInt(3, testResult.getCorrectAnswers());
			pstmt.setInt(4, testResult.getFailAnswers());
			pstmt.setDate(5, testResult.getDate());
			pstmt.executeUpdate();
			

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. Could not insert test result: "+testResult, ex);
			throw new InquirerDataException("Error occurred while accessing the database. Could not insert test result: "+testResult, ex);
		}finally{
						
			connectionManager.closeDBConnection(connection);
		}
	}
	
		
	public void deleteTestResult(TestResult testResult)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_RESULT);
			pstmt.setInt(1, testResult.getId());			
			pstmt.executeUpdate();
			

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. Could not delete test result: "+testResult, ex);
			throw new InquirerDataException("Error occurred while accessing the database. Could not delete test result: "+testResult, ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
		
	public void deleteTestResult(Account account)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		
		try {
			
			PreparedStatement pstmt =  connection.prepareStatement(SQL_DELETE_RESULT_BY_ACCOUNT);
			pstmt.setInt(1, account.getId());			
			pstmt.executeUpdate();
			

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. Could not delete test result whith account id: "+account.getId(), ex);
			throw new InquirerDataException("Error occurred while accessing the database. Could not delete test result whith account id: "+account.getId(), ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	
	public void deleteTestResult(Test test)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		try {
			
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_RESULT_BY_TEST);
			pstmt.setInt(1, test.getId());			
			pstmt.executeUpdate();
			

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. Could not delete test result whith test id: "+test.getId(), ex);
			throw new InquirerDataException("Error occurred while accessing the database. Could not delete test result whith test id: "+test.getId(), ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void close(){
		//do nothing;
	}

		
}
