<%@page import="java.util.ResourceBundle"%>
<%@ page import="org.apache.commons.lang3.exception.ExceptionUtils"%>
<%@ page pageEncoding="UTF-8" 	contentType="text/html; charset=UTF-8" %>

<%
String status = String.valueOf(request.getAttribute("javax.servlet.error.status_code"));

String errorMessage = null;
String fullStackTrace = null;
Exception exception = null;

if("404".equals(status.trim())){
	errorMessage = ((ResourceBundle)request.getSession().getAttribute("RBUNDLE")).getString("err_404");
}
else{
	errorMessage = String.valueOf(request.getAttribute("javax.servlet.error.message"));
	exception = (Exception)request.getAttribute("javax.servlet.error.exception") ;
	if(exception != null){
		errorMessage = exception.getMessage();
		fullStackTrace = ExceptionUtils.getStackTrace(exception).replace('\t',' ').trim();
	}
}	
%>

<div class="errorDiv">
	${RBUNDLE.getString("err_title")}<br/>
	
	<%=errorMessage %> <br/>
	<% if(fullStackTrace != null) { %>
	Full stack trace : <%=fullStackTrace %> <br/>
	<% } %>
</div>