package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.Statement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.services.DataService;
import com.lvg.inquirer.services.InquirerServiceManager;

public class InquirerDataService implements DataService {

	@SuppressWarnings("unchecked")
	public Account login(String username, String password, Integer role)
			throws InvalidDataException, InquirerDataException {
		List<Account> accountList = (List<Account>)InquirerServiceManager.getServletContext().getAttribute(InquirerConstants.ACCOUNTS_LIST);
		for (Account account : accountList) {
			if (StringUtils.equals(account.getUsername(), username)) {
				if (StringUtils.equals(account.getPassword(), password)) {
					for (Role r : account.getRole()) {
						if (r.getId().equals(role))
							return account;
					}
					throw new InvalidDataException("Invalid role");
				}else
					throw new InvalidDataException("Invalid password");
			}
			
		}
		throw new InvalidDataException("User not found");

	}

	public List<Role> rolesList() throws InquirerDataException {
		
		return ListDataStorage.ALL_ROLES;
	}
	
	@SuppressWarnings("unchecked")
	public List<Account> accountsList() throws InquirerDataException{
		
		return (List<Account>)InquirerServiceManager.getServletContext().getAttribute(InquirerConstants.ACCOUNTS_LIST);
	}

	public void close() {
		// do nothing
	}

	public Connection getDataBaseConnection() throws InquirerDataException {
		
		return null;
	}

	public List<Role> getRolesByAccountId(Integer id) throws InquirerDataException {
		throw new InquirerDataException("Non support this method!");
	}

	public int getLastInsertId(Statement stmt) throws InquirerDataException {
		throw new InquirerDataException("Non support this method!");
	}

}
