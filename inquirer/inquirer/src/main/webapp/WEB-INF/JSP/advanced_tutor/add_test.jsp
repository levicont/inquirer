<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.testForm.action.value = action;
		document.testForm.submit();
	}
</script>

<div class="mainForm">

	<form action="<%=request.getContextPath()%>/save_test.php"
		method="post" name="testForm">
		<input type="hidden" name="action" value="none" /> <input
			type="hidden" name="test" value="${TEST_ID }" />
		<p class="formTitle">
			<c:if test="${EDITED_TEST != null }"> ${RBUNDLE.getString("edit_test_caption") }</c:if>
			<c:if test="${EDITED_TEST == null }"> ${RBUNDLE.getString("add_test_caption") }</c:if>
		</p>
		<table>
			<tr>
				<td>${RBUNDLE.getString("add_test_title") }:</td>
				<td><input type="text" name="title" value="" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("add_test_description") }:</td>
				<td><textarea rows="5" cols="20" maxlength="250"
						name="description" /></textarea></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("add_test_time_limit") }:</td>
				<td><input type="number" name="timeLimit" value="" /></td>
			</tr>
			<c:if test="${TEST_ID != null}">
				<tr>
					<td>${RBUNDLE.getString("edit_test_edit_question") }:</td>
					<td><button onclick="executeAction('all_questions')">...</button></td>
				</tr>
			</c:if>
			<tr>
				<td><button onclick="executeAction('cancel')">${RBUNDLE.getString("add_test_bt_cancel") }</button></td>
				<td><button onclick="executeAction('new_test')">${RBUNDLE.getString("add_test_bt_save") }</button></td>
			</tr>

		</table>

	</form>
	
</div>