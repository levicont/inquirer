package com.lvg.inquirer.actions;

import java.io.IOException;

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
		try {
			Account validAccount = accountManager.getAccount(request.getParameter("username"),
					request.getParameter("email"));
			sendRecoverEmail(validAccount);

			request.setAttribute("RECOVER_MESSAGE", "Recovered data were sent to e-mail adress. ");
			gotoToJSP("/recover.jsp", request, response);
		} catch (InvalidDataException ex) {
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/recover.jsp", request, response);
		} catch (InquirerDataException ex) {
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			ex.printStackTrace();
			gotoToJSP("/recover.jsp", request, response);
		}

	}

	@SuppressWarnings("deprecation")
	private void sendRecoverEmail(Account account) throws InquirerDataException {
		
		
		Email email = new SimpleEmail();
		email.setHostName("smtp.gmail.com");
		email.setAuthenticator(new DefaultAuthenticator("levicont84", "levicont84"));
		email.setSmtpPort(587);
		email.setSSLOnConnect(true);
		email.setSSL(true);
		email.setStartTLSEnabled(true);
		email.setSSLCheckServerIdentity(true);

		try {
			email.setFrom("levicont84@gmail.com");
			email.setSubject("TestMail");
			email.setMsg("Recovered account: Username: "+account.getUsername()+", Password: "+account.getPassword()+", roles: "+account.getRole());
			email.addTo("victor84lg@ukr.net");
			email.send();
		} catch (EmailException ex) {
			LOGGER.error("Can't send mail! ", ex);
			throw new InquirerDataException("Can't send mail! ");
			
		}
		
		
	}


}
