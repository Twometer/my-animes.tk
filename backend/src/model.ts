export enum AnimeStatus {
    Airing,
    Finished,
    ToBeAnnounced,
    Unreleased,
    Upcoming
}

export enum AnimeType {
    ONA,
    OVA,
    TV,
    Movie,
    Music,
    Special
}

export enum WatchState {
    Watching,
    Watched,
    Paused,
    Dropped,
    Queued
}

export interface UserProfile {
    aboutMe: String,
    imageUrl: String,
    location: String,
    favoriteAnime: String,
    favoriteCharacter: String
}

export interface User {
    _id: String,
    username: String,
    password: String,
    createdOn: Date,
    profile: UserProfile
}

export interface AnimeTitle {
    language: String;
    content: String;
}

export interface AnimeEpisode {
    episodeNo: Number,
    seasonNo: Number,
    title: String,
    synopsis: String,
    length: Number,
    thumbnailUrl: String,
    airedOn: Date
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

export interface WatchlistEntry {
    _id: String,
    userId: String,
    animeId: String,
    state: WatchState,
    startedOn?: Date,
    finishedOn?: Date
}
