using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Internal;
using Microsoft.Extensions.Logging;
using myanimes.Database;
using myanimes.Database.Entities.Animes;
using myanimes.Database.Entities.Mappings;
using myanimes.Models;
using System.Linq;
using System.Threading.Tasks;

namespace myanimes.Services
{
    public class CacheService
    {
        private readonly ILogger<CacheService> logger;

        private readonly DatabaseContext database;

        private readonly KitsuService kitsu;

        public CacheService(ILogger<CacheService> logger, DatabaseContext database, KitsuService kitsu)
        {
            this.logger = logger;
            this.database = database;
            this.kitsu = kitsu;
        }

        public async Task<Anime> GetAnime(string slug)
        {
            var dbAnime = await database.Animes.SingleOrDefaultAsync(a => a.Slug == slug);

            if (dbAnime == default)
            {
                logger.LogDebug("Cache miss while querying anime '{0}'", slug);
                var anime = await kitsu.GetAnime(slug);
                await AnimeToDatabase(anime);
                return anime;
            }
            else
            {
                logger.LogDebug("Cache hit while querying anime '{0}'", slug);
                return await DatabaseToAnime(dbAnime);
            }
        }

        private async Task<Anime> DatabaseToAnime(AnimeDbo animeDbo)
        {
            var anime = new Anime();
            anime.CopyBaseProperties(source: animeDbo);

            anime.Genres = await database.GenreMappings.Where(m => m.AnimeId == anime.Id)
                .Join(database.Genres, m => m.GenreId, g => g.Id, (m, g) => g).ToListAsync();

            anime.Studios = await database.StudioMappings.Where(m => m.AnimeId == anime.Id)
                .Join(database.Studios, m => m.StudioId, s => s.Id, (m, s) => s).ToListAsync();

            anime.Titles = await database.Titles.Where(t => t.AnimeId == anime.Id).ToListAsync();
            anime.Episodes = await database.Episodes.Where(e => e.AnimeId == anime.Id)
                .OrderBy(e => e.SeasonNumber).ThenBy(e => e.EpisodeNumber).ToListAsync();
            anime.Characters = await database.Characters.Where(c => c.AnimeId == anime.Id).ToListAsync();
            anime.StreamingLinks = await database.StreamingLinks.Where(l => l.AnimeId == anime.Id).ToListAsync();

            return anime;
        }

        private async Task AnimeToDatabase(Anime anime)
        {
            var animeDbo = new AnimeDbo();
            animeDbo.CopyBaseAndNavigationProperties(source: anime);

            database.Animes.Add(animeDbo);

            foreach (var genre in anime.Genres)
            {
                var genreDbo = await database.Genres.SingleOrDefaultAsync(g => g.Slug == genre.Slug);
                database.GenreMappings.Add(new GenreMapping { Anime = animeDbo, Genre = genreDbo ?? genre });
            }

            foreach (var studio in anime.Studios)
            {
                var studioDbo = await database.Studios.SingleOrDefaultAsync(s => s.Slug == studio.Slug);
                database.StudioMappings.Add(new StudioMapping { Anime = animeDbo, Studio = studioDbo ?? studio });
            }

            await database.SaveChangesAsync();
        }
    }
}
