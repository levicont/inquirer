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
			cssStyle : 'light-theme',
			prevText:"${RBUNDLE.getString('pagenator_text_prev')}",
			nextText:"${RBUNDLE.getString('pagenator_text_next')}"
		});
	});
	function executeAction() {
		document.accountForm.submit();
	}
</script>
<div>
	<form name="selectForm" action="${CONTEXT }/all_results.php" method="post">
		<table>
			<tr>
				<td>${RBUNDLE.getString("all_test_results_choose_student") }:</td>
				<td>
					<select name="accountUsername" id="role">
						<c:if test="${CURRENT_SELECTED_ACCOUNT != null }">
							<c:if test="${CURRENT_SELECTED_ACCOUNT.getId()  != CURRENT_SESSION_ACCOUNT.getId()}">
								<option >${CURRENT_SESSION_ACCOUNT.getUsername()}</option>
							</c:if>							
							<option selected="selected">${CURRENT_SELECTED_ACCOUNT.getUsername()}</option>
						</c:if>
						<c:if test="${CURRENT_SELECTED_ACCOUNT == null }">
							<option >${CURRENT_SESSION_ACCOUNT.getUsername()}</option>
						</c:if>
						<c:if test="${STUDENTS_LIST != null }">
							<c:forEach var="student" varStatus="index" items="${STUDENTS_LIST }">							
								<option>${student.getUsername()}</option>
							</c:forEach>						
						</c:if>						
					</select>
				</td>
				<td>
					<button onclick="executeAction()">${RBUNDLE.getString("all_test_results_bt_choose_student") }</button>
				</td>
			</tr>
		</table>
	</form>
	<p></p>
</div>
<div class="accounts">
	
	<table class="accountsTable">
		<caption>${RBUNDLE.getString("all_test_results_title") }</caption>
		<thead>
			<tr>
				<th>${RBUNDLE.getString("all_test_results_col_num") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_title") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_total") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_correct") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_fail") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_value") }</th>
				<th>${RBUNDLE.getString("all_test_results_col_date") }</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="result" varStatus="index" items="${RESULTS_LIST}">
				<c:if
					test="${(index.count > (page-1)*ITEMS_ON_PAGE) && (index.count <= (page)*ITEMS_ON_PAGE)}">
					<c:set var="QUESTIONS_COUNT" value="${result.correctAnswers+result.failAnswers}"/>
					
					<tr>
						<td id="accounts"><a href="${CONTEXT }/test_mistakes.php?id=${result.id }">${index.count}</a></td>
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