const AnimeStatus = {
    Airing: 1,
    Finished: 2,
    ToBeAnnounced: 3,
    Unreleased: 4,
    Upcoming: 5
};

const AnimeType = {
    ONA: 1,
    OVA: 2,
    TV: 3,
    Movie: 4,
    Music: 5,
    Special: 6
};

const WatchState = {
    Watching: 1,
    Watched: 2,
    Paused: 3,
    Dropped: 4,
    Queued: 5
}

module.exports = { AnimeStatus, AnimeType, WatchState };