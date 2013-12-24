package com.lvg.inquirer.listeners;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.services.AccountDataService;

public class InquirerSessionListener implements HttpSessionListener {
	private final Logger LOGGER = Logger.getLogger(InquirerSessionListener.class);
	private final AccountDataService accountManager = new AccountDataBaseManager();

	public void sessionCreated(HttpSessionEvent se) {
		HttpSession session = se.getSession();

		LOGGER.info("A new session with id='" + session.getId() + "' has been created");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A new session with id='" + session.getId() + "' has been created");
		}

	}

	
	public void sessionDestroyed(HttpSessionEvent se) {
		HttpSession session = se.getSession();
		
		
		try {
			session.getServletContext().setAttribute(InquirerConstants.ACCOUNTS_LIST, accountManager.accountList());
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to load account list", ex);
		}
		LOGGER.info("A session with id='" + session.getId() + "' has been destroyed");
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("A session with id='" + session.getId() + "' has been destroyed");
		}

	}

}
