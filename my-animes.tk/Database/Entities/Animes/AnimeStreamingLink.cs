using System.Collections.Generic;

namespace my_animes.tk.Database.Entities.Animes
{
    public class AnimeStreamingLink
    {
        public int Id { get; set; }

        public int AnimeId { get; set; }

        public Anime Anime { get; set; }

        public string Url { get; set; }

        public IEnumerable<string> SubbedLanguages { get; set; }

        public IEnumerable<string> DubbedLanguages { get; set; }
    }
}
