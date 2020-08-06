package tk.myanimes.model;

import java.time.Instant;
import java.util.Collection;
import java.util.Map;

public class AnimeInfo {

    private long id;

    private String slug;

    private String canonicalTitle;

    private Map<String, String> titles;

    private Collection<String> categories;

    private String synopsis;

    private Collection<AnimeStudioInfo> animeStudios;

    private String thumbnail;

    private String coverPicture;

    private String ageRating;

    private boolean nsfw;

    private Instant startDate;

    private Instant endDate;

    private String youtubeVideoId;

    private AnimeStatus status;

    private ShowType showType;

    private int episodeCount;

    private int episodeLength;

    private int totalLength;

    public String getAlternateTitle() {
        if (canonicalTitle.equals(getEnglishTitle())) {
            if (titles.containsKey("en_jp"))
                return titles.get("en_jp");
            return titles.values().iterator().next();
        } else return canonicalTitle;
    }

    public String getJapaneseTitle() {
        if (titles.containsKey("jp"))
            return titles.get("jp");
        for (var entry : titles.entrySet())
            if (entry.getKey().contains("jp"))
                return entry.getValue();
        return getCanonicalTitle();
    }

    public String getEnglishTitle() {
        if (titles.size() == 0)
            return canonicalTitle;
        if (titles.containsKey("en"))
            return titles.get("en");
        if (titles.containsKey("en_us"))
            return titles.get("en_us");
        return canonicalTitle;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
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

    public Collection<AnimeStudioInfo> getAnimeStudios() {
        return animeStudios;
    }

    public void setAnimeStudios(Collection<AnimeStudioInfo> animeStudios) {
        this.animeStudios = animeStudios;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
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

    public boolean isNsfw() {
        return nsfw;
    }

    public void setNsfw(boolean nsfw) {
        this.nsfw = nsfw;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public AnimeStatus getStatus() {
        return status;
    }

    public void setStatus(AnimeStatus status) {
        this.status = status;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
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
                ", slug='" + slug + '\'' +
                ", canonicalTitle='" + canonicalTitle + '\'' +
                ", titles=" + titles +
                ", categories=" + categories +
                ", synopsis='" + synopsis + '\'' +
                ", animeStudios=" + animeStudios +
                ", thumbnail='" + thumbnail + '\'' +
                ", coverPicture='" + coverPicture + '\'' +
                ", ageRating='" + ageRating + '\'' +
                ", nsfw=" + nsfw +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", youtubeVideoId='" + youtubeVideoId + '\'' +
                ", status=" + status +
                ", showType=" + showType +
                ", episodeCount=" + episodeCount +
                ", episodeLength=" + episodeLength +
                ", totalLength=" + totalLength +
                ", englishTitle='" + getEnglishTitle() + '\'' +
                '}';
    }
}
