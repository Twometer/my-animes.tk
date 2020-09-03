using myanimes.Database.Entities.Mappings;
using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class AnimeGenre
    {
        public int Id { get; set; }

        public string Slug { get; set; }

        public string Name { get; set; }

        public IEnumerable<GenreMapping> GenreMappings { get; set; }
    }
}
