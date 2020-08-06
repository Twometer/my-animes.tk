<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="currentPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="animes" scope="request" type="tk.myanimes.model.AnimeList"/>
<jsp:useBean id="user" scope="request" type="tk.myanimes.model.UserInfo"/>
<jsp:useBean id="formatter" scope="request" type="tk.myanimes.text.Formatter"/>

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
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Unica+One&family=Open+Sans:wght@300&family=Exo:wght@300&display=swap">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
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
        <ul class="navbar-nav mr-auto ml-5">
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

<div class="main-content">
    <div class="row align-items-center mt-5">
        <div class="col-sm-auto">
            <span class="profile-picture"
                  style="background-image: url(${user.profilePicture})"></span>
        </div>
        <div class="ml-5 col-sm">
            <h2>${user.name}
                <button class="button primary inline ml-3">Follow</button>
            </h2>

            ${user.biography}
        </div>
        <div class="col-sm-auto">
            <p><img alt="Animes" class="icon"
                    src="${pageContext.request.contextPath}/icon/film.svg"> ${formatter.formatPlurals(animes.size(), "anime", "animes")}
            </p>
            <p><img alt="Watchtime" class="icon"
                    src="${pageContext.request.contextPath}/icon/clock.svg"> ${formatter.formatDuration(animes.getTotalDuration())}
            </p>
            <p><img alt="Location" class="icon"
                    src="${pageContext.request.contextPath}/icon/map-pin.svg"> ${user.location}</p>
        </div>
    </div>
    <c:if test="${user.favoriteAnime != null}">
        <div id="favorite-anime" class="row align-items-center mt-5 p-3">
            <div class="col-sm-auto">
            <span class="cover-picture small shadow"
                  style="background-image: url('${user.favoriteAnime.thumbnail}')"></span>
            </div>
            <div class="ml-2 col-sm">
                <h4 class="mb-3"><a class="link-muted"
                                    href="${pageContext.request.contextPath}/anime/${user.favoriteAnime.slug}">${user.favoriteAnime.englishTitle}</a>
                </h4>
                <h4 class="mt-3" style="opacity: 0.35">my favorite anime</h4>
            </div>
        </div>
    </c:if>
    <div class="row mt-5 button-bar">
        <button class="button in-bar primary selected" data-filter="all">All</button>
        <button class="button in-bar watching" data-filter="watching">Watching</button>
        <button class="button in-bar watched" data-filter="watched">Watched</button>
        <button class="button in-bar paused" data-filter="paused">Paused</button>
        <button class="button in-bar cancelled" data-filter="cancelled">Cancelled</button>
        <button class="button in-bar queued" data-filter="queued">Queued</button>
    </div>
    <c:if test="${isAuthenticated}">
        <p class="ml-auto text-right mt-3">
            <a class="add-item" data-toggle="modal" data-target="#anime-property-modal" data-mode="add">add</a>
        </p>
    </c:if>
    <div class="row mt-5">
        <c:if test="${animes.size() == 0}">
            <div id="empty-list">
                No animes... yet :3
            </div>
        </c:if>
        <c:forEach var="item" items="${animes}">
            <div class="anime-list-item row align-items-center w-100 my-2" data-state="${item.watchState.toString()}">
                <div class="col-sm-auto">
                <span class="cover-picture"
                      style="background-image: url(${item.anime.thumbnail})"></span>
                </div>
                <div class="ml-2 col-sm">
                    <h3><i class="dot ${item.watchState.toString()}"></i> <a class="link-muted"
                                                                             href="${pageContext.request.contextPath}/anime/${item.anime.slug}">${item.anime.englishTitle}</a>
                    </h3>
                    <div class="row mt-3">
                        <div class="col-2"><img alt="Rating" class="icon"
                                                src="${pageContext.request.contextPath}/icon/star.svg"> ${item.score != -1 ? formatter.formatScore(item.score) : "unrated"}
                        </div>
                        <div class="col-3"><img alt="Watched" class="icon"
                                                src="${pageContext.request.contextPath}/icon/calendar.svg"> ${formatter.formatDateRelative(item.watchDate)}
                        </div>
                        <div class="col-3"><img alt="Episodes" class="icon"
                                                src="${pageContext.request.contextPath}/icon/tv.svg"> ${formatter.formatAnimeLength(item.anime)}
                        </div>
                        <div class="col-auto"><img alt="Studio" class="icon"
                                                   src="${pageContext.request.contextPath}/icon/video.svg"> ${formatter.formatStudios(item.anime.animeStudios)}
                        </div>
                    </div>
                </div>
                <c:if test="${isAuthenticated}">
                    <div class="anime-options hidden col-sm-auto">
                        <button class="btn" data-toggle="modal" data-target="#anime-property-modal"
                                data-anime-name="${item.anime.englishTitle}" data-anime-id="${item.anime.id}"
                                data-mode="edit" data-anime-state="${item.watchState.toString()}"
                                data-anime-date="${formatter.formatDateSystem(item.watchDate)}">
                            <img src="${pageContext.request.contextPath}/icon/edit.svg">
                        </button>
                        <button class="btn" data-toggle="modal" data-target="#delete-anime-modal"
                                data-anime-name="${item.anime.englishTitle}" data-anime-id="${item.anime.id}">
                            <img src="${pageContext.request.contextPath}/icon/delete.svg">
                        </button>
                    </div>
                </c:if>
            </div>
        </c:forEach>
    </div>
</div>

<div class="modal fade" id="delete-anime-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body mx-auto my-5">
                <form method="post" action="${pageContext.request.contextPath}/list" autocomplete="off"
                      style="width: 300px">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" id="delete-anime-id" name="animeId" value="">
                    <h2 class="mb-5 text-center">Confirm delete</h2>
                    <p class="text-center">
                        Do you really want to delete <span id="delete-anime-name"
                                                           class="font-weight-bold">$ANIMENAME$</span>
                        from your anime list?
                    </p>
                    <div class="mt-5 text-center">
                        <button type="submit" class="button cancelled mr-4">Delete</button>
                        <a data-dismiss="modal">cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="anime-property-modal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered" role="document">
        <div class="modal-content">
            <div class="modal-body mx-auto my-5">
                <form method="post" action="${pageContext.request.contextPath}/list" autocomplete="off"
                      style="width: 300px">
                    <input id="edit-anime-id" type="hidden" name="animeId" value="-1"/>
                    <input id="property-action" type="hidden" name="action" value="add">
                    <h2 id="anime-property-header" class="mb-5 text-center">Add new anime</h2>
                    <h5 id="edit-anime-name" class="text-center">$animename</h5>
                    <p id="anime-search-box" class="mt-3 mb-4">
                        <label for="anime-search">Anime name</label>
                        <input type="hidden" name="animeSlug" id="search-anime-slug" required>
                        <input id="anime-search" placeholder="search..." type="text" value="" required>
                    </p>

                    <div id="anime-search-results">
                        <div class="search-message">Searching...</div>
                    </div>

                    <div class="form-group my-4">
                        <label for="watchState">State</label>
                        <select name="watchState" class="form-control" id="watchState">
                            <option value="watching">Watching</option>
                            <option value="watched">Watched</option>
                            <option value="paused">Paused</option>
                            <option value="cancelled">Cancelled</option>
                            <option value="queued">Queued</option>
                        </select>
                    </div>

                    <div class="form-group my-4" id="date-box">
                        <label for="watchDate">Watch date</label>
                        <input type="date" id="watchDate" name="watchDate" required>
                    </div>

                    <div class="form-group my-4" id="rating-box">
                        <div class="row">
                            <label class="col" for="rating-story">Story</label>
                            <div class="col star-input">
                                <input id="rating-story" name="rating-story" type="hidden">
                            </div>
                        </div>

                        <div class="row">
                            <label class="col" for="rating-characters">Characters</label>
                            <div class="col star-input">
                                <input id="rating-characters" name="rating-characters" type="hidden">
                            </div>
                        </div>

                        <div class="row">
                            <label class="col" for="rating-art">Art</label>
                            <div class="col star-input">
                                <input id="rating-art" name="rating-art" type="hidden">
                            </div>
                        </div>

                        <div class="row">
                            <label class="col" for="rating-enjoyment">Entertainment</label>
                            <div class="col star-input">
                                <input id="rating-enjoyment" name="rating-enjoyment" type="hidden">
                            </div>
                        </div>
                    </div>

                    <div class="mt-5 text-center">
                        <button type="submit" class="button primary mr-4">Save</button>
                        <a data-dismiss="modal">cancel</a>
                    </div>
                </form>
            </div>
        </div>
    </div>
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
<script src="${pageContext.request.contextPath}/script/search.js"></script>
<script src="${pageContext.request.contextPath}/script/list.js"></script>

</html>