package com.lvg.inquirer.actions;

import java.io.IOException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.apache.log4j.Logger;

import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.services.AccountDataService;

public class RecoverAccountHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 2579524524848957379L;
	private final Logger LOGGER = Logger.getLogger(RecoverAccountHandler.class);
	private final AccountDataService accountManager = new AccountDataBaseManager();

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if ("recover".equals(request.getParameter("action"))) {
			validAccount(request, response);
			
		} else {
			if ("cancel_recover".equals(request.getParameter("action"))) {
				redirectRequest("/login.php", request, response);
			} else {
				gotoToJSP("/recover.jsp", request, response);
			}
		}

	}

	private void validAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		try {			
			Account validAccount = accountManager.getAccount(request.getParameter("username"),
					request.getParameter("email"));
			sendRecoverEmail(validAccount);
			request.setAttribute("RECOVER_MESSAGE", errMessage.getString("recover_message_sent"));
			gotoToJSP("/recover.jsp", request, response);
		} catch (InvalidDataException ex) {
			LOGGER.error("Not possible to send email message: "+ex.getMessage());
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ex.getMessage()));
			gotoToJSP("/recover.jsp", request, response);
		} catch (InquirerDataException ex) {
			LOGGER.error("Not possible to send email message: "+ex.getMessage());
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			ex.printStackTrace();
			gotoToJSP("/recover.jsp", request, response);
		}

	}

	@SuppressWarnings("deprecation")
	private void sendRecoverEmail(Account account) throws InquirerDataException, InvalidDataException {
		
		
		Email email = new SimpleEmail();
		email.setHostName(EMAIL_HOST_NAME);
		email.setAuthenticator(new DefaultAuthenticator(EMAIL_LOGIN, EMAIL_PASSWORD));
		email.setSmtpPort(EMAIL_PORT);
		email.setSSLOnConnect(true);
		email.setSSL(true);
		email.setStartTLSEnabled(true);
		email.setSSLCheckServerIdentity(true);

		try {
			email.setFrom(EMAIL_FROM);
			email.setSubject("TestMail");
			email.setMsg("Recovered account: Username: "+account.getUsername()+", Password: "+account.getPassword()+", roles: "+account.getRole());
			//TODO paste email of real account
			email.addTo("victor84lg@ukr.net");
			email.send();
		} catch (EmailException ex) {
			LOGGER.error("Can't send mail! ", ex);
			throw new InvalidDataException(ERR_SEND_EMAIL);
			
		}
		
		
	}


}
