using myanimes.Database;
using myanimes.Database.Entities.Animes;

namespace myanimes.Services
{
    public class CacheService
    {
        private DatabaseContext databaseContext;

        private KitsuService kitsuService;

        public CacheService(DatabaseContext databaseContext, KitsuService kitsuService)
        {
            this.databaseContext = databaseContext;
            this.kitsuService = kitsuService;
        }

        public Anime GetAnime(string slug)
        {
            return null; // TODO
        }
    }
}
