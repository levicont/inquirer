package com.lvg.inquirer.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;

public class RegistrationHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("ACTION", "REGISTRATION");
		gotoToJSP("registration.jsp", request, response);
	}

}
