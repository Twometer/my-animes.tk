package tk.myanimes.model;

import java.util.Collection;
import java.util.Map;

public class AnimeInfo {

    private long id;

    private String canonicalTitle;

    private Map<String, String> titles;

    private Collection<String> categories;

    private String synopsis;

    private Collection<String> animeStudios;

    private String coverPicture;

    private String ageRating;

    private int episodeCount;

    private int episodeLength;

    private int totalLength;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Collection<String> getCategories() {
        return categories;
    }

    public void setCategories(Collection<String> categories) {
        this.categories = categories;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
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

    public int getTotalLength() {
        return totalLength;
    }

    public void setTotalLength(int totalLength) {
        this.totalLength = totalLength;
    }

    @Override
    public String toString() {
        return "AnimeInfo{" +
                "id=" + id +
                ", canonicalTitle='" + canonicalTitle + '\'' +
                ", titles=" + titles +
                ", categories=" + categories +
                ", synopsis='" + synopsis + '\'' +
                ", animeStudios=" + animeStudios +
                ", coverPicture='" + coverPicture + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", episodeCount=" + episodeCount +
                ", episodeLength=" + episodeLength +
                ", totalLength=" + totalLength +
                '}';
    }
}
