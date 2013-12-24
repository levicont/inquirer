package com.lvg.inquirer.actions.advanced_tutor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.TestDataBaseManager;
import com.lvg.inquirer.services.TestDataService;

public class AllTestsHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 8980586119604409531L;
	private static final TestDataService testManager = new TestDataBaseManager();
	
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String ITEMS_PAGE = "page";
		final Integer ITEMS_ON_PAGE = 5;
		Integer page = 1;
		
		if(request.getParameter(ITEMS_PAGE)!=null){
			page = Integer.parseInt(request.getParameter(ITEMS_PAGE));
			if(page<1)
				page=1;
		}		
		request.getServletContext().setAttribute("TESTS_LIST", testManager.getTestList());
		
		request.setAttribute("TEST_ITEMS", testManager.getTestList().size());
		request.setAttribute("ITEMS_ON_PAGE", ITEMS_ON_PAGE);
		request.setAttribute(ITEMS_PAGE, page);
		gotoToJSP("/advanced_tutor/tests_table.jsp", request, response);

	}

}
