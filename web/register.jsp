<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="rootPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="showError" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">

    <title>Register | myanimes</title>

    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Unica+One&family=Open+Sans:wght@300&family=Exo:wght@300&display=swap"
          rel="stylesheet">
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
            <a href="${rootPath}/login">back to login</a>
        </div>
    </form>
</div>

</body>

<script crossorigin="anonymous"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"></script>
<script crossorigin="anonymous"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>

</html>