/* Anime Search Script */

(function () {

    const endpoint = _basePath + "/search?raw=true&q=";

    var searchBox = $("#anime-search");
    var resultBox = $("#anime-search-results");

    var timeout = -1;
    var hasResults = false;

    searchBox.on('input', function () {
        if (timeout !== -1)
            clearTimeout(timeout);
        timeout = setTimeout(doSearch, 500);
    });

    function doSearch() {
        var query = searchBox.val().trim();
        if (query.length === 0) {
            hideResultBox();
            hasResults = false;
        } else {
            showResultBox();
            resultBox.html('<div class="search-message">Searching...</div>');
            hasResults = true;

            var url = endpoint + encodeURIComponent(query);
            httpGetAsync(url, function (e) {
                resultBox.html(e);
            });
        }
    }

    var nextFocusOnSearchItem = false;

    window.searchItemMouseDown = function () {
        nextFocusOnSearchItem = true;
    }

    window.searchItemClicked = function (i) {
        var element = $(i);
        var title = element.data('title');
        var slug = element.data('slug');
        $("#search-anime-slug").val(slug);
        $("#anime-search").val(title);
        hideResultBox();
    }

    searchBox.focusout(function () {
        if (nextFocusOnSearchItem) {
            nextFocusOnSearchItem = false;
        } else {
            hideResultBox();
        }
    });

    searchBox.focus(function () {
        if (hasResults)
            showResultBox();
    });

    function showResultBox() {
        resultBox.css({
            display: 'block',
            top: (searchBox.offset().top + searchBox.height() + 8) + 'px',
            left: searchBox.offset().left + 'px',
            width: searchBox.width() + 'px',
        });
    }

    function hideResultBox() {
        resultBox.css('display', 'none');
    }

    function httpGetAsync(theUrl, callback) {
        var xmlHttp = new XMLHttpRequest();
        xmlHttp.onreadystatechange = function () {
            if (xmlHttp.readyState === 4 && xmlHttp.status === 200)
                callback(xmlHttp.responseText);
        }
        xmlHttp.open("GET", theUrl, true); // true for asynchronous
        xmlHttp.send(null);
    }

})();