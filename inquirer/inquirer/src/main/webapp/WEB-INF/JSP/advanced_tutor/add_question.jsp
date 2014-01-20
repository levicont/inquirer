<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.questionForm.action.value = action;
		document.questionForm.submit();
	}
</script>

<div>
	
	<form action="<%=request.getContextPath()%>/save_question.php"
		method="post" name="questionForm">
		<input type="hidden" name="action" value="none" />
		<input type="hidden" name="test" value="${TEST_ID}" />
		<p class="formTitle">
			<c:if test="${EDITED_TEST != null }"> Edit question</c:if>
			<c:if test="${EDITED_TEST == null }"> Add new question</c:if>
		</p>
		<table>
			<tr>
				<td>Question:</td>
				<td colspan="2"><textarea rows="5" cols="40" maxlength="250"
						name="text" /></textarea></td>
			</tr>
			<tr>
					<td class="formTitle" colspan="3">Answers</td>				
			</tr>
			<c:forEach begin="1" end="${DEFAULT_ANSWERS_COUNT }" step="1" varStatus="index">
				<tr>
					<td colspan="3">Answer ${index.count}:</td>				
				</tr>
				<tr>
					
					<td colspan="3"><textarea rows="4" cols="35" maxlength="250"
						name="answer_${index.count}" ></textarea></td>
				</tr>
				<tr>
					<td colspan="3"><input type="checkbox" name="isCorrect_${index.count}"/>Answer is correct.</td>
				</tr>
			</c:forEach>
			<tr>
				<td><button onclick="executeAction('cancel')">Cancel</button></td>
				<td><button onclick="executeAction('new_question')">Save question</button></td>
			</tr>

		</table>

	</form>
	
</div>