package tk.myanimes.anime;

import tk.myanimes.db.DbAnimeCompany;
import tk.myanimes.io.DataAccess;
import tk.myanimes.io.Database;
import tk.myanimes.model.AnimeInfo;

import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

public class AnimeCache {

    private static final Logger log = Logger.getLogger(AnimeCache.class.getName());
    private static final AnimeCache instance = new AnimeCache();

    public static AnimeCache instance() {
        return instance;
    }

    public AnimeInfo tryGetFullAnimeInfo(SearchResult result) throws SQLException, IOException {
        var cacheEntries = DataAccess.instance().getAnimeInfoDao().queryForEq("slug", result.getAnimeInfo().getSlug());
        if (cacheEntries.size() == 0) {
            log.info("Cache Miss: Full Anime query: " + result.getAnimeInfo().getSlug());
            var info = AnimeProvider.instance().getFullInfo(result);
            Database.storeAnimeInfo(info);
            return info;
        } else {
            return Database.convertAnime(cacheEntries.get(0));
        }
    }

    public String tryGetCompany(long companyId) throws SQLException, IOException {
        var company = DataAccess.instance().getAnimeCompanyDao().queryForId(companyId);
        if (company == null) {
            log.info("Cache Miss: Company name query: " + companyId);
            var name = AnimeProvider.instance().getCompanyName(companyId);
            DataAccess.instance().getAnimeCompanyDao().create(new DbAnimeCompany(companyId, name));
            return name;
        } else return company.getName();
    }
}
