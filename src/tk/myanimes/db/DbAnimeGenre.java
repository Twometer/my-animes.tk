package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "anime_genres")
public class DbAnimeGenre {

    @DatabaseField
    private long id;

    @DatabaseField
    private long animeId;

    @DatabaseField
    private String genre;

}
