package com.lvg.inquirer.actions.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class AddAccountHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = 9202710738024779123L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		gotoToJSP("admin/add_account.jsp", request, response);

	}

}
