<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Title</title>
    <meta charset="UTF-8">
</head>
<body>
<h1>${list}</h1><br>

<c:forEach var="myList" items="${list}">
    <h1>${myList}</h1>
</c:forEach>

</body>
</html>
