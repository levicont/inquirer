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

			items : "${TEST_ITEMS}",
			itemsOnPage : "${ITEMS_ON_PAGE}",
			currentPage : "${page}",
			hrefTextPrefix : "?page=",
			cssStyle : 'light-theme'
		});
	});
</script>
<p class="error">
		<jsp:include page="../modules/validMessage.jsp"/>
	</p>
<div class="accounts">
	
	<table class="accountsTable">
		<caption>The Tests table</caption>
		<thead>
			<tr>
				<th>id</th>
				<th>#</th>
				<th>Title</th>
				<th>Description</th>
				<th>Author</th>
				<th>Time Limit (sec)</th>
				<th>Service</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="tests" varStatus="index" items="${TESTS_LIST}">
				<c:if
					test="${(index.count > (page-1)*ITEMS_ON_PAGE) && (index.count <= (page)*ITEMS_ON_PAGE)}">
					<c:set var="TEST_ID" value="${tests.id}" />
					<tr>
						<td id="accounts">${tests.id}</td>
						<td id="accounts">${index.count}</td>
						<td id="accounts">${tests.title}</td>
						<td id="accounts">${tests.description}</td>
						<td id="accounts">${tests.author.username}</td>
						<td id="accounts">${tests.timeLimit }</td>
						<td id="accounts">
						<c:if test="${ ROLE=='Administrator' || ROLE=='Advanced tutor' || ROLE=='Tutor' }">
							<c:if test="${ ROLE=='Administrator' || ROLE=='Advanced tutor'}">
								<a class="accountService"
								href="${CONTEXT }/edit_test.php?id=${TEST_ID }">Edit</a>
								<a class="accountService"
								href="${CONTEXT }/delete_test.php?id=${TEST_ID }"
								onclick="return(window.confirm('Are sure to want delete?'))">Delete</a>
							</c:if>
							<c:if test="${ROLE=='Tutor' && tests.author.id==CURRENT_SESSION_ACCOUNT.id}">
								<a class="accountService"
								href="${CONTEXT }/edit_test.php?id=${TEST_ID }">Edit</a>
								<a class="accountService"
								href="${CONTEXT }/delete_test.php?id=${TEST_ID }"
								onclick="return(window.confirm('Are sure to want delete?'))">Delete</a>
							</c:if>
							<a class="accountService"
								href="${CONTEXT }/start_test.php?id=${TEST_ID }">Start Test</a>
						</c:if>
						<c:if test="${ROLE=='Student' }">
							<a class="accountService"
								href="${CONTEXT }/start_test.php?id=${TEST_ID }">Start Test</a>
						</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<c:if
				test="${ ROLE=='Administrator' || ROLE=='Advanced tutor' || ROLE=='Tutor' }">
				<tr>
					<td class="refTD" colspan="7"><a class="addNew"
						href="${CONTEXT}/add_test.php">New test</a></td>
				</tr>
			</c:if>

		</tbody>
	</table>
	<div class="pager">
		<p class="pagination"></p>
	</div>
	
</div>