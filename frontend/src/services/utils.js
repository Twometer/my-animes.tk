module.exports.getTitle = function (anime, preferredLanguages) {
    let titles = anime.titles;

    function getSingleTitle(langugae) {
        for (let title of titles) {
            if (title.language == langugae) return title.value;
        }
        return null;
    }

    for (let preferredLanguage of preferredLanguages) {
        let title = getSingleTitle(preferredLanguage);
        if (title != null) return title;
    }

    let firstOne = Object.keys(titles)[0];
    return titles[firstOne];
};

module.exports.typeToString = function (type) {
    switch (type) {
        case 'ona':
            return 'Original net animation';
        case 'ova':
            return 'Original video animation';
        case 'tv':
            return 'TV Show';
        case 'movie':
            return 'Movie';
        case 'music':
            return 'Music';
        case 'special':
            return 'Special';
    }
    return 'Unknown';
};

module.exports.reformatDate = function (date) {
    if (date.includes('T')) date = date.substr(0, date.indexOf('T'));
    let parsedDate = new Date(Date.parse(date));
    return parsedDate.toShortFormat();
};
