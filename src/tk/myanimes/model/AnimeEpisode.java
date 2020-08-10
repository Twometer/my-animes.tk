package tk.myanimes.model;

import java.time.Instant;

public class AnimeEpisode {

    private int seasonNumber;

    private int episodeNumber;

    private String canonicalTitle;

    private String synopsis;

    private Instant airDate;

    private int length;

    private String thumbnail;

    public AnimeEpisode(int seasonNumber, int episodeNumber, String canonicalTitle, String synopsis, Instant airDate, int length, String thumbnail) {
        this.seasonNumber = seasonNumber;
        this.episodeNumber = episodeNumber;
        this.canonicalTitle = canonicalTitle;
        this.synopsis = synopsis;
        this.airDate = airDate;
        this.length = length;
        this.thumbnail = thumbnail;
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

    public Instant getAirDate() {
        return airDate;
    }

    public void setAirDate(Instant airDate) {
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
