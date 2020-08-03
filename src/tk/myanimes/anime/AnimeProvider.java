package tk.myanimes.anime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.myanimes.model.AnimeInfo;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AnimeProvider {

    private static final String USER_AGENT = "Twometer/MyAnimes/ApiClient/1.0";

    private static final AnimeProvider instance = new AnimeProvider();

    private final OkHttpClient client = new OkHttpClient();

    public static AnimeProvider instance() {
        return instance;
    }

    public KitsuAnimeInfo getKitsuAnime(long animeId) throws IOException {
        var url = "https://kitsu.io/api/edge/anime/" + animeId;
        var reply = parse(request(url)).getAsJsonObject("data");
        return parseKitsuAnimeInfo(reply);
    }

    public List<KitsuAnimeInfo> searchAnime(String query) throws IOException {
        var baseUrl = "https://kitsu.io/api/edge/anime?filter[text]=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        var reply = parse(request(baseUrl)).getAsJsonArray("data");
        var results = new ArrayList<KitsuAnimeInfo>();
        for (var item : reply) {
            results.add(parseKitsuAnimeInfo(item));
        }
        return results;
    }

    private KitsuAnimeInfo parseKitsuAnimeInfo(JsonElement dataObject) {
        var obj = dataObject.getAsJsonObject();
        var attrs = obj.getAsJsonObject("attributes");

        var anime = new AnimeInfo();
        anime.setSlug(attrs.get("slug").getAsString());
        anime.setCanonicalTitle(attrs.get("canonicalTitle").getAsString());
        anime.setTitles(new HashMap<>());
        for (var title : attrs.getAsJsonObject("titles").entrySet()) {
            anime.getTitles().put(title.getKey(), title.getValue().getAsString());
        }
        anime.setCategories(new ArrayList<>());
        anime.setSynopsis(attrs.get("synopsis").getAsString());
        anime.setAnimeStudios(new ArrayList<>());
        anime.setCoverPicture(attrs.getAsJsonObject("posterImage").get("tiny").getAsString());

        anime.setAgeRating(getNullableString(attrs, "ageRatingGuide"));
        anime.setEpisodeCount(getNullableInt(attrs, "episodeCount"));
        anime.setEpisodeLength(getNullableInt(attrs, "episodeLength"));
        anime.setTotalLength(attrs.get("totalLength").getAsInt());

        return new KitsuAnimeInfo(obj.get("id").getAsString(), anime);
    }

    public String getCompanyName(long companyId) throws IOException {
        var companyUrl = String.format("https://kitsu.io/api/edge/media-productions/%d/company", companyId);
        var reply = parse(request(companyUrl)).getAsJsonObject("data");
        return reply.getAsJsonObject("attributes").get("name").getAsString();
    }

    public AnimeInfo getFullInfo(KitsuAnimeInfo kitsu) throws IOException, SQLException {
        var categoryUrl = String.format("https://kitsu.io/api/edge/anime/%s/genres", kitsu.getRemoteIdentifier());
        var categoryReply = parse(request(categoryUrl)).getAsJsonArray("data");

        for (var item : categoryReply) {
            var categoryName = item.getAsJsonObject().getAsJsonObject("attributes").get("name").getAsString();
            kitsu.getAnimeInfo().getCategories().add(categoryName);
        }

        var productionsUrl = String.format("https://kitsu.io/api/edge/anime/%s/productions", kitsu.getRemoteIdentifier());
        var productionsReply = parse(request(productionsUrl)).getAsJsonArray("data");

        for (var item : productionsReply) {
            var obj = item.getAsJsonObject();
            var attrs = obj.getAsJsonObject("attributes");
            if (attrs.get("role").getAsString().equals("studio")) {
                var companyId = obj.get("id").getAsLong();
                kitsu.getAnimeInfo().getAnimeStudios().add(AnimeCache.instance().tryGetCompany(companyId));
            }
        }

        return kitsu.getAnimeInfo();
    }

    private String request(String url) throws IOException {
        var request = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    private JsonObject parse(String json) {
        return JsonParser.parseString(json).getAsJsonObject();
    }

    private String getNullableString(JsonObject object, String name) {
        var elem = object.get(name);
        if (elem.isJsonNull())
            return "";
        else return elem.getAsString();
    }

    private int getNullableInt(JsonObject object, String name) {
        var elem = object.get(name);
        if (elem.isJsonNull())
            return 0;
        else return elem.getAsInt();
    }
}
