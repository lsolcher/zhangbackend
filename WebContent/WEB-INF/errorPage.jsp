<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
    <head>
        <meta charset="utf-8">
        <title>Page Not Found :(</title>

        <style>
            @import url('https://fonts.googleapis.com/css?family=Roboto+Slab:100,300,400,700|Roboto:100,300,400,700');

            body {
                background: #e4f0f1;
                text-align: center;
                font-family: 'Roboto Slab', serif;
            }
            a {
                color: #6ca6a1;
            }
        </style>

    </head>
<body>
    <h1>${errorMsg}</h1>
    <h2><a href=${index}>zurück</a></h2>
    <img src="/ZhangProjectBackend/resources/img/notfound.gif" />
</body>
</html>