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
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.services.AccountDataService;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.RoleDataService;

public class AccountDataBaseManager implements InquirerConstants, AccountDataService {

	private final Logger LOGGER = Logger.getLogger(AccountDataBaseManager.class);
	
	private final String COLUMN_NAME_ID = "id_accounts";
	private final String COLUMN_NAME_USERNAME = "username";
	private final String COLUMN_NAME_PASSWORD = "password";
	private final String COLUMN_NAME_EMAIL = "email";
	private final String COLUMN_NAME_ENABLED = "enabled";
	
	private final String SQL_GET_ALL_ACCOUNTS = "SELECT * FROM accounts";
	private final String SQL_ALL_ACCOUNTS_VALIDATE = "SELECT * FROM accounts WHERE username=? OR email=?";
	private final String SQL_GET_ACCOUNTS_BY_NAME = "SELECT * FROM accounts WHERE username=? AND email=?";
	private final String SQL_GET_ACCOUNTS_BY_ID = "SELECT * FROM accounts WHERE id_accounts=?";
	private final String SQL_UPDATE_ACCOUNT = "UPDATE accounts SET username=?, password=?, email=?, enabled=? WHERE id_accounts=?;";
	private final String SQL_ADD_ACCOUNT = "INSERT INTO accounts VALUE (null, ?, ?, ?,?);";
	private final String SQL_DELETE_ACCOUNT = "DELETE FROM accounts WHERE id_accounts=?";
	
	private DBConnectionService connectionManager = new DBConnectionManager();
	private RoleDataService rolesManager = new RoleDataBaseManager();
	
	public void close(){
		//Do nothig
	};

	public List<Account> accountList() throws InquirerDataException{
		List<Account> result = new ArrayList<Account>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(SQL_GET_ALL_ACCOUNTS);
			ResultSet rs = stmt.getResultSet();
			while (rs.next()) {
				Account account = new Account();
				account.setId(rs.getInt(COLUMN_NAME_ID));
				account.setUsername(rs.getString(COLUMN_NAME_USERNAME));
				account.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				account.setEmail(rs.getString(COLUMN_NAME_EMAIL));
				account.setEnabled(rs.getInt(COLUMN_NAME_ENABLED));
				account.setRole(rolesManager.getRolesByAccount(account));
				result.add(account);
			}
			if(result.isEmpty())
				throw new InquirerDataException("Data base is not contain any account!");
			
			return result;
			
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load account list from DB! ", ex);
			throw new InquirerDataException("Not possible to load account list from DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void checkAccount(Account account) throws InquirerDataException{
		List<Account> result = new ArrayList<Account>();
		Connection connection = connectionManager.getDBConnection();
		try {
			PreparedStatement pstmt = connection.prepareStatement(SQL_ALL_ACCOUNTS_VALIDATE);
			pstmt.setString(1, account.getUsername());
			pstmt.setString(2, account.getEmail());
			pstmt.executeQuery();
			ResultSet rs = pstmt.getResultSet();
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt(COLUMN_NAME_ID));
				a.setUsername(rs.getString(COLUMN_NAME_USERNAME));
				a.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				a.setEmail(rs.getString(COLUMN_NAME_EMAIL));
				a.setEnabled(rs.getInt(COLUMN_NAME_ENABLED));
				a.setRole(rolesManager.getRolesByAccount(account));
				if(!a.getId().equals(account.getId()))
					result.add(account);
			}			
			if(!result.isEmpty())
				throw new InquirerDataException("Account with username: "+account.getUsername()+" or email: "+account.getEmail()+" already exists");
			
		} catch (SQLException ex) {
			LOGGER.error("Not possible to load account list from DB! ", ex);
			throw new InquirerDataException("Not possible to load account list from DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	
	public void addAccount(Account account)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			checkAccount(account);
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_ACCOUNT);
			pstmt.setString(1, account.getUsername());
			pstmt.setString(2, account.getPassword());
			pstmt.setString(3, account.getEmail());
			pstmt.setInt(4, account.getEnabled());
			pstmt.executeUpdate();
			rolesManager.addRolesByAccount(account);
			connection.commit();			
		}catch(SQLException ex){
			LOGGER.error("Not possible to add account in DB! ", ex);
			throw new InquirerDataException("Not possible to add account in DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	
	public void updateAccount(Account account)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_ACCOUNT);
			pstmt.setString(1, account.getUsername());
			pstmt.setString(2, account.getPassword());
			pstmt.setString(3, account.getEmail());
			pstmt.setInt(4, account.getEnabled());
			pstmt.setInt(5, account.getId());
			pstmt.executeUpdate();
			rolesManager.updateRolesByAccount(account);
			connection.commit();
		}catch(SQLException ex){
			LOGGER.error("Not possible to update account with id: "+account.getId()+" in DB! ", ex);
			throw new InquirerDataException("Not possible to update account with id: "+account.getId()+" in DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void saveAccounts(List<Account> accounts) throws InquirerDataException{
		
		for(Account account : accounts){
			updateAccount(account);
		}
	}
	
	public void deleteAccount(Account account)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			connection.setAutoCommit(false);
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_ACCOUNT);
			pstmt.setInt(1, account.getId());
			pstmt.executeUpdate();
			rolesManager.deleteRolesByAccount(account);
			connection.commit();
		}catch(SQLException ex){
			LOGGER.error("Not possible to update account with id: "+account.getId()+" in DB! ", ex);
			throw new InquirerDataException("Not possible to update account with id: "+account.getId()+" in DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public Account getAccount(Integer accountId)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		Account result = new Account();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ACCOUNTS_BY_ID);
			pstmt.setInt(1, accountId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				result.setId(rs.getInt(COLUMN_NAME_ID));
				result.setUsername(rs.getString(COLUMN_NAME_USERNAME));
				result.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				result.setEnabled(rs.getInt(COLUMN_NAME_ENABLED));
				result.setRole(rolesManager.getRolesByAccount(result));
				return result;
			}else{
				throw new InquirerDataException("Not possible to find account with id: "+accountId);
			}
			
		}catch(SQLException ex){
			LOGGER.error("Not possible to find account with id: "+accountId, ex);
			throw new InquirerDataException("Not possible to find account with id: "+accountId, ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public Account getAccount(String username, String email)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		Account result = new Account();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ACCOUNTS_BY_NAME);
			pstmt.setString(1, username);
			pstmt.setString(2, email);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				result.setId(rs.getInt(COLUMN_NAME_ID));
				result.setUsername(rs.getString(COLUMN_NAME_USERNAME));
				result.setPassword(rs.getString(COLUMN_NAME_PASSWORD));
				result.setEnabled(rs.getInt(COLUMN_NAME_ENABLED));
				result.setRole(rolesManager.getRolesByAccount(result));
				return result;
			}else{
				throw new InquirerDataException("Not possible to find account with username: "+username+" and email: "+email);
			}
			
		}catch(SQLException ex){
			LOGGER.error("Not possible to find account with username: "+username+" and email: "+email, ex);
			throw new InquirerDataException("Not possible to find account with username: "+username+" and email: "+email, ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}

}
