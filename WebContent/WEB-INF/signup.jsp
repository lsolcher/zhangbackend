<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
  <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <title>Sign Up Form</title>
        
	          <!-- styles -->
	    <spring:url var ="bootstrapcss" value="/resources/css/bootstrap.css" />
	    <link rel="stylesheet" href="${bootstrapcss}"/>
        <spring:url var ="style" value="/resources/css/style2.css" />
        <link rel="stylesheet" href="${style}">
        <spring:url var ="signup" value="/resources/css/signup.css" />
        <link rel="stylesheet" href="${signup}">
	    <link rel="stylesheet" href="css/normalize.css">
	    <!-- end of styles -->
	
	    	<!-- libs -->
	 	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
		<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.js"></script>
		<script src="//ajax.googleapis.com/ajax/libs/angularjs/1.2.0rc1/angular-route.min.js"></script>
		<spring:url var ="bootstrap" value="/resources/js/bootstrap.js" />
	  	<script type="text/javascript" src="${bootstrap}"></script>
        <spring:url var ="layout" value="/resources/js/layout.js" />
        <script type="text/javascript" src="${layout}"></script>
		<!-- libs end -->
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
                 <li><a href="/ZhangProjectBackend/index.html" id="login" >Anmeldung</a></li>
                </ul>
            </nav>
        </div>
    </header>

    <form action="#" th:action="@{/signup}" th:object="${user}" method="post">
      
        <h3>Registrierung</h3>
        
        <fieldset>
          <label for="name">Vorname:</label>
          <input type="text" th:field="*{firstName}" id="name" name="firstName" required>
          
          <label for="name">Nachname:</label>
          <input type="text" th:field="*{lastName}" id="name" name="lastName" required>
          
          <label for="mail">Email:</label>
          <input type="email" th:field="*{mail}" id="mail" name="mail" required>
          
          <label for="password">Password:</label>
          <input type="password" th:field="*{password}" id="password" name="password" required>
      
        <label for="job">Job Role:</label>
        <select id="job" name="user_job" required>
          <optgroup label="Universität">
            <option value="prof">Professor</option>
            <option value="teacher">Lehrkraft</option>
            <option value="rest">Sonstige</option>
          </optgroup>
        </select>
        
        </fieldset>
        <button type="submit" value="Submit" onclick="lala()">Sign Up</button>
      </form>
      
    </body>
</html>