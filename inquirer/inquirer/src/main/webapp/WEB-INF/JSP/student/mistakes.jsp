<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

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
			<tr>
				<td>${mistake.question.number }</td>
				<td>${mistake.question.title }</td>
				<td>${mistake.failAnswerText }</td>
				<td>${mistake.correctAnswerText}</td>			
			</tr>
			</c:forEach>
		</tbody>	
	</table>
	

</div>