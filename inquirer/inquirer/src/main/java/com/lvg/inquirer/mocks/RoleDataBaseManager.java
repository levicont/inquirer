package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.RoleDataService;

public class RoleDataBaseManager implements RoleDataService {
	
	private final Logger LOGGER = Logger.getLogger(RoleDataBaseManager.class);
	private final String SQL_GET_ALL_ROLES = "SELECT * FROM roles";
	private final String SQL_GET_ROLES_BY_ID = "SELECT roles.id_roles, roles.name FROM roles, accounts_roles, accounts "
			+ "WHERE accounts.id_accounts = accounts_roles.id_accounts "
			+ "AND accounts_roles.id_roles = roles.id_roles " + "AND accounts.id_accounts =?";
	private final String SQL_DELETE_ACCOUNTS_ROLES = "DELETE FROM accounts_roles WHERE id_accounts=? ";
	private final String SQL_ADD_ACCOUNT_ROLE = "INSERT INTO accounts_roles VALUE(null,?,?);";
	
	
	private DBConnectionService connectionManager= new DBConnectionManager();
	
	public void close() {
		// do nothing

	}

	public List<Role> rolesList() throws InquirerDataException {
		List<Role> result = new ArrayList<Role>();
		Connection connection = connectionManager.getDBConnection();
		try {
			Statement stmt = connection.createStatement();
			stmt.execute(SQL_GET_ALL_ROLES);
			ResultSet rs = stmt.getResultSet();

			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id_roles"));
				role.setName(rs.getString("name"));

				result.add(role);
			}
			return result;

		} catch (SQLException ex) {
			LOGGER.error("Not possible to load role list from DB:", ex);
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		}
		finally{
			connectionManager.closeDBConnection(connection);
		}
	}

	
	public List<Role> getRolesByAccount(Account account) throws InquirerDataException {
		List<Role> result = null;
		Connection connection = connectionManager.getDBConnection();
		try {
			result = new ArrayList<Role>();
			PreparedStatement pstmt = connection.prepareStatement(SQL_GET_ROLES_BY_ID);
			pstmt.setInt(1, account.getId());
			pstmt.execute();
			ResultSet rs = pstmt.getResultSet();

			while (rs.next()) {
				Role role = new Role();
				role.setId(rs.getInt("id_roles"));
				role.setName(rs.getString("name"));
				result.add(role);
			}

			pstmt.close();
			rs.close();
			return result;

		} catch (SQLException ex) {
			LOGGER.error("Not possible to load role list by Account id:"+account.getId()+" from DB:", ex);
			throw new InquirerDataException("Error accurred while accessing the database! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void addRolesByAccount(Account account) throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		List<Role> roleList = account.getRole();
		try{
			deleteRolesByAccount(account);
			PreparedStatement pstmt = connection.prepareStatement(SQL_ADD_ACCOUNT_ROLE);
			for(Role role : roleList){
				pstmt.setInt(1, account.getId());
				pstmt.setInt(2, role.getId());
				pstmt.executeUpdate();
			}
			
		}catch(SQLException ex){
			LOGGER.error("Not possible to add role by account in DB! ", ex);
			throw new InquirerDataException("Not possible to add role by account in DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
	public void updateRolesByAccount(Account account)throws InquirerDataException{
		deleteRolesByAccount(account);
		addRolesByAccount(account);
	}
	
	public void deleteRolesByAccount(Account account)throws InquirerDataException{
		Connection connection = connectionManager.getDBConnection();
		try{
			PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_ACCOUNTS_ROLES);
			pstmt.setInt(1, account.getId());
			pstmt.executeUpdate();			
		}catch(SQLException ex){
			LOGGER.error("Not possible to delete role_account by account in DB! ", ex);
			throw new InquirerDataException("Not possible to delete role_account by account in DB! ", ex);
		}finally{
			connectionManager.closeDBConnection(connection);
		}
	}
	
}
