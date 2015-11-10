<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	<h1>RuntimeError Page</h1>
    <p>Application has encountered an error. Please contact support on ...</p>
    <!--
    Failed URL: ${url}
    Exception:  ${exception.message}
    <c:forEach items="${exception.stackTrace}" var="ste">    
    	${ste} 
    </c:forEach>
    -->
</body>

</html>
