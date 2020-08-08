<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="currentPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>Dashboard | myanimes</title>

    <link href="${basePath}/style/main.css" rel="stylesheet">
    <link href="${basePath}/style/list.css" rel="stylesheet">
</head>

<body>
<%@include file="include/navbar.jsp" %>

<div class="main-content pt-5">

    <h1>Hi, ${authenticatedUser.name}!</h1>
    <p>Sorry, the dashboard is still work-in-progress</p>
    <p>In the meantime, you can try visiting <a
            href="${basePath}/user/${authenticatedUser.name}">your anime list</a>.</p>


</div>

</body>

<%@include file="include/footer.html" %>

</html>