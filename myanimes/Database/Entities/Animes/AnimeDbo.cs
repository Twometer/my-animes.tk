using myanimes.Database.Entities.Mappings;
using System;
using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class AnimeDbo : AnimeBase
    {
        public IEnumerable<GenreMapping> Genres { get; set; }

        public IEnumerable<StudioMapping> Studios { get; set; }

        public DateTime EntryExpirationDate { get; set; }
    }
}
