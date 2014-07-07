<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ page import="com.lvg.inquirer.InquirerConstants"%>
<%@ taglib  uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script>
function executeAction(action) {
	document.profileForm.action.value = action;
	document.profileForm.submit();
}
</script>

<div class="mainForm">
	
	<form action="${CONTEXT }/save_profile.php" method="post" name="profileForm">
	<input type="hidden" name="action" value="none" />
		<p class="formTitle">${RBUNDLE.getString("profile_fm_title") } </p>
		<table>
			
			<tr>
				<td>${RBUNDLE.getString("profile_fm_usname") }:</td>
				<td><input type="text" name="username" value="${CURRENT_SESSION_ACCOUNT.username}" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if>/></td>
			</tr>			
			<tr>
				<td>${RBUNDLE.getString("profile_fm_email") }:</td>
				<td><input type="email" name="email" value="${CURRENT_SESSION_ACCOUNT.email}" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if> /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("profile_fm_old_pwd") }:</td>
				<td><input type="password" name="oldPassword" value="" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if>/></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("profile_fm_new_pwd") }:</td>
				<td><input type="password" name="newPassword" value="" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if> /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("profile_fm_conf_pwd") }:</td>
				<td><input type="password" name="confirmPassword" value="" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if>/></td>
			</tr>
						
			<tr>	
				<td><button onclick="executeAction('cancel')">${RBUNDLE.getString("profile_fm_bt_cancel") }</button></td>
				<td><button onclick="executeAction('save')" <c:if test="${CURRENT_SESSION_ROLE.getId()==4 }">disabled="disabled"</c:if> >${RBUNDLE.getString("profile_fm_bt_save") }</button></td>
			</tr>
			<tr>
				
			</tr>	
		</table>

	</form>
	
	
</div>