(function () {

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

            var url = window.location.href;
            var payload = serializeForm($form);

            function ajaxCallback(data, status) {
                handleResponse($form, data, status === "success");
            }

            jQuery.ajax({
                type: "POST",
                urL: url,
                data: payload,
                dataType: "json",
                contentType: "application/json",
                success: ajaxCallback,
                error: ajaxCallback
            })
        });
    }

    function handleResponse($form, data, success) {
        var response = data.responseJSON;

        if (!success) {
            console.error(response.message);
        } else {
            console.log("Okay!");
        }
    }

    $("form").each(function () {
        const $form = $(this);
        registerForm($form);
    });

})();