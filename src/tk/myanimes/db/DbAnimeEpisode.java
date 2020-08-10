package tk.myanimes.db;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "anime_episodes")
public class DbAnimeEpisode {

    @DatabaseField(generatedId = true)
    private long id;

    @DatabaseField
    private long animeId;

    @DatabaseField
    private int seasonNumber;

    @DatabaseField
    private int episodeNumber;

    @DatabaseField
    private String canonicalTitle;

    @DatabaseField
    private String synopsis;

    @DatabaseField
    private long airDate;

    @DatabaseField
    private int length;

    @DatabaseField
    private String thumbnail;

    public DbAnimeEpisode(long animeId, int seasonNumber, int episodeNumber, String canonicalTitle, String synopsis, long airDate, int length, String thumbnail) {
        this.animeId = animeId;
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.canonicalTitle = canonicalTitle;
        this.synopsis = synopsis;
        this.airDate = airDate;
        this.length = length;
        this.thumbnail = thumbnail;
    }

    public DbAnimeEpisode() {
    }

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

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public int getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public long getAirDate() {
        return airDate;
    }

    public void setAirDate(long airDate) {
        this.airDate = airDate;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
