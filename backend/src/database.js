'use strict';

const xa = require("xa");
const mongoose = require("mongoose");

const { AnimeStatus, AnimeType, WatchState } = require('./model');

exports.connect = (url) => {
    mongoose.connect(url, { useNewUrlParser: true, useUnifiedTopology: true, useCreateIndex: true })
        .then(() => xa.success("Connected to database"));
}

exports.getConnection = () => mongoose.connection;

exports.Account = mongoose.model('Account', {
    _id: String,
    username: { type: String, unique: true },
    password: String,
    createdOn: { type: Date, default: Date.now },
    profile: {
        about: String,
        imageUrl: String,
        location: String,
        favAnime: String
    }
});

exports.Anime = mongoose.model('Anime', {
    slug: { type: String, unique: true },
    canonicalTitle: String,
    titles: [{ language: String, value: String }],
    genres: [String],
    studios: [String],
    startedOn: Date,
    finishedOn: Date,
    synopsis: String,
    posterUrl: String,
    thumbnailUrl: String,
    trailerYoutubeId: String,
    ageRating: String,
    nsfw: Boolean,
    episodeLength: Number,
    totalLength: Number,
    status: { type: Number, enum: Object.values(AnimeStatus) },
    type: { type: Number, enum: Object.values(AnimeType) },
    episodes: [{ title: String, synopsis: String, season: Number, episode: Number, length: Number, thumbnailUrl: String, airedOn: Date }],
    characters: [{ name: String, description: String, imageUrl: String }],
    streamingLinks: [{ url: String, subLanguages: [String], dubLanguages: [String] }],
    _entryExpiringOn: Date
});

exports.AnimeListEntry = mongoose.model('AnimeListEntry', {
    _id: String,
    user: String,
    anime: String,
    state: { type: Number, enum: Object.values(WatchState) },
    startedOn: Date,
    finishedOn: Date
});