(function () {

    /* Loaders & utils
    -------------------------------------------------- */
    const spinner = $('<div class="spinner"><div class="rect1"></div><div class="rect2"></div><div class="rect3"></div><div class="rect4"></div><div class="rect5"></div></div>');

    function ajax(url, payload, callback) {
        function internalCallback(data, status) {
            callback(data.responseJSON || data, status);
        }

        var encodedPayload = (typeof payload === "string") ? payload : JSON.stringify(payload);

        jQuery.ajax({
            type: "POST",
            url: url,
            data: encodedPayload,
            dataType: "json",
            contentType: "application/json",
            success: internalCallback,
            error: internalCallback
        })
    }

    /* Search boxes
    -------------------------------------------------- */
    (function () {
        const endpoint = "/search";

        const $results = $('<div class="anime-search-results"></div>');
        const $status = $('<div class="anime-search-status"></div>');
        const $container = $('<div class="anime-search-container"></div>')
        const $slugInput = $('<input name="anime-slug" type="hidden"></div>')

        $status.append(spinner);
        $results.append($status);
        $container.append($results);
        $container.append($slugInput);

        var timeout = -1;

        function clearResults() {
            $results.find('.anime-search-item').remove();
        }

        function showResults(results, box) {
            $status.hide();
            clearResults();

            for (var result of results) {
                var img = $('<img width="50">');
                img.attr('src', result.thumbnail);

                var imgCol = $('<div class="col-auto"></div>');
                imgCol.append(img);

                const title = result.titles[0].text;
                var titleCol = $('<div class="col"></div>');
                titleCol.html(title);

                var resultDiv = $('<div class="anime-search-item row align-items-center"></div>');
                resultDiv.append(imgCol);
                resultDiv.append(titleCol);
                $results.append(resultDiv);

                const slug = result.slug;
                resultDiv.click(function () {
                    box.val(title);
                    $slugInput.val(slug);
                });
            }
        }

        function runSearch(box) {
            var query = box.val().trim();

            if (query.length === 0) {
                $results.hide();
            } else {
                clearResults();
                $status.show();
                $results.show();
                ajax(endpoint, { query: query }, function (data, status) {                   
                    var results = data.searchResults;
                    showResults(results, box);
                });
            }
        }

        function registerSearchBox(box) {
            var parent = box.parent();  // Find parent
            box = box.detach();         // Remove box from its original parent
            parent.append($container); // Create container
            
            $container.prepend(box); // Add search box to container
            $results.hide();

            box.on('input', function () {
                if (timeout !== -1)
                    clearTimeout(timeout);
                timeout = setTimeout(function () {
                    runSearch(box);
                }, 250);
            });

            box.on('click', function (e) {
                e.stopPropagation();
                if (box.val().trim().length > 0)
                    $results.show();
            });
        }

        $(".anime-search").each(function () {
            var $box = $(this);
            registerSearchBox($box);
        });

        $(document).on('click', function () {
            $results.hide();
        });
    })();


    /* Custom form implementation with ajax
    -------------------------------------------------- */
    (function () {
        function serializeForm($form) {
            var array = $form.serializeArray();
            var obj = {};

            for (var i = 0; i < array.length; i++) {
                var item = array[i];
                obj[item.name] = item.value;
            }

            return obj;
        }

        function registerForm($form) {
            $form.on('submit', function (e) {
                e.preventDefault();

                var button = $form.find("button[type=submit]");
                var oldContent = button.html();
                button.prop('disabled', true);
                button.html(spinner);

                var url = window.location.href;
                var payload = serializeForm($form);

                function ajaxCallback(data, status) {
                    handleResponse($form, data, status);
                    button.prop('disabled', false);
                    button.html(oldContent);
                }

                ajax(url, payload, ajaxCallback);
            });
        }

        function handleResponse($form, data, status) {
            var errorBox = $form.find(".error-message");

            if (!data) {
                errorBox.html("Internal server error");
                console.error("Cannot read response:", status);
            } else if (status !== "success") {
                var message = data.message;
                errorBox.html(message);
                console.error(message);
            } else {
                console.log("Okay!");
                dispatchRedirect();
            }
        }

        function dispatchRedirect() {
            var urlParams = new URLSearchParams(window.location.search);
            var myParam = urlParams.get('ReturnUrl');


            var path = "";
            if (myParam && myParam !== "") {
                path = myParam;
            } else {
                path = "/";
            }

            window.location.href = window.location.protocol + "//" + window.location.host + path;
        }

        $("form").each(function () {
            const $form = $(this);
            registerForm($form);
        });
    })();
})();