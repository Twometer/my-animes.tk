using myanimes.Database.Entities.Animes;
using System.Collections.Generic;
using System.IO;
using System.Net;
using System.Threading.Tasks;

namespace myanimes.Services
{
    public class KitsuService
    {
        private const string UserAgent = "myanimes/ApiClient/2.0";
        private readonly IWebProxy NullProxy = new WebProxy();

        public async Task<IEnumerable<Anime>> GetTrendingAnimes()
        {
            var json = await HttpGet("https://kitsu.io/api/edge/trending/anime");

            return null; // TODO
        }

        public async Task<Anime> GetAnime(string slug)
        {
            var encodedSlug = WebUtility.UrlEncode(slug);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[slug]={encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character");

            return null; // TODO
        }

        public async Task<IEnumerable<SearchResult>> Search(string query)
        {
            var encodedQuery = WebUtility.UrlEncode(query);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[text]={encodedQuery}&fields[anime]=slug,titles,posterImage");
            
            return null; // TODO
        }

        private async Task<string> HttpGet(string url)
        {
            var request = WebRequest.CreateHttp(url);
            request.Method = "GET";
            request.UserAgent = UserAgent;
            request.Proxy = NullProxy;

            using var response = await request.GetResponseAsync();
            using var stream = response.GetResponseStream();
            using var reader = new StreamReader(stream);
            return await reader.ReadToEndAsync();
        }
    }
}
