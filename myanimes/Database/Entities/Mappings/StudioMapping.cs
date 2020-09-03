﻿using myanimes.Database.Entities.Animes;

namespace myanimes.Database.Entities.Mappings
{
    public class StudioMapping
    {
        public int AnimeId { get; set; }

        public Anime Anime { get; set; }

        public int StudioId { get; set; }

        public AnimeStudio Studio { get; set; }
    }
}
