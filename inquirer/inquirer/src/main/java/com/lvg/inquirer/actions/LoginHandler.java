package com.lvg.inquirer.actions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.lvg.inquirer.InquirerConstants;
import com.lvg.inquirer.exceptions.InquirerDataException;
import com.lvg.inquirer.exceptions.InvalidDataException;
import com.lvg.inquirer.mocks.RoleDataBaseManager;
import com.lvg.inquirer.models.Account;
import com.lvg.inquirer.models.Role;
import com.lvg.inquirer.services.RoleDataService;

public class LoginHandler extends AbstractInquirerServletHandler {

	private static final long serialVersionUID = 1363769414330823768L;
	private final Map<Integer, String> homePages = new HashMap<Integer, String>();
	private final String ROLES_ATTRIBUTE = "roles";
	private static final RoleDataService roleManager = new RoleDataBaseManager();
	public LoginHandler() {
		homePages.put(ROLE_STUDENT, "/student/home.php");
		homePages.put(ROLE_ADMIN, "/admin/home.php");
		homePages.put(ROLE_TUTOR, "/tutor/home.php");
		homePages.put(ROLE_ADVANCED_TUTOR, "/advanced_tutor/home.php");
	}

	@Override
	protected void handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (request.getMethod().equals("GET")) {
			showLoginPage(request, response);
		} else {
			loginHandler(request, response);
		}
	}

	protected void showLoginPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<Role> roles = roleManager.rolesList();
		if (request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT) != null) {
			Account a = (Account) request.getSession().getAttribute(CURRENT_SESSION_ACCOUNT);
			Integer idRole = a.getRole().get(0).getId();
			redirectRequest(homePages.get(idRole), request, response);
		} else {
			request.setAttribute(ROLES_ATTRIBUTE, roles);
			gotoToJSP("/login.jsp", request, response);
		}

	}

	protected void validateRequest(String username, String password) throws InvalidDataException {
				 
		if (StringUtils.isBlank(username)) {
			throw new InvalidDataException(ERR_USNAME_BLANK);
		}
		if (StringUtils.isBlank(password)) {
			throw new InvalidDataException(ERR_PASSWORD_BLANK);
		}
	}

	protected void loginHandler(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		Integer idRole = Integer.parseInt(request.getParameter("role"));
		ResourceBundle errMessage = (ResourceBundle)request.getSession().getAttribute(RESOURCE_BUNDLE);
		ResourceBundle resBundle = errMessage;
		try {
			validateRequest(username, password);
			Account account = getDataService().login(username, password, idRole);
			String homePage = homePages.get(idRole);
			if (homePage != null) {
				request.getSession().setAttribute(CURRENT_SESSION_ACCOUNT, account);
				request.getSession().setAttribute("ROLE", resBundle.getString(InquirerConstants.NAMES_ROLES.get(idRole)));
				request.getSession().setAttribute(CURRENT_SESSION_ROLE, roleManager.getRoleById(idRole));
				redirectRequest(homePage, request, response);
			} else {
				String errStr = errMessage.getString(ERR_UNSUPPORTED_ROLE)+idRole;
				throw new InquirerDataException(errStr);
			}

		} catch (InvalidDataException ex) {
			request.setAttribute(VALIDATION_MESSAGE, errMessage.getString(ex.getMessage()));
			gotoToJSP("/login.jsp", request, response);

		}
	}
}
