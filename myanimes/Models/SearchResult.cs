using myanimes.Database.Entities.Animes;
using System.Collections.Generic;

namespace myanimes.Models
{
    public class SearchResult
    {
        public string Slug { get; set; }

        public IEnumerable<Title> Titles { get; set; }

        public string Thumbnail { get; set; }
    }
}
