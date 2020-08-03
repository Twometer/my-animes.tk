package tk.myanimes.anime;

import tk.myanimes.model.AnimeInfo;

/**
 * A class that holds an anime as described by a single request to the kitsu API
 * It contains the kitsuId (remoteIdentifier) and all info except the categories and the
 * anime studios as those require separate requests to the kitsu API.
 */
public class KitsuAnimeInfo {

    private String remoteIdentifier;

    private AnimeInfo animeInfo;

    public KitsuAnimeInfo(String remoteIdentifier, AnimeInfo animeInfo) {
        this.remoteIdentifier = remoteIdentifier;
        this.animeInfo = animeInfo;
    }

    public String getRemoteIdentifier() {
        return remoteIdentifier;
    }

    public void setRemoteIdentifier(String remoteIdentifier) {
        this.remoteIdentifier = remoteIdentifier;
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
                "remoteIdentifier='" + remoteIdentifier + '\'' +
                ", animeInfo=" + animeInfo +
                '}';
    }
}
