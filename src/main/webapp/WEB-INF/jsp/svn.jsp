<!DOCTYPE html>
<html ng-app>
<html>
<head>
    <%--   <script src="/js/angular/angular.min.js"></script>
       <meta charset="UTF-8">
       <meta http-equiv="X-UA-Compatible" content="IE=edge">
       <meta name="viewport" content="width=device-width, initial-scale=1">--%>
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>svn</title>

    <!-- Bootstrap -->
    <link href="/bootstrap/css/bootstrap.css" rel="stylesheet">
</head>
<body>
<form action="/test">
    <div class="form-group">
        <%--<td>id</td>--%>
        <%--<input type="text" class="form-control" name="id" placeholder="id" >--%>
            <td>sourceDir</td><input type="text" class="form-control" name="sourceDir" placeholder="sourceDir">
            <td>destDir</td><input type="text" class="form-control" name="destDir" placeholder="destDir">
            <td>libDir</td><input type="text" class="form-control" name="libDir" placeholder="libDir">
            <td>jdkVersion</td><input type="text" class="form-control" name="jdkVersion" placeholder="jdkVersion">
            <td>charSet</td><input type="text" class="form-control" name="charSet" placeholder="charSet">
        <input class="btn btn-default" type="submit" value="Submit">
    </div>
</form>
<%--<from action="test">
    <input type="text" name="name" value="">
    <input type="submit">
</from>--%>
</body>
</html>
