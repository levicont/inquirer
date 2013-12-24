<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
function executeAction(action) {
	document.accountForm.action.value = action;
	document.accountForm.submit();
}
</script>

<div class="registerForm">
	
	<form action="<%=request.getContextPath()%>/admin/save_account.php" method="post" name="accountForm">
	<input type="hidden" name="action" value="none" />
		<p class="formTitle">Registration new account</p>
		<table>
			
			<tr>
				<td>User name:</td>
				<td><input type="text" name="username" value=""/></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td>Confirm password:</td>
				<td><input type="password" name="confpassword" value="" /></td>
			</tr>
			<tr>
				<td>E-mail:</td>
				<td><input type="email" name="email" value="" /></td>
			</tr>
			<tr>	
				<td>Role:</td><td><input type="checkbox" name="chkStudent"  checked="checked"
									value="<%=InquirerConstants.ROLE_STUDENT %>" /> Student </td>
			</tr>
			<tr>	
				<td><button onclick="executeAction('cancel_register')">Cancel</button></td>
				<td><button onclick="executeAction('register')" >Register</button></td>
			</tr>
			<tr>
				
			</tr>	
		</table>

	</form>
	<p class="error">
		<jsp:include page="modules/validMessage.jsp" />
	</p>
	
</div>