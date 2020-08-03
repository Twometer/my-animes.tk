<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="results" scope="request" type="java.util.List<tk.myanimes.anime.SearchResult>"/>

<c:forEach var="result" items="${results}">
    <div class="row align-items-center w-100 my-1" kitsu-id="${result.remoteIdentifier}"
         slug="${result.animeInfo.slug}">
        <div class="col-sm-auto">
            <img src="${result.animeInfo.coverPicture}" width=50>
        </div>
        <div class="ml-2 col-sm">
            <h5>${result.animeInfo.canonicalTitle}</h5>
                ${result.animeInfo.episodeCount} episodes
        </div>
    </div>
</c:forEach>