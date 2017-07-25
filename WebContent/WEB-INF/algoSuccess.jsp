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
				<a href="" class="navbar-brand"><img src="/ZhangProjectBackend/resources/img/logo.png" width="39" />Lehreinsatzplanung</a>
				</div>
				<nav class="collapse navbar-collapse" role="navigation">
					<ul class="nav navbar-nav pull-right">
						<li><a href="/ZhangProjectBackend/controlpanel.html" id="controlpanel" >Zurück zu den Einstellungen</a></li>
					</ul>
				</nav>
			</div>
    </header>
	<div class="container content-container">
		<div class="row">
			<div class="col-md-12 prop-wrapper">
				<div ng-controller="renderData">
					<div ng-repeat="table in data" class="table-wrapper">
						<h4>{{table.programName}}</h4>
						<table>
							<tr>
								<th>Montag</th>
								<th>Dienstag</th>
								<th>Mittwoch</th>
								<th>Donnerstag</th>
								<th>Freitag</th>
							</tr>
							<tr>
								<td>{{table.schedule[0][0]}}</td>
								<td>{{table.schedule[1][0]}}</td>
								<td>{{table.schedule[2][0]}}</td>
								<td>{{table.schedule[3][0]}}</td>
								<td>{{table.schedule[4][0]}}</td>
							</tr>
							<tr>
								<td>{{table.schedule[0][1]}}</td>
								<td>{{table.schedule[1][1]}}</td>
								<td>{{table.schedule[2][1]}}</td>
								<td>{{table.schedule[3][1]}}</td>
								<td>{{table.schedule[4][1]}}</td>
							</tr>
								<tr>
								<td>{{table.schedule[0][2]}}</td>
								<td>{{table.schedule[1][2]}}</td>
								<td>{{table.schedule[2][2]}}</td>
								<td>{{table.schedule[3][2]}}</td>
								<td>{{table.schedule[4][2]}}</td>
							</tr>
								<tr>
								<td>{{table.schedule[0][3]}}</td>
								<td>{{table.schedule[1][3]}}</td>
								<td>{{table.schedule[2][3]}}</td>
								<td>{{table.schedule[3][3]}}</td>
								<td>{{table.schedule[4][3]}}</td>
							</tr>
								<tr>
								<td>{{table.schedule[0][4]}}</td>
								<td>{{table.schedule[1][4]}}</td>
								<td>{{table.schedule[2][4]}}</td>
								<td>{{table.schedule[3][4]}}</td>
								<td>{{table.schedule[4][4]}}</td>
							</tr>
								<tr>
								<td>{{table.schedule[0][5]}}</td>
								<td>{{table.schedule[1][5]}}</td>
								<td>{{table.schedule[2][5]}}</td>
								<td>{{table.schedule[3][5]}}</td>
								<td>{{table.schedule[4][5]}}</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>

  </body>
</html>
