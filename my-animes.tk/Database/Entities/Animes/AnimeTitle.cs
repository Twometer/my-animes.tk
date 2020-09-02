namespace my_animes.tk.Database.Entities.Animes
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
