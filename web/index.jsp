<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta content="width=device-width, initial-scale=1.0" name="viewport">
    <meta content="ie=edge" http-equiv="X-UA-Compatible">

    <title>myanimes</title>

    <link crossorigin="anonymous" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css2?family=Unica+One&family=Open+Sans:wght@300&family=Exo:wght@300&display=swap"
          rel="stylesheet">
    <link href="style/main.css" rel="stylesheet">
    <link href="style/index.css" rel="stylesheet">
</head>

<body>
<div class="backdrop-overlay">
    <div class="main-content">
        <img src="img/welcome-girl.png">
        <h1>Welcome To myanimes</h1>
        <h2>Share your favorite animes with the world</h2>
        <a class="container-link" href="${pageContext.request.contextPath}/register">
            <button class="button primary mr-4">Join</button>
        </a>
        <a href="#">Explore</a>
    </div>
</div>


</body>

</html>

