using myanimes.Database.Entities.Mappings;
using System;
using System.Collections.Generic;

namespace myanimes.Database.Entities.Animes
{
    public class Anime
    {
        public int Id { get; set; }

        public string Slug { get; set; }

        public string CanonicalTitle { get; set; }

        public IEnumerable<AnimeTitle> Titles { get; set; }

        public IEnumerable<GenreMapping> Genres { get; set; }

        public IEnumerable<StudioMapping> Studios { get; set; }

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

        public IEnumerable<AnimeEpisode> Episodes { get; set; }

        public IEnumerable<AnimeCharacter> Characters { get; set; }

        public IEnumerable<AnimeStreamingLink> StreamingLinks { get; set; }


        public DateTime EntryExpirationDate { get; set; }
    }
}
