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
            var dbAnime = await database.Animes.AsQueryable()
                .Where(a => a.Slug == slug)
                .Include(a => a.Genres)
                .ThenInclude(g => g.Genre)
                .Include(a => a.Studios)
                .ThenInclude(s => s.Studio)
                .Include(a => a.Titles)
                .Include(a => a.Episodes)
                .Include(a => a.Characters)
                .Include(a => a.StreamingLinks)
                .SingleOrDefaultAsync();

            if (dbAnime == null)
            {
                logger.LogDebug("Cache miss while querying anime '{0}'", slug);
                var anime = await kitsu.GetAnime(slug);
                await AnimeToDatabase(anime);
                return anime;
            }
            else
            {
                logger.LogDebug("Cache hit while querying anime '{0}'", slug);
                return DatabaseToAnime(dbAnime);
            }
        }

        private Anime DatabaseToAnime(AnimeDbo animeDbo)
        {
            var anime = new Anime();
            anime.CopyBaseProperties(animeDbo);
            anime.Genres = animeDbo.Genres.Select(m => m.Genre);
            anime.Studios = animeDbo.Studios.Select(s => s.Studio);
            anime.Episodes = anime.Episodes.OrderBy(e => e.EpisodeNumber);
            return anime;
        }   

        private async Task AnimeToDatabase(Anime anime)
        {
            var animeDbo = new AnimeDbo();
            animeDbo.CopyBaseProperties(anime);

            var addResult = database.Animes.Add(animeDbo);
            await database.SaveChangesAsync();

            var animeId = addResult.Entity.Id;

            foreach (var genre in anime.Genres)
            {
                genre.Id = await GetOrAddGenre(genre);
                var mapping = new GenreMapping { AnimeId = animeId, GenreId = genre.Id };
                database.GenreMappings.Add(mapping);
            }

            foreach (var studio in anime.Studios)
            {
                studio.Id = await GetOrAddStudio(studio);
                var mapping = new StudioMapping { AnimeId = animeId, StudioId = studio.Id };
                database.StudioMappings.Add(mapping);
            }

            await database.SaveChangesAsync();
        }

        private async Task<int> GetOrAddGenre(Genre genre)
        {
            var dbGenre = await database.Genres.AsQueryable()
                .Where(g => g.Slug == genre.Slug)
                .SingleOrDefaultAsync();

            if (dbGenre == default)
            {
                dbGenre = database.Genres.Add(genre).Entity;
                await database.SaveChangesAsync();
            }

            return dbGenre.Id;
        }

        private async Task<int> GetOrAddStudio(Studio studio)
        {
            var dbStudio = await database.Studios.AsQueryable()
                .Where(s => s.Slug == studio.Slug)
                .SingleOrDefaultAsync();

            if (dbStudio == default)
            {
                dbStudio = database.Studios.Add(studio).Entity;
                await database.SaveChangesAsync();
            }

            return dbStudio.Id;
        }
    }
}
