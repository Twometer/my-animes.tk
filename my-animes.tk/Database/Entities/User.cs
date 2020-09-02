using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace my_animes.tk.Database.Entities
{
    public class User
    {
        public int Id { get; set; }

        public string Name { get; set; }

        public byte[] PasswordHash { get; set; }

        public string Location { get; set; }

        public string Biography { get; set; }

        public string ProfilePictureUrl { get; set; }
        
        public Anime FavoriteAnime { get; set; }
    }
}
