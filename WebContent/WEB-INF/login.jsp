  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app" xmlns:th="http://www.thymeleaf.org">
  <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Log In Form</title>
        
	    	<!-- styles -->
	    <spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
	    <link rel="stylesheet" href="${bootstrapcss}"/>
	    <spring:url var ="style" value="/resources/css/style2.css" />
	    <link rel="stylesheet" href="${style}">
	    <link rel="stylesheet" href="css/normalize.css">
        <link href='http://fonts.googleapis.com/css?family=Nunito:400,300' rel='stylesheet' type='text/css'>
        <spring:url var ="style" value="/resources/css/signup.css" />
    	<link rel="stylesheet" href="${style}">
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
		<spring:url var ="calendar" value="/resources/js/calendar.js" />
		<script type="text/javascript" src="${calendar}"></script>
		<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
	  	<script type="text/javascript" src="${bootstrap}"></script>
		<!-- libs end -->
    </head>
    <body>
		<form action="#" th:action="@{/login}" th:object="${user}" method="post">
      		<h1>Log in</h1>
	        
	        <fieldset>
	          <legend></legend>
	          
	          <label for="name">Nachname:</label>
	          <input type="text" th:field="*{username}" id="name" name="username">
	          
	          <label for="password">Password:</label>
	          <input type="password" th:field="*{password}" id="password" name="password">
	              
	        </fieldset>
	        <button type="submit" value="Submit">Log in</button>
      </form>
    </body>
</html>