package tk.myanimes.io;

import tk.myanimes.text.Validator;

public class PathHelper {

    public static String joinPath(String... paths) {
        var builder = new StringBuilder();
        for (int i = 0; i < paths.length; i++) {
            var curPath = paths[i];
            if (Validator.nullOrEmpty(curPath))
                continue;

            var nextPath = i < paths.length - 1 ? paths[i + 1] : "";
            builder.append(curPath);
            if (!Validator.nullOrEmpty(nextPath) && !curPath.endsWith("/") && !nextPath.startsWith("/"))
                builder.append("/");
        }
        return builder.toString();
    }

}
