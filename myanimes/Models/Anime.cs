using myanimes.Database.Entities.Animes;
using System.Collections.Generic;

namespace myanimes.Models
{
    public class Anime : AnimeBase
    {
        public IEnumerable<Genre> Genres { get; set; }

        public IEnumerable<Studio> Studios { get; set; }
    }
}
