<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script>
	function executeAction(action) {
		document.resultForm.action.value = action;
		document.resultForm.submit();
	}
	function a(ev){
		ev.preventDefault();
		return false;
	}
</script>
<div>
	<table class="accountsTable">
	<caption>Test results</caption>
		<thead>
			<tr>
				<th>Test title</th>
				<th>Correct answers</th>
				<th>Fail answers</th>
				<th>Value</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>${CURRENT_TEST_RESULT.test.title }</td>
				<td>${CURRENT_TEST_RESULT.correctAnswers }</td>
				<td>${CURRENT_TEST_RESULT.failAnswers }</td>
				<td><f:formatNumber maxFractionDigits="2">${CURRENT_TEST_RESULT.correctAnswers*100/QUESTIONS_COUNT }</f:formatNumber>
				%</td>			
			</tr>
		</tbody>
	
	</table>
	<form class="resultForm" action="${CONTEXT }/all_tests.php" method="post" name="resultForm">
	<input type="hidden" name="action" value="none">
	<input type="button" onclick="executeAction('back')" value="Back"/>
	</form>

</div>