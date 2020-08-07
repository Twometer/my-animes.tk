package tk.myanimes.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static final AppConfig instance = new AppConfig();

    private static final String DEFAULT_BASE_URL = "";
    private static final String DEFAULT_BASE_PATH = "/myanimes";

    private final String baseUrl;

    private final String basePath;

    public AppConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(DataAccess.instance().getContext().getFile("myanimes.properties")));
        } catch (IOException e) {
            basePath = DEFAULT_BASE_PATH;
            baseUrl = DEFAULT_BASE_URL;
            return;
        }
        var basePath = properties.getProperty("basePath", DEFAULT_BASE_PATH);
        this.basePath = basePath.equals("/") ? "" : basePath;
        this.baseUrl = properties.getProperty("baseUrl", DEFAULT_BASE_URL);
    }

    public static AppConfig instance() {
        return instance;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public String getBasePath() {
        return basePath;
    }
}
