  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Registration success!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
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
</head>
<body>
	<div class="login-success">
		<h1>Registered as:</h1>
	    <p th:inline='text'>Name: ${user.lastName} </p>
	    <p th:inline='text'>Passwort: ${user.password} </p>
	</div>
</body>
</html>	