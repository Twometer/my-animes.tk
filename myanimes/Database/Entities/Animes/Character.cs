namespace myanimes.Database.Entities.Animes
{
    public class Character
    {
        public int Id { get; set; }

        public int AnimeId { get; set; }

        public AnimeDbo Anime { get; set; }

        public string Slug { get; set; }

        public string Name { get; set; }

        public string Description { get; set; }

        public string ImageUrl { get; set; }
    }
}
