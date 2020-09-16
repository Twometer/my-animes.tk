using myanimes.Database;
using myanimes.Database.Entities.Animes;
using myanimes.Models;
using System.Threading.Tasks;

namespace myanimes.Extensions
{
    public static class DatabaseExtensions
    {
        public static async Task<AnimeDbo> ToAnimeDbo(this Anime anime, DatabaseContext ctx)
        {
            var animeDbo = new AnimeDbo();
            animeDbo.CopyBaseProperties(anime);

            return animeDbo; // TODO
        }

        public static async Task<Anime> ToAnime(this AnimeDbo animeDbo, DatabaseContext ctx)
        {
            var anime = new Anime();
            anime.CopyBaseProperties(animeDbo);

            return anime; // TODO
        }
    }
}
