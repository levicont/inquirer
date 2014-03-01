<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.accountForm.action.value = action;
		document.accountForm.submit();
	}
</script>

<div class="mainForm">

	<form action="<%=request.getContextPath()%>/admin/save_account.php"
		method="post" name="accountForm">
		<input type="hidden" name="action" value="none" />
		
		<p class="formTitle">
			<c:if test="${EDITED_ACCOUNT != null }">${RBUNDLE.getString("edit_account_title") }</c:if>
			<c:if test="${EDITED_ACCOUNT == null }">${RBUNDLE.getString("add_account_title") }</c:if>
		</p>
		<table>
			<tr>
				<td>${RBUNDLE.getString("edit_account_usname") }:</td>
				<td><input type="text" name="username" value="${USERNAME }" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("edit_account_password") }:</td>
				<td><input type="password" name="password" value="${PASSWORD }" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("edit_account_conf_pwd") }:</td>
				<td><input type="password" name="confpassword"
					value="${PASSWORD }" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("edit_account_email") }:</td>
				<td><input type="email" name="email" value="${EMAIL }" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("edit_account_role") }:</td>
				<td><input type="checkbox" name="chkAdmin"
					value="<%=InquirerConstants.ROLE_ADMIN%>"
					<c:if test="${CHK_1 != null }">checked="checked"</c:if> />
					Administrator</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="chkAdvancedTutor"
					value="<%=InquirerConstants.ROLE_ADVANCED_TUTOR%>"
					<c:if test="${CHK_2 != null }">checked="checked"</c:if> />
					Advanced tutor</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="chkTutor"
					value="<%=InquirerConstants.ROLE_TUTOR %>"
					<c:if test="${CHK_3 != null }">checked="checked"</c:if> /> Tutor</td>
			</tr>
			<tr>
				<td></td>
				<td><input type="checkbox" name="chkStudent"
					value="<%=InquirerConstants.ROLE_STUDENT %>"
					<c:if test="${CHK_4 != null }">checked="checked"</c:if> /> Student
				</td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("edit_account_status") }</td>
				<td><select name="enabled" id="role">
						<c:if test="${ENABLED == '0'}">
							<option value="0">Disabled</option>
							<option value="1">Enabled</option>
						</c:if>
						<c:if test="${ENABLED != '0'}">
							<option value="1">Enabled</option>
							<option value="0">Disabled</option>
						</c:if>
						
				</select></td>
			</tr>
			<tr>
				<td><button onclick="executeAction('cancel')">${RBUNDLE.getString("edit_account_bt_cancel") }</button></td>
				<c:if test="${EDITED_ACCOUNT != null }"> <td><input type="button" value="${RBUNDLE.getString('edit_account_bt_save') }" onclick="executeAction('edit')" /></td></c:if>
			<c:if test="${EDITED_ACCOUNT == null }"> <td><input type="button" value="${RBUNDLE.getString('edit_account_bt_save') }" onclick="executeAction('add')" /></td></c:if>
				
			</tr>
			<tr>

			</tr>
		</table>

	</form>
	
</div>