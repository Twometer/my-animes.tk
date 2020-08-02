package tk.myanimes.model;

import java.util.ArrayList;

public class AnimeList extends ArrayList<AnimeListItem> {

    public final int getTotalDuration() {
        var duration = 0;
        for (var item : this) {
            duration += item.getAnime().getTotalDuration();
        }
        return duration;
    }

}
