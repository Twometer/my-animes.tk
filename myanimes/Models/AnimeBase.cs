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

        public void CopyBaseProperties(AnimeBase other)
        {
            Id = other.Id;
            Slug = other.Slug;
            CanonicalTitle = other.CanonicalTitle;
            StartDate = other.StartDate;
            EndDate = other.EndDate;
            Synopsis = other.Synopsis;
            PosterImageUrl = other.PosterImageUrl;
            ThumbnailUrl = other.ThumbnailUrl;
            TrailerYoutubeId = other.TrailerYoutubeId;
            AgeRating = other.AgeRating;
            Nsfw = other.Nsfw;
            EpisodeCount = other.EpisodeCount;
            EpisodeLength = other.EpisodeLength;
            TotalLength = other.TotalLength;
            Status = other.Status;
            Type = other.Type;
        }

        public void CopyBaseAndNavigationProperties(AnimeBase other)
        {
            CopyBaseProperties(other);
            Titles = other.Titles;
            Episodes = other.Episodes;
            Characters = other.Characters;
            StreamingLinks = other.StreamingLinks;
        }
    }
}
