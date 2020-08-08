<nav class="navbar navbar-expand-lg navbar-dark">
    <span class="navbar-brand">myanimes</span>
    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
            aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
    </button>

    <div class="collapse navbar-collapse" id="navbarSupportedContent">
        <ul class="navbar-nav ml-lg-5">
            <li class="nav-item active"><a class="nav-link" href="${basePath}/dashboard">Dashboard</a>
            </li>
        </ul>
        <form class="form-inline ml-lg-5" action="${basePath}/search" autocomplete="off">
            <input type="search" class="light" name="q" placeholder="search...">
        </form>
        <ul class="navbar-nav ml-auto">
            <c:choose>
                <c:when test="${!isAuthenticated}">
                    <li class="nav-item active"><a class="nav-link"
                                                   href="${basePath}/login?src=${currentPath}">Log
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
                               href="${basePath}/profile?src=${currentPath}">My Profile</a>
                            <a class="dropdown-item"
                               href="${basePath}/user/${authenticatedUser.name}">My List</a>
                            <div class="dropdown-divider"></div>
                            <a class="dropdown-item"
                               href="${basePath}/login?logoff&src=${currentPath}">Log off</a>
                        </div>
                    </li>
                </c:otherwise>
            </c:choose>
        </ul>
    </div>
</nav>