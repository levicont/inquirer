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
import com.lvg.inquirer.models.Answer;
import com.lvg.inquirer.models.Question;
import com.lvg.inquirer.services.AnswerDataService;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.QuestionDataService;

public class AnswerDataBaseManager implements AnswerDataService, InquirerConstants {
	private static final Logger LOGGER = Logger.getLogger(AnswerDataBaseManager.class);
	

	private DBConnectionService connectionManager = new DBConnectionManager();
	private QuestionDataService questionManager = new QuestionDataBaseManager();

	private final String COLUMN_NAME_ID = "id_answers";
	private final String COLUMN_NAME_QUESTION = "id_questions";
	private final String COLUMN_NAME_TEXT = "text";
	private final String COLUMN_NAME_IS_CORRECT = "is_correct";

	private final String SQL_ALL_ANSWERS = "SELECT * FROM answers";
	private final String SQL_GET_ANSWERS_BY_ID = "SELECT * FROM answers WHERE id_answers=?";
	private final String SQL_GET_ANSWERS_BY_QUESTION_ID = "SELECT * FROM answers WHERE id_questions=?";
	private final String SQL_GET_CORRECT_ANSWERS_BY_QUESTION = "SELECT * FROM answers WHERE id_questions=? AND is_correct>0";
	private final String SQL_ADD_NEW_ANSWER = "INSERT INTO answers VALUE (null,?,?,?)";
	private final String SQL_UPDATE_ANSWER = "UPDATE answers SET text=?, is_correct=? WHERE id_answers=?";
	private final String SQL_DELETE_ANSWER = "DELETE FROM answers WHERE id_answers=?";
	private final String SQL_DELETE_ANSWER_BY_QUESTION = "DELETE FROM answers WHERE id_questions=?";

	public List<Answer> getAnswerList() throws InquirerDataException, InvalidDataException {
		List<Answer> result = new ArrayList<Answer>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_ALL_ANSWERS);
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt(COLUMN_NAME_ID));
				answer.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				answer.setText(rs.getString(COLUMN_NAME_TEXT));
				answer.setIsCorrect(rs.getInt(COLUMN_NAME_IS_CORRECT));
				result.add(answer);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load answer list from DB ", ex);
			throw new InquirerDataException("Not possible to load answer list from DB ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public List<Answer> getAnswerListByQuestion(Question question) throws InquirerDataException, InvalidDataException {

		List<Answer> result = new ArrayList<Answer>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ANSWERS_BY_QUESTION_ID);
			pstmt.setInt(1, question.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt(COLUMN_NAME_ID));
				answer.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				answer.setText(rs.getString(COLUMN_NAME_TEXT));
				answer.setIsCorrect(rs.getInt(COLUMN_NAME_IS_CORRECT));
				result.add(answer);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load answer list from DB ", ex);
			throw new InquirerDataException("Not possible to load answer list from DB ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}
	
	public List<Answer> getCorrectAnswerListByQuestion(Question question) throws InquirerDataException, InvalidDataException {

		List<Answer> result = new ArrayList<Answer>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_CORRECT_ANSWERS_BY_QUESTION);
			pstmt.setInt(1, question.getId());
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Answer answer = new Answer();
				answer.setId(rs.getInt(COLUMN_NAME_ID));
				answer.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				answer.setText(rs.getString(COLUMN_NAME_TEXT));
				answer.setIsCorrect(rs.getInt(COLUMN_NAME_IS_CORRECT));
				result.add(answer);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load answer list from DB ", ex);
			throw new InquirerDataException("Not possible to load answer list from DB ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}


	public Answer getAnswer(Integer id) throws InquirerDataException, InvalidDataException {
		Answer result = new Answer();
		Connection connection = connectionManager.getDBConnection();

		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ANSWERS_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				result = new Answer();
				result.setId(rs.getInt(COLUMN_NAME_ID));
				result.setQuestion(questionManager.getQuestion(rs.getInt(COLUMN_NAME_QUESTION)));
				result.setText(rs.getString(COLUMN_NAME_TEXT));
				result.setIsCorrect(rs.getInt(COLUMN_NAME_IS_CORRECT));
				return result;
			}
			throw new InvalidDataException("Not possible to find answer with id:" + id);

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. ", ex);
			throw new InquirerDataException("Error occurred while accessing the database. ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void insertAnswer(Answer answer) throws InquirerDataException, InvalidDataException {

		Connection connection = connectionManager.getDBConnection();
		try {

			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_NEW_ANSWER);
			pstmt.setInt(1, answer.getQuestion().getId());
			pstmt.setString(2, answer.getText());
			pstmt.setInt(3, answer.getIsCorrect());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. ", ex);
			throw new InquirerDataException("Error occurred while accessing the database. ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void updateAnswer(Answer answer) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();

		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_ANSWER);
			pstmt.setString(1, answer.getText());
			pstmt.setInt(2, answer.getIsCorrect());
			pstmt.setInt(3, answer.getId());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. ", ex);
			throw new InquirerDataException("Error occurred while accessing the database. ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void deleteAnswer(Answer answer) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_ANSWER);
			pstmt.setInt(1, answer.getId());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. ", ex);
			throw new InquirerDataException("Error occurred while accessing the database. ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}
	}

	public void deleteAnswerByQuestion(Question question) throws InquirerDataException, InvalidDataException {
		Connection connection = connectionManager.getDBConnection();

		try {

			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_ANSWER_BY_QUESTION);
			pstmt.setInt(1, question.getId());
			pstmt.executeUpdate();

		} catch (SQLException ex) {
			LOGGER.error("Error occurred while accessing the database. ", ex);
			throw new InquirerDataException("Error occurred while accessing the database. ", ex);
		} finally {
			connectionManager.closeDBConnection(connection);
		}

	}

	public void close(){
		//do nothing;
	}
}
