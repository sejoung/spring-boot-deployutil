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
        <td>name</td>
        <input type="text" class="form-control" name="name" placeholder="name">
        <%--<input type="text" class="form-control" id="description" name="description" placeholder="description" width="150px">
        <input type="text" class="form-control" id="devHostname" name="devHostname" placeholder="devHostname" width="150px">
        <input type="text" class="form-control" id="testHostname" name="testHostname" placeholder="testHostname" width="150px">
        <input type="text" class="form-control" id="prodHostname" name="prodHostname" placeholder="prodHostname" width="150px">
        <input type="text" class="form-control" id="devServerWorkDir" name="devServerWorkDir" placeholder="devServerWorkDir" width="150px">
        <input type="text" class="form-control" id="testServerWorkDir" name="testServerWorkDir" placeholder="testServerWorkDir" width="150px">
        <input type="text" class="form-control" id="prodServerWorkDir" name="prodServerWorkDir" placeholder="prodServerWorkDir" width="150px">
        <input type="text" class="form-control" id="scanPath" name="scanPath" placeholder="scanPath" width="150px">
        <input type="text" class="form-control" id="projectJdkVersion" name="projectJdkVersion" placeholder="projectJdkVersion" width="150px">
        <input type="text" class="form-control" id="mavenUseYn" name="mavenUseYn" placeholder="mavenUseYn" width="150px">--%>
        <input class="btn btn-default" type="submit" value="Submit">
    </div>
</form>
<%--<from action="test">
    <input type="text" name="name" value="">
    <input type="submit">
</from>--%>
</body>
</html>
