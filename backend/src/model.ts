export interface UserProfile {
    aboutMe: String,
    avatarUrl: String,
    location: String,
    favoriteAnimeId: String,
    favoriteCharacter: String
}

export interface User {
    _id: String,
    username: String,
    password: String,
    createdOn: Date,
    profile: UserProfile
}

export enum AnimeStatus {
    Airing = "airing",
    Finished = "finished",
    ToBeAnnounced = "toBeAnnounced",
    Unreleased = "unreleased",
    Upcoming = "upcoming"
}

export enum AnimeType {
    ONA = "ona",
    OVA = "ova",
    TV = "tv",
    Movie = "movie",
    Music = "music",
    Special = "special"
}

export interface AnimeTitle {
    language: String;
    value: String;
}

export interface AnimeEpisode {
    title: String,
    episodeNo: Number,
    seasonNo: Number,
    length: Number,
    airedOn: Date,
    thumbnailUrl: String,
    synopsis: String
}

export interface AnimeCharacter {
    name: String,
    description: String,
    pictureUrl: String
}

export interface AnimeStreamingLink {
    url: String,
    subbedLanguages: String[],
    dubbedLanguages: String[]
}

export interface Anime {
    _id: String,
    canonicalTitle: String,
    titles: AnimeTitle[],
    genres: String[],
    studios: String[],
    airingStartedOn: Date,
    airingEndedOn: Date,
    synopsis: String,
    posterUrl: String,
    thumbnailUrl: String,
    coverImageUrl: String,
    trailerYoutubeId: String,
    ageRating: String,
    nsfw: Boolean,
    episodeLength: Number,
    totalLength: Number,
    status: AnimeStatus,
    type: AnimeType,
    episodes: AnimeEpisode[],
    characters: AnimeCharacter[],
    streamingLinks: AnimeStreamingLink[],
    _entryExpiringOn: Date
}

export interface SearchResult {
    _id: String,
    titles: AnimeTitle[],
    thumbnailUrl: String,
    type: AnimeType
}

export enum WatchState {
    Watching = "watching",
    Watched = "watched",
    Paused = "paused",
    Dropped = "dropped",
    Queued = "queued"
}

export interface WatchlistEntry {
    _id: String,
    userId: String,
    animeId: String,
    state: WatchState,
    startedOn?: Date,
    finishedOn?: Date,
    rewatchCount: Number
}