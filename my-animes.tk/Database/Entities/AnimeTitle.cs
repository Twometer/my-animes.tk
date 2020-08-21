using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace my_animes.tk.Database.Entities
{
    public class AnimeTitle
    {
        public int Id { get; set; }

        public int AnimeId { get; set; }
        public Anime Anime { get; set; }

        public string Language { get; set; }

        public string Title { get; set; }
    }
}
