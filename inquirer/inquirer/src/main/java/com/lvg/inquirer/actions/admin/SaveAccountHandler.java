package com.lvg.inquirer.actions.admin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.actions.AbstractInquirerServletHandler;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.AccountDataBaseManager;
import com.lvg.inquirer.mocks.ListDataStorage;
import com.lvg.inquirer.mocks.RoleDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.services.AccountDataService;
import com.lvg.inquirer.services.RoleDataService;

public class SaveAccountHandler extends AbstractInquirerServletHandler implements InquirerConstants {

	private static final long serialVersionUID = -3684134246731276681L;
	private static final Logger LOGGER = Logger.getLogger(SaveAccountHandler.class);
	private static final AccountDataService accountManager = new AccountDataBaseManager();
	private static final RoleDataService roleManager = new RoleDataBaseManager();
	
	
	private final String ADMIN_CHK_ATTRIBUTE = "chkAdmin";
	private final String ADVANCE_TUTOR_CHK_ATTRIBUTE = "chkAdvancedTutor";
	private final String TUTOR_CHK_ATTRIBUTE = "chkTutor";
	private final String STUDENT_CHK_ATTRIBUTE = "chkStudent";
	private List<String> ALL_ROLES_ATTRIBUTES = Arrays.asList(new String[] { ADMIN_CHK_ATTRIBUTE,
			ADVANCE_TUTOR_CHK_ATTRIBUTE, TUTOR_CHK_ATTRIBUTE, STUDENT_CHK_ATTRIBUTE });

	

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
		if ("cancel_register".equals(request.getParameter("action"))) {
			redirectRequest("/login.php", request, response);
		} else {
			if ("cancel".equals(request.getParameter("action"))) {
				if(request.getSession().getAttribute("EDITED_ACCOUNT")!=null)
						request.getSession().setAttribute("EDITED_ACCOUNT", null);
						request.removeAttribute("EDITED_ACCOUNT");
				redirectRequest("/admin/home.php", request, response);
			} else {
				if ("edit".equals(request.getParameter("action"))) {
					doEditAccount(request, response);
					LOGGER.info("\n Account has been edited  ");
					request.getServletContext().setAttribute(InquirerConstants.ACCOUNTS_LIST,
							accountManager.accountList());
					redirectRequest("/admin/home.php", request, response);
				} else {
					if ("register".equals(request.getParameter("action"))) {
						registerRequest(request, response);
						LOGGER.info("\n New account has been added  ");
						request.getServletContext().setAttribute(InquirerConstants.ACCOUNTS_LIST,
								accountManager.accountList());
						redirectRequest("/login.php", request, response);
					} else {
						validateRequest(request, response);
						LOGGER.info("\n New account has been added  ");
						request.getServletContext().setAttribute(InquirerConstants.ACCOUNTS_LIST,
								accountManager.accountList());
						redirectRequest("/admin/home.php", request, response);
					}
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void validateRequest(HttpServletRequest request, HttpServletResponse response) throws IOException,
			ServletException {
		String username = null;
		String password = null;
		String email = null;
		List<Account> accountList = null;
		
		ServletContext context = request.getServletContext();
		accountList = (List<Account>) context.getAttribute(ACCOUNTS_LIST);

		try {
			checkFields(request);
			List<Role> roles = getRoles(request);
			Account newAccount = new Account(username, password, roles);
			checkAccountName(newAccount, accountList);
			newAccount.setEmail(email);
			newAccount.setEnabled(Integer.parseInt(request.getParameter("enabled")));
			// newAccount.setId(getAccountLastId(accountList)+1);
			accountManager.addAccount(newAccount);
			accountList.add(newAccount);
		} catch (InvalidDataException ex) {
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/admin/add_account.jsp", request, response);
		} catch (InquirerDataException ex) {
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/admin/add_account.jsp", request, response);
		}
	}

	private void checkFields(HttpServletRequest request) throws InvalidDataException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String confpassword = request.getParameter("confpassword");

		try {
			if (null != request.getParameter("enabled"))
				Integer.parseInt(request.getParameter("enabled"));
			
		} catch (NumberFormatException ex) {
			throw new InvalidDataException("Status incorrect!");
		}
		if (StringUtils.isBlank(username)) {
			throw new InvalidDataException("User name is blank!");
		}
		if (StringUtils.isBlank(password)) {
			throw new InvalidDataException("Password is blank!");
		}
		if (StringUtils.isBlank(confpassword)) {
			throw new InvalidDataException("Confirm password is blank!");
		}
		if(StringUtils.isBlank(email))
			throw new InvalidDataException("Email is blank!");
		if (!StringUtils.equals(password, confpassword)) {
			throw new InvalidDataException("Passwords in fields are not same!");
		}

	}

	private List<Role> getRoles(HttpServletRequest request) throws InvalidDataException {
		List<Role> result = new ArrayList<Role>();

		Integer checkedRole = 0;
		for (String roleName : ALL_ROLES_ATTRIBUTES) {
			if (null != request.getParameter(roleName)) {
				checkedRole = Integer.valueOf(request.getParameter(roleName));
				if (checkedRole != null && checkedRole != 0) {
					result.add(ListDataStorage.ALL_ROLES_MAP.get(checkedRole));
				}
			}
		}

		if (result.isEmpty())
			throw new InvalidDataException("Roles is not checked!");

		return result;
	}

	private void checkAccountName(Account account, List<Account> accountList) throws InvalidDataException {
		for (Account a : accountList) {
			if (account.getUsername().equals(a.getUsername()))
				throw new InvalidDataException("User with such name already exists!");
		}
	}

	private void doEditAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			InquirerDataException, IOException {
		Account editedAccount = (Account) request.getSession().getAttribute("EDITED_ACCOUNT");
		try {
			checkFields(request);
			editedAccount.setUsername(request.getParameter("username"));
			editedAccount.setPassword(request.getParameter("password"));
			editedAccount.setEmail(request.getParameter("email"));
			editedAccount.setRole(getRoles(request));
			editedAccount.setEnabled(Integer.parseInt(request.getParameter("enabled")));
			accountManager.updateAccount(editedAccount);
			request.getServletContext().setAttribute(ACCOUNTS_LIST, accountManager.accountList());
			request.getSession().removeAttribute("EDITED_ACCOUNT");
		} catch (InvalidDataException ex) {
			request.setAttribute("USERNAME", editedAccount.getUsername());
			request.setAttribute("PASSWORD", editedAccount.getPassword());
			request.setAttribute("EMAIL", editedAccount.getEmail());
			for (Role r : editedAccount.getRole())
				request.setAttribute("CHK_" + r.getId(), "checked");
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/admin/add_account.jsp", request, response);
		}
	}

	private void registerRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			InquirerDataException, IOException {
		Account regAccount = new Account();
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(new Role(ROLE_STUDENT,NAME_ROLE_STUDENT));
		try {
			checkFields(request);
			regAccount.setUsername(request.getParameter("username"));
			regAccount.setPassword(request.getParameter("password"));
			regAccount.setEmail(request.getParameter("email"));
			regAccount.setRole(roleList);
			accountManager.checkAccount(regAccount);
			accountManager.addAccount(regAccount);
			
			request.getSession().removeAttribute("register");
		} catch (InvalidDataException ex) {
			request.setAttribute("USERNAME", regAccount.getUsername());
			request.setAttribute("PASSWORD", regAccount.getPassword());
			request.setAttribute("EMAIL", regAccount.getEmail());
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/registration.jsp", request, response);

		} catch (InquirerDataException ex) {
			request.setAttribute("USERNAME", regAccount.getUsername());
			request.setAttribute("PASSWORD", regAccount.getPassword());
			request.setAttribute("EMAIL", regAccount.getEmail());
			request.setAttribute(VALIDATION_MESSAGE, ex.getMessage());
			gotoToJSP("/registration.jsp", request, response);

		}
	}

}
