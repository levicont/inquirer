package com.lvg.inquirer.actions.student;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

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
		if ("cancel".equals(action)) {
			redirectRequest("/all_tests.php", request, response);
		} else {
			saveProfile(request, response);			
		}

	}

	private void saveProfile(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
		// TODO
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		try {
			checkProfileFields(request, response);
			accountManager.updateAccount((Account)request.getAttribute("UPDATED_ACCOUNT"));
			redirectRequest("/logout.php", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to update profile",ex);
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			forwardRequest("/profile.php", request, response);
		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to update profile",ex);
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ex.getMessage()));
			forwardRequest("/profile.php", request, response);
		}
	}

	private void checkProfileFields(HttpServletRequest request, HttpServletResponse response)
			throws InvalidDataException, InquirerDataException {
		
		Account account = (Account) request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);
		Account newAccount = accountManager.getAccount(account.getId());

		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		if (StringUtils.isBlank(username))
			throw new InvalidDataException(ERR_USNAME_BLANK);
		newAccount.setUsername(username);

		if (StringUtils.isBlank(email))
			throw new InvalidDataException(ERR_EMAIL_BLANK);
		newAccount.setEmail(email);

		if (StringUtils.isNotBlank(newPassword)) {
			if (StringUtils.isBlank(oldPassword)) {
				throw new InvalidDataException(ERR_PROFILE_OLD_PASSWORD_BLANK);
			} else {
				if (oldPassword.equals(account.getPassword())) {
					if (!newPassword.equals(confirmPassword))
						throw new InvalidDataException(ERR_PROFILE_PASSWORDS_NOT_EQUAL);
				} else {
					throw new InvalidDataException(ERR_PROFILE_BAD_OLD_PASSWORD);
				}
			}
			newAccount.setPassword(newPassword);
		}
		if(account.getUsername().equals(username) && account.getEmail().equals(email)){
			if(StringUtils.isBlank(oldPassword) && StringUtils.isBlank(newPassword))		
				throw new InvalidDataException(ERR_PROFILE_NO_CHANGE);
			if(StringUtils.isNotBlank(oldPassword) && StringUtils.isBlank(newPassword))
				throw new InvalidDataException(ERR_PROFILE_NO_CHANGE);
		}
		accountManager.checkAccount(newAccount);
		request.setAttribute("UPDATED_ACCOUNT", newAccount);

	}

}
