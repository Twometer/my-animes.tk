using myanimes.Database.Entities.Animes;
using System.Collections.Generic;

namespace myanimes.Services
{
    public class KitsuService
    {
        public List<Anime> GetTrendingAnimes()
        {
            // https://kitsu.io/api/edge/trending/anime
            return null; // TODO
        }

        public Anime GetAnime(string slug)
        {
            // https://kitsu.io/api/edge/anime?filter[slug]=$SLUG$&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character
            return null; // TODO
        }

        public List<SearchResult> Search(string query)
        {
            // https://kitsu.io/api/edge/anime?filter[text]=$QUERY$&fields[anime]=slug,titles,posterImage
            return null; // TODO
        }       
    }
}
