package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "anime_studios")
public class DbAnimeStudio {

    @DatabaseField
    private long id;

    @DatabaseField
    private long animeId;

    @DatabaseField
    private String studioName;

}
