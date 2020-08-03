package tk.myanimes.anime;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tk.myanimes.db.DbAnimeCompany;
import tk.myanimes.io.DataAccess;
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

    public List<SearchResult> searchAnime(String query) throws IOException {
        var baseUrl = "https://kitsu.io/api/edge/anime?filter[text]=" + URLEncoder.encode(query, StandardCharsets.UTF_8);
        var reply = parse(request(baseUrl)).getAsJsonArray("data");

        var results = new ArrayList<SearchResult>();

        for (var item : reply) {
            var obj = item.getAsJsonObject();
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
            anime.setCoverPicture(attrs.getAsJsonObject("posterImage").get("small").getAsString());

            anime.setAgeRating(getNullableString(attrs, "ageRatingGuide"));
            anime.setEpisodeCount(getNullableInt(attrs, "episodeCount"));
            anime.setEpisodeLength(getNullableInt(attrs, "episodeLength"));
            anime.setTotalLength(attrs.get("totalLength").getAsInt());

            results.add(new SearchResult(obj.get("id").getAsString(), anime));
        }

        return results;
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

    private String getCompanyName(long companyId) throws IOException {
        var companyUrl = String.format("https://kitsu.io/api/edge/media-productions/%d/company", companyId);
        var reply = parse(request(companyUrl)).getAsJsonObject("data");
        return reply.getAsJsonObject("attributes").get("name").getAsString();
    }

    private String tryGetCompanyFromCache(long companyId) throws SQLException, IOException {
        var company = DataAccess.instance().getAnimeCompanyDao().queryForId(companyId);
        if (company == null) {
            var name = getCompanyName(companyId);
            DataAccess.instance().getAnimeCompanyDao().create(new DbAnimeCompany(companyId, name));
            return name;
        } else return company.getName();
    }

    public AnimeInfo getFullInfo(SearchResult result) throws IOException, SQLException {
        var categoryUrl = String.format("https://kitsu.io/api/edge/anime/%s/genres", result.getRemoteIdentifier());
        var categoryReply = parse(request(categoryUrl)).getAsJsonArray("data");

        for (var item : categoryReply) {
            var categoryName = item.getAsJsonObject().getAsJsonObject("attributes").get("name").getAsString();
            result.getAnimeInfo().getCategories().add(categoryName);
        }

        var productionsUrl = String.format("https://kitsu.io/api/edge/anime/%s/productions", result.getRemoteIdentifier());
        var productionsReply = parse(request(productionsUrl)).getAsJsonArray("data");

        for (var item : productionsReply) {
            var obj = item.getAsJsonObject();
            var attrs = obj.getAsJsonObject("attributes");
            if (attrs.get("role").getAsString().equals("studio")) {
                var companyId = obj.get("id").getAsLong();
                result.getAnimeInfo().getAnimeStudios().add(tryGetCompanyFromCache(companyId));
            }
        }

        return result.getAnimeInfo();
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

}
