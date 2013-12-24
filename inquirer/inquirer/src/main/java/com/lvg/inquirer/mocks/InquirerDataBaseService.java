package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.services.DataService;
import com.lvg.inquirer.services.InquirerServiceManager;
import com.lvg.inquirer.services.RoleDataService;

public class InquirerDataBaseService implements DataService {

	private static final Logger LOGGER = Logger.getLogger(InquirerDataBaseService.class);
	//private AccountDataService accountService = new AccountDataBaseManager();
	//private RoleDataService roleService = new RoleDataBaseManager();
	
	

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			LOGGER.info("Connection to database has been established!");
		} catch (ClassNotFoundException ex) {
			throw new RuntimeException("Failed to load DB driver.", ex);
		}
	}

	public Connection getDataBaseConnection() throws InquirerDataException {
		try {
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost/inquirer", "inquirer",
					"inquirer");
			return connection;
		} catch (SQLException ex) {
			throw new InquirerDataException("Failed to establish the database connection.", ex);
		}

	}
	
	public void close(){
		//Do nothing;
	}

	
	@SuppressWarnings("unchecked")
	public Account login(String username, String password, Integer role) throws InvalidDataException,
			InquirerDataException {
		List<Account> accountList = (List<Account>) InquirerServiceManager.getServletContext().getAttribute(
				InquirerConstants.ACCOUNTS_LIST);
		for (Account account : accountList) {
			if (StringUtils.equals(account.getUsername(), username)) {
				if (StringUtils.equals(account.getPassword(), password)) {
					if (!account.isEnabled())
						throw new InvalidDataException("No rights to enter!");
					for (Role r : account.getRole()) {
						if (r.getId().equals(role))
							return account;
					}
					throw new InvalidDataException("Invalid role");
				} else
					throw new InvalidDataException("Invalid password");
			}

		}
		throw new InvalidDataException("User not found");

	}

	
	
}
