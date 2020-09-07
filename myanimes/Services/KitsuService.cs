using Microsoft.AspNetCore.Http.Features;
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

            return null; // TODO
        }

        public async Task<Anime> GetAnime(string slug)
        {
            var encodedSlug = WebUtility.UrlEncode(slug);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[slug]={encodedSlug}&include=genres,productions.company,animeProductions.producer,episodes,streamingLinks,characters.character");

            var includedObjects = json["included"] as JArray;
            var attributes = json["data"][0]["attributes"];
            var titles = attributes["titles"] as JObject;

            var animeTitles = new List<Title>();
            foreach (var item in titles)
                animeTitles.Add(new Title { Language = item.Key, Text = item.Value.Value<string>() });

            var animeGenres = includedObjects.Where(o => o.Value<string>("type") == "genre")
                .Select(g => new Genre { Name = g.Value<string>("name"), Slug = g.Value<string>("slug") });

            var anime = new Anime
            {
                Slug = attributes.Value<string>("slug"),
                CanonicalTitle = attributes.Value<string>("canonicalTitle"),
                Titles = animeTitles,
                StartDate = attributes.Value<DateTime>("startDate"),
                EndDate = attributes.Value<DateTime>("endDate"),
                Synopsis = attributes.Value<string>("synopsis"),
                CoverImageUrl = attributes["coverImage"].Value<string>("small"),
                ThumbnailUrl = attributes["coverImage"].Value<string>("tiny"),
                TrailerYoutubeId = attributes.Value<string>("youtubeVideoId"),
                AgeRating = attributes.Value<string>("ageRatingGuide"),
                Nsfw = attributes.Value<bool>("nsfw"),
                EpisodeCount = attributes.Value<int>("episodeCount"),
                EpisodeLength = attributes.Value<int>("episodeLength"),
                TotalLength = attributes.Value<int>("totalLength"),
                Status = attributes.Value<ShowStatus>("status"),
                Type = attributes.Value<ShowType>("subtype")
            };


            return anime; // TODO
        }

        public async Task<IEnumerable<SearchResult>> Search(string query)
        {
            var encodedQuery = WebUtility.UrlEncode(query);
            var json = await HttpGet($"https://kitsu.io/api/edge/anime?filter[text]={encodedQuery}&fields[anime]=slug,titles,posterImage");

            return null; // TODO
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
    }
}
