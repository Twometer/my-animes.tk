using myanimes.Database.Entities.Animes;

namespace myanimes.Database.Entities.Mappings
{
    public class StudioMapping
    {
        public int AnimeId { get; set; }

        public AnimeDbo Anime { get; set; }

        public int StudioId { get; set; }

        public Studio Studio { get; set; }
    }
}
