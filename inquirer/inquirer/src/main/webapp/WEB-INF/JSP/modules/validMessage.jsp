<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<c:if test="${VALIDATION_MESSAGE != null}">${RBUNDLE.getString("err_title") }: ${VALIDATION_MESSAGE } </c:if>