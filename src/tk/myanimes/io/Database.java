package tk.myanimes.io;

import tk.myanimes.db.DbUser;
import tk.myanimes.model.AnimeInfo;
import tk.myanimes.model.AnimeList;
import tk.myanimes.model.AnimeListItem;
import tk.myanimes.model.UserInfo;

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

    public static AnimeList getAnimeList(UserInfo userInfo) throws SQLException {
        var animeList = new AnimeList();
        for (var dbItem : DataAccess.instance().getAnimeListItemDao().queryForEq("userId", userInfo.getId())) {
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

        if (dbAnime == null)
            return null;

        var anime = new AnimeInfo();
        anime.setId(dbAnime.getId());
        anime.setCanonicalTitle(dbAnime.getCanonicalTitle());
        anime.setTitles(new HashMap<>());
        anime.setCategories(new ArrayList<>());
        anime.setSynopsis(dbAnime.getSynopsis());
        anime.setAnimeStudios(new ArrayList<>());
        anime.setCoverPicture(dbAnime.getCoverPicture());
        anime.setAgeRating(dbAnime.getAgeRating());
        anime.setEpisodeCount(dbAnime.getEpisodeCount());
        anime.setEpisodeLength(dbAnime.getEpisodeLength());

        for (var title : DataAccess.instance().getAnimeTitleDao().queryForEq("animeId", anime.getId()))
            anime.getTitles().put(title.getLanguage(), title.getTitle());

        for (var category : DataAccess.instance().getAnimeCategoryDao().queryForEq("animeId", anime.getId()))
            anime.getCategories().add(category.getCategoryName());

        for (var studio : DataAccess.instance().getAnimeStudioDao().queryForEq("animeId", anime.getId()))
            anime.getAnimeStudios().add(studio.getStudioName());

        return anime;
    }

}
