package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import tk.myanimes.model.WatchState;

@DatabaseTable(tableName = "anime_list")
public class DbAnimeListItem {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long userId;

    @DatabaseField
    private long animeId;

    @DatabaseField
    private WatchState watchState;

    @DatabaseField
    private long watchDate;

    @DatabaseField
    private float score;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getAnimeId() {
        return animeId;
    }

    public void setAnimeId(long animeId) {
        this.animeId = animeId;
    }

    public WatchState getWatchState() {
        return watchState;
    }

    public void setWatchState(WatchState watchState) {
        this.watchState = watchState;
    }

    public long getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(long watchDate) {
        this.watchDate = watchDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
