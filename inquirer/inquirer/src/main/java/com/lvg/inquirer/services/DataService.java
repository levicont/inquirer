package com.lvg.inquirer.services;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;

public interface DataService extends DataClosable{
	
	Account login(String username, String password, Integer Role) throws InvalidDataException, InquirerDataException;
	
	
	
}
