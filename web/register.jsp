<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="showError" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>Register | myanimes</title>

    <link href="style/main.css" rel="stylesheet">
    <link href="style/login.css" rel="stylesheet">
</head>

<body>

<div class="backdrop-image" style="background-image: url('img/register-wallpaper.jpg');"></div>
<div class="backdrop-overlay"></div>

<div class="login-container">
    <form class="login-content" method="post" autocomplete="off">
        <h1>myanimes</h1>
        <h2>Nice to meet you!</h2>
        <p>
            <input name="username" placeholder="username" type="text" required>
        </p>
        <p>
            <input name="password" placeholder="password" type="password" required>
        </p>
        <p>
            <input name="passwordConfirm" placeholder="confirm password" type="password" required>
        </p>
        <c:if test="${showError}">
            <span class="password-error">${errorMessage}</span>
        </c:if>
        <div class="submit-ui">
            <button type="submit" class="button primary mr-4">Register</button>
            <a href="${basePath}/login">back to login</a>
        </div>
    </form>
</div>

</body>

<%@include file="include/footer.html" %>

</html>