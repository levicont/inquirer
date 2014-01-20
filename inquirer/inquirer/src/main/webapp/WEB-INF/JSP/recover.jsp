<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.accountForm.action.value = action;
		document.accountForm.submit();
	}
</script>

<div class="registerForm">
	<p>
		<strong>${RECOVER_MESSAGE }</strong>
	</p>
	<c:if test="${RECOVER_MESSAGE != null }">
		<a href="${CONTEXT }/login.php"> Back to login page </a>
	</c:if>
	<c:if test="${RECOVER_MESSAGE  == null}">
			<form action="${CONTEXT }/recover.php" method="post" name="accountForm">
				<input type="hidden" name="action" value="none" />
				<p class="formTitle">Recover account</p>
				<table>
					
					<tr>
						<td>User name:</td>
						<td><input type="text" name="username" value="" /></td>
					</tr>
					<tr>
						<td>E-mail:</td>
						<td><input type="email" name="email" value="" /></td>
					</tr>
					<tr>
						<td><button onclick="executeAction('cancel_recover')">Cancel</button></td>
						<td><button onclick="executeAction('recover')">Recover</button></td>
					</tr>
					<tr>

					</tr>
				</table>

			</form>
	</c:if>
	<c:if test="${VALIDATION_MESSAGE != null }">
	<p class="error">
		<jsp:include page="modules/validMessage.jsp" />
	</p>
</c:if>

</div>