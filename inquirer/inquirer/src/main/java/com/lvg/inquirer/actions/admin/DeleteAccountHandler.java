package com.lvg.inquirer.actions.admin;

import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.services.AccountDataService;

public class DeleteAccountHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 4541623817773899817L;
	private static final AccountDataService accountManager = new AccountDataBaseManager();

	@SuppressWarnings("unchecked")
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int id = Integer.parseInt(request.getParameter("id"));
		Account removedAccount = null;
		List<Account> accountList = (List<Account>)request.getServletContext().getAttribute(ACCOUNTS_LIST);
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
				
		for(Account a : accountList)
			if(id==a.getId()){
				removedAccount=a;
				break;
			}
		if(!removedAccount.equals(request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT))){
			accountManager.deleteAccount(removedAccount);
			accountList.remove(removedAccount);
			redirectRequest("/admin/home.php", request, response);	
		}else{
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ERR_DELETE_USER));
			redirectRequest("/admin/home.php", request, response);
		}
		
		

	}

}
