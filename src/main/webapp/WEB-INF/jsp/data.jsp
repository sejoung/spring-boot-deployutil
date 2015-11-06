<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<body>
<div>time : <c:out value="${time}"/></div> 
<div>totalPages : <c:out value="${datas.totalPages}"/></div>
<div>totalElements : <c:out value="${datas.totalElements}"/></div>
<div>size  : <c:out value="${datas.size}"/></div>
<div>number  : <c:out value="${datas.number}"/></div>
<table>
<thead>
	<tr>
		<th>id</th>
		<th>name</th>
	</tr>
</thead>
<tbody>
	<c:forEach items="${datas.content}" var="data" varStatus="status">
    	<tr>
    		<td><c:out value="${data.test.id}"/>${status.count}</td>
    		<td><c:out value="${data.test.name}"/></td>
    	</tr>
    </c:forEach>
</tbody>
</table>
</body>
</html>
