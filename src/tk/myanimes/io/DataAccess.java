package tk.myanimes.io;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;

import java.io.File;
import java.sql.SQLException;

public class DataAccess {

    private static final DataAccess instance = new DataAccess();
    private final String WEBAPP_NAME = "myanimes";
    private final DataContext context = DataContext.forWebapp(WEBAPP_NAME);
    private ConnectionSource connectionSource;

    public static DataAccess instance() {
        return instance;
    }

    private void openConnection() throws SQLException {
        if (connectionSource == null) {
            File dbFile = context.getFile("charakteristikum.db");
            String databaseUrl = String.format("jdbc:sqlite:%s", dbFile.getAbsolutePath());
            connectionSource = new JdbcConnectionSource(databaseUrl);

        }
    }


}
