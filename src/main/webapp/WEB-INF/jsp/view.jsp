<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	sourceDir:<c:out value="${datas.sourceDir}" /><br>
	sourceWWWDir:<c:out value="${datas.sourceWWWDir}" /><br>
	sourceLibDir:<c:out value="${datas.sourceLibDir}" /><br>
	sourceDeployDir:<c:out value="${datas.sourceDeployDir}" /><br>
	javaVersion:<c:out value="${datas.javaVersion}" /><br>

	<%--<c:out value="${datas.name}" />--%>

</body>
</html>
