package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.TestMistake;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.ResultDataService;
import com.lvg.inquirer.services.TestMistakeDataService;

public class TestMistakeDataBaseManager implements TestMistakeDataService, InquirerConstants {
	
	private static final Logger LOGGER = Logger.getLogger(TestMistakeDataBaseManager.class);
	
	private DBConnectionService connectionManager = new DBConnectionManager();
	private ResultDataService resultManager = new ResultDataBaseManager();
	private QuestionDataService questionManager = new QuestionDataBaseManager();

	private final String COLUMN_NAME_ID = "id_test_mistakes";
	private final String COLUMN_NAME_TEST_RESULT = "id_test_results";
	private final String COLUMN_NAME_QUESTION = "id_questions";
	private final String COLUMN_NAME_FAIL_ANSWER = "text_fail_answer";
	private final String COLUMN_NAME_CORRECT_ANSWER = "text_correct_answer";
	
	private final String SQL_ADD_NEW_MISTAKE = "INSERT INTO test_mistakes VALUE (null,?,?,?,?)"; 
	private final String SQL_UPDATE_MISTAKE = "UPDATE test_mistakes SET id_test_results=?, id_questions=?, text_fail_answer=?, text_correct_answer=? WHERE id_test_mistakes=?";
	private final String SQL_DELETE_MISTAKE = "DELETE FROM test_mistakes WHERE id_test_mistakes=?";
	private final String SQL_SELECT_MISTAKE_BY_ID = "SELECT * FROM test_mistakes WHERE id_test_mistakes=?";
	private final String SQL_SELECT_MISTAKES_BY_TEST_RESULTS = "SELECT * FROM test_mistakes WHERE id_test_results=?";
	
	
	@Override
	public void close() {
		// do nothing

	}

	@Override
	public void addTestMistake(TestMistake testMistake) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try{
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_NEW_MISTAKE);
			pstmt.setInt(1, testMistake.getTestResult().getId());
			pstmt.setInt(2, testMistake.getQuestion().getId());
			pstmt.setString(3, testMistake.getFailAnswerText());
			pstmt.setString(4, testMistake.getCorrectAnswerText());
			pstmt.execute();
			connection.commit();
			
		}catch(SQLException ex){
			LOGGER.error("Test mistake could not to be added in DB");
			throw new InquirerDataException("Test mistake could not to be added in DB");
		}finally{
			connectionManager.closeDBConnection(connection);
		}

	}

	@Override
	public void updateTestMistake(TestMistake testMistake) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try{
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_MISTAKE);
			pstmt.setInt(1, testMistake.getQuestion().getId());
			pstmt.setString(2, testMistake.getFailAnswerText());
			pstmt.setString(3, testMistake.getCorrectAnswerText());
			pstmt.setInt(4, testMistake.getTestResult().getId());
			pstmt.execute();
			connection.commit();
			
		}catch(SQLException ex){
			LOGGER.error("Test mistake could not to be updated in DB");
			throw new InquirerDataException("Test mistake could not to be updated in DB");
		}finally{
			connectionManager.closeDBConnection(connection);
		}

	}

	@Override
	public void deleteTestMistake(TestMistake testMistake) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try{
			
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_MISTAKE);
			pstmt.setInt(1, testMistake.getTestResult().getId());
			pstmt.execute();
			connection.commit();
			
		}catch(SQLException ex){
			LOGGER.error("Test mistake could not to be deleted from DB");
			throw new InquirerDataException("Test mistake could not to be deleted from DB");
		}finally{
			connectionManager.closeDBConnection(connection);
		}


	}

	@Override
	public TestMistake getTestMistake(Integer id) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try{			
			PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT_MISTAKE_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				TestMistake result = new TestMistake();
				result.setId(id);
				result.setTestResult(resultManager.getTestResult(rs.getInt(COLUMN_NAME_TEST_RESULT)));
				result.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				result.setFailAnswerText(rs.getString(COLUMN_NAME_FAIL_ANSWER));
				result.setCorrectAnswerText(rs.getString(COLUMN_NAME_CORRECT_ANSWER));
				return result;
			}
			throw new InquirerDataException("Not possible to find test mistake by id="+id);
			
		}catch(SQLException ex){
			LOGGER.error("Not possible to find test mistake by id="+id);
			throw new InquirerDataException("Not possible to find test mistake by id="+id);
		}finally{
			connectionManager.closeDBConnection(connection);
		}		
	}

	@Override
	public List<TestMistake> getMistakesByResult(TestResult testResult) throws InquirerDataException, InvalidDataException {
		ArrayList<TestMistake> mistakesList = new ArrayList<TestMistake>();
		TestMistake testMistake;
		Connection connection = connectionManager.getDBConnection();
		
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_SELECT_MISTAKES_BY_TEST_RESULTS);
			pstmt.setInt(1, testResult.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				testMistake = new TestMistake();
				testMistake.setId(rs.getInt(COLUMN_NAME_ID));
				testMistake.setTestResult(testResult);
				testMistake.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				testMistake.setFailAnswerText(rs.getString(COLUMN_NAME_FAIL_ANSWER));
				testMistake.setCorrectAnswerText(rs.getString(COLUMN_NAME_CORRECT_ANSWER));
				mistakesList.add(testMistake);
			}
			return mistakesList;
						
		}catch(SQLException ex){
			LOGGER.error("Not possible to find test mistake by test result whith id: "+testResult.getId());
			throw new InquirerDataException("Not possible to find test mistake by test result whith id: "+testResult.getId());
		}finally{
			connectionManager.closeDBConnection(connection);
		}		
	}
	
	

}
