<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<script type="text/javascript"
	src="${CONTEXT }/resources/js/jquery.simplePagination.js">
	
</script>

<script type="text/javascript">
	$(function() {
		$("p.pagination").pagination({

			items : "${ACCOUNTS_ITEMS}",
			itemsOnPage : "${ACCOUNTS_ON_PAGE}",
			currentPage : "${page}",
			hrefTextPrefix : "?page=",
			cssStyle : 'light-theme'
		});
	});
</script>

<div class="accounts">	
	<table class="accountsTable">
		<caption>${RBUNDLE.getString("account_table_title") }</caption>
		<thead>
			<tr>
				<th>${RBUNDLE.getString("account_table_col_id") }</th>
				<th>${RBUNDLE.getString("account_table_col_num") }</th>
				<th>${RBUNDLE.getString("account_table_col_usname") }</th>
				<th>${RBUNDLE.getString("account_table_col_email") }</th>
				<th>${RBUNDLE.getString("account_table_col_roles") }</th>
				<th>${RBUNDLE.getString("account_table_col_enable") }</th>
				<th>${RBUNDLE.getString("account_table_col_serv") }</th>
			</tr>
		</thead>
		<tbody>

			<c:forEach var="accounts" varStatus="index" items="${ACCOUNTS_LIST }">
				<c:if
					test="${(index.count > (page-1)*ACCOUNTS_ON_PAGE) && (index.count <= (page)*ACCOUNTS_ON_PAGE)}">
					<c:set var="ACCOUNT_ID" value="${accounts.id }" />
					<tr>
						<td id="accounts">${accounts.id}</td>
						<td id="accounts">${index.count}</td>
						<td id="accounts">${accounts.username}</td>
						<td id="accounts">${accounts.email}</td>
						<td id="accounts"><c:forEach var="role"
								items="${accounts.role }" varStatus="item">
					${role.name}<c:if test="${!item.last }">,</c:if>
								<br>
							</c:forEach></td>
						<td id="accounts"> 
							<c:if test="${accounts.enabled !=0 }" >enabled</c:if>
							<c:if test="${accounts.enabled == 0 }" >disabled</c:if>
						</td>
						<td id="accounts"><a class="accountService"
							href="${CONTEXT }/admin/edit_account.php?id=${ACCOUNT_ID }">${RBUNDLE.getString("account_table_bt_edit") }</a>
							<a class="accountService" href="${CONTEXT }/admin/delete_account.php?id=${ACCOUNT_ID }"
							onclick="return(window.confirm('${RBUNDLE.getString('delete_account_confirm') }'))">${RBUNDLE.getString("account_table_bt_delete") }</a>
						</td>
					</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td class="refTD" colspan="7">
				<a class="addNew" href="${CONTEXT}/admin/add_account.php">${RBUNDLE.getString("account_table_bt_new_acc") }</a></td>
			</tr>

		</tbody>
	</table>
	<div class="pager">
		<p class="pagination"></p>
	</div>	
</div>