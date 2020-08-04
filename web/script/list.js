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

            var filter = button.attr('data-filter');
            $(".anime-list-item").each(function () {
                var $this = $(this);
                if (filter === 'all' || $this.attr('data-state') === filter)
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