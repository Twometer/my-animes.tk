using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using myanimes.Database;
using myanimes.Extensions;
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
            var animes = database.Animes.AsQueryable().Where(a => a.Slug == slug);
            if (await animes.CountAsync() == 0)
            {
                logger.LogDebug("Cache miss while querying anime '{0}'", slug);
                var webAnime = await kitsu.GetAnime(slug);
                var dbAnime = await webAnime.ToAnimeDbo(database);
                await database.Animes.AddAsync(dbAnime);
                return webAnime;
            }
            else
            {
                logger.LogDebug("Cache hit while querying anime '{0}'", slug);
                var dbAnime = await animes.SingleAsync();
                return await dbAnime.ToAnime(database);
            }
        }
    }
}
