package tk.myanimes.io;

import com.j256.ormlite.dao.Dao;
import tk.myanimes.db.*;
import tk.myanimes.model.*;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {

    public static void storeUserInfo(UserInfo userInfo) throws SQLException {
        var dbUser = new DbUser();
        dbUser.setId(userInfo.getId());
        dbUser.setName(userInfo.getName());
        dbUser.setLocation(userInfo.getLocation());
        dbUser.setBiography(userInfo.getBiography());
        dbUser.setProfilePicture(userInfo.getProfilePicture());
        dbUser.setFavoriteAnimeId(userInfo.getFavoriteAnime() == null ? 0 : userInfo.getFavoriteAnime().getId());
        dbUser.setPasswordHash(userInfo.getPasswordHash());
        dbUser.setSetupComplete(userInfo.isSetupComplete());
        DataAccess.instance().getUserDao().createOrUpdate(dbUser);
        userInfo.setId(dbUser.getId());
    }

    public static UserInfo findUserInfo(String username) throws SQLException {
        var users = DataAccess.instance().getUserDao().queryForEq("name", username);
        if (users.size() != 1)
            return null;
        return convertUser(users.get(0));
    }

    public static UserInfo getUserInfo(long userId) throws SQLException {
        var user = DataAccess.instance().getUserDao().queryForId(userId);
        if (user == null)
            return null;
        return convertUser(user);
    }

    private static UserInfo convertUser(DbUser user) throws SQLException {
        var newUser = new UserInfo();
        newUser.setId(user.getId());
        newUser.setName(user.getName());
        newUser.setLocation(user.getLocation());
        newUser.setBiography(user.getBiography());
        newUser.setProfilePicture(user.getProfilePicture());
        newUser.setFavoriteAnime(getAnimeInfo(user.getFavoriteAnimeId()));
        newUser.setPasswordHash(user.getPasswordHash());
        newUser.setSetupComplete(user.isSetupComplete());
        return newUser;
    }

    public static void addToAnimeList(UserInfo userInfo, AnimeInfo animeInfo, float score, Instant watchDate, WatchState watchState) throws SQLException {
        var dbAnimeListItem = new DbAnimeListItem();
        dbAnimeListItem.setAnimeId(animeInfo.getId());
        dbAnimeListItem.setScore(score);
        dbAnimeListItem.setWatchState(watchState);
        dbAnimeListItem.setWatchDate(watchDate.toEpochMilli());
        dbAnimeListItem.setUserId(userInfo.getId());
        DataAccess.instance().getAnimeListItemDao().create(dbAnimeListItem);
    }

    public static boolean animeListContains(UserInfo userInfo, long id) throws SQLException {
        return !DataAccess.instance().getAnimeListItemDao().queryForEq("animeId", id).isEmpty();
    }

    public static void removeFromAnimeList(UserInfo userInfo, long animeId) throws SQLException {
        var builder = DataAccess.instance().getAnimeListItemDao().deleteBuilder();
        builder.where().eq("animeId", animeId);
        builder.delete();
    }

    public static AnimeList getAnimeList(UserInfo userInfo) throws SQLException {
        var animeList = new AnimeList();

        var queryBuilder = DataAccess.instance().getAnimeListItemDao().queryBuilder();
        queryBuilder.where().eq("userId", userInfo.getId());
        queryBuilder.orderBy("watchDate", true);

        for (var dbItem : queryBuilder.query()) {
            var item = new AnimeListItem();
            item.setAnime(getAnimeInfo(dbItem.getAnimeId()));
            item.setWatchState(dbItem.getWatchState());
            item.setWatchDate(Instant.ofEpochMilli(dbItem.getWatchDate()));
            item.setScore(dbItem.getScore());
            animeList.add(item);
        }

        return animeList;
    }

    private static AnimeInfo getAnimeInfo(long animeId) throws SQLException {
        var dbAnime = DataAccess.instance().getAnimeInfoDao().queryForId(animeId);
        return convertAnime(dbAnime);
    }

    public static AnimeInfo convertAnime(DbAnimeInfo dbAnime) throws SQLException {
        if (dbAnime == null)
            return null;

        var anime = new AnimeInfo();
        anime.setId(dbAnime.getId());
        anime.setSlug(dbAnime.getSlug());
        anime.setCanonicalTitle(dbAnime.getCanonicalTitle());
        anime.setTitles(new HashMap<>());
        anime.setCategories(new ArrayList<>());
        anime.setSynopsis(dbAnime.getSynopsis());
        anime.setAnimeStudios(new ArrayList<>());
        anime.setCoverPicture(dbAnime.getCoverPicture());
        anime.setAgeRating(dbAnime.getAgeRating());
        anime.setEpisodeCount(dbAnime.getEpisodeCount());
        anime.setEpisodeLength(dbAnime.getEpisodeLength());
        anime.setTotalLength(dbAnime.getTotalLength());

        for (var title : DataAccess.instance().getAnimeTitleDao().queryForEq("animeId", anime.getId()))
            anime.getTitles().put(title.getLanguage(), title.getTitle());

        for (var category : DataAccess.instance().getAnimeCategoryDao().queryForEq("animeId", anime.getId()))
            anime.getCategories().add(category.getCategoryName());

        for (var studio : DataAccess.instance().getAnimeStudioDao().queryForEq("animeId", anime.getId())) {
            var company = DataAccess.instance().getAnimeCompanyDao().queryForId(studio.getCompanyId());
            anime.getAnimeStudios().add(company.getName().replace("Pictures", "").trim());
        }

        return anime;
    }

    public static void storeAnimeInfo(AnimeInfo anime) throws SQLException {
        var dbAnime = new DbAnimeInfo();
        dbAnime.setId(anime.getId());
        dbAnime.setSlug(anime.getSlug());
        dbAnime.setCanonicalTitle(anime.getCanonicalTitle());
        dbAnime.setSynopsis(anime.getSynopsis());
        dbAnime.setCoverPicture(anime.getCoverPicture());
        dbAnime.setAgeRating(anime.getAgeRating());
        dbAnime.setEpisodeCount(anime.getEpisodeCount());
        dbAnime.setEpisodeLength(anime.getEpisodeLength());
        dbAnime.setTotalLength(anime.getTotalLength());
        DataAccess.instance().getAnimeInfoDao().createOrUpdate(dbAnime);
        anime.setId(dbAnime.getId());

        deleteByAnimeId(DataAccess.instance().getAnimeTitleDao(), dbAnime.getId());
        for (var title : anime.getTitles().entrySet()) {
            var dbTitle = new DbAnimeTitle();
            dbTitle.setAnimeId(dbAnime.getId());
            dbTitle.setLanguage(title.getKey());
            dbTitle.setTitle(title.getValue());
            DataAccess.instance().getAnimeTitleDao().create(dbTitle);
        }

        deleteByAnimeId(DataAccess.instance().getAnimeCategoryDao(), dbAnime.getId());
        for (var cat : anime.getCategories()) {
            var dbCategory = new DbAnimeCategory();
            dbCategory.setAnimeId(dbAnime.getId());
            dbCategory.setCategoryName(cat);
            DataAccess.instance().getAnimeCategoryDao().create(dbCategory);
        }

        deleteByAnimeId(DataAccess.instance().getAnimeStudioDao(), dbAnime.getId());
        for (var studio : anime.getAnimeStudios()) {
            var dbStudio = new DbAnimeStudio();
            var dbCompany = DataAccess.instance().getAnimeCompanyDao().queryForEq("name", studio).get(0);
            dbStudio.setAnimeId(dbAnime.getId());
            dbStudio.setCompanyId(dbCompany.getId());
            DataAccess.instance().getAnimeStudioDao().create(dbStudio);
        }
    }

    private static <T, ID> void deleteByAnimeId(Dao<T, ID> dao, long animeId) throws SQLException {
        var builder = dao.deleteBuilder();
        builder.where().eq("animeId", animeId);
        builder.delete();
    }

}
