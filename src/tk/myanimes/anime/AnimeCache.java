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

    public AnimeInfo tryGetFullAnimeInfoBySlug(String slug) throws IOException, SQLException {
        var cacheEntries = DataAccess.instance().getAnimeInfoDao().queryForEq("slug", slug);
        if (cacheEntries.size() == 0) {
            log.info("Cache Miss: Full Anime by slug query: " + slug);

            var baseInfo = AnimeProvider.instance().searchAnimeBySlug(slug);
            if (baseInfo == null) {
                log.info("Cache Error: Anime does not exist: " + slug);
                return null;
            }

            var info = AnimeProvider.instance().getFullInfo(baseInfo);
            Database.storeAnimeInfo(info);
            return info;
        } else {
            log.info("Cache Hit: Found anime for by-slug query: " + slug);
            return Database.convertAnime(cacheEntries.get(0));
        }
    }

    public AnimeInfo tryGetFullAnimeInfo(KitsuAnimeInfo result) throws SQLException, IOException {
        var cacheEntries = DataAccess.instance().getAnimeInfoDao().queryForEq("slug", result.getAnimeInfo().getSlug());
        if (cacheEntries.size() == 0) {
            log.info("Cache Miss: Full Anime query: " + result.getAnimeInfo().getSlug());
            var info = AnimeProvider.instance().getFullInfo(result);
            Database.storeAnimeInfo(info);
            return info;
        } else {
            log.info("Cache Hit: Found anime for " + result.getAnimeInfo().getSlug());
            return Database.convertAnime(cacheEntries.get(0));
        }
    }

    public String tryGetCompany(long companyId) throws SQLException, IOException {
        var company = DataAccess.instance().getAnimeCompanyDao().queryForId(companyId);
        if (company == null) {
            log.info("Cache Miss: Company name query: " + companyId);
            var name = AnimeProvider.instance().getCompanyName(companyId);
            if (name == null) {
                log.info("Cache Error: Company does not exist: " + companyId);
                return null;
            }

            DataAccess.instance().getAnimeCompanyDao().create(new DbAnimeCompany(companyId, name));
            return name;
        } else {
            log.info("Cache Hit: Found company for " + company);
            return company.getName();
        }
    }
}
