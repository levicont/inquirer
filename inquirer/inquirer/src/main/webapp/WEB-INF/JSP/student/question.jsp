<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<script>
	time = ${QUESTION.test.timeLimit * 60};
	
	function executeAction(action) {
		document.questionForm.action.value = action;
		document.questionForm.submit();
	}
	function a(ev){
		ev.preventDefault();
		return false;
	}
	function writeTimer(){
		if(time > 0){
			time--;			
			var timeP = document.getElementById("timerP");
			var timeText = timeP.firstChild;
			timeText.nodeValue = time;
			if(time<11)
				timeP.style.color = "red";
		}else{
			executeAction("next_question");
		}
	}
	onload = function(){
		setInterval("writeTimer()",1000);
	}
</script>

<div id="timer">
	<p >${RBUNDLE.getString("question_timer_before") } <span id="timerP">${QUESTION.test.timeLimit * 60}</span> ${RBUNDLE.getString("question_timer_after") }</p>
</div>

<div>
	<form class="questionForm" action="${CONTEXT }/next_question.php"
		method="post" name="questionForm" onsubmit="return false;">
				
		<input type="hidden" name="action" value="none"/> 
		<input type="hidden" name="timeStamp" value="${TIME_STAMP}"/>
		<input type="hidden" name="question" value="${QUESTION.id}" />
		<p class="questionFormTitle">${QUESTION.text }</p>
		<table id="answers">
			<tr>
					<td id="answersTitle" colspan="3">${RBUNDLE.getString("question_answer_choice") }:</td>
			</tr>
			<c:forEach var="answer" items="${ANSWERS_LIST}" varStatus="ansIndex">
				<c:set var="ANSCOUNT" value="${ansIndex.count + 1 }"/>
				<tr>
					<td colspan="3">
					<input type="checkbox" name="isCorrect_${answer.id}" />${ansIndex.count} - ${answer.text }</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3">
				<input type="checkbox" name="unknow" />${ANSCOUNT } - <span>${RBUNDLE.getString("question_unknown") }</span></td>
			</tr>
			<tr>				
				<td><button onclick="executeAction('cancel')">${RBUNDLE.getString("question_bt_cancel") }</button></td>
				<td><button onclick="executeAction('next_question')">${RBUNDLE.getString("question_bt_next") }</button></td>
			</tr>

		</table>
	</form>

</div>
