(function () {

    var spinner = buildSpinner();

    function buildSpinner() {
        return $('<div class="spinner"><div class="rect1"></div><div class="rect2"></div><div class="rect3"></div><div class="rect4"></div><div class="rect5"></div></div>');
    }

    function serializeForm($form) {
        var array = $form.serializeArray();
        var obj = {};

        for (var i = 0; i < array.length; i++) {
            var item = array[i];
            obj[item.name] = item.value;
        }

        return JSON.stringify(obj);
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

            jQuery.ajax({
                type: "POST",
                url: url,
                data: payload,
                dataType: "json",
                contentType: "application/json",
                success: ajaxCallback,
                error: ajaxCallback
            })
        });
    }

    function handleResponse($form, dataObj, status) {
        var errorBox = $form.find(".error-message");

        var data = dataObj.responseJSON || dataObj;

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