package tk.myanimes.text;

public class Sanitizer {

    private static final char[] FORBIDDEN_CHARS = {'\\', '/', '"', ':', '*', '?', '<', '>', '|'};
    private static final char REPLACEMENT_CHAR = '_';

    public static String sanitizePath(String path) {
        StringBuilder result = new StringBuilder();
        char[] input = path.toCharArray();
        for (char chr : input)
            if (isForbidden(chr)) result.append(REPLACEMENT_CHAR);
            else result.append(chr);
        return result.toString();
    }

    private static boolean isForbidden(char chr) {
        for (char forbidden : FORBIDDEN_CHARS)
            if (forbidden == chr)
                return true;
        return false;
    }

}
