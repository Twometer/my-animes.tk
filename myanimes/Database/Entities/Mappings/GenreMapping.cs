using myanimes.Database.Entities.Animes;

namespace myanimes.Database.Entities.Mappings
{
    public class GenreMapping
    {
        public int AnimeId { get; set; }

        public AnimeDbo Anime { get; set; }

        public int GenreId { get; set; }

        public Genre Genre { get; set; }
    }
}
