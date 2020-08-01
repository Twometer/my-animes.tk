package tk.myanimes.api;

import tk.myanimes.model.Anime;

public interface AnimeProvider {

    Anime getForName(String name);

}
