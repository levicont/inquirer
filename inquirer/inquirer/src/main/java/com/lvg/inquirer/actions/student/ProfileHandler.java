package com.lvg.inquirer.actions.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.services.AccountDataService;

public class ProfileHandler extends AbstractInquirerServletHandler implements InquirerConstants{
	
	private static final long serialVersionUID = 7039250464383719373L;
	private static final Logger LOGGER = Logger.getLogger(ProfileHandler.class);
	private static final AccountDataService accountManager = new AccountDataBaseManager();
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		gotoToJSP("/student/profile.jsp", request, response);

	}

}
