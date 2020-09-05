using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class Anime : AnimeBase
    {
        public IEnumerable<AnimeGenre> Genres { get; set; }

        public IEnumerable<AnimeStudio> Studios { get; set; }
    }
}
