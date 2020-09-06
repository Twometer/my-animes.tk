using myanimes.Database.Entities.Animes;

namespace myanimes.Database
{
    public static class DatabaseExtensions
    {
        public static AnimeDbo ToAnimeDbo(this Anime anime, DatabaseContext ctx)
        {
            return null; // TODO
        }

        public static Anime ToAnime(this AnimeDbo animeDbo, DatabaseContext ctx)
        {
            return null; // TODO
        }
    }
}
