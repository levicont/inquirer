package com.lvg.inquirer.actions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 7518445769544856666L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().invalidate();
		redirectRequest("/login.php", request, response);
	}

}
