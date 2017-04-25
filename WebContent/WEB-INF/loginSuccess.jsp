  <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html SYSTEM "http://www.thymeleaf.org/dtd/xhtml1-strict-thymeleaf-4.dtd">
<html xmlns="http://www.w3.org/1999/xhtml"
      xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Log In success!</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<h1>Successful Log in</h1>
        
    <p>Congratulations, you logged in successfully!</p>
    
	<span>You logged in as:</span>
    <p th:inline='text'>Name: ${user.lastName} </p>
    <p th:inline='text'>Passwort: ${user.password} </p>
</body>
</html>	