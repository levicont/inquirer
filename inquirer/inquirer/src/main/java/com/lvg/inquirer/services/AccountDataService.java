package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;

public interface AccountDataService extends DataClosable {
	
	List<Account> accountList()throws InquirerDataException;
	
	void saveAccounts(List<Account> accounts)throws InquirerDataException;

	void addAccount(Account account) throws InquirerDataException;
	
	void deleteAccount(Account account)throws InquirerDataException;
	
	void updateAccount(Account account)throws InquirerDataException;	
	
	void checkAccount(Account account)throws InquirerDataException;
	
	Account getAccount(String username, String email)throws InquirerDataException, InvalidDataException;
	
	Account getAccount(Integer id)throws InquirerDataException, InvalidDataException;
	
}
