package tk.myanimes.anime;

import tk.myanimes.model.AnimeInfo;

/**
 * A class that holds an anime as described by a single request to the kitsu API
 * It contains the kitsuId (remoteIdentifier) and all info except the categories and the
 * anime studios as those require separate requests to the kitsu API.
 */
public class SearchResult {

    private final String kitsuId;

    private AnimeInfo animeInfo;

    public SearchResult(String kitsuId, AnimeInfo animeInfo) {
        this.kitsuId = kitsuId;
        this.animeInfo = animeInfo;
    }

    public String getKitsuId() {
        return kitsuId;
    }

    public AnimeInfo getAnimeInfo() {
        return animeInfo;
    }

    public void setAnimeInfo(AnimeInfo animeInfo) {
        this.animeInfo = animeInfo;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "kitsuId='" + kitsuId + '\'' +
                ", animeInfo=" + animeInfo +
                '}';
    }
}
