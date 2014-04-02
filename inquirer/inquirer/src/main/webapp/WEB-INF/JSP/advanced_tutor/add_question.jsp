<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script>
	function executeAction(action) {
		document.questionForm.action.value = action;
		document.questionForm.submit();
	}
</script>

<div class="mainForm">
	
	<form action="<%=request.getContextPath()%>/save_question.php"
		method="post" name="questionForm">
		<input type="hidden" name="action" value="none" />
		<input type="hidden" name="test" value="${TEST_ID}" />
		<p class="formTitle">
			<c:if test="${EDITED_TEST != null }">${RBUNDLE.getString("edit_question_title") }</c:if>
			<c:if test="${EDITED_TEST == null }">${RBUNDLE.getString("add_question_title") }</c:if>
		</p>
		<table>
			<tr>
				<td>${RBUNDLE.getString("add_question_number")}:</td>
				<td colspan="2">
					<input id="questionNumberInput" type="number" name="number" value="${QUESTION_NUMBER}">
				</td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("add_question") }:</td>
				<td colspan="2">
					<textarea id="taQuestion" rows="5" cols="40" maxlength="250" name="text">${QUESTION_TEXT}</textarea>
				</td>
			</tr>
			<tr>
					<td class="formTitle" colspan="3">${RBUNDLE.getString("add_question_answers_title") }</td>				
			</tr>			
			<c:forEach var="answer" items="${ANSWERS_LIST}" varStatus="ansIndex">

				<tr>
					<td id="tdAnswer" colspan="3">${RBUNDLE.getString("add_question_answer") } ${ansIndex.count}:</td>
				</tr>
				<tr>
					<td colspan="3"><c:set var="answerText">${answer.text }</c:set>
						<textarea id="taAnswer" rows="4" cols="35" maxlength="250"						
							name="answer_${ansIndex.count}">${answerText}</textarea></td>
				</tr>
				<tr>
					<td colspan="3"><input type="checkbox"
						<c:if test="${answer.isCorrect != 0 }">checked="checked"</c:if>
						name="isCorrect_${ansIndex.count}" />${RBUNDLE.getString("add_question_correct") }</td>
				</tr>
			</c:forEach>
			
			<c:if test="${(DEFAULT_ANSWERS_COUNT-SIZE_OF_ANSWER_LIST) > 0 }">
				<c:forEach begin="${SIZE_OF_ANSWER_LIST+1}"
					end="${DEFAULT_ANSWERS_COUNT }" step="1" varStatus="index">
					<tr>
						<td id="tdAnswer" colspan="3">${RBUNDLE.getString("add_question_answer") } ${index.count+SIZE_OF_ANSWER_LIST}:</td>
					</tr>
					<tr>
						<td colspan="3"><textarea id="taAnswer" rows="4" cols="35" maxlength="250"						 
								name="answer_${index.count+SIZE_OF_ANSWER_LIST}"></textarea></td>
					</tr>
					<tr>
						<td colspan="3"><input type="checkbox"
							name="isCorrect_${index.count+SIZE_OF_ANSWER_LIST}" />${RBUNDLE.getString("add_question_correct") }</td>
					</tr>
				</c:forEach>
			</c:if>			
			<tr>
				<td><button onclick="executeAction('cancel')">${RBUNDLE.getString("add_question_bt_cancel") }</button></td>
				<td><button onclick="executeAction('new_question')">${RBUNDLE.getString("add_question_bt_save") }</button></td>
			</tr>

		</table>

	</form>
	
</div>