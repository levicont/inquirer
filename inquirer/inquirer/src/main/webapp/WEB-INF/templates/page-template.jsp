<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
		<link rel="stylesheet" type="text/css" 	href="${CONTEXT }/resources/css/main.css"/>
		<link rel="stylesheet" type="text/css" 	href="${CONTEXT }/resources/css/simplePagination.css" />
		<script type="text/javascript"	src="${CONTEXT }/resources/js/jquery-2.0.3.js"></script>
		<title>Inquirer page</title>
	</head>
<body class="default">
	<div class="content">
	<div class="header">
		<h1>${RBUNDLE.getString("title") }</h1>
		<div class="language"> 
			<a href="${CONTEXT }/login.php?lang=ru">рус</a>
			<a href="${CONTEXT }/login.php?lang=en">eng</a>
		</div>
	</div>

	<div class="main">

		<c:if test="${CURRENT_SESSION_ACCOUNT != null}">
			<div class="mainMenu">
				<p class="menuTitle">${RBUNDLE.getString("main_menu_title") }</p>
				<ol>
					<c:if test="${ROLE == RBUNDLE.getString('name_role_admin') }">
						<li><a href="${CONTEXT }/profile.php">${RBUNDLE.getString("main_menu_profile") }</a></li>
						<li><a href="${CONTEXT }/all_tests.php">${RBUNDLE.getString("main_menu_tests") }</a></li>
						<li><a href="${CONTEXT }/add_test.php">${RBUNDLE.getString("main_menu_new_test") }</a></li>
						<li><a href="${CONTEXT }/all_accounts.php">${RBUNDLE.getString("main_menu_accounts") }</a></li>
						<li><a href="${CONTEXT }/all_results.php">${RBUNDLE.getString("main_menu_results") }</a></li>
						<li><a href="${CONTEXT }/start_test.php">${RBUNDLE.getString("main_menu_start_test") }</a></li>

					</c:if>
					<c:if test="${ROLE == RBUNDLE.getString('name_role_advanced_tutor')}">

						<li><a href="${CONTEXT }/profile.php">${RBUNDLE.getString("main_menu_profile") }</a></li>
						<li><a href="${CONTEXT }/all_tests.php">${RBUNDLE.getString("main_menu_tests") }</a></li>
						<li><a href="${CONTEXT }/add_test.php">${RBUNDLE.getString("main_menu_new_test") }</a></li>						
						<li><a href="${CONTEXT }/all_results.php">${RBUNDLE.getString("main_menu_results") }</a></li>
						<li><a href="${CONTEXT }/start_test.php">${RBUNDLE.getString("main_menu_start_test") }</a></li>
					</c:if>
					<c:if test="${ROLE == RBUNDLE.getString('name_role_tutor')}">

						<li><a href="${CONTEXT }/profile.php">${RBUNDLE.getString("main_menu_profile") }</a></li>
						<li><a href="${CONTEXT }/all_tests.php">${RBUNDLE.getString("main_menu_tests") }</a></li>
						<li><a href="${CONTEXT }/add_test.php">${RBUNDLE.getString("main_menu_new_test") }</a></li>						
						<li><a href="${CONTEXT }/all_results.php">${RBUNDLE.getString("main_menu_results") }</a></li>
						<li><a href="${CONTEXT }/start_test.php">${RBUNDLE.getString("main_menu_start_test") }</a></li>
					</c:if>					
					<c:if test="${ROLE == RBUNDLE.getString('name_role_student') }">

						<li><a href="${CONTEXT }/profile.php">${RBUNDLE.getString("main_menu_profile") }</a></li>
						<li><a href="${CONTEXT }/all_tests.php">${RBUNDLE.getString("main_menu_tests") }</a></li>										
						<li><a href="${CONTEXT }/all_results.php">${RBUNDLE.getString("main_menu_results") }</a></li>
						<li><a href="${CONTEXT }/start_test.php">${RBUNDLE.getString("main_menu_start_test") }</a></li>

					</c:if>
				</ol>

			</div>
		</c:if>
		<div>
			<div class="mainContext">
				<c:if test="${CURRENT_SESSION_ACCOUNT != null}">
					<jsp:include page="./home_header.jsp" flush="true" />
				</c:if>
				<jsp:include page="${currentPage }" flush="true" />
			</div>
		</div>
	</div>
	</div>
	<div class="footer">
		<p>Copyrights LVG Corp. 2013</p>
	</div>
</body>
</html>