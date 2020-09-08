using myanimes.Database.Entities.Animes;
using myanimes.Models;
using Newtonsoft.Json.Linq;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
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
            return (json["data"] as JArray)?.Select(item =>
            {
                var anime = new Anime();
                ReadCoreAnimeInfo(anime, item);
                return anime;
            });
        }

        public async Task<Anime> GetAnime(string slug)
        {
            var encodedSlug = WebUtility.UrlEncode(slug);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[slug]={encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character");

            var attributes = json["data"][0]["attributes"];
            var included = json["included"] as JArray;

            var animeEpisodes = included.Where(o => o.Value<string>("type") == "episodes")
                .Select(e => new Episode
                {
                    Title = e.Value<string>("title"),
                    Synopsis = e.Value<string>("synopsis"),
                    SeasonNumber = e.Value<int>("seasonNumber"),
                    EpisodeNumber = e.Value<int>("number"),
                    Length = e.Value<int>("length"),
                    ThumbnailUrl = e["thumbnail"].Value<string>("original"),
                    AirDate = e.Value<DateTime>("airdate")
                });

            var animeCharacters = included.Where(o => o.Value<string>("type") == "characters")
                .Select(c => new Character
                {
                    Name = c.Value<string>("name"),
                    Slug = c.Value<string>("slug"),
                    Description = c.Value<string>("description"),
                    ImageUrl = c["image"].Value<string>("original")
                });

            var animeStreamingLinks = included.Where(o => o.Value<string>("type") == "streamingLinks")
                .Select(l => new StreamingLink
                {
                    Url = l.Value<string>("url"),
                    SubbedLanguages = (l["subs"] as JArray)?.Select(c => c.Value<string>()),
                    DubbedLanguages = (l["dubs"] as JArray)?.Select(c => c.Value<string>())
                });

            var animeGenres = included.Where(o => o.Value<string>("type") == "genres")
                .Select(g => new Genre
                {
                    Name = g.Value<string>("name"),
                    Slug = g.Value<string>("slug")
                });

            var animeStudios = included.Where(o => o.Value<string>("type") == "producers")
                .Select(s => new Studio
                {
                    Name = s.Value<string>("name"),
                    Slug = s.Value<string>("slug")
                });

            var anime = new Anime
            {  
                Episodes = animeEpisodes,
                Characters = animeCharacters,
                StreamingLinks = animeStreamingLinks,
                Genres = animeGenres,
                Studios = animeStudios
            };
            ReadCoreAnimeInfo(anime, attributes);

            return anime;
        }

        public async Task<IEnumerable<SearchResult>> Search(string query)
        {
            var encodedQuery = WebUtility.UrlEncode(query);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[text]={encodedQuery}&fields[anime]=slug,titles,posterImage");

            return (json["data"] as JArray)?.Select(item =>
            {
                var attributes = item["attributes"];
                return new SearchResult
                {
                    Slug = attributes.Value<string>("slug"),
                    Titles = ReadTitles(attributes),
                    Thumbnail = attributes["posterImage"].Value<string>("tiny")
                };
            });
        }

        private async Task<JObject> HttpGet(string url)
        {
            var request = WebRequest.CreateHttp(url);
            request.Method = "GET";
            request.UserAgent = UserAgent;
            request.Proxy = NullProxy;

            using var response = await request.GetResponseAsync();
            using var stream = response.GetResponseStream();
            using var reader = new StreamReader(stream);
            return JObject.Parse(await reader.ReadToEndAsync());
        }

        private void ReadCoreAnimeInfo(Anime anime, JToken attributes)
        {
            anime.Slug = attributes.Value<string>("slug");
            anime.CanonicalTitle = attributes.Value<string>("canonicalTitle");
            anime.Titles = ReadTitles(attributes);
            anime.StartDate = attributes.Value<DateTime>("startDate");
            anime.EndDate = attributes.Value<DateTime>("endDate");
            anime.Synopsis = attributes.Value<string>("synopsis");
            anime.CoverImageUrl = attributes["coverImage"].Value<string>("small");
            anime.ThumbnailUrl = attributes["coverImage"].Value<string>("tiny");
            anime.TrailerYoutubeId = attributes.Value<string>("youtubeVideoId");
            anime.AgeRating = attributes.Value<string>("ageRatingGuide");
            anime.Nsfw = attributes.Value<bool>("nsfw");
            anime.EpisodeCount = attributes.Value<int>("episodeCount");
            anime.EpisodeLength = attributes.Value<int>("episodeLength");
            anime.TotalLength = attributes.Value<int>("totalLength");
            anime.Status = attributes.Value<ShowStatus>("status");
            anime.Type = attributes.Value<ShowType>("subtype");
        }

        private IEnumerable<Title> ReadTitles(JToken attributes)
        {
            return (attributes["titles"] as JObject)?.Properties()
                .Select(t => new Title
                {
                    Language = t.Name,
                    Text = t.Value<string>()
                });
        }
    }
}
