using myanimes.Database.Entities.Animes;
using System.Collections.Generic;

namespace myanimes.Services
{
    public class SearchResult
    {
        public string Slug { get; set; }

        public List<Title> Titles { get; set; }

        public string Thumbnail { get; set; }
    }
}
