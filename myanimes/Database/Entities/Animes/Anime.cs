using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class Anime : AnimeBase
    {
        public IEnumerable<Genre> Genres { get; set; }

        public IEnumerable<Studio> Studios { get; set; }
    }
}
