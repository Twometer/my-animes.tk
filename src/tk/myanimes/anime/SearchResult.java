package tk.myanimes.anime;

import tk.myanimes.model.AnimeInfo;

public class SearchResult {

    private String remoteIdentifier;

    private AnimeInfo animeInfo;

    public SearchResult(String remoteIdentifier, AnimeInfo animeInfo) {
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
