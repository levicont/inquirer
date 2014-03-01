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
		<p class="formTitle">${RBUNDLE.getString("reg_fm_title") }<br>
		</p>
		<table>
			
			<tr>
				<td>${RBUNDLE.getString("reg_fm_usname") }:</td>
				<td><input type="text" name="username" value=""/></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("reg_fm_password") }:</td>
				<td><input type="password" name="password" value=""/></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("reg_fm_conf_pwd") }:</td>
				<td><input type="password" name="confpassword" value="" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("reg_fm_email") }:</td>
				<td><input type="email" name="email" value="" /></td>
			</tr>
			<tr>	
				<td>${RBUNDLE.getString("reg_fm_role") }:</td><td><input type="checkbox" name="chkStudent"  checked="checked"
									value="<%=InquirerConstants.ROLE_STUDENT %>" /> Student </td>
			</tr>
			<tr>	
				<td><button onclick="executeAction('cancel_register')">${RBUNDLE.getString("reg_fm_bt_cancel") }</button></td>
				<td><button onclick="executeAction('register')" >${RBUNDLE.getString("reg_fm_bt_register") }</button></td>
			</tr>
			<tr>
				
			</tr>	
		</table>

	</form>
	<c:if test="${VALIDATION_MESSAGE != null }">
	<p class="error">
		<jsp:include page="modules/validMessage.jsp" />
	</p>
</c:if>
	
</div>