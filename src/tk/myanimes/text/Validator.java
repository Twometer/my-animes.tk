package tk.myanimes.text;

public class Validator {

    public static boolean nullOrEmpty(String... strings) {
        for (var str : strings)
            if (str == null || str.isBlank())
                return true;
        return false;
    }

    public static boolean isValidUsername(String username) {
        if (username.length() == 0)
            return false;

        if (username.length() > 20)
            return false;

        for (var c : username.toCharArray())
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '.')
                return false;

        return true;
    }

}
