<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="basePath" scope="request" type="java.lang.String"/>
<jsp:useBean id="formatter" scope="request" type="tk.myanimes.text.Formatter"/>
<jsp:useBean id="query" scope="request" type="java.lang.String"/>
<jsp:useBean id="results" scope="request" type="java.util.List<tk.myanimes.anime.SearchResult>"/>
<jsp:useBean id="currentPath" scope="request" type="java.lang.String"/>
<jsp:useBean id="isAuthenticated" scope="request" type="java.lang.Boolean"/>
<jsp:useBean id="authenticatedUser" scope="request" type="tk.myanimes.model.UserInfo"/>
<!DOCTYPE html>
<html lang="en">

<head>
    <%@include file="include/header.html" %>

    <title>Search | myanimes</title>

    <link href="${basePath}/style/main.css" rel="stylesheet">
    <link href="${basePath}/style/list.css" rel="stylesheet">
</head>

<body>
<%@include file="include/navbar.jsp" %>

<div class="main-content pt-5">
    <h1 class="mb-5">Search results for <i>${query}</i></h1>
    <c:forEach var="result" items="${results}">
        <a href="${basePath}/anime/${result.animeInfo.slug}" class="link-muted">
            <div class="row align-items-center search-result-item large">
                <div class="col-sm-auto">
                    <img src="${result.animeInfo.thumbnail}" alt="Thumbnail" width=80>
                </div>
                <div class="col-sm">
                    <h4>${result.animeInfo.englishTitle}</h4>
                    <h6 class="mb-3">${result.animeInfo.alternateTitle.equalsIgnoreCase(result.animeInfo.englishTitle) ? result.animeInfo.japaneseTitle : result.animeInfo.alternateTitle}</h6>
                        ${formatter.formatShowType(result.animeInfo.showType)}
                </div>
            </div>
        </a>
    </c:forEach>
</div>

</body>

<%@include file="include/footer.html" %>

</html>