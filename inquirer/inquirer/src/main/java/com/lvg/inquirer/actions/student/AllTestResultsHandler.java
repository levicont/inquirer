package com.lvg.inquirer.actions.student;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.ResultDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.ResultDataService;


public class AllTestResultsHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = 2778128994953923324L;
	private static final ResultDataService resultManager = new ResultDataBaseManager();
	
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String ITEMS_PAGE = "page";
		final Integer ITEMS_ON_PAGE =10;
		Integer page = 1;
		
		if(request.getParameter(ITEMS_PAGE)!=null){
			page = Integer.parseInt(request.getParameter(ITEMS_PAGE));
			if(page<1)
				page=1;
		}
		
		List<TestResult> testResultList = resultManager.getTestResultList((Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT));
		//TODO add to session
		request.getServletContext().setAttribute("RESULTS_LIST", testResultList);
		request.setAttribute("RESULTS_ITEMS", testResultList.size());
		request.setAttribute("ITEMS_ON_PAGE", ITEMS_ON_PAGE);
		request.setAttribute(ITEMS_PAGE, page);
		
		gotoToJSP("/student/results_table.jsp", request, response);

	}

}
