﻿using myanimes.Database.Entities.Animes;
using System.Collections.Generic;

namespace myanimes.Services
{
    public class KitsuService
    {
        // Search by Text: https://kitsu.io/api/edge/anime?filter[text]=$QUERY$&fields[anime]=slug,titles,posterImage
        // Get full info by slug: https://kitsu.io/api/edge/anime?filter[slug]=darling-in-the-franxx&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character
        // Get trending animes: https://kitsu.io/api/edge/trending/anime

        public List<Anime> GetTrendingAnimes()
        {
            return null; // TODO
        }

        public Anime FindBySlug(string slug)
        {
            return null; // TODO
        }

    }
}
