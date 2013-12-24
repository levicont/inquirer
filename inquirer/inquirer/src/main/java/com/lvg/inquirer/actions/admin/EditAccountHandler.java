package com.lvg.inquirer.actions.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;

public class EditAccountHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = 841726187395355694L;

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		fillRequest(request);
		gotoToJSP("/admin/add_account.jsp", request, response);

	}
	
	private void fillRequest(HttpServletRequest request){
		Integer accountId = Integer.parseInt(request.getParameter("id"));
		List<Account> accountList = (List<Account>)request.getServletContext().getAttribute(ACCOUNTS_LIST);
		List<Role> roles = null;
		for(Account a: accountList){
			if(a.getId().equals(accountId)){
				request.setAttribute("USERNAME", a.getUsername());
				request.setAttribute("PASSWORD", a.getPassword());
				request.setAttribute("EMAIL", a.getEmail());
				request.setAttribute("ENABLED", a.getEnabled());
				roles = a.getRole();
				for(Role r: roles){
					request.setAttribute("CHK_"+r.getId(), "checked");
				}
				request.getSession().setAttribute("EDITED_ACCOUNT", a);
				break;
			}
			
		}
	}

}
