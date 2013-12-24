<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.testForm.action.value = action;
		document.testForm.submit();
	}
	function a(ev){
		ev.preventDefault();
		return false;
	}
</script>

<div>

	<form action="<%=request.getContextPath()%>/save_test.php" onsubmit="a(event);"
		method="post" name="testForm" >
		<input type="hidden" name="action" value="none" /> 
		<input type="hidden" name="test" value="${EDITED_TEST_ID}" />
		<p class="formTitle">
			Edit test
		</p>
		<table>
			<tr>
				<td>Title:</td>
				<td><input type="text" name="title" value="${TITLE }" /></td>
			</tr>
			<tr>
				<td>Description:</td>
				<td><textarea rows="5" cols="20" maxlength="250"
						name="description" >${DESCRIPTION }</textarea></td>
			</tr>
			<tr>
				<td>Time limit:</td>
				<td><input type="number" name="timeLimit" value="${TIME_LIMIT }"  /></td>
			</tr>
			<c:if test="${EDITED_TEST_ID != null}">
				<tr>
					<td>Edit questions:</td>
					<td><input type="button" onclick="executeAction('all_questions')" value="..."/></td>
				</tr>
			</c:if>
			<tr>
				<td><input type="button" onclick="executeAction('cancel')" value="Cancel"/></td>
				<td><input type="button" onclick="executeAction('edit')" value="Save Test"/></td>
			</tr>

		</table>

	</form>
	<p class="error">
		<jsp:include page="../modules/validMessage.jsp" />
	</p>

</div>