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
			cssStyle : 'light-theme',
			prevText:"${RBUNDLE.getString('pagenator_text_prev')}",
			nextText:"${RBUNDLE.getString('pagenator_text_next')}"
		});
	});
</script>
<div class="accounts">

	<table class="accountsTable">
		<caption>${RBUNDLE.getString("test_table_title") }</caption>
		<thead>
			<tr>
				<th>${RBUNDLE.getString("test_table_col_id") }</th>
				<th>${RBUNDLE.getString("test_table_col_num") }</th>
				<th>${RBUNDLE.getString("test_table_col_title") }</th>
				<th>${RBUNDLE.getString("test_table_col_descript") }</th>
				<th>${RBUNDLE.getString("test_table_col_author") }</th>
				<th>${RBUNDLE.getString("test_table_col_time") }</th>
				<th>${RBUNDLE.getString("test_table_col_service") }</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="tests" varStatus="index" items="${TESTS_LIST}">
				<c:if
					test="${(index.count > (page-1)*ITEMS_ON_PAGE) && (index.count <= (page)*ITEMS_ON_PAGE)}">
					<c:set var="TEST_ID" value="${tests.id}" />
					<c:if test="${(index.count mod 2)!=0 }">
						<tr id="oddRow">	
					</c:if>
					<c:if test="${(index.count mod 2)==0 }">
						<tr id="evenRow">	
					</c:if>					
						<td id="accounts">${tests.id}</td>
						<td id="accounts">${index.count}</td>
						<td id="accounts">${tests.title}</td>
						<td id="accounts">${tests.description}</td>
						<td id="accounts">${tests.author.username}</td>
						<td id="accounts">${tests.timeLimit }</td>
						<td id="accounts">
							<c:if	test="${ ROLE==RBUNDLE.getString('name_role_admin') || ROLE==RBUNDLE.getString('name_role_advanced_tutor') || ROLE==RBUNDLE.getString('name_role_tutor') }">
								<c:if test="${ ROLE==RBUNDLE.getString('name_role_admin')|| ROLE==RBUNDLE.getString('name_role_advanced_tutor')}">
									<a class="accountService"
										href="${CONTEXT }/edit_test.php?id=${TEST_ID }">${RBUNDLE.getString("test_table_bt_edit") }</a>
									<a class="accountService"
										href="${CONTEXT }/delete_test.php?id=${TEST_ID }"
										onclick="return(window.confirm('${RBUNDLE.getString('confirm_delete_test') }'))">${RBUNDLE.getString("test_table_bt_delete") }</a>
								</c:if>								
								<c:if
									test="${ROLE==RBUNDLE.getString('name_role_tutor') && tests.author.id==CURRENT_SESSION_ACCOUNT.id}">
									<a class="accountService"
										href="${CONTEXT }/edit_test.php?id=${TEST_ID }">${RBUNDLE.getString("test_table_bt_edit") }</a>
									<a class="accountService"
										href="${CONTEXT }/delete_test.php?id=${TEST_ID }"
										onclick="return(window.confirm('${RBUNDLE.getString('confirm_delete_test') }'))">${RBUNDLE.getString("test_table_bt_delete") }</a>
								</c:if>
								<a class="accountService"
									href="${CONTEXT }/start_test.php?id=${TEST_ID }">${RBUNDLE.getString("test_table_bt_start") }</a>
							</c:if> 
							<c:if test="${ROLE==RBUNDLE.getString('name_role_student') }">
								<a class="accountService"
									href="${CONTEXT }/start_test.php?id=${TEST_ID }">${RBUNDLE.getString("test_table_bt_start") }</a>
							</c:if>
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<c:if
				test="${ROLE==RBUNDLE.getString('name_role_admin') || ROLE==RBUNDLE.getString('name_role_advanced_tutor') || ROLE==RBUNDLE.getString('name_role_tutor')}">
				<tr>
					<td class="refTD" colspan="7"><a class="addNew"
						href="${CONTEXT}/add_test.php">${RBUNDLE.getString("test_table_bt_new_test") }</a></td>
				</tr>
			</c:if>

		</tbody>
	</table>	
	<div class="pager">
		<p class="pagination"></p>
	</div>

</div>