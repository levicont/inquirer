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
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.QuestionDataService;
import com.lvg.inquirer.services.TestDataService;

public class QuestionDataBaseManager implements QuestionDataService, InquirerConstants {

	private static final Logger LOGGER = Logger.getLogger(QuestionDataBaseManager.class);
	

	private DBConnectionManager connectionManager = new DBConnectionManager();
	private TestDataService testManager = new TestDataBaseManager();

	private final String COLUMN_NAME_ID = "id_questions";
	private final String COLUMN_NAME_TEST = "id_tests";
	private final String COLUMN_NAME_TEXT = "text";
	private final String COLUMN_NAME_NUMBER = "number";

	private final String SQL_ALL_QUESTIONS = "SELECT * FROM questions";
	private final String SQL_GET_LAST_INSERTED_QUESTION = "SELECT MAX(id_questions) FROM questions";
	private final String SQL_GET_QUESTIONS_BY_TEST_ID = "SELECT * FROM questions WHERE id_tests=?";
	private final String SQL_GET_VALID_QUESTIONS = "SELECT * FROM questions WHERE id_tests=? AND number=?";
	private final String SQL_GET_QUESTION_BY_ID = "SELECT * FROM questions WHERE id_questions=?";
	private final String SQL_ADD_NEW_QUESTION = "INSERT INTO questions VALUE (null,?,?,?)";
	private final String SQL_UPDATE_QUESTION = "UPDATE questions SET text=?, number=? WHERE id_questions=?";
	private final String SQL_DELETE_QUESTION = "DELETE FROM questions WHERE id_questions=?";
	private final String SQL_GET_MAX_QUESTION_NUMBER_BY_TEST = "SELECT max(number) from questions WHERE id_tests=?";
	private final String SQL_GET_QUESTIONS_COUNT_BY_NUMBER_AND_TEST = "SELECT count(number) FROM questions WHERE number=? AND id_tests=?";
	private final String SQL_GET_QUESTION_BY_NUMBER_AND_TEST = "SELECT * FROM questions WHERE number=? AND id_tests=?";
	public List<Question> getQuestionList() throws InquirerDataException, InvalidDataException {
		List<Question> result = new ArrayList<Question>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_ALL_QUESTIONS);
			while (rs.next()) {
				Question question = new Question();
				question.setId(rs.getInt(COLUMN_NAME_ID));
				question.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				question.setText(rs.getString(COLUMN_NAME_TEXT));
				question.setNumber(rs.getInt(COLUMN_NAME_NUMBER));
				result.add(question);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Error accurred while accessing the database! ", ex);
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void addQuestion(Question question) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();

		try {
			validQuestion(question);
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_NEW_QUESTION);
			pstmt.setInt(1, question.getTest().getId());
			pstmt.setString(2, question.getText());
			pstmt.setInt(3, question.getNumber());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Not possible to add new test " + question, ex);
			throw new InquirerDataException("Not possible to add new test " + question, ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void updateQuestion(Question question) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();

		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_QUESTION);
			pstmt.setString(1, question.getText());
			pstmt.setInt(2, question.getNumber());
			pstmt.setInt(3, question.getId());
			
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Not possible to update question " + question, ex);
			throw new InquirerDataException("Not possible to update question " + question, ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void deleteQuestion(Question question) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_QUESTION);
			pstmt.setInt(1, question.getId());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Not possible to delete question " + question, ex);
			throw new InquirerDataException("Not possible to delete question " + question, ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public List<Question> getQuestionsByTest(Test test) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		List<Question> result = new ArrayList<Question>();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_QUESTIONS_BY_TEST_ID);
			pstmt.setInt(1, test.getId());
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				Question question = new Question();
				question.setId(rs.getInt(COLUMN_NAME_ID));
				question.setTest(test);
				question.setText(rs.getString(COLUMN_NAME_TEXT));
				question.setNumber(rs.getInt(COLUMN_NAME_NUMBER));
				result.add(question);
			}
			return result;
		}catch(SQLException ex){
			LOGGER.error("Not possible to load question by test " + test, ex);
			throw new InquirerDataException("Not possible to load question by test " + test, ex);

		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}

	public Question getQuestion(Integer id) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		Question question = new Question();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_QUESTION_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				question.setId(rs.getInt(COLUMN_NAME_ID));
				question.setTest(testManager.getTest(rs.getInt(COLUMN_NAME_TEST)));
				question.setText(rs.getString(COLUMN_NAME_TEXT));
				question.setNumber(rs.getInt(COLUMN_NAME_NUMBER));
				return question;
			}
			throw new InquirerDataException("Could not find question with id:" + id);
		} catch (SQLException ex) {
			LOGGER.error("Error accurred while accessing the database! ", ex);
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void close() {
		// do nothing
	}

	public Question getLastInsertedQuestion() throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_GET_LAST_INSERTED_QUESTION);
			if (rs.next()) {
				Question question = getQuestion(rs.getInt("MAX(id_questions)"));
				return question;
			}

			throw new InquirerDataException("Could not find last inserted question ");
		} catch (SQLException ex) {
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}
	
	public Question getQuestionByNumberAndTest(Integer questionNumber, Test test) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		Question question = new Question();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_QUESTION_BY_NUMBER_AND_TEST);
			pstmt.setInt(1, questionNumber);
			pstmt.setInt(2, test.getId());
			
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				question.setId(rs.getInt(COLUMN_NAME_ID));
				question.setTest(test);
				question.setText(rs.getString(COLUMN_NAME_TEXT));
				question.setNumber(rs.getInt(COLUMN_NAME_NUMBER));
				return question;
			}
			LOGGER.error("The question could not be found with number:" + questionNumber);
			throw new InquirerDataException("The question could not be found with number:" + questionNumber);
		} catch (SQLException ex) {
			LOGGER.error("Error accurred while accessing the database! ", ex);
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	private void validQuestion(Question question) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_VALID_QUESTIONS);
			pstmt.setInt(1, question.getTest().getId());
			pstmt.setInt(2, question.getNumber());
			ResultSet rs = pstmt.executeQuery();

			if (rs.next()) {
				throw new InvalidDataException("This is not valid number of question: " + question.getText());
			}

		} catch (SQLException ex) {
			LOGGER.error("Not possible to valid test " + question, ex);
			throw new InquirerDataException("Not possible to valid test " + question, ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}
	}

	@Override
	public Integer getNextQuestionNumberByTest(Test test) throws InquirerDataException, InvalidDataException {
		Integer result = 1;
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_MAX_QUESTION_NUMBER_BY_TEST);
			pstmt.setInt(1, test.getId());
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				if(rs.wasNull()){
					return 1;
				}else{
					result = rs.getInt(1)+1;
					return result;
				}				
			}
			return 1;

		} catch (SQLException ex) {
			LOGGER.error("Not possible to calculate max question number", ex);
			throw new InquirerDataException("Not possible to calculate max question number", ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}		
	}
	

	public Integer getQuestionCountByNumberAndTest(Integer questionNumber, Test test)throws InvalidDataException, InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		Integer result = 0;
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_QUESTIONS_COUNT_BY_NUMBER_AND_TEST);
			pstmt.setInt(1, questionNumber);
			pstmt.setInt(2, test.getId());
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				result = rs.getInt(1);
				return result;
			}
			return result;

		} catch (SQLException ex) {
			LOGGER.error("Not possible to execute query from questions table", ex);
			throw new InquirerDataException("Not possible to execute query from questions table", ex);

		} finally {
			connectionManager.closeDBConnection(connection);
		}		
	}
	
}
