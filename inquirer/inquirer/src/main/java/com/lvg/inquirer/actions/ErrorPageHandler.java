package com.lvg.inquirer.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;

public class ErrorPageHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = -584998722614511099L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		gotoToJSP("/modules/validMessage.jsp", request, response);

	}

}
