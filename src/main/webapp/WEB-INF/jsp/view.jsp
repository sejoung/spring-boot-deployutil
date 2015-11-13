<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
	sourceDir:<c:out value="${datas.sourceDir}" /><br>
	destDir:<c:out value="${datas.destDir}" /><br>
	libDir:<c:out value="${datas.libDir}" /><br>
	jdkVersion:<c:out value="${datas.jdkVersion}" /><br>
	charSet:<c:out value="${datas.charSet}" /><br>

	<%--<c:out value="${datas.name}" />--%>

</body>
</html>
