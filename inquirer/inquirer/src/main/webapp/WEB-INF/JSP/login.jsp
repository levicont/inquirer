<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@page import="com.lvg.inquirer.InquirerConstants"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="loginForm">
	<c:if test="${VALIDATION_MESSAGE != null }">
		<p class="error">
			<jsp:include page="modules/validMessage.jsp" />
		</p>
	</c:if>

	<form action="<%=request.getContextPath()%>/login.php" method="post">
		<table class="formTable">
			<tr>
				<td>${RBUNDLE.getString("login_fm_login") }:</td>
				<td><input title="${RBUNDLE.getString('login_fm_login') }" type="text" name="username" /></td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("login_fm_password") }:</td>
				<td><input title="${RBUNDLE.getString('login_fm_password') } " type="password" name="password" />
				</td>
			</tr>
			<tr>
				<td>${RBUNDLE.getString("login_fm_role") }:</td>
				<td><select name="role" id="role">
						<option value="1">Administrator</option>
						<option value="2">Advanced tutor</option>
						<option value="3">Tutor</option>
						<option value="4">Student</option>
				</select></td>
			</tr>
			<tr>
				<td><input type="submit" value="${RBUNDLE.getString('login_fm_bt_login') }" /></td>
			</tr>
		</table>
	</form>
	<p>
		<a href="${CONTEXT }/register.php">${RBUNDLE.getString("login_fm_ref_reg") }</a> <a
			href="${CONTEXT }/recover.php">${RBUNDLE.getString("login_fm_ref_recover") }</a>
	</p>
</div>
