<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="currentPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="formatter" scope="request" type="tk.myanimes.text.Formatter"/>
<jsp:useBean id="anime" scope="request" type="tk.myanimes.model.AnimeInfo"/>
<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>${anime.englishTitle} | myanimes</title>

    <link href="${basePath}/style/main.css" rel="stylesheet">
    <link href="${basePath}/style/list.css" rel="stylesheet">
</head>

<body>
<%@include file="include/navbar.jsp" %>

<div class="main-content pt-5">

    <div class="row align-items-center anime-header">
        <div class="col-lg-auto mr-4-lg">
            <img class="shadow" src="${anime.coverPicture}" width=200 alt="Cover picture">
        </div>
        <div class="col-lg ml-lg-5">
            <h1>${anime.englishTitle}</h1>
            <h4>${anime.japaneseTitle}</h4>
            <p class="mt-3">
                ${anime.synopsis}
            </p>
        </div>
    </div>

    <h2 class="mt-5 mb-2">Info</h2>

    <div class="row">
        <div class="col-md">
            <table>
                <tr>
                    <th>Alternative title:</th>
                    <td>${anime.alternateTitle}</td>
                </tr>
                <tr>
                    <th>Status:</th>
                    <td>${anime.status}</td>
                </tr>
                <tr>
                    <th>Type:</th>
                    <td>${formatter.formatShowType(anime.showType)}</td>
                </tr>
                <tr>
                    <th>Studios:</th>
                    <td>${formatter.formatStudios(anime.animeStudios)}</td>
                </tr>
                <tr>
                    <th>Start date:</th>
                    <td>${formatter.formatDateAbsolute(anime.startDate)}</td>
                </tr>
                <tr>
                    <th>End date:</th>
                    <td>${formatter.formatDateAbsolute(anime.endDate)}</td>
                </tr>
            </table>
        </div>
        <div class="col-md">
            <table>
                <tr>
                    <th>Categories:</th>
                    <td>${formatter.formatStrings(anime.categories)}</td>
                </tr>
                <tr>
                    <th>Episodes:</th>
                    <td>${anime.episodeCount}</td>
                </tr>
                <tr>
                    <th>Episode length:</th>
                    <td>${anime.episodeLength} minutes</td>
                </tr>
                <tr>
                    <th>Total length:</th>
                    <td>${formatter.formatDuration(anime.totalLength)}</td>
                </tr>
                <tr>
                    <th>Age rating:</th>
                    <td>${formatter.formatString(anime.ageRating)}</td>
                </tr>
                <tr>
                    <th>NSFW:</th>
                    <td>${formatter.formatBoolean(anime.nsfw)}</td>
                </tr>
            </table>
        </div>
    </div>

    <h2 class="mt-5 mb-2">Trailer</h2>
    <c:choose>
        <c:when test="${anime.youtubeVideoId == null || anime.youtubeVideoId.length() == 0}">
            <div class="alert alert-light">No trailer available</div>
        </c:when>
        <c:otherwise>
            <div class="embed-responsive embed-responsive-16by9">
                <iframe class="embed-responsive-item" src="https://www.youtube.com/embed/${anime.youtubeVideoId}"
                        allowfullscreen allow="encrypted-media"></iframe>
            </div>
        </c:otherwise>
    </c:choose>

    <h2 class="mt-5 mb-2">Episodes</h2>
    <c:choose>
        <c:when test="${anime.episodes.size() == 0}">
            <div class="alert alert-light">No episode info available</div>
        </c:when>
        <c:otherwise>
            <c:forEach var="episode" items="${anime.episodes}">
                <p>
                    <img src="${episode.thumbnail}" width="100">
                <h2>S${episode.seasonNumber}E${episode.episodeNumber}: ${episode.canonicalTitle}</h2>
                ${episode.synopsis}
                (Length: ${episode.length})
                </p>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

</body>

<%@include file="include/footer.html" %>

</html>