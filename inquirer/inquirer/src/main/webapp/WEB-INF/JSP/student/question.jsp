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
			var timeDiv = document.getElementById("timer");
			var timeP = document.getElementById("timerP");
			var timeText = timeP.firstChild;
			timeText.nodeValue = time;			
		}else{
			executeAction("next_question");
		}
	}
	onload = function(){
		setInterval("writeTimer()",1000);
	}
</script>
<a href="javascript:writeTimer()">Start timer</a>
<div id="timer">
	<p id="timerP">${QUESTION.test.timeLimit}</p>
</div>

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