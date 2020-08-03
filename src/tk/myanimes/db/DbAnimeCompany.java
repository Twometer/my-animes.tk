package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "anime_companies")
public class DbAnimeCompany {

    @DatabaseField(id = true)
    private long id;

    @DatabaseField
    private String name;

    public DbAnimeCompany() {
    }

    public DbAnimeCompany(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
