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
import com.lvg.inquirer.models.Test;
import com.lvg.inquirer.services.AccountDataService;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.TestDataService;

public class TestDataBaseManager implements TestDataService, InquirerConstants{
	
	private static final Logger LOGGER = Logger.getLogger(TestDataBaseManager.class);
	
	
	private DBConnectionService connectionManager = new DBConnectionManager();
	private AccountDataService accountManager = new AccountDataBaseManager();
	
	
	private final String COLUMN_NAME_ID = "id_tests";
	private final String COLUMN_NAME_AUTHOR = "id_accounts";
	private final String COLUMN_NAME_TITLE = "title";
	private final String COLUMN_NAME_DESCRIPTION = "description";
	private final String COLUMN_NAME_TIME_LIMIT = "time_limit";
	
	private final String SQL_ALL_TESTS = "SELECT * FROM tests";
	private final String SQL_TEST_BY_ID = "SELECT * FROM tests WHERE id_tests=?";
	private final String SQL_ADD_NEW_TEST = "INSERT INTO tests VALUE (null,?,?,?,?)";
	private final String SQL_UPDATE_TEST = "UPDATE tests SET title=?, description=?, time_limit=? WHERE id_tests=?";
	private final String SQL_DELETE_TEST = "DELETE FROM tests WHERE id_tests=?";
		
	
	public List<Test> getTestList()throws InquirerDataException, InvalidDataException{
		List<Test> result = new ArrayList<Test>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_ALL_TESTS);
			while (rs.next()) {
				Test test = new Test();
				test.setId(rs.getInt(COLUMN_NAME_ID));
				test.setAuthor(accountManager.getAccount(rs.getInt(COLUMN_NAME_AUTHOR)));
				test.setTitle(rs.getString(COLUMN_NAME_TITLE));
				test.setDescription(rs.getString(COLUMN_NAME_DESCRIPTION));
				test.setTimeLimit(rs.getInt(COLUMN_NAME_TIME_LIMIT));
				result.add(test);
			}
			return result;
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load test list from database! ", ex);
			throw new InquirerDataException("Not possible to load test list from database! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}

	}
	
	public void addTest(Test test)throws InquirerDataException, InvalidDataException{
		validTest(test);
		Connection connection = connectionManager.getDBConnection();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_NEW_TEST);
			pstmt.setInt(1, test.getAuthor().getId());
			pstmt.setString(2, test.getTitle());
			pstmt.setString(3, test.getDescription());
			pstmt.setInt(4, test.getTimeLimit());
			pstmt.executeUpdate();
			
						
		}catch(SQLException ex){
			LOGGER.error("Not possible to add new test "+test,ex);
			throw new InquirerDataException("Not possible to add new test "+test,ex);
			
		}finally{
			connectionManager.closeDBConnection(connection);
		}

	}
	
	public void updateTest(Test test)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_TEST);
			pstmt.setString(1, test.getTitle());
			pstmt.setString(2, test.getDescription());
			pstmt.setInt(3, test.getTimeLimit());
			pstmt.setInt(4, test.getId());						
			pstmt.executeUpdate();						
		}catch(SQLException ex){
			LOGGER.error("Not possible to update test "+test,ex);
			throw new InquirerDataException("Not possible to update test "+test,ex);
			
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void deleteTest(Test test)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_TEST);
			pstmt.setInt(1, test.getId());
			pstmt.executeUpdate();
						
		}catch(SQLException ex){
			LOGGER.error("Not possible to delete test "+test,ex);
			throw new InquirerDataException("Not possible to delete test "+test,ex);
			
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public Test getTest(Integer id)throws InquirerDataException, InvalidDataException{
		Test result = new Test();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_TEST_BY_ID);
			pstmt.setInt(1, id);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()) {
				result.setId(rs.getInt(COLUMN_NAME_ID));
				result.setAuthor(accountManager.getAccount(rs.getInt(COLUMN_NAME_AUTHOR)));
				result.setTitle(rs.getString(COLUMN_NAME_TITLE));
				result.setDescription(rs.getString(COLUMN_NAME_DESCRIPTION));
				result.setTimeLimit(rs.getInt(COLUMN_NAME_TIME_LIMIT));
				return result;
			}else{
				throw new InquirerDataException("Not possible to find test with id: "+id);
			}
			
		} catch (SQLException ex) {
			LOGGER.error("Not possible to find test with id: "+id, ex);
			throw new InquirerDataException("Not possible to find test with id: "+id, ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}

	}
		
	private void validTest(Test test)throws InquirerDataException, InvalidDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(SQL_ALL_TESTS);
			while(rs.next()){
				if(test.getTitle().equals(rs.getString(COLUMN_NAME_TITLE)))
					throw new InvalidDataException("This is not valid title of test: "+test.getTitle());
			}
			
		}catch(SQLException ex){
			LOGGER.error("Not possible to valid test "+test,ex);
			throw new InquirerDataException("Not possible to valid test "+test,ex);
			
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void close(){
		//do nothing
	}
}
