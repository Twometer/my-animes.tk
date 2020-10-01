using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace myanimes.Models.Response
{
    public class SearchResponseModel
    {
        public SearchResponseModel(IEnumerable<SearchResult> searchResults)
        {
            SearchResults = searchResults;
        }

        public IEnumerable<SearchResult> SearchResults { get; set; }

    }
}
