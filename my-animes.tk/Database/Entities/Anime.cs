using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace my_animes.tk.Database.Entities
{
    public class Anime
    {
        public int Id { get; set; }

        public string Slug { get; set; }

        public string CanonicalTitle { get; set; }

        public IEnumerable<AnimeTitle> Titles { get; set; }

        public IEnumerable<string> Categories { get; set; }

        public IEnumerable<AnimeStudio> Studios { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public string Synopsis { get; set; }

        public string CoverImageUrl { get; set; }

        public string ThumbnailUrl { get; set; }

        public string TrailerYoutubeId { get; set; }

        public string AgeRating { get; set; }

        public bool Nsfw { get; set; }

        public int EpisodeCount { get; set; }

        public int EpisodeLength { get; set; }

        public int TotalLength { get; set; }

        public AnimeStatus Status { get; set; }

        public AnimeType Type { get; set; }


        public DateTime EntryExpirationDate { get; set; }

    }
}
