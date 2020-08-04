package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import tk.myanimes.model.AnimeStudioNamespace;

@DatabaseTable(tableName = "anime_studios")
public class DbAnimeStudio {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long animeId;

    @DatabaseField
    private long studioId;

    @DatabaseField
    private AnimeStudioNamespace location;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAnimeId() {
        return animeId;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public long getStudioId() {
        return studioId;
    }

    public void setStudioId(long studioId) {
        this.studioId = studioId;
    }

    public AnimeStudioNamespace getLocation() {
        return location;
    }

    public void setLocation(AnimeStudioNamespace location) {
        this.location = location;
    }
}
