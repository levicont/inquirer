package com.lvg.inquirer.services;

import java.util.List;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;

public interface RoleDataService extends DataClosable {
	
	List<Role> rolesList() throws InquirerDataException;
	
	List<Role> getRolesByAccount(Account account) throws InquirerDataException;
	
	void updateRolesByAccount(Account account) throws InquirerDataException;
	
	void addRolesByAccount(Account account)throws InquirerDataException;
	
	void deleteRolesByAccount(Account account) throws InquirerDataException;
}
