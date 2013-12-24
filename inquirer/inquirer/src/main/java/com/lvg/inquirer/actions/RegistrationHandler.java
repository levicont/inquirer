package com.lvg.inquirer.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;

public class RegistrationHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = 676291163097336268L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("ACTION", "REGISTRATION");
		gotoToJSP("registration.jsp", request, response);
	}

}
