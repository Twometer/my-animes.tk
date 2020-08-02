package tk.myanimes.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class AnimeInfo {

    private String identifier;

    private String canonicalTitle;

    private Map<String, String> titles;

    private Collection<String> genres;

    private String synopsis;

    private Collection<String> animeStudios;

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

    public Collection<String> getGenres() {
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

    public void setGenres(Collection<String> genres) {
        this.genres = genres;
    }

    public Collection<String> getAnimeStudios() {
        return animeStudios;
    }

    public void setAnimeStudios(Collection<String> animeStudios) {
        this.animeStudios = animeStudios;
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
