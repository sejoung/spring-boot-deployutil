<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org"
	  xmlns:sec="http://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<head>
	<title>WELCOME</title>
</head>
<body>
	<c:url value="/resources/text.txt" var="url"/>
	<spring:url value="/resources/text.txt" htmlEscape="true" var="springUrl" />
	Spring URL: ${springUrl} at ${time}
	<br>
	JSTL URL: ${url}
	<br>
	Message: ${message}
	<form action="/login">
		<div><label> SVN Url : <input type="text" name="svnUrl" value="http://210.218.225.23/svn/cspapi/branches/cspapi_poc"/> </label></div>
		<div><label> User Name : <input type="text" name="svnId" value="AhnSangHyuk" /> </label></div>
		<div><label> Password: <input type="password" name="svnPassword" value="dkstkdgur" /> </label></div>
		<div><input type="submit" value="Sign In"/></div>
	</form>
</body>

</html>
