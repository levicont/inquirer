package com.lvg.inquirer.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.models.Account;

public class AdminHomeHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = -3900923726049569012L;

	private final String ACCOUNTS_PAGE = "page";
	private final Integer ACCOUNTS_ON_PAGE = 10;

	
	@SuppressWarnings("unchecked")
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int page;		
		List<Account> accountList = (List<Account>) request.getServletContext().getAttribute(ACCOUNTS_LIST);

		if (!StringUtils.isBlank( request.getParameter(ACCOUNTS_PAGE))) {
			try {
				checkPage(request.getParameter(ACCOUNTS_PAGE));
				page = Integer.parseInt(request.getParameter(ACCOUNTS_PAGE));
								
				request.setAttribute("ACCOUNT_PAGES_COUNT", getPages(accountList, ACCOUNTS_ON_PAGE));
				request.setAttribute("ACCOUNTS_ITEMS", accountList.size());
				request.setAttribute(ACCOUNTS_PAGE, page);
				request.setAttribute("ACCOUNTS_ON_PAGE", ACCOUNTS_ON_PAGE);
				gotoToJSP("/admin/home.jsp", request, response);

			} catch (Exception ex) {
				request.setAttribute("ACCOUNT_PAGES_COUNT", getPages(accountList, ACCOUNTS_ON_PAGE));
				request.setAttribute(ACCOUNTS_PAGE, 1);
				request.setAttribute("ACCOUNTS_ON_PAGE", ACCOUNTS_ON_PAGE);
				request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
				gotoToJSP("/admin/home.jsp", request, response);

			}
		} else {
			request.setAttribute("ACCOUNT_PAGES_COUNT", getPages(accountList, ACCOUNTS_ON_PAGE));
			request.setAttribute("ACCOUNTS_ITEMS", accountList.size());
			request.setAttribute("ACCOUNTS_ON_PAGE", ACCOUNTS_ON_PAGE);
			request.setAttribute(ACCOUNTS_PAGE, 1);

			gotoToJSP("/admin/home.jsp", request, response);
		}
	}

	private void checkPage(String page) throws InvalidDataException {

		int currentPage = 1;
		try {
			currentPage = Integer.parseInt(page);
			if (currentPage <= 0)
				throw new InvalidDataException("Page not found: " + page);

		} catch (NumberFormatException ex) {
			throw new InvalidDataException("Incorrect page" + page, ex);
		}
	}

	private Integer[] getPages(List<Account> list, int itemsOnPage) {
		int size = list.size();
		int length = (int)Math.ceil(size/(float)itemsOnPage);
		Integer[] result = new Integer[length];
		for(int i = 0; i<result.length; i++){
			result[i]=i+1;
		}
		return result;
	}

}
