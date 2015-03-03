<%@ page session="false" %>
<!DOCTYPE html>
<html lang="en" ng-app="myslides">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>

    <base href="${pageContext.request.contextPath}/"/>

    <title>My Slides</title>

    <meta name="description" content=""/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <style>
        [ng\:cloak], [ng-cloak], [data-ng-cloak], [x-ng-cloak], .ng-cloak, .x-ng-cloak {
            display: none !important;
        }
    </style>
</head>

<body>

<div ng-view></div>

<script src="webjars/jquery/2.1.3/jquery.min.js"></script>
<script src="webjars/bootstrap/3.3.2-1/js/bootstrap.js"></script>
<script src="webjars/angularjs/1.3.13/angular.min.js"></script>
<script src="webjars/angularjs/1.3.13/angular-route.min.js"></script>
<script src="webjars/angularjs/1.3.13/angular-sanitize.min.js"></script>
<script src="js/myslides.js"></script>

<link rel="stylesheet" href="webjars/bootstrap/3.3.2-1/css/bootstrap.min.css">
<link rel="stylesheet" href="css/myslides.css">

</body>
</html>
