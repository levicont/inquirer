<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="f"%>

<div>
	<p class="role">
		${ROLE} page
	</p>
	<p class="greeting">
		Hello, ${CURRENT_SESSION_ACCOUNT.username }! <a
			href="<%=request.getContextPath()%>/logout.php">Logout</a>
	</p>
	<c:if test="${VALIDATION_MESSAGE != null }">
		<p class="error">
			<jsp:include page="../JSP/modules/validMessage.jsp" />
		</p>
	</c:if>
</div>