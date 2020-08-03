<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="showError" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
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
    <link href="style/login.css" rel="stylesheet">
</head>

<body>

<div class="backdrop-image" style="background-image: url('img/profile-wallpaper.jpg');"></div>
<div class="backdrop-overlay"></div>

<div class="login-container">
    <form class="login-content" method="post" autocomplete="off">
        <h1>Your Profile</h1>
        <h2>Tell us something about yourself</h2>
        <p>
            <input name="username" placeholder="username" type="text" required value="${authenticatedUser.name}">
        </p>
        <p>
            <input name="bio" placeholder="about you" type="text" value="${authenticatedUser.biography}">
        </p>
        <p>
            <input name="profilePicUrl" placeholder="profile picture link" type="url"
                   value="${authenticatedUser.profilePicture}">
        </p>
        <p>
            <input name="location" placeholder="location" type="text" value="${authenticatedUser.location}">
        </p>
        <p>
            <input type="hidden" name="favoriteAnimeId" id="search-anime-id">
            <input id="anime-search" placeholder="favorite anime" type="text"
                   value="${authenticatedUser.favoriteAnime != null ? authenticatedUser.favoriteAnime.englishTitle : ""}">
        </p>
        <div id="anime-search-results">
            <div class="search-message">Searching...</div>
        </div>
        <c:if test="${showError}">
            <span class="password-error">${errorMessage}</span>
        </c:if>
        <div class="submit-ui">
            <button type="submit" class="button primary mr-4">Save</button>
            <a href="${pageContext.request.contextPath}/dashboard">cancel</a>
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
<script src="script/search.js"></script>

</html>