package tk.myanimes.io;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import tk.myanimes.db.*;

import java.io.File;
import java.sql.SQLException;

public class DataAccess {

    private static final DataAccess instance = new DataAccess();
    private final String WEBAPP_NAME = "myanimes";
    private final DataContext context = DataContext.forWebapp(WEBAPP_NAME);

    private ConnectionSource connectionSource;
    private Dao<DbUser, Long> userDao;
    private Dao<DbAnimeInfo, Long> animeInfoDao;
    private Dao<DbAnimeTitle, Long> animeTitleDao;
    private Dao<DbAnimeCategory, Long> animeCategoryDao;
    private Dao<DbAnimeStudio, Long> animeStudioDao;
    private Dao<DbAnimeListItem, Long> animeListItemDao;

    public static DataAccess instance() {
        return instance;
    }

    private void ensureConnected() throws SQLException {
        if (connectionSource == null) {
            File dbFile = context.getFile("charakteristikum.db");
            String databaseUrl = String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
            connectionSource = new JdbcConnectionSource(databaseUrl);

            TableUtils.createTableIfNotExists(connectionSource, DbUser.class);
            TableUtils.createTableIfNotExists(connectionSource, DbAnimeInfo.class);
            TableUtils.createTableIfNotExists(connectionSource, DbAnimeTitle.class);
            TableUtils.createTableIfNotExists(connectionSource, DbAnimeCategory.class);
            TableUtils.createTableIfNotExists(connectionSource, DbAnimeStudio.class);
            TableUtils.createTableIfNotExists(connectionSource, DbAnimeListItem.class);

            userDao = DaoManager.createDao(connectionSource, DbUser.class);
            animeInfoDao = DaoManager.createDao(connectionSource, DbAnimeInfo.class);
            animeTitleDao = DaoManager.createDao(connectionSource, DbAnimeTitle.class);
            animeCategoryDao = DaoManager.createDao(connectionSource, DbAnimeCategory.class);
            animeStudioDao = DaoManager.createDao(connectionSource, DbAnimeStudio.class);
            animeListItemDao = DaoManager.createDao(connectionSource, DbAnimeListItem.class);
        }
    }

    public Dao<DbUser, Long> getUserDao() throws SQLException {
        ensureConnected();
        return userDao;
    }

    public Dao<DbAnimeInfo, Long> getAnimeInfoDao() throws SQLException {
        ensureConnected();
        return animeInfoDao;
    }

    public Dao<DbAnimeTitle, Long> getAnimeTitleDao() throws SQLException {
        ensureConnected();
        return animeTitleDao;
    }

    public Dao<DbAnimeCategory, Long> getAnimeCategoryDao() throws SQLException {
        ensureConnected();
        return animeCategoryDao;
    }

    public Dao<DbAnimeStudio, Long> getAnimeStudioDao() throws SQLException {
        ensureConnected();
        return animeStudioDao;
    }

    public Dao<DbAnimeListItem, Long> getAnimeListItemDao() throws SQLException {
        ensureConnected();
        return animeListItemDao;
    }
}
