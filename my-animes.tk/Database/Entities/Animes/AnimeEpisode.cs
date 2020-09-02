using System;

namespace my_animes.tk.Database.Entities.Animes
{
    public class AnimeEpisode
    {
        public int Id { get; set; }

        public int AnimeId { get; set; }

        public Anime Anime { get; set; }

        public string Title { get; set; }

        public string Synopsis { get; set; }

        public int SeasonNumber { get; set; }

        public int EpisodeNumber { get; set; }

        public int Length { get; set; }

        public string ThumbnailUrl { get; set; }

        public DateTime AirDate { get; set; }
    }
}
