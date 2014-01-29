<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript"
	src="${CONTEXT}/resources/js/jquery.simplePagination.js">
	
</script>

<script type="text/javascript">
	$(function() {
		$("p.pagination").pagination({

			items : "${QUESTION_ITEMS}",
			itemsOnPage : "${ITEMS_ON_PAGE}",
			currentPage : "${page}",
			hrefTextPrefix : "?page=",
			cssStyle : 'light-theme'
		});
	});
</script>
<div class="testTitle">
	<a href="${CONTEXT}/edit_test.php?id=${test_id }">
	Back to test <span>${CURRENT_TEST.title}</span>
	</a>
</div>
<div class="accounts">
	<table class="accountsTable">
		<caption>The Questions table</caption>
		<thead>
			<tr>
				<th>id</th>
				<th>#</th>
				<th>Test title</th>
				<th>Question</th>
				<th>Service</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="questions" varStatus="index"
				items="${QUESTIONS_LIST}">
				<c:if
					test="${(index.count > (page-1)*ITEMS_ON_PAGE) && (index.count <= (page)*ITEMS_ON_PAGE)}">
					<c:set var="QUESTION_ID" value="${questions.id}" />
					<tr>
						<td id="accounts">${questions.id}</td>
						<td id="accounts">${index.count}</td>
						<td id="accounts">${questions.test.title}</td>
						<td id="accounts">${questions.text}</td>
						<td id="accounts"><c:if
								test="${ ROLE=='Administrator' || ROLE=='Advanced tutor' || ROLE=='Tutor' }">
								<a class="accountService"
									href="${CONTEXT }/edit_question.php?id=${QUESTION_ID }&test_id=${test_id}">Edit</a>
								<a class="accountService"
									href="${CONTEXT }/delete_question.php?id=${QUESTION_ID }"
									onclick="return(window.confirm('Are sure to want delete?'))">Delete</a>
							</c:if></td>
					</tr>
				</c:if>
			</c:forEach>
			<c:if
				test="${ ROLE=='Administrator' || ROLE=='Advanced tutor' || ROLE=='Tutor' }">
				<tr>
					<td class="refTD" colspan="5"><a class="addNew"
						href="${CONTEXT}/add_question.php?test_id=${test_id}">New question</a></td>
				</tr>
			</c:if>

		</tbody>
	</table>
	<div class="pager">
		<p class="pagination"></p>
	</div>
	
</div>