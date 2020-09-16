using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using myanimes.Database;
using myanimes.Database.Entities.Animes;
using myanimes.Database.Entities.Mappings;
using myanimes.Extensions;
using myanimes.Models;
using System.Collections.Generic;
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
            var animes = database.Animes.AsQueryable()
                .Where(a => a.Slug == slug);

            if (await animes.CountAsync() == 0)
            {
                logger.LogDebug("Cache miss while querying anime '{0}'", slug);
                var anime = await kitsu.GetAnime(slug);
                await AnimeToDatabase(anime);
                return anime;
            }
            else
            {
                logger.LogDebug("Cache hit while querying anime '{0}'", slug);
                var dbAnime = await animes.SingleAsync();
                return DatabaseToAnime(dbAnime);
            }
        }

        private Anime DatabaseToAnime(AnimeDbo animeDbo)
        {
            var anime = new Anime();
            anime.CopyBaseProperties(animeDbo);
            anime.Genres = animeDbo.Genres.Select(m => m.Genre);
            anime.Studios = animeDbo.Studios.Select(s => s.Studio);
            return anime;
        }

        private async Task AnimeToDatabase(Anime anime)
        {
            var animeDbo = new AnimeDbo();
            animeDbo.CopyBaseProperties(anime);

            var addResult = await database.Animes.AddAsync(animeDbo);
            var animeId = addResult.Entity.Id;

            foreach (var genre in anime.Genres)
            {
                genre.Id = await AddOrFindGenre(genre);
                var mapping = new GenreMapping { AnimeId = animeId, GenreId = genre.Id };
                await database.GenreMappings.AddAsync(mapping);
            }

            foreach (var studio in anime.Studios)
            {
                studio.Id = await AddOrFindStudio(studio);
                var mapping = new StudioMapping { AnimeId = animeId, StudioId = studio.Id };
                await database.StudioMappings.AddAsync(mapping);
            }

            await database.SaveChangesAsync();
        }

        private async Task<int> AddOrFindGenre(Genre genre)
        {
            var dbGenre = await database.Genres.AsQueryable()
                .Where(g => g.Slug == genre.Slug)
                .SingleOrDefaultAsync();

            if (dbGenre == default)
                dbGenre = (await database.Genres.AddAsync(genre)).Entity;

            await database.SaveChangesAsync();

            return dbGenre.Id;
        }

        private async Task<int> AddOrFindStudio(Studio studio)
        {
            var dbStudio = await database.Studios.AsQueryable()
                .Where(s => s.Slug == studio.Slug)
                .SingleOrDefaultAsync();

            if (dbStudio == default)
                dbStudio = (await database.Studios.AddAsync(studio)).Entity;

            await database.SaveChangesAsync();

            return dbStudio.Id;
        }
    }
}
