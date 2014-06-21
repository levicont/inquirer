package com.lvg.inquirer.actions.student;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.mocks.ResultDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.models.TestResult;
import com.lvg.inquirer.services.AccountDataService;
import com.lvg.inquirer.services.ResultDataService;


public class AllTestResultsHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = 2778128994953923324L;
	private static final ResultDataService resultManager = new ResultDataBaseManager();
	private static final AccountDataService accountManager = new AccountDataBaseManager(); 
	private static final String CURRENT_SELECTED_ACCOUNT = "CURRENT_SELECTED_ACCOUNT";
	private static final String STUDENTS_LIST = "STUDENTS_LIST";
	
	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		final String ITEMS_PAGE = "page";
		final Integer ITEMS_ON_PAGE =10;
		Integer page = 1;
		List<TestResult> testResultList = null;
		List<Account> studentList = getStudentAccounts(accountManager.accountList());
		Account currentAccount  = (Account)request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);		
		
		if(request.getParameter(ITEMS_PAGE)!=null){
			page = Integer.parseInt(request.getParameter(ITEMS_PAGE));
			if(page<1)
				page=1;
		}
		if(null==request.getParameter("accountUsername")){
			testResultList = resultManager.getTestResultList(currentAccount);
		}else{
			Account account = accountManager.getAccount(request.getParameter("accountUsername"));
			testResultList = resultManager.getTestResultList(account);
			request.setAttribute(CURRENT_SELECTED_ACCOUNT, account);
		}
		if(isAccountNotStudent(currentAccount)){
			request.setAttribute(STUDENTS_LIST, studentList);
		}
		
		//TODO add to session
		
		request.getServletContext().setAttribute("RESULTS_LIST", testResultList);
		
		request.setAttribute("RESULTS_ITEMS", testResultList.size());
		request.setAttribute("ITEMS_ON_PAGE", ITEMS_ON_PAGE);
		request.setAttribute(ITEMS_PAGE, page);
		if(null != currentAccount){
			gotoToJSP("/student/results_table.jsp", request, response);
			return;
		}else{
			redirectRequest("/login.php", request, response);
		}
	}
	
	private List<Account> getStudentAccounts(List<Account> allAccounts){
		List<Account> result = new ArrayList<Account>();
		for(Account account : allAccounts){
			List<Role> roles = account.getRole();
			if(roles.size()==1){
				if(roles.get(0).getId()==4){
					result.add(account);
				}
			}
		}
		
		
		return result;
	}
	
	private Boolean isAccountNotStudent(Account account){
		if(null==account)
			return false;
		List<Role> roles = account.getRole();
		for(Role role : roles){
			if(role.getId()==1){
				return true;
			}
			if(role.getId()==2){
				return true;
			}
			if(role.getId()==3){
				return true;
			}
		}
		return false;
	}
	

}
