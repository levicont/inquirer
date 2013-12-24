package com.lvg.inquirer.services;

import java.sql.Connection;

import com.lvg.inquirer.exceptions.InquirerDataException;

public interface DBConnectionService {
	
	Connection getDBConnection()throws InquirerDataException;
	
	void closeDBConnection(Connection connection) throws InquirerDataException;
}
