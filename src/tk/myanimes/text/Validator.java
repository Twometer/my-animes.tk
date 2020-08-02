package tk.myanimes.text;

public class Validator {


    public static boolean isValidUsername(String username) {
        if (username.length() > 20)
            return false;

        for (var c : username.toCharArray())
            if (!Character.isLetterOrDigit(c) && c != '_' && c != '.')
                return false;

        return true;
    }

}
