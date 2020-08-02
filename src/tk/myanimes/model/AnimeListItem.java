package tk.myanimes.model;

import java.time.Instant;

public class AnimeListItem {

    private AnimeInfo anime;

    private WatchState watchState;

    private Instant watchDate;

    private float score;

    public AnimeInfo getAnime() {
        return anime;
    }

    public void setAnime(AnimeInfo anime) {
        this.anime = anime;
    }

    public WatchState getWatchState() {
        return watchState;
    }

    public void setWatchState(WatchState watchState) {
        this.watchState = watchState;
    }

    public Instant getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(Instant watchDate) {
        this.watchDate = watchDate;
    }

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }
}
