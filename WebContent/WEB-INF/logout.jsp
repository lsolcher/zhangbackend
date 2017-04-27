  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html ng-app="zhang-app" xmlns:th="http://www.thymeleaf.org">
  <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Logged out</title>
        
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
      
        <h1>You logged out successfully.</h1>
    	<p>Thank you for letting us know your wishes. We will try to combine all given wishes, but please be aware that there are a lot of constrains combining all students and teachers when developing the perfect plan.</p>
    	<p>If you want to add or change any detail, please log in again.</p>
    
    	<h2>Log in</h2>
        
        <fieldset>
          <legend>Your basic info</legend>
          
          <label for="name">Nachname:</label>
          <input type="text" th:field="*{lastName}" id="name" name="lastName">
          
          <label for="password">Password:</label>
          <input type="password" th:field="*{password}" id="password" name="password">
              
        </fieldset>
        <button type="submit" value="Submit">Log in</button>
      </form>
      
    </body>
</html>