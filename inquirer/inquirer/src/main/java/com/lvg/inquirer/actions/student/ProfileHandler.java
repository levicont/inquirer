package com.lvg.inquirer.actions.student;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;

public class ProfileHandler extends AbstractInquirerServletHandler implements InquirerConstants{
	
	private static final long serialVersionUID = 7039250464383719373L;
	
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		gotoToJSP("/student/profile.jsp", request, response);

	}

}
