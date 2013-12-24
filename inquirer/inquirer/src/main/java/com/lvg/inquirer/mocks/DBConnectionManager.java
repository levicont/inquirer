package com.lvg.inquirer.mocks;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.services.DBConnectionService;
import com.lvg.inquirer.services.DataService;

public class DBConnectionManager implements DBConnectionService {
	
	private final Logger LOGGER = Logger.getLogger(DBConnectionManager.class); 
	private DataService service = new InquirerDataBaseService();

	public Connection getDBConnection()throws InquirerDataException {
		
		return ((InquirerDataBaseService)service).getDataBaseConnection();
	}

	public void closeDBConnection(Connection connection) throws InquirerDataException {
		try{
			connection.close();
		}catch(SQLException ex){
			LOGGER.warn("Not possible to close DB connection. ", ex);
			throw new InquirerDataException("Not possible to close DB connection. ", ex);
		}

	}

}
