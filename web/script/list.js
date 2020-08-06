/* Star rating library */
(function () {
    $(".star-input").each(function () {
        var $this = $(this);
        var input = $this.children("input");
        var stars = [];
        for (var i = 0; i < 5; i++) {
            const starIndex = i;
            const star = $('<span class="fa fa-star"></span>');
            $this.append(star);
            stars.push(star);
            star.mouseover(function () {
                for (var j = 0; j < 5; j++)
                    if (j <= starIndex)
                        stars[j].addClass('checked');
                    else
                        stars[j].removeClass('checked');
            });
            star.click(function () {
                input.val(starIndex + 1);
            });
        }
        $this.mouseleave(function () {
            for (var j = 0; j < 5; j++)
                if (j <= input.val() - 1)
                    stars[j].addClass('checked');
                else
                    stars[j].removeClass('checked');
        });
        input.val(1);
        stars[0].addClass('checked');
    });
})();
/* List filtering library */
(function () {
    var buttons = [];
    $(".button.in-bar").each(function () {
        buttons.push($(this));
    });
    for (const button of buttons) {
        button.click(function () {
            for (const otherButton of buttons) {
                otherButton.removeClass("selected");
            }
            button.addClass("selected");

            var filter = button.data('filter');
            $(".anime-list-item").each(function () {
                var $this = $(this);
                if (filter === 'all' || $this.data('state') === filter)
                    $this.removeClass('filtered');
                else
                    $this.addClass('filtered');
            });
        });
    }
})();

/* Dialog visibility management */
(function () {
    var ratingBox = $("#rating-box");
    var dateBox = $("#date-box");
    var dateInput = $("#watchDate");
    var watchState = $("#watchState");
    watchState.change(function () {
        var val = watchState.val();

        if (val === 'watched') ratingBox.show();
        else ratingBox.hide();

        if (val === 'queued') {
            dateBox.hide();
            dateInput.removeAttr('required');
        } else {
            dateBox.show();
            dateInput.prop('required', true);
        }
    });
    ratingBox.hide();
})();

/* Action button fading */
(function () {
    var items = $(".anime-list-item");
    items.mouseover(function () {
        $(this).children(".anime-options").removeClass('hidden');
    });
    items.mouseleave(function () {
        $(this).children(".anime-options").addClass('hidden');
    });
})();
/* Modal data loading */
(function () {
    $('#delete-anime-modal').on('show.bs.modal', function (event) {
        loadAnimeData($(event.relatedTarget), event, "delete");
    })
    $('#anime-property-modal').on('show.bs.modal', function (event) {
        var button = $(event.relatedTarget);
        var mode = button.data('mode');
        $("#property-action").val(mode);
        if (mode === 'edit') {
            $("#anime-search-box").hide();
            $("#edit-anime-name").show();
            $("#anime-property-header").text("Edit anime");
            $("#anime-search").removeAttr('required');
            setWatchProperties(button.data('anime-date'), button.data('anime-state'));
        } else if (mode === 'add') {
            $("#anime-search-box").show();
            $("#edit-anime-name").hide();
            $("#anime-property-header").text("Add new anime");
            $("#anime-search").prop('required', true);
            setWatchProperties('', 'watching');
        }
        loadAnimeData(button, event, "edit");
    })

    function setWatchProperties(date, state) {
        $("#watchDate").val(date);
        var watchState = $("#watchState");
        watchState.val(state)
        watchState.change();
    }

    function loadAnimeData(button, event, idPrefix) {
        var animeName = button.data('anime-name')
        var animeId = button.data('anime-id');

        $('#' + idPrefix + '-anime-name').text(animeName)
        $('#' + idPrefix + '-anime-id').val(animeId)
    }
})();