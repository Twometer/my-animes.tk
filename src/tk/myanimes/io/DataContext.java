package tk.myanimes.io;

import tk.myanimes.text.Sanitizer;

import java.io.File;

public class DataContext {

    private final String basePath;

    private DataContext(String basePath) {
        this.basePath = basePath;
    }

    public static DataContext forWebapp(String webappName) {
        File dataFolder = new File(System.getProperty("catalina.base"), "webdata");
        if (!dataFolder.exists())
            if (!dataFolder.mkdir())
                System.err.println("Failed to create data folder!");
        File appFolder = new File(dataFolder, Sanitizer.sanitizePath(webappName));
        if (!appFolder.exists())
            if (!appFolder.mkdir())
                System.err.println("Failed to create application folder!");

        return new DataContext(appFolder.getAbsolutePath());
    }

    public File getFile(String name) {
        return new File(basePath, Sanitizer.sanitizePath(name));
    }

}
