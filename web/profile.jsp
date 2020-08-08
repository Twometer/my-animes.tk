<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="showError" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="errorMessage" scope="request" type="java.lang.String"/>

<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>Profile | myanimes</title>

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
            <input type="hidden" name="favoriteAnimeSlug" id="search-anime-slug">
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
            <a onclick="history.back()">cancel</a>
        </div>
    </form>
</div>

</body>

<%@include file="include/footer.html" %>
<script>window._basePath = '${basePath}';</script>
<script src="script/search.js"></script>

</html>