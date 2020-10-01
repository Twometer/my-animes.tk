using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class StreamingLink
    {
        public int Id { get; set; }

        public int AnimeId { get; set; }

        public AnimeDbo Anime { get; set; }

        public string Url { get; set; }

        public IEnumerable<string> SubbedLanguages { get; set; }

        public IEnumerable<string> DubbedLanguages { get; set; }
    }
}
