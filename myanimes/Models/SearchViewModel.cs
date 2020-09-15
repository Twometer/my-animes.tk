using System.Collections.Generic;

namespace myanimes.Models
{
    public class SearchViewModel
    {
        public string Query { get; }

        public IEnumerable<SearchResult> SearchResults { get; }

        public SearchViewModel(string query, IEnumerable<SearchResult> searchResults)
        {
            Query = query;
            SearchResults = searchResults;
        }
    }
}
