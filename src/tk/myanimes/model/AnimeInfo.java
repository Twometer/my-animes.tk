package tk.myanimes.model;

import java.util.List;
import java.util.Map;

public class AnimeInfo {

    private String identifier;

    private String canonicalTitle;

    private Map<String, String> titles;

    private List<String> genres;

    private String synopsis;

    private String animeStudio;

    private String coverPicture;

    private String ageRating;

    private int episodeCount;

    private int episodeLength;

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCanonicalTitle() {
        return canonicalTitle;
    }

    public void setCanonicalTitle(String canonicalTitle) {
        this.canonicalTitle = canonicalTitle;
    }

    public Map<String, String> getTitles() {
        return titles;
    }

    public void setTitles(Map<String, String> titles) {
        this.titles = titles;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getAnimeStudio() {
        return animeStudio;
    }

    public void setAnimeStudio(String animeStudio) {
        this.animeStudio = animeStudio;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    public String getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(String ageRating) {
        this.ageRating = ageRating;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(int episodeCount) {
        this.episodeCount = episodeCount;
    }

    public int getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(int episodeLength) {
        this.episodeLength = episodeLength;
    }

    public int getTotalDuration() {
        return episodeCount * episodeLength;
    }
}
