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
            <td>svnId</td><input type="text" class="form-control" name="svnId" placeholder="svnId">
            <td>svnPassword</td><input type="text" class="form-control" name="svnPassword" placeholder="svnPassword">
            <td>svnUrl</td><input type="text" class="form-control" name="svnUrl" placeholder="svnUrl">
            <td>sourceDir</td><input type="text" class="form-control" name="sourceDir" placeholder="sourceDir">
            <td>startRevision</td><input type="text" class="form-control" name="startRevision" placeholder="startRevision">
            <td>endRevision</td><input type="text" class="form-control" name="endRevision" placeholder="endRevision">
            <td>javaVersion</td><input type="text" class="form-control" name="javaVersion" placeholder="javaVersion">
            <td>javaLibPath</td><input type="text" class="form-control" name="javaLibPath" placeholder="javaLibPath">
            <td>sourceDeployDir</td><input type="text" class="form-control" name="sourceDeployDir" placeholder="sourceDeployDir">
            <td>sourceLibDir</td><input type="text" class="form-control" name="sourceLibDir" placeholder="sourceLibDir">
            <td>tomcatLibPath</td><input type="text" class="form-control" name="tomcatLibPath" placeholder="tomcatLibPath">
        <input class="btn btn-default" type="submit" value="Submit">
    </div>
</form>
<%--<from action="test">
    <input type="text" name="name" value="">
    <input type="submit">
</from>--%>
</body>
</html>
