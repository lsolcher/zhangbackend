<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html ng-app="zhang-app">
  <head>
	<meta charset="utf-8">
	<title>LEP-Tool</title>

	<!-- styles -->
	<spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
	<link rel="stylesheet" href="${bootstrapcss}"/>
	<spring:url var ="style" value="/resources/css/style2.css" />
	<link rel="stylesheet" href="${style}">
	<spring:url var ="signup" value="/resources/css/signup.css" />
	<link rel="stylesheet" href="${signup}">
	<!-- end of styles -->

	<!-- libs -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
	<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
	<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
	<spring:url var ="courses" value="/resources/js/courses.js" />
	<script type="text/javascript" src="${courses}"></script>
	<spring:url var ="priorities" value="/resources/js/priorities.js" />
	<script type="text/javascript" src="${priorities}"></script>
	<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
	<script type="text/javascript" src="${bootstrap}"></script>
	<spring:url var ="layout" value="/resources/js/layout.js" />
	<script type="text/javascript" src="${layout}"></script>
	<!-- libs end -->
	<script>
		var initCourses = '<% String schedules = (String) request.getAttribute("schedules"); out.print(schedules); %>';
		try {
			initCourses = JSON.parse(initCourses);
		console.log(initCourses);
		} catch(e) {
			//console.log('NOOO', initCourses);
			console.log(e.stack);
		}
	</script>
  </head>
  <body>

    <header>
      <div class="container">
				<div class="navbar-header">
					<button class="navbar-toggle" type="button" data-toggle="collapse" data-target=".navbar-collapse">
						<span class="sr-only">Toggle navigation</span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
						<span class="icon-bar"></span>
					</button>
					<a href="" class="navbar-brand">HTW FB4 Lehreinsatzplanung</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
						<li><a href="/ZhangProjectBackend/logout.html" id="logout" >Logout</a></li>
						<li><a href="/ZhangProjectBackend/signup.html" id="signup" >Signup</a></li>
					</ul>
				</nav>
			</div>
    </header>
	<div ng-controller="renderData">
		<div ng-repeat="table in data">
			<h1>{{table.programName}}</h1>
			<table style="width:100%">
				<tr ng-repeat="tr in table.schedule track by $index">
					<th ng-repeat="td in tr track by $index">{{td}}</th>
				</tr>
			</table>
		</div>
	</div>

  </body>
</html>
