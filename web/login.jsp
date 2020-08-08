<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="showLoginError" scope="request" type="java.lang.Boolean"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>Login | myanimes</title>

    <link href="style/main.css" rel="stylesheet">
    <link href="style/login.css" rel="stylesheet">
</head>

<body>

<div class="backdrop-image" style="background-image: url('img/login-wallpaper.jpg');"></div>
<div class="backdrop-overlay"></div>

<div class="login-container">
    <form class="login-content" method="post">
        <h1>myanimes</h1>
        <h2>Welcome back!</h2>
        <p>
            <input name="username" placeholder="username" type="text" required>
        </p>
        <p>
            <input name="password" placeholder="password" type="password" required>
        </p>
        <c:if test="${showLoginError}">
            <span class="password-error">Invalid credentials :c</span>
        </c:if>
        <div class="submit-ui">
            <button type="submit" class="button primary mr-4">Log in</button>
            <a href="${basePath}/register">new here?</a>
        </div>
    </form>
</div>

</body>

<%@include file="include/footer.html" %>

</html>