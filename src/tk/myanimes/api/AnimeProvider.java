package tk.myanimes.api;

import tk.myanimes.model.AnimeInfo;

public interface AnimeProvider {

    AnimeInfo getForName(String name);

}
