<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
<table>
<thead>
	<tr>
		<th>id</th>
		<th>name</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${datas}" var="data" varStatus="status">
    	<tr>
    		<td><c:out value="${data.id}"/></td>
    		<td><c:out value="${data.name}"/></td>
    	</tr>
    </c:forEach>
</tbody>
</table>
</body>
</html>
