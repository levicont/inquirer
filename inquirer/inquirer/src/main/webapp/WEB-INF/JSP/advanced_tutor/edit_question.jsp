<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
	function executeAction(action) {
		document.questionForm.action.value = action;
		document.questionForm.submit();
	}
</script>

<div>
	
	<form action="<%=request.getContextPath()%>/save_question.php"
		method="post" name="questionForm" onsubmit="return false;">
		<input type="hidden" name="action" value="none" /> 
		<input type="hidden" name="test" value="${test_id}" /> 
		<input type="hidden" name="question" value="${EDITED_QUESTION.id}" />
		<p class="formTitle">Edit question</p>
		<table>
			<tr>
				<td>Question:</td>
				<td colspan="2"><textarea rows="5" cols="40" maxlength="250"
						name="text">${QUESTION_TEXT} </textarea></td>
			</tr>
			<tr>
				<td class="formTitle" colspan="3">Answers</td>
			</tr>

			<c:forEach var="answer" items="${ANSWERS_LIST}" varStatus="ansIndex">

				<tr>
					<td colspan="3">Answer ${ansIndex.count}:</td>
				</tr>
				<tr>
					<td colspan="3"><c:set var="answerText">${answer.text }</c:set>
						<textarea rows="4" cols="35" maxlength="250"
							name="answer_${ansIndex.count}">${answerText}</textarea></td>
				</tr>
				<tr>
					<td colspan="3"><input type="checkbox"
						<c:if test="${answer.isCorrect != 0 }">checked="checked"</c:if>
						name="isCorrect_${ansIndex.count}" />Answer is correct.</td>
				</tr>
			</c:forEach>
			
			<c:if test="${(DEFAULT_ANSWERS_COUNT-SIZE_OF_ANSWER_LIST) > 0 }">
				<c:forEach begin="${SIZE_OF_ANSWER_LIST+1}"
					end="${DEFAULT_ANSWERS_COUNT }" step="1" varStatus="index">
					<tr>
						<td colspan="3">Answer ${index.count+SIZE_OF_ANSWER_LIST}:</td>
					</tr>
					<tr>
						<td colspan="3"><textarea rows="4" cols="35" maxlength="250"
								name="answer_${index.count+SIZE_OF_ANSWER_LIST}"></textarea></td>
					</tr>
					<tr>
						<td colspan="3"><input type="checkbox"
							name="isCorrect_${index.count+SIZE_OF_ANSWER_LIST}" />Answer is
							correct.</td>
					</tr>
				</c:forEach>
			</c:if>
			<tr>
				<td><button onclick="executeAction('cancel')">Cancel</button></td>
				<td><button onclick="executeAction('edit_question')">Save
						question</button></td>
			</tr>

		</table>

	</form>
	<p class="error">
		<jsp:include page="../modules/validMessage.jsp" />
	</p>

</div>