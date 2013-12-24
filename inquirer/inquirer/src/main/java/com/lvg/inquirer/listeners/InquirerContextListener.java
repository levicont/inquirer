package com.lvg.inquirer.listeners;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.services.AccountDataService;

public class InquirerContextListener implements ServletContextListener{
	
	private final Logger LOGGER = Logger.getLogger(InquirerContextListener.class);
	private ServletContext context = null;
	private static final AccountDataService accountManager = new AccountDataBaseManager();

	public void contextInitialized(ServletContextEvent sce) {
		context = sce.getServletContext();		
		context.setAttribute(InquirerConstants.CONTEXT, context.getContextPath());
		try{
		List<Account> validAccounts = accountManager.accountList();
		
		context.setAttribute(InquirerConstants.ACCOUNTS_LIST, validAccounts);
		
		LOGGER.info(" <=== Context "+context.getServletContextName() +" has been initialized! ===> ");
		}catch(InquirerDataException ex){
			LOGGER.error("Not possible to load account list.", ex);
			return;
		}
	}

	
	public void contextDestroyed(ServletContextEvent sce) {
		LOGGER.info(" <== Context "+context.getServletContextName() +" has been destroyed! ===> ");
	}
	

}
