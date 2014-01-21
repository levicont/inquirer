<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script>
	function executeAction(action) {
		document.questionForm.action.value = action;
		document.questionForm.submit();
	}
	function a(ev){
		ev.preventDefault();
		return false;
	}
</script>



<div>
	<form action="${CONTEXT }/next_question.php"
		method="post" name="questionForm" onsubmit="return false;">
				
		<input type="hidden" name="action" value="none"/> 
		
		<input type="hidden" name="question" value="${QUESTION.id}" />
		<p class="formTitle">${QUESTION.text }</p>
		<table>
			<tr>
					<td colspan="3">Answers choices:</td>
			</tr>
			<c:forEach var="answer" items="${ANSWERS_LIST}" varStatus="ansIndex">

				<tr>
					<td colspan="3">
					<input type="checkbox" name="isCorrect_${answer.id}" />${ansIndex.count} - ${answer.text }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3">
				<input type="checkbox" name="unknow" /><span>  </span> I don't know.</td>
			</tr>
			<tr>				
				<td><button onclick="executeAction('cancel')">Cancel</button></td>
				<td><button onclick="executeAction('next_question')">Next question</button></td>
			</tr>

		</table>
	</form>

</div>