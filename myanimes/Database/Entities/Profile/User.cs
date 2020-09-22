using myanimes.Database.Entities.Animes;
using myanimes.Database.Entities.Mappings;
using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;

namespace myanimes.Database.Entities.Profile
{
    public class User
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public byte[] PasswordSalt { get; set; }

        public byte[] PasswordHash { get; set; }

        public DateTime JoinDate { get; set; }

        public string Location { get; set; }

        public string Biography { get; set; }

        public string ProfilePictureUrl { get; set; }
        
        [ForeignKey("FavoriteAnime")]
        public int FavoriteAnimeId { get; set; }

        public AnimeDbo FavoriteAnime { get; set; }

        public IEnumerable<AnimeListItem> AnimeList { get; set; }

        public IEnumerable<FollowerMapping> Followers { get; set; }

        public IEnumerable<FollowerMapping> Following { get; set; }
    }
}
