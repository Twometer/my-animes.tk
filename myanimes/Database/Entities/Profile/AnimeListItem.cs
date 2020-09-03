using myanimes.Database.Entities.Animes;
using System;
using System.ComponentModel.DataAnnotations.Schema;

namespace myanimes.Database.Entities.Profile
{
    public class AnimeListItem
    {
        public int Id { get; set; }

        public int UserId { get; set; }

        public User User { get; set; }

        [ForeignKey("Anime")]
        public int AnimeId { get; set; }

        public Anime Anime { get; set; }
        
        public WatchState WatchState { get; set; }

        public DateTime DateAdded { get; set; }

        public DateTime DateModified { get; set; }

        public DateTime DateWatched { get; set; }

        public int ArtScore { get; set; }

        public int StoryScore { get; set; }

        public int CharacterScore { get; set; }

        public int EntertainmentScore { get; set; }
    }
}
