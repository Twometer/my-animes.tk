namespace myanimes.Models.View
{
    public class AnimeViewModel
    {
        public Anime Anime { get; }

        public AnimeViewModel(Anime anime)
        {
            Anime = anime;
        }
    }
}
