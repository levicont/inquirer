<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript"
	src="${CONTEXT}/resources/js/jquery.simplePagination.js">
	
</script>

<script type="text/javascript">
	$(function() {
		$("p.pagination").pagination({

			items : "${RESULTS_ITEMS}",
			itemsOnPage : "${ITEMS_ON_PAGE}",
			currentPage : "${page}",
			hrefTextPrefix : "?page=",
			cssStyle : 'light-theme'
		});
	});
</script>
<div class="accounts">
	
	<table class="accountsTable">
		<caption>The tests results</caption>
		<thead>
			<tr>
				<th>#</th>
				<th>Test title</th>
				<th>Questions</th>
				<th>Correct<br> answers</th>
				<th>Fail<br> answers</th>
				<th>Value</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="result" varStatus="index" items="${RESULTS_LIST}">
				<c:if
					test="${(index.count > (page-1)*ITEMS_ON_PAGE) && (index.count <= (page)*ITEMS_ON_PAGE)}">
					<c:set var="QUESTIONS_COUNT" value="${result.correctAnswers+result.failAnswers}"/>
					<tr>
						<td id="accounts">${index.count}</td>
						<td id="accounts">${result.test.title}</td>
						<td id="accounts">${QUESTIONS_COUNT}</td>
						<td id="accounts">${result.correctAnswers}</td>
						<td id="accounts">${result.failAnswers}</td>
						<td id="accounts"><f:formatNumber maxFractionDigits="2">${result.correctAnswers*100/QUESTIONS_COUNT }</f:formatNumber>%</td>
						<td id="accounts"><f:formatDate value="${result.date }" pattern="dd-MM-yyyy HH:mm:ss" />
						
						</td>
					</tr>
				</c:if>
			</c:forEach>
			

		</tbody>
	</table>
	<div class="pager">
		<p class="pagination"></p>
	</div>
	
</div>