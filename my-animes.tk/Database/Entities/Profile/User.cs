using my_animes.tk.Database.Entities.Animes;
using System;
using System.Collections.Generic;

namespace my_animes.tk.Database.Entities.Profile
{
    public class User
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public byte[] PasswordHash { get; set; }

        public DateTime JoinDate { get; set; }

        public string Location { get; set; }

        public string Biography { get; set; }

        public string ProfilePictureUrl { get; set; }
        
        public Anime FavoriteAnime { get; set; }

        public IEnumerable<AnimeListItem> AnimeList { get; set; }

        public IEnumerable<UserFollowing> Following { get; set; }
    }
}
