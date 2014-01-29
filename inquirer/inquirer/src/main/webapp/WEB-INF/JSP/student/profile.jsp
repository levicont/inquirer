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
		<p class="formTitle">Profile </p>
		<table>
			
			<tr>
				<td>User name:</td>
				<td><input type="text" name="username" value="${CURRENT_SESSION_ACCOUNT.username}"/></td>
			</tr>			
			<tr>
				<td>E-mail:</td>
				<td><input type="email" name="email" value="${CURRENT_SESSION_ACCOUNT.email}" /></td>
			</tr>
			<tr>
				<td>Old Password:</td>
				<td><input type="password" name="oldPassword" value=""/></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="password" name="newPassword" value=""/></td>
			</tr>
			<tr>
				<td>Confirm password:</td>
				<td><input type="password" name="confirmPassword" value="" /></td>
			</tr>
						
			<tr>	
				<td><button onclick="executeAction('cancel')">Cancel</button></td>
				<td><button onclick="executeAction('save')" >Save</button></td>
			</tr>
			<tr>
				
			</tr>	
		</table>

	</form>
	
	
</div>