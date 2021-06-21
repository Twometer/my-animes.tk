import * as db from './database'
import * as model from './model'
import axios from "axios";
import config from "./config";

export interface SearchResult {
    id: String,
    titles: model.AnimeTitle[],
    thumbnailUrl: String
}

async function getRawAnime(slug: string): Promise<any> {
    let encodedSlug = encodeURIComponent(slug);
    let url = `https://kitsu.io/api/edge/anime?filter[slug]=${encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character`
    return (await axios.get(url)).data
}

function convertRawAnime(raw: any): model.Anime {
    // TODO
}

function convertRawTitles(raw: any): model.AnimeTitle[] {
    // TODO
}

export async function getAnime(id: string): Promise<model.Anime | null> {
    let dbAnime = await db.Anime.findById(id);
    let expired = dbAnime != null && dbAnime._entryExpiringOn.getTime() < Date.now();

    if (dbAnime == null || expired) {
        let newAnime = convertRawAnime(await getRawAnime(id));
        if (newAnime == null)
            return null;

        let expiryDate = new Date();
        expiryDate.setDate(expiryDate.getDate() + config.CACHE_DAYS);
        newAnime._entryExpiringOn = expiryDate;

        if (dbAnime != null) {
            Object.assign(dbAnime, newAnime)
            await dbAnime.save();
        } else {
            await new db.Anime(newAnime).save();
        }
    }

    return dbAnime;
}

export async function search(query: string): Promise<SearchResult[]> {
    let encodedQuery = encodeURIComponent(query);
    let url = `https://kitsu.io/api/edge/anime?filter[text]=${encodedQuery}&fields[anime]=slug,titles,posterImage`
    let response = (await axios.get(url)).data;

    let results: SearchResult[] = [];
    for (let result of response.data) {
        result.push({
            id: result.attributes.slug,
            titles: convertRawTitles(result.attributes.titles),
            thumbnail: result.attributes.posterImage.tiny
        })
    }

    return results;
}