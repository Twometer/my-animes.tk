using myanimes.Database.Entities.Animes;
using System;
using System.Collections.Generic;

namespace myanimes.Models
{
    public class AnimeBase
    {
        public int Id { get; set; }

        public string Slug { get; set; }

        public string CanonicalTitle { get; set; }

        public IEnumerable<Title> Titles { get; set; }

        public DateTime StartDate { get; set; }

        public DateTime EndDate { get; set; }

        public string Synopsis { get; set; }

        public string PosterImageUrl { get; set; }

        public string ThumbnailUrl { get; set; }

        public string TrailerYoutubeId { get; set; }

        public string AgeRating { get; set; }

        public bool Nsfw { get; set; }

        public int EpisodeCount { get; set; }

        public int EpisodeLength { get; set; }

        public int TotalLength { get; set; }

        public ShowStatus Status { get; set; }

        public ShowType Type { get; set; }

        public IEnumerable<Episode> Episodes { get; set; }

        public IEnumerable<Character> Characters { get; set; }

        public IEnumerable<StreamingLink> StreamingLinks { get; set; }

        public void CopyBaseProperties(AnimeBase source)
        {
            Id = source.Id;
            Slug = source.Slug;
            CanonicalTitle = source.CanonicalTitle;
            StartDate = source.StartDate;
            EndDate = source.EndDate;
            Synopsis = source.Synopsis;
            PosterImageUrl = source.PosterImageUrl;
            ThumbnailUrl = source.ThumbnailUrl;
            TrailerYoutubeId = source.TrailerYoutubeId;
            AgeRating = source.AgeRating;
            Nsfw = source.Nsfw;
            EpisodeCount = source.EpisodeCount;
            EpisodeLength = source.EpisodeLength;
            TotalLength = source.TotalLength;
            Status = source.Status;
            Type = source.Type;
        }

        public void CopyBaseAndNavigationProperties(AnimeBase source)
        {
            CopyBaseProperties(source);
            Titles = source.Titles;
            Episodes = source.Episodes;
            Characters = source.Characters;
            StreamingLinks = source.StreamingLinks;
        }
    }
}
