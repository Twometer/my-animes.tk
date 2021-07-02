import mongoose from 'mongoose';
import config from './config'
import * as model from './model'

export function connect() {
    return mongoose.connect(config.DB_URL, {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        useCreateIndex: true
    })
}

function createModel<T>(name: string, schema: any): mongoose.Model<T> {
    return mongoose.model<T>(name, new mongoose.Schema(schema));
}

export const User = createModel<model.User>('User', {
    _id: String,
    username: {type: String, unique: true},
    password: String,
    createdOn: {type: Date, default: Date.now},
    profile: {
        aboutMe: String,
        location: String,
        avatarUrl: String,
        favoriteAnimeId: String,
        favoriteCharacter: String
    }
})

export const Anime = createModel<model.Anime>('Anime', {
    _id: String,
    canonicalTitle: String,
    titles: [{language: String, value: String}],
    genres: [String],
    studios: [String],
    airingStartedOn: Date,
    airingFinishedOn: Date,
    synopsis: String,
    posterUrl: String,
    thumbnailUrl: String,
    trailerYoutubeId: String,
    ageRating: String,
    nsfw: Boolean,
    episodeLength: Number,
    totalLength: Number,
    status: {type: String, enum: model.AnimeStatus},
    type: {type: String, enum: model.AnimeType},
    episodes: [{
        title: String,
        episodeNo: Number,
        seasonNo: Number,
        length: Number,
        airedOn: Date,
        thumbnailUrl: String,
        synopsis: String
    }],
    characters: [{name: String, description: String, pictureUrl: String}],
    streamingLinks: [{url: String, subbedLanguages: [String], dubbedLanguages: [String]}],
    _entryExpiringOn: Date
})

export const WatchlistEntry = createModel<model.WatchlistEntry>('WatchlistEntry', {
    _id: String,
    userId: String,
    animeId: String,
    state: {type: String, enum: model.WatchState},
    startedOn: Date,
    finishedOn: Date
})