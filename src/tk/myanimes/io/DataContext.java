package tk.myanimes.io;

import tk.myanimes.text.Sanitizer;

import java.io.File;
import java.util.logging.Logger;

public class DataContext {

    private static final Logger log = Logger.getLogger(DataContext.class.getName());

    private final String basePath;

    private DataContext(String basePath) {
        this.basePath = basePath;
    }

    public static DataContext forWebapp(String webappName) {
        File dataFolder = new File(System.getProperty("catalina.base"), "webdata");
        if (!dataFolder.exists())
            if (!dataFolder.mkdir())
                log.severe("Failed to create data folder!");

        File appFolder = new File(dataFolder, Sanitizer.sanitizePath(webappName));
        if (!appFolder.exists())
            if (!appFolder.mkdir())
                log.severe("Failed to create application folder!");

        return new DataContext(appFolder.getAbsolutePath());
    }

    public File getFile(String name) {
        return new File(basePath, Sanitizer.sanitizePath(name));
    }

}
