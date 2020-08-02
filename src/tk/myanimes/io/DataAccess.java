package tk.myanimes.io;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import tk.myanimes.db.DbUser;

import java.io.File;
import java.sql.SQLException;

public class DataAccess {

    private static final DataAccess instance = new DataAccess();
    private final String WEBAPP_NAME = "myanimes";
    private final DataContext context = DataContext.forWebapp(WEBAPP_NAME);

    private ConnectionSource connectionSource;
    private Dao<DbUser, Long> userDao;

    public static DataAccess instance() {
        return instance;
    }

    private void ensureConnected() throws SQLException {
        if (connectionSource == null) {
            File dbFile = context.getFile("charakteristikum.db");
            String databaseUrl = String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
            connectionSource = new JdbcConnectionSource(databaseUrl);

            TableUtils.createTableIfNotExists(connectionSource, DbUser.class);
        }
    }

    public Dao<DbUser, Long> getUserDao() throws SQLException {
        ensureConnected();
        return userDao;
    }
}
