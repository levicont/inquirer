<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.testForm.action.value = action;
		document.testForm.submit();
	}
</script>

<div>

	<form action="<%=request.getContextPath()%>/save_test.php"
		method="post" name="testForm">
		<input type="hidden" name="action" value="none" /> <input
			type="hidden" name="test" value="${TEST_ID }" />
		<p class="formTitle">
			<c:if test="${EDITED_TEST != null }"> Edit test</c:if>
			<c:if test="${EDITED_TEST == null }"> Add new test</c:if>
		</p>
		<table>
			<tr>
				<td>Title:</td>
				<td><input type="text" name="title" value="" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><textarea rows="5" cols="20" maxlength="250"
						name="description" /></textarea></td>
			</tr>
			<tr>
				<td>Time limit:</td>
				<td><input type="number" name="timeLimit" value="" /></td>
			</tr>
			<c:if test="${TEST_ID != null}">
				<tr>
					<td>Edit questions:</td>
					<td><button onclick="executeAction('all_questions')">...</button></td>
				</tr>
			</c:if>
			<tr>
				<td><button onclick="executeAction('cancel')">Cancel</button></td>
				<td><button onclick="executeAction('new_test')">Add new test</button></td>
			</tr>

		</table>

	</form>
	<p class="error">
		<jsp:include page="../modules/validMessage.jsp" />
	</p>

</div>