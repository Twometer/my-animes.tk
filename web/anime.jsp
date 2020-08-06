<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="currentPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="formatter" scope="request" type="tk.myanimes.text.Formatter"/>
<jsp:useBean id="anime" scope="request" type="tk.myanimes.model.AnimeInfo"/>
<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">

    <title>myanimes</title>

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/css/bootstrap.min.css"
          integrity="sha384-9aIt2nRpC12Uk9gS9baDl411NQApFmC26EwAOH8WgZl5MYYxFfc+NcPb1dKGj7Sk" crossorigin="anonymous">
    <link href="https://fonts.googleapis.com/css2?family=Unica+One&family=Open+Sans:wght@300&family=Exo:wght@300&display=swap"
          rel="stylesheet">
    <link href="${pageContext.request.contextPath}/style/main.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/style/list.css" rel="stylesheet">
</head>

<body>
<nav class="navbar navbar-expand-lg navbar-dark">
    <span class="navbar-brand">myanimes</span>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav mr-auto ml-lg-5">
            <li class="nav-item active"><a class="nav-link" href="${pageContext.request.contextPath}/dashboard">Dashboard</a>
            </li>
            <li class="nav-item active"><a class="nav-link" href="#">Animes</a></li>
            <li class="nav-item active"><a class="nav-link" href="#">Characters</a></li>
        </ul>
        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${!isAuthenticated}">
                    <li class="nav-item active"><a class="nav-link"
                                                   href="${pageContext.request.contextPath}/login?src=${currentPath}">Log
                        in</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="nav-item active dropdown">
                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                           data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                ${authenticatedUser.name}
                        </a>
                        <div class="dropdown-menu" aria-labelledby="navbarDropdown">
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/profile?src=${currentPath}">My Profile</a>
                            <a class="dropdown-item"
                               href="${pageContext.request.contextPath}/user/${authenticatedUser.name}">My List</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/login?logoff">Log off</a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>

<div class="main-content pt-5">

    <div class="row align-items-center anime-header">
        <div class="col-lg-auto mr-4-lg">
            <img class="shadow" src="${anime.coverPicture}" width=200>
        </div>
        <div class="col-lg ml-lg-5">
            <h1>${anime.englishTitle}</h1>
            <h4>${anime.japaneseTitle}</h4>
            <p class="mt-3">
                ${anime.synopsis}
            </p>
        </div>
    </div>

    <h2 class="mt-5">About</h2>

    <div class="row">
        <div class="col-md">
            <p>Alternative title: ${anime.alternateTitle}</p>
            <p>Start date: ${formatter.formatDateAbsolute(anime.startDate)}</p>
            <p>End date: ${formatter.formatDateAbsolute(anime.endDate)}</p>
            <p>Studios: ${formatter.formatStudios(anime.animeStudios)}</p>
            <p>Status: ${anime.status}</p>
            <p>Type: ${anime.showType}</p>
        </div>
        <div class="col-md">
            <p>Categories: ${formatter.formatStrings(anime.categories)}</p>
            <p>Age rating: ${anime.ageRating}</p>
            <p>NSFW: ${anime.nsfw}</p>
            <p>Episodes: ${anime.episodeCount}</p>
            <p>Episode length: ${anime.episodeLength} minutes</p>
            <p>Total length: ${formatter.formatDuration(anime.totalLength)}</p>
        </div>
    </div>

    <h2 class="mt-5">Trailer</h2>
    <div class="embed-responsive embed-responsive-16by9">
        <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/${anime.youtubeVideoId}"
                allowfullscreen allow="encrypted-media"></iframe>
    </div>

    <h2 class="mt-5">Episodes</h2>
    <div class="alert alert-light">Work in progress</div>
</div>

</body>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
        integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
        crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"
        integrity="sha384-OgVRvuATP1z7JjHLkuOU7Xw704+h835Lr+6QL9UvYjZE3Ipu6Tp75j7Bh/kR0JKI"
        crossorigin="anonymous"></script>

</html>