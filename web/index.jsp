<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>

<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>myanimes</title>

    <link href="style/main.css" rel="stylesheet">
    <link href="style/index.css" rel="stylesheet">
</head>

<body>
<div class="backdrop-overlay">
    <div class="main-content">
        <img src="img/welcome-girl.png" alt="Welcome picture">
        <h1>Welcome To myanimes</h1>
        <h2>Share your favorite animes with the world</h2>
        <a class="container-link" href="register">
            <button class="button primary mr-4">Join</button>
        </a>
        <a class="container-link" href="login">
            <button class="button primary">Log in</button>
        </a>
    </div>
</div>

</body>

</html>

