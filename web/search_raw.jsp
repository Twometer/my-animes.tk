<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="results" scope="request" type="java.util.List<tk.myanimes.anime.SearchResult>"/>

<c:forEach var="result" items="${results}">
    <div class="row align-items-center search-result-item" data-slug="${result.animeInfo.slug}"
         onclick="searchItemClicked(this);" onmousedown="searchItemMouseDown()"
         data-title="${result.animeInfo.englishTitle}">
        <div class="col-sm-auto">
            <img src="${result.animeInfo.thumbnail}" width=50 alt="Thumbnail">
        </div>
        <div class="col-sm">
                ${result.animeInfo.englishTitle}
        </div>
    </div>
</c:forEach>