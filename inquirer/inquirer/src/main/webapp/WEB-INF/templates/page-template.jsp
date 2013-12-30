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
	<div class="header">
		<h1>Inquirer center</h1>
	</div>

	<div class="main">

		<c:if test="${CURRENT_SESSION_ACCOUNT != null}">
			<div class="mainMenu">
				<p class="menuTitle">Available actions</p>
				<ol>
					<c:if test="${ROLE == 'Administrator' }">
						<li><a href="${CONTEXT }/profile.php">Profile</a></li>
						<li><a href="${CONTEXT }/all_tests.php">List all test</a></li>
						<li><a href="${CONTEXT }/add_test.php">Add new test</a></li>
						<li><a href="${CONTEXT }/all_accounts.php">List all
								accounts</a></li>
						<li><a href="${CONTEXT }/all_results.php">List test
								results</a></li>
						<li><a href="${CONTEXT }/start_test.php">Start test</a></li>

					</c:if>
					<c:if test="${(ROLE == 'Advanced tutor')||(ROLE == 'Tutor') }">

						<li><a href="${CONTEXT }/profile.php">Profile</a></li>
						<li><a href="${CONTEXT }/all_tests.php">List all test</a></li>
						<li><a href="${CONTEXT }/add_test.php">Add new test</a></li>
						<li><a href="${CONTEXT }/all_results.php">List test
								results</a></li>
						<li><a href="${CONTEXT }/start_test.php">Start test</a></li>
					</c:if>
					<c:if test="${ROLE == 'Student' }">

						<li><a href="${CONTEXT }/profile.php">Profile</a></li>
						<li><a href="${CONTEXT }/all_tests.php">List all test</a></li>
						<li><a href="${CONTEXT }/all_results.php">List test
								results</a></li>
						<li><a href="${CONTEXT }/start_test.php">Start test</a></li>

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
	<div class="footer">
		<p>Copyrights LVG Corp. 2013</p>
	</div>
</body>
</html>