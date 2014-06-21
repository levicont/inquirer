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
	<caption>${RBUNDLE.getString("test_result_title") }</caption>
		<thead>
			<tr>
				<th>${RBUNDLE.getString("test_result_col_title") }</th>
				<th>${RBUNDLE.getString("test_result_col_correct") }</th>
				<th>${RBUNDLE.getString("test_result_col_fail") }</th>
				<th>${RBUNDLE.getString("test_result_col_value") }</th>
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
	<p></p>
	
	<c:if test="${CURRENT_TEST_MISTAKES.size() > 0 }">
	<div id="mistakesContanier">
	<table class="accountsTable">
	<caption>${RBUNDLE.getString("test_mistakes_title") }</caption>
		<thead>
			<tr>
				<th>${RBUNDLE.getString("test_mistakes_col_num_question") }</th>
				<th>${RBUNDLE.getString("test_mistakes_col_question") }</th>
				<th>${RBUNDLE.getString("test_mistakes_col_fail_answer") }</th>
				<th>${RBUNDLE.getString("test_mistakes_col_correct_answer") }</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="mistake" items="${CURRENT_TEST_MISTAKES}" varStatus="index">
				<c:if test="${(index.count mod 2)!=0 }">
						<tr id="oddRow">	
				</c:if>
				<c:if test="${(index.count mod 2)==0 }">
						<tr id="evenRow">	
				</c:if>
					
				<td>${mistake.question.number }</td>
				<td>${mistake.question.text }</td>
				<td>${mistake.failAnswerText }</td>
				<td>${mistake.correctAnswerText}</td>			
			</tr>
			</c:forEach>
		</tbody>	
	</table>
</div>
	</c:if>
	<form class="resultForm" action="${CONTEXT }/all_results.php" method="post" name="resultForm">
	<input type="hidden" name="action" value="none">
	<input type="button" onclick="executeAction('back')" value="${RBUNDLE.getString('test_result_show_all_results') }"/>
	</form>

</div>