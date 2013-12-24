package com.lvg.inquirer.actions.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.omg.CORBA.DynAnyPackage.Invalid;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.services.AccountDataService;

public class SaveProfileHandler extends AbstractInquirerServletHandler implements InquirerConstants {
	
	private static final long serialVersionUID = 7946329274485338322L;
	private static final Logger LOGGER = Logger.getLogger(ProfileHandler.class);
	private static final AccountDataService accountManager = new AccountDataBaseManager();
	
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String action = request.getParameter("action");
		if("cancel".equals(action)){
			redirectRequest("/all_tests.php", request, response);
		}else{
			saveProfile(request, response);
			redirectRequest("/all_tests.php", request, response);
		}

	}
	
	private void saveProfile(HttpServletRequest request, HttpServletResponse response){
		//TODO 
		try{
		checkProfileFields(request, response);
		Account account = (Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);
		}catch(InquirerDataException ex){
			
		}catch(InvalidDataException ex){
			
		}
	}
	
	private void checkProfileFields(HttpServletRequest request, HttpServletResponse response)throws InvalidDataException, InquirerDataException{
		Account account = (Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);
		Account newAccount = accountManager.getAccount(account.getId());
		
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");
		
		if(StringUtils.isBlank(username))
			throw new InvalidDataException("Username is blank");
		newAccount.setUsername(username);
		
		if(StringUtils.isBlank(email))
			throw new InvalidDataException("Email is blank");
		newAccount.setEmail(email);
		
		if(StringUtils.isNotBlank(newPassword)){
			if(StringUtils.isBlank(oldPassword)){
				throw new InvalidDataException("Old password is blank");
			}else{
				if(oldPassword.equals(account.getPassword())){
					if(newPassword.equals(confirmPassword))
						throw new InvalidDataException("New password is not equal to confirm password");
				}else{
					throw new InvalidDataException("Old password is not correct");
				}
			}
			newAccount.setPassword(newPassword);
		}
		
		accountManager.checkAccount(newAccount);
				
		
	}

}
