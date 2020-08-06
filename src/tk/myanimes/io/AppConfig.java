package tk.myanimes.io;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class AppConfig {

    private static final AppConfig instance = new AppConfig();
    private static final String DEFAULT_ROOT_PATH = "/myanimes";

    private final String rootPath;

    public AppConfig() {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(DataAccess.instance().getContext().getFile("myanimes.properties")));
        } catch (IOException e) {
            rootPath = DEFAULT_ROOT_PATH;
            return;
        }
        rootPath = properties.getProperty("rootPath", DEFAULT_ROOT_PATH);
    }

    public static AppConfig instance() {
        return instance;
    }

    public String getRootPath() {
        return rootPath;
    }
}
