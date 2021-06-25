import * as db from './database'
import * as model from './model'
import axios from "axios";
import config from "./config";
import {daysFromNow} from "./util";

async function getRawAnime(slug: string): Promise<any> {
    let encodedSlug = encodeURIComponent(slug);
    let url = `https://kitsu.io/api/edge/anime?filter[slug]=${encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character`
    return (await axios.get(url)).data
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
                synopsis: i.synopsis,
            };
        })

    let characters = included.filter(i => i.type == 'characters')
        .map(i => i.attributes)
        .map<model.AnimeCharacter>(i => {
            return {
                name: i.name,
                description: i.description,
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
        synopsis: attributes.synopsis,
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
        _entryExpiringOn: daysFromNow(config.CACHE_DAYS)
    }
}

function convertRawTitles(raw: any): model.AnimeTitle[] {
    let titles: model.AnimeTitle[] = [];
    for (let key of Object.keys(raw)) {
        titles.push({
            language: key,
            value: raw[key]
        })
    }
    return titles;
}

export async function load(id: string): Promise<model.Anime | null> {
    id = id.toLowerCase();

    let dbAnime = await db.Anime.findById(id);
    let expired = dbAnime != null && dbAnime._entryExpiringOn.getTime() < Date.now();

    if (dbAnime == null || expired) {
        let newAnime = convertRawAnime(await getRawAnime(id));
        if (newAnime == null)
            return null;

        if (dbAnime != null) {
            Object.assign(dbAnime, newAnime)
            await dbAnime.save();
        } else {
            await new db.Anime(newAnime).save();
            return newAnime;
        }
    }

    return dbAnime;
}

export async function search(query: string): Promise<model.SearchResult[]> {
    let encodedQuery = encodeURIComponent(query);
    let url = `https://kitsu.io/api/edge/anime?filter[text]=${encodedQuery}&fields[anime]=slug,titles,posterImage,subtype`
    let response = (await axios.get(url)).data;

    let results: model.SearchResult[] = [];
    for (let result of response.data) {
        let attributes = result.attributes;
        console.log(attributes);
        results.push({
            _id: attributes.slug,
            titles: convertRawTitles(attributes.titles),
            thumbnailUrl: attributes.posterImage.tiny,
            type: convertEnum(attributes.subtype, model.AnimeType)
        })
    }

    return results;
}