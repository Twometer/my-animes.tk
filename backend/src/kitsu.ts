import * as db from './database'
import * as model from './model'
import * as cache from "./cache";
import axios from "axios";
import config from "./config";
import {daysFromNow} from "./util";
import striptags from "striptags";
import {decode} from "html-entities";
import * as log from 'cutelog.js'

async function getRawAnime(slug: string): Promise<any> {
    let encodedSlug = encodeURIComponent(slug);
    let url = `https://kitsu.io/api/edge/anime?filter[slug]=${encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character`
    return (await axios.get(url)).data
}

function processText(html: string): string {
    return decode(striptags(html)).trim();
}

function convertEnum<T>(raw: any, type: any): T {
    for (let enumMember of Object.keys(type)) {
        if (enumMember.toLowerCase() == raw.toLowerCase()) {
            return type[enumMember];
        }
    }
    throw Error(`Cannot convert value ${raw} into enum ${type.constructor.name}`)
}

function convertRawAnime(raw: any): model.Anime | null {
    if (raw.data.length == 0)
        return null;

    let attributes: any = raw.data[0].attributes;
    let included: any[] = raw.included;

    let episodes = included.filter(i => i.type == 'episodes')
        .map(i => i.attributes)
        .map<model.AnimeEpisode>(i => {
            return {
                title: i.canonicalTitle,
                episodeNo: i.relativeNumber,
                seasonNo: i.seasonNumber,
                length: i.length,
                airedOn: i.airdate,
                thumbnailUrl: i.thumbnail?.original,
                synopsis: processText(i.synopsis),
            };
        })

    let characters = included.filter(i => i.type == 'characters')
        .map(i => i.attributes)
        .map<model.AnimeCharacter>(i => {
            return {
                name: i.name,
                description: processText(i.description),
                pictureUrl: i.image?.original
            }
        })

    let streamingLinks = included.filter(i => i.type == 'streamingLinks')
        .map(i => i.attributes)
        .map<model.AnimeStreamingLink>(i => {
            return {
                url: i.url,
                subbedLanguages: i.subs || [],
                dubbedLanguages: i.dubs || []
            }
        })

    let genres = included.filter(i => i.type == 'genres')
        .map<string>(i => {
            return i.attributes.name
        })

    let studios = included.filter(i => i.type == 'producers')
        .map<string>(i => {
            return i.attributes.name
        })

    if (!attributes.totalLength || attributes.totalLength == 0) {
        attributes.totalLength = episodes.length * attributes.episodeLength;
    }

    return {
        _id: attributes.slug,
        canonicalTitle: attributes.canonicalTitle,
        titles: convertRawTitles(attributes.titles),
        genres,
        studios,
        airingStartedOn: attributes.startDate,
        airingEndedOn: attributes.endDate,
        synopsis: processText(attributes.synopsis),
        posterUrl: attributes.posterImage?.small,
        thumbnailUrl: attributes.posterImage?.tiny,
        trailerYoutubeId: attributes.youtubeVideoId,
        ageRating: attributes.ageRatingGuide,
        nsfw: attributes.nsfw,
        episodeLength: attributes.episodeLength,
        totalLength: attributes.totalLength,
        status: convertEnum(attributes.status, model.AnimeStatus),
        type: convertEnum(attributes.subtype, model.AnimeType),
        episodes,
        characters,
        streamingLinks,
        _entryExpiringOn: daysFromNow(config.ANIME_TTL)
    }
}

function convertRawTitles(raw: any): model.AnimeTitle[] {
    let titles: model.AnimeTitle[] = [];
    for (let key of Object.keys(raw)) {
        titles.push({
            language: key,
            value: processText(raw[key])
        })
    }
    return titles;
}

async function getAnime(id: string): Promise<model.Anime | null> {
    return convertRawAnime(await getRawAnime(id));
}

function stripInternals(db: model.Anime): model.Anime {
    let raw = <any>db;
    delete raw.__v
    for (let title of raw.titles) delete title._id
    for (let ep of raw.episodes) delete ep._id
    for (let ch of raw.characters) delete ch._id
    for (let sl of raw.streamingLinks) delete sl._id
    return db;
}

export async function load(id: string): Promise<model.Anime | null> {
    id = id.toLowerCase().trim();

    let cacheKey = `anime::${id}`;
    let cachedState = await cache.get(cacheKey);

    switch (cachedState) {
        case cache.State.Present:
            let dbAnime = (await db.Anime.findById(id))!!;
            let expired = dbAnime._entryExpiringOn.getTime() < Date.now();

            if (expired) {
                log.info(`Renewing cached data on anime ${id}`)
                let newData = await getAnime(id);
                Object.assign(dbAnime, newData);
                await dbAnime.save();
            }

            return stripInternals(dbAnime.toObject());

        case cache.State.NeverCached:
            let anime = await getAnime(id);
            if (anime == null) {
                await cache.set(cacheKey, cache.State.Nonexistent);
            } else {
                await new db.Anime(anime).save();
                await cache.set(cacheKey, cache.State.Present);
            }

            return anime;

        case cache.State.Nonexistent:
            return null;
    }

    throw Error(`Cache failure: Unexpected state ${cachedState} for anime ${id}`)
}

export async function search(query: string): Promise<model.SearchResult[]> {
    let encodedQuery = encodeURIComponent(query);
    let url = `https://kitsu.io/api/edge/anime?filter[text]=${encodedQuery}&fields[anime]=slug,titles,posterImage,subtype`
    let response = (await axios.get(url)).data;

    let results: model.SearchResult[] = [];
    for (let result of response.data) {
        let attributes = result.attributes;
        results.push({
            _id: attributes.slug,
            titles: convertRawTitles(attributes.titles),
            thumbnailUrl: attributes.posterImage.tiny,
            type: convertEnum(attributes.subtype, model.AnimeType)
        })
    }

    return results;
}