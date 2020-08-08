package tk.myanimes.anime;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.myanimes.model.*;
import tk.myanimes.text.Parser;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Interface to the Kitsu API. Should only be used directly for
 * search results. Other static data should be queried using the Anime Cache
 * to avoid spamming the Kitsu API
 */
public class AnimeProvider {

    private static final String USER_AGENT = "Twometer/MyAnimes/ApiClient/1.0";

    private static final AnimeProvider instance = new AnimeProvider();

    private final OkHttpClient client = new OkHttpClient();

    public static AnimeProvider instance() {
        return instance;
    }

    public List<SearchResult> searchAnime(String query) throws IOException {
        return searchAnimeByTag("text", query);
    }

    SearchResult searchAnimeBySlug(String slug) throws IOException {
        var results = searchAnimeByTag("slug", slug);
        return results.isEmpty() ? null : results.get(0);
    }

    private List<SearchResult> searchAnimeByTag(String tag, String query) throws IOException {
        var baseUrl = String.format("https://kitsu.io/api/edge/anime?filter[%s]=%s", tag, URLEncoder.encode(query, StandardCharsets.UTF_8));
        var reply = parse(request(baseUrl)).getAsJsonArray("data");
        var results = new ArrayList<SearchResult>();
        for (var item : reply)
            results.add(parseKitsuAnimeInfo(item));
        return results;
    }

    SearchResult searchAnimeById(long animeId) throws IOException {
        var url = "https://kitsu.io/api/edge/anime/" + animeId;
        var reply = parse(request(url)).getAsJsonObject("data");
        return parseKitsuAnimeInfo(reply);
    }

    private SearchResult parseKitsuAnimeInfo(JsonElement dataObject) {
        var obj = dataObject.getAsJsonObject();
        var attrs = obj.getAsJsonObject("attributes");

        var anime = new AnimeInfo();
        anime.setSlug(attrs.get("slug").getAsString());
        anime.setCanonicalTitle(attrs.get("canonicalTitle").getAsString());
        anime.setTitles(new HashMap<>());
        for (var title : attrs.getAsJsonObject("titles").entrySet()) {
            if (!title.getValue().isJsonNull())
                anime.getTitles().put(title.getKey(), title.getValue().getAsString());
        }
        anime.setCategories(new ArrayList<>());
        anime.setSynopsis(attrs.get("synopsis").getAsString());
        anime.setAnimeStudios(new ArrayList<>());
        anime.setThumbnail(attrs.getAsJsonObject("posterImage").get("tiny").getAsString());
        anime.setCoverPicture(attrs.getAsJsonObject("posterImage").get("small").getAsString());
        anime.setAgeRating(getNullableString(attrs, "ageRatingGuide"));
        anime.setNsfw(attrs.get("nsfw").getAsBoolean());
        anime.setStartDate(Parser.parseDate(getNullableString(attrs, "startDate")));
        anime.setEndDate(Parser.parseDate(getNullableString(attrs, "endDate")));
        anime.setYoutubeVideoId(getNullableString(attrs, "youtubeVideoId"));
        anime.setStatus(AnimeStatus.parse(getNullableString(attrs, "status")));
        anime.setShowType(ShowType.parse(getNullableString(attrs, "subtype")));
        anime.setEpisodeCount(getNullableInt(attrs, "episodeCount"));
        anime.setEpisodeLength(getNullableInt(attrs, "episodeLength"));
        anime.setTotalLength(attrs.get("totalLength").getAsInt());

        return new SearchResult(obj.get("id").getAsString(), anime);
    }

    String getCompanyName(long companyId) throws IOException {
        var companyUrl = String.format("https://kitsu.io/api/edge/media-productions/%d/company", companyId);
        var reply = parse(request(companyUrl)).getAsJsonObject("data");
        return reply.getAsJsonObject("attributes").get("name").getAsString();
    }

    String getProducerName(long producerId) throws IOException {
        var companyUrl = String.format("https://kitsu.io/api/edge/anime-productions/%d/producer", producerId);
        var reply = parse(request(companyUrl)).getAsJsonObject("data");
        return reply.getAsJsonObject("attributes").get("name").getAsString();
    }

    AnimeInfo getFullInfo(SearchResult kitsu) throws IOException, SQLException {
        var categoryUrl = String.format("https://kitsu.io/api/edge/anime/%s/genres", kitsu.getKitsuId());
        var categoryReply = parse(request(categoryUrl)).getAsJsonArray("data");

        for (var item : categoryReply) {
            var categoryName = item.getAsJsonObject().getAsJsonObject("attributes").get("name").getAsString();
            kitsu.getAnimeInfo().getCategories().add(categoryName);
        }

        parseCompanies(kitsu);
        if (kitsu.getAnimeInfo().getAnimeStudios().size() == 0)
            parseProducers(kitsu);

        return kitsu.getAnimeInfo();
    }

    // Yes, this is twice the same code for the EXACT same data
    // but with different names and ID namespaces. Kitsu did it that way.
    // I have no idea why they did it that way, and I'm too lazy to write code
    // that handles this BS more elegant.
    private void parseProducers(SearchResult kitsu) throws IOException, SQLException {
        var productionsUrl = String.format("https://kitsu.io/api/edge/anime/%s/anime-productions", kitsu.getKitsuId());
        var productionsReply = parse(request(productionsUrl)).getAsJsonArray("data");

        for (var item : productionsReply) {
            var obj = item.getAsJsonObject();
            var attrs = obj.getAsJsonObject("attributes");
            if (attrs.get("role").getAsString().equals("studio")) {
                var producerId = obj.get("id").getAsLong();
                kitsu.getAnimeInfo().getAnimeStudios().add(new AnimeStudioInfo(producerId, AnimeCache.instance().tryGetProducer(producerId), AnimeStudioNamespace.Producer));
            }
        }
    }

    private void parseCompanies(SearchResult kitsu) throws IOException, SQLException {
        var productionsUrl = String.format("https://kitsu.io/api/edge/anime/%s/productions", kitsu.getKitsuId());
        var productionsReply = parse(request(productionsUrl)).getAsJsonArray("data");

        for (var item : productionsReply) {
            var obj = item.getAsJsonObject();
            var attrs = obj.getAsJsonObject("attributes");
            if (attrs.get("role").getAsString().equals("studio")) {
                var companyId = obj.get("id").getAsLong();
                kitsu.getAnimeInfo().getAnimeStudios().add(new AnimeStudioInfo(companyId, AnimeCache.instance().tryGetCompany(companyId), AnimeStudioNamespace.Company));
            }
        }
    }

    private String request(String url) throws IOException {
        var request = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            var body = response.body();
            if (body == null)
                throw new IOException("Request returned a null-body");
            return body.string();
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
